package com.gmail.heagoo.a.c

import com.android.apksig.SigningCertificateLineage
import com.android.apksig.internal.util.X509CertificateUtils
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.security.InvalidKeyException
import java.security.Key
import java.security.KeyFactory
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.PrivateKey
import java.security.Provider
import java.security.UnrecoverableKeyException
import java.security.cert.Certificate
import java.security.cert.X509Certificate
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.util.ArrayList
import java.util.Collection
import java.util.Enumeration
import java.util.Iterator
import java.util.List
import javax.crypto.EncryptedPrivateKeyInfo
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class SignerParams {
    private String certFile
    private List<X509Certificate> certs
    private String keyFile
    private String keyPasswordSpec
    private String keystoreFile
    private String keystoreKeyAlias
    private String keystorePasswordSpec
    private String keystoreProviderArg
    private String keystoreProviderClass
    private String keystoreProviderName
    private String keystoreType
    private String name
    private Charset passwordCharset
    private PrivateKey privateKey
    private final SigningCertificateLineage.SignerCapabilities.Builder signerCapabilitiesBuilder = new SigningCertificateLineage.SignerCapabilities.Builder()
    private String v1SigFileBasename

    private fun decryptPkcs8EncodedKey(EncryptedPrivateKeyInfo encryptedPrivateKeyInfo, List<Array<Char>> list) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(encryptedPrivateKeyInfo.getAlgName())
        InvalidKeySpecException invalidKeySpecException = null
        InvalidKeyException invalidKeyException = null
        Iterator<Array<Char>> it = list.iterator()
        while (it.hasNext()) {
            try {
                return encryptedPrivateKeyInfo.getKeySpec(secretKeyFactory.generateSecret(PBEKeySpec(it.next())))
            } catch (InvalidKeyException e) {
                invalidKeyException = e
            } catch (InvalidKeySpecException e2) {
                invalidKeySpecException = e2
            }
        }
        if (invalidKeyException == null && invalidKeySpecException == null) {
            throw RuntimeException("No passwords")
        }
        if (invalidKeyException != null) {
            throw invalidKeyException
        }
        throw invalidKeySpecException
    }

    private fun drain(InputStream inputStream, OutputStream outputStream) throws IOException {
        Array<Byte> bArr = new Byte[65536]
        while (true) {
            Int i = inputStream.read(bArr)
            if (i == -1) {
                return
            } else {
                outputStream.write(bArr, 0, i)
            }
        }
    }

    private fun getKeyStoreKey(KeyStore keyStore, String str, List<Array<Char>> list) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        UnrecoverableKeyException unrecoverableKeyException = null
        Iterator<Array<Char>> it = list.iterator()
        while (it.hasNext()) {
            try {
                return keyStore.getKey(str, it.next())
            } catch (UnrecoverableKeyException e) {
                unrecoverableKeyException = e
            }
        }
        if (unrecoverableKeyException == null) {
            throw RuntimeException("No key passwords")
        }
        throw unrecoverableKeyException
    }

    private fun loadKeyStoreFromFile(KeyStore keyStore, String str, List<Array<Char>> list) throws Exception {
        Exception exc = null
        for (Array<Char> cArr : list) {
            try {
                if (str == null) {
                    keyStore.load(null, cArr)
                    return
                }
                FileInputStream fileInputStream = FileInputStream(str)
                try {
                    keyStore.load(fileInputStream, cArr)
                    fileInputStream.close()
                    return
                } finally {
                }
            } catch (Exception e) {
                exc = e
            }
        }
        if (exc != null) {
            throw exc
        }
        throw RuntimeException("No keystore passwords")
    }

    private fun loadPkcs8EncodedPrivateKey(PKCS8EncodedKeySpec pKCS8EncodedKeySpec) throws InvalidKeySpecException, NoSuchAlgorithmException {
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(pKCS8EncodedKeySpec)
        } catch (InvalidKeySpecException e) {
            try {
                return KeyFactory.getInstance("EC").generatePrivate(pKCS8EncodedKeySpec)
            } catch (InvalidKeySpecException e2) {
                try {
                    return KeyFactory.getInstance("DSA").generatePrivate(pKCS8EncodedKeySpec)
                } catch (InvalidKeySpecException e3) {
                    throw InvalidKeySpecException("Not an RSA, EC, or DSA private key")
                }
            }
        }
    }

    private fun loadPrivateKeyAndCertsFromFiles(PasswordRetriever passwordRetriever) throws Exception {
        PKCS8EncodedKeySpec pKCS8EncodedKeySpec
        if (this.keyFile == null) {
            throw ParameterException("Private key file (--key) must be specified")
        }
        if (this.certFile == null) {
            throw ParameterException("Certificate file (--cert) must be specified")
        }
        Array<Byte> fully = readFully(File(this.keyFile))
        try {
            pKCS8EncodedKeySpec = decryptPkcs8EncodedKey(EncryptedPrivateKeyInfo(fully), passwordRetriever.getPasswords(this.keyPasswordSpec != null ? this.keyPasswordSpec : PasswordRetriever.SPEC_STDIN, "Private key password for " + this.name, this.passwordCharset != null ? new Array<Charset>{this.passwordCharset} : new Charset[0]))
        } catch (IOException e) {
            if (this.keyPasswordSpec != null) {
                throw InvalidKeySpecException("Failed to parse encrypted private key blob " + this.keyFile, e)
            }
            pKCS8EncodedKeySpec = PKCS8EncodedKeySpec(fully)
        }
        try {
            this.privateKey = loadPkcs8EncodedPrivateKey(pKCS8EncodedKeySpec)
            FileInputStream fileInputStream = FileInputStream(this.certFile)
            try {
                Collection<? extends Certificate> collectionGenerateCertificates = X509CertificateUtils.generateCertificates(fileInputStream)
                fileInputStream.close()
                ArrayList arrayList = ArrayList(collectionGenerateCertificates.size())
                Iterator<? extends Certificate> it = collectionGenerateCertificates.iterator()
                while (it.hasNext()) {
                    arrayList.add((X509Certificate) it.next())
                }
                this.certs = arrayList
            } finally {
            }
        } catch (InvalidKeySpecException e2) {
            throw InvalidKeySpecException("Failed to load PKCS #8 encoded private key from " + this.keyFile, e2)
        }
    }

    private fun loadPrivateKeyAndCertsFromKeyStore(PasswordRetriever passwordRetriever) throws Exception {
        KeyStore keyStore
        Provider provider
        Key keyStoreKey
        if (this.keystoreFile == null) {
            throw ParameterException("KeyStore (--ks) must be specified")
        }
        String defaultType = this.keystoreType
        if (defaultType == null) {
            defaultType = KeyStore.getDefaultType()
        }
        String str = this.keystoreProviderName
        if (str != null) {
            keyStore = KeyStore.getInstance(defaultType, str)
        } else {
            String str2 = this.keystoreProviderClass
            if (str2 != null) {
                Class<?> cls = Class.forName(str2)
                if (!Provider.class.isAssignableFrom(cls)) {
                    throw ParameterException("Keystore Provider class " + this.keystoreProviderClass + " not subclass of " + Provider.class.getName())
                }
                if (this.keystoreProviderArg != null) {
                    try {
                        provider = (Provider) cls.getConstructor(String.class).newInstance(this.keystoreProviderArg)
                    } catch (NoSuchMethodException e) {
                        provider = (Provider) cls.getMethod("configure", String.class).invoke((Provider) cls.getConstructor(new Class[0]).newInstance(new Object[0]), this.keystoreProviderArg)
                    }
                } else {
                    provider = (Provider) cls.getConstructor(new Class[0]).newInstance(new Object[0])
                }
                keyStore = KeyStore.getInstance(defaultType, provider)
            } else {
                keyStore = KeyStore.getInstance(defaultType)
            }
        }
        String str3 = this.keystorePasswordSpec
        if (str3 == null) {
            str3 = PasswordRetriever.SPEC_STDIN
        }
        Charset charset = this.passwordCharset
        Array<Charset> charsetArr = charset != null ? new Array<Charset>{charset} : new Charset[0]
        List<Array<Char>> passwords = passwordRetriever.getPasswords(str3, "Keystore password for " + this.name, charsetArr)
        loadKeyStoreFromFile(keyStore, "NONE".equals(this.keystoreFile) ? null : this.keystoreFile, passwords)
        String str4 = null
        try {
            if (this.keystoreKeyAlias == null) {
                Enumeration<String> enumerationAliases = keyStore.aliases()
                if (enumerationAliases != null) {
                    while (enumerationAliases.hasMoreElements()) {
                        String strNextElement = enumerationAliases.nextElement()
                        if (keyStore.isKeyEntry(strNextElement)) {
                            if (this.keystoreKeyAlias != null) {
                                throw ParameterException(this.keystoreFile + " contains multiple key entries. --ks-key-alias option must be used to specify which entry to use.")
                            }
                            this.keystoreKeyAlias = strNextElement
                        }
                    }
                }
                if (this.keystoreKeyAlias == null) {
                    throw ParameterException(this.keystoreFile + " does not contain key entries")
                }
            }
            str4 = this.keystoreKeyAlias
            if (!keyStore.isKeyEntry(str4)) {
                throw ParameterException(this.keystoreFile + " entry \"" + str4 + "\" does not contain a key")
            }
            if (this.keyPasswordSpec != null) {
                keyStoreKey = getKeyStoreKey(keyStore, str4, passwordRetriever.getPasswords(this.keyPasswordSpec, "Key \"" + str4 + "\" password for " + this.name, charsetArr))
            } else {
                try {
                    keyStoreKey = getKeyStoreKey(keyStore, str4, passwords)
                } catch (UnrecoverableKeyException e2) {
                    keyStoreKey = getKeyStoreKey(keyStore, str4, passwordRetriever.getPasswords(PasswordRetriever.SPEC_STDIN, "Key \"" + str4 + "\" password for " + this.name, charsetArr))
                }
            }
            if (keyStoreKey == null) {
                throw ParameterException(this.keystoreFile + " entry \"" + str4 + "\" does not contain a key")
            }
            if (!(keyStoreKey is PrivateKey)) {
                throw ParameterException(this.keystoreFile + " entry \"" + str4 + "\" does not contain a private key. It contains a key of algorithm: " + keyStoreKey.getAlgorithm())
            }
            this.privateKey = (PrivateKey) keyStoreKey
            Array<Certificate> certificateChain = keyStore.getCertificateChain(str4)
            if (certificateChain == null || certificateChain.length == 0) {
                throw ParameterException(this.keystoreFile + " entry \"" + str4 + "\" does not contain certificates")
            }
            this.certs = ArrayList(certificateChain.length)
            for (Certificate certificate : certificateChain) {
                this.certs.add((X509Certificate) certificate)
            }
        } catch (UnrecoverableKeyException e3) {
            throw IOException("Failed to obtain key with alias \"" + str4 + "\" from " + this.keystoreFile + ". Wrong password?", e3)
        }
    }

    private static Array<Byte> readFully(File file) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream()
        FileInputStream fileInputStream = FileInputStream(file)
        try {
            drain(fileInputStream, byteArrayOutputStream)
            fileInputStream.close()
            return byteArrayOutputStream.toByteArray()
        } catch (Throwable th) {
            try {
                throw th
            } catch (Throwable th2) {
                try {
                    fileInputStream.close()
                } catch (Throwable th3) {
                    th.addSuppressed(th3)
                }
                throw th2
            }
        }
    }

    public List<X509Certificate> getCerts() {
        return this.certs
    }

    fun getKeyFile() {
        return this.keyFile
    }

    fun getKeystoreKeyAlias() {
        return this.keystoreKeyAlias
    }

    fun getName() {
        return this.name
    }

    fun getPrivateKey() {
        return this.privateKey
    }

    public SigningCertificateLineage.SignerCapabilities.Builder getSignerCapabilitiesBuilder() {
        return this.signerCapabilitiesBuilder
    }

    fun getV1SigFileBasename() {
        return this.v1SigFileBasename
    }

    Boolean isEmpty() {
        return this.name == null && this.keystoreFile == null && this.keystoreKeyAlias == null && this.keystorePasswordSpec == null && this.keyPasswordSpec == null && this.passwordCharset == null && this.keystoreType == null && this.keystoreProviderName == null && this.keystoreProviderClass == null && this.keystoreProviderArg == null && this.keyFile == null && this.certFile == null && this.v1SigFileBasename == null && this.privateKey == null && this.certs == null
    }

    fun loadPrivateKeyAndCerts(PasswordRetriever passwordRetriever) throws Exception {
        if (this.keystoreFile == null) {
            if (this.keyFile == null) {
                throw ParameterException("KeyStore (--ks) or private fun file(--key) must be specified")
            }
            loadPrivateKeyAndCertsFromFiles(passwordRetriever)
        } else {
            if (this.keyFile != null) {
                throw ParameterException("--ks and --key may not be specified at the same time")
            }
            if (this.certFile != null) {
                throw ParameterException("--ks and --cert may not be specified at the same time")
            }
            loadPrivateKeyAndCertsFromKeyStore(passwordRetriever)
        }
    }

    fun setCertFile(String str) {
        this.certFile = str
    }

    fun setKeyFile(String str) {
        this.keyFile = str
    }

    fun setKeyPasswordSpec(String str) {
        this.keyPasswordSpec = str
    }

    fun setKeystoreFile(String str) {
        this.keystoreFile = str
    }

    fun setKeystoreKeyAlias(String str) {
        this.keystoreKeyAlias = str
    }

    fun setKeystorePasswordSpec(String str) {
        this.keystorePasswordSpec = str
    }

    fun setKeystoreProviderArg(String str) {
        this.keystoreProviderArg = str
    }

    fun setKeystoreProviderClass(String str) {
        this.keystoreProviderClass = str
    }

    fun setKeystoreProviderName(String str) {
        this.keystoreProviderName = str
    }

    fun setKeystoreType(String str) {
        this.keystoreType = str
    }

    fun setName(String str) {
        this.name = str
    }

    fun setPasswordCharset(Charset charset) {
        this.passwordCharset = charset
    }

    fun setV1SigFileBasename(String str) {
        this.v1SigFileBasename = str
    }
}
