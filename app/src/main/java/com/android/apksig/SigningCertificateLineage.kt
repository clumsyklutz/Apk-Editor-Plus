package com.android.apksig

import com.android.apksig.DefaultApkSignerEngine
import com.android.apksig.apk.ApkFormatException
import com.android.apksig.apk.ApkUtils
import com.android.apksig.internal.apk.ApkSigningBlockUtils
import com.android.apksig.internal.apk.SignatureAlgorithm
import com.android.apksig.internal.apk.v3.V3SchemeSigner
import com.android.apksig.internal.apk.v3.V3SigningCertificateLineage
import com.android.apksig.internal.util.ByteBufferUtils
import com.android.apksig.internal.util.Pair
import com.android.apksig.internal.util.RandomAccessFileDataSink
import com.android.apksig.util.DataSink
import com.android.apksig.util.DataSource
import com.android.apksig.util.DataSources
import com.android.apksig.zip.ZipFormatException
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.PrivateKey
import java.security.SignatureException
import java.security.cert.CertificateEncodingException
import java.security.cert.X509Certificate
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.Iterator
import java.util.List

class SigningCertificateLineage {
    public static val MAGIC = 1056913873
    public final Int mMinSdkVersion
    public final List<V3SigningCertificateLineage.SigningCertificateNode> mSigningLineage

    public static class Builder {
        public Int mMinSdkVersion
        public SignerCapabilities mNewCapabilities
        public final SignerConfig mNewSignerConfig
        public SignerCapabilities mOriginalCapabilities
        public final SignerConfig mOriginalSignerConfig

        constructor(SignerConfig signerConfig, SignerConfig signerConfig2) {
            if (signerConfig == null || signerConfig2 == null) {
                throw NullPointerException("Can't pass null SignerConfigs when constructing a new SigningCertificateLineage")
            }
            this.mOriginalSignerConfig = signerConfig
            this.mNewSignerConfig = signerConfig2
        }

        fun build() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateEncodingException {
            if (this.mMinSdkVersion < 28) {
                this.mMinSdkVersion = 28
            }
            if (this.mOriginalCapabilities == null) {
                this.mOriginalCapabilities = new SignerCapabilities.Builder().build()
            }
            if (this.mNewCapabilities == null) {
                this.mNewCapabilities = new SignerCapabilities.Builder().build()
            }
            return SigningCertificateLineage.createSigningLineage(this.mMinSdkVersion, this.mOriginalSignerConfig, this.mOriginalCapabilities, this.mNewSignerConfig, this.mNewCapabilities)
        }

        fun setMinSdkVersion(Int i) {
            this.mMinSdkVersion = i
            return this
        }

        fun setNewCapabilities(SignerCapabilities signerCapabilities) {
            if (signerCapabilities == null) {
                throw NullPointerException("signerCapabilities == null")
            }
            this.mNewCapabilities = signerCapabilities
            return this
        }

        fun setOriginalCapabilities(SignerCapabilities signerCapabilities) {
            if (signerCapabilities == null) {
                throw NullPointerException("signerCapabilities == null")
            }
            this.mOriginalCapabilities = signerCapabilities
            return this
        }
    }

    public static class SignerCapabilities {
        public final Int mCallerConfiguredFlags
        public final Int mFlags

        public static class Builder {
            public Int mCallerConfiguredFlags
            public Int mFlags

            constructor() {
                this.mFlags = SigningCertificateLineage.calculateDefaultFlags()
            }

            constructor(Int i) {
                this.mFlags = i
            }

            fun build() {
                return SignerCapabilities(this.mFlags, this.mCallerConfiguredFlags)
            }

            fun setAuth(Boolean z) {
                this.mCallerConfiguredFlags |= 16
                if (z) {
                    this.mFlags |= 16
                } else {
                    this.mFlags &= -17
                }
                return this
            }

            fun setCallerConfiguredCapabilities(SignerCapabilities signerCapabilities) {
                this.mFlags = (signerCapabilities.mCallerConfiguredFlags & signerCapabilities.mFlags) | (this.mFlags & (signerCapabilities.mCallerConfiguredFlags ^ (-1)))
                return this
            }

            fun setInstalledData(Boolean z) {
                this.mCallerConfiguredFlags |= 1
                if (z) {
                    this.mFlags |= 1
                } else {
                    this.mFlags &= -2
                }
                return this
            }

            fun setPermission(Boolean z) {
                this.mCallerConfiguredFlags |= 4
                if (z) {
                    this.mFlags |= 4
                } else {
                    this.mFlags &= -5
                }
                return this
            }

            fun setRollback(Boolean z) {
                this.mCallerConfiguredFlags |= 8
                if (z) {
                    this.mFlags |= 8
                } else {
                    this.mFlags &= -9
                }
                return this
            }

            fun setSharedUid(Boolean z) {
                this.mCallerConfiguredFlags |= 2
                if (z) {
                    this.mFlags |= 2
                } else {
                    this.mFlags &= -3
                }
                return this
            }
        }

        constructor(Int i, Int i2) {
            this.mFlags = i
            this.mCallerConfiguredFlags = i2
        }

        fun equals(SignerCapabilities signerCapabilities) {
            return this.mFlags == signerCapabilities.mFlags
        }

        public final Int getFlags() {
            return this.mFlags
        }

        fun hasAuth() {
            return (this.mFlags & 16) != 0
        }

        fun hasInstalledData() {
            return (this.mFlags & 1) != 0
        }

        fun hasPermission() {
            return (this.mFlags & 4) != 0
        }

        fun hasRollback() {
            return (this.mFlags & 8) != 0
        }

        fun hasSharedUid() {
            return (this.mFlags & 2) != 0
        }
    }

    public static class SignerConfig {
        public final X509Certificate mCertificate
        public final PrivateKey mPrivateKey

        public static class Builder {
            public final X509Certificate mCertificate
            public final PrivateKey mPrivateKey

            constructor(PrivateKey privateKey, X509Certificate x509Certificate) {
                this.mPrivateKey = privateKey
                this.mCertificate = x509Certificate
            }

            fun build() {
                return SignerConfig(this.mPrivateKey, this.mCertificate)
            }
        }

        constructor(PrivateKey privateKey, X509Certificate x509Certificate) {
            this.mPrivateKey = privateKey
            this.mCertificate = x509Certificate
        }

        fun getCertificate() {
            return this.mCertificate
        }

        fun getPrivateKey() {
            return this.mPrivateKey
        }
    }

    constructor(Int i, List<V3SigningCertificateLineage.SigningCertificateNode> list) {
        this.mMinSdkVersion = i
        this.mSigningLineage = list
    }

    fun calculateDefaultFlags() {
        return 23
    }

    fun calculateMinSdkVersion(List<V3SigningCertificateLineage.SigningCertificateNode> list) {
        Int minSdkVersion
        if (list == null) {
            throw IllegalArgumentException("Can't calculate minimum SDK version of null nodes")
        }
        Int i = 28
        Iterator<V3SigningCertificateLineage.SigningCertificateNode> it = list.iterator()
        while (it.hasNext()) {
            SignatureAlgorithm signatureAlgorithm = it.next().sigAlgorithm
            if (signatureAlgorithm != null && (minSdkVersion = signatureAlgorithm.getMinSdkVersion()) > i) {
                i = minSdkVersion
            }
        }
        return i
    }

    fun consolidateLineages(List<SigningCertificateLineage> list) {
        if (list == null || list.isEmpty()) {
            return null
        }
        Int i = 0
        Int i2 = 0
        for (Int i3 = 0; i3 < list.size(); i3++) {
            Int size = list.get(i3).size()
            if (size > i2) {
                i = i3
                i2 = size
            }
        }
        List<V3SigningCertificateLineage.SigningCertificateNode> list2 = list.get(i).mSigningLineage
        for (Int i4 = 0; i4 < list.size(); i4++) {
            if (i4 != i) {
                List<V3SigningCertificateLineage.SigningCertificateNode> list3 = list.get(i4).mSigningLineage
                if (!list3.equals(list2.subList(0, list3.size()))) {
                    throw IllegalArgumentException("Inconsistent SigningCertificateLineages. Not all lineages are subsets of each other.")
                }
            }
        }
        return list.get(i)
    }

    fun createSigningLineage(Int i, SignerConfig signerConfig, SignerCapabilities signerCapabilities, SignerConfig signerConfig2, SignerCapabilities signerCapabilities2) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateEncodingException {
        return SigningCertificateLineage(i, ArrayList()).spawnFirstDescendant(signerConfig, signerCapabilities).spawnDescendant(signerConfig, signerConfig2, signerCapabilities2)
    }

    fun read(ByteBuffer byteBuffer) throws IOException {
        ApkSigningBlockUtils.checkByteOrderLittleEndian(byteBuffer)
        if (byteBuffer.remaining() < 8) {
            throw IllegalArgumentException("Improper SigningCertificateLineage format: insufficient data for header.")
        }
        if (byteBuffer.getInt() == 1056913873) {
            return read(byteBuffer, byteBuffer.getInt())
        }
        throw IllegalArgumentException("Improper SigningCertificateLineage format: MAGIC header mismatch.")
    }

    fun read(ByteBuffer byteBuffer, Int i) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        if (i != 1) {
            throw IllegalArgumentException("Improper SigningCertificateLineage format: unrecognized version.")
        }
        try {
            List<V3SigningCertificateLineage.SigningCertificateNode> signingCertificateLineage = V3SigningCertificateLineage.readSigningCertificateLineage(ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer))
            return SigningCertificateLineage(calculateMinSdkVersion(signingCertificateLineage), signingCertificateLineage)
        } catch (ApkFormatException e) {
            throw IOException("Unable to read list of signing certificate nodes in SigningCertificateLineage", e)
        }
    }

    fun readFromApkDataSource(DataSource dataSource) throws IOException, ApkFormatException {
        try {
            ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(ApkSigningBlockUtils.findSignature(dataSource, ApkUtils.findZipSections(dataSource), -262969152, new ApkSigningBlockUtils.Result(3)).signatureBlock)
            ArrayList arrayList = ArrayList(1)
            while (lengthPrefixedSlice.hasRemaining()) {
                try {
                    arrayList.add(readFromSignedData(ApkSigningBlockUtils.getLengthPrefixedSlice(ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice))))
                } catch (IllegalArgumentException unused) {
                }
            }
            if (arrayList.isEmpty()) {
                throw IllegalArgumentException("The provided APK does not contain a valid lineage.")
            }
            return arrayList.size() > 1 ? consolidateLineages(arrayList) : (SigningCertificateLineage) arrayList.get(0)
        } catch (ApkSigningBlockUtils.SignatureNotFoundException unused2) {
            throw IllegalArgumentException("The provided APK does not contain a valid V3 signature block.")
        } catch (ZipFormatException e) {
            throw ApkFormatException(e.getMessage())
        }
    }

    fun readFromApkFile(File file) throws IOException, ApkFormatException {
        RandomAccessFile randomAccessFile = RandomAccessFile(file, "r")
        try {
            SigningCertificateLineage fromApkDataSource = readFromApkDataSource(DataSources.asDataSource(randomAccessFile, 0L, randomAccessFile.length()))
            randomAccessFile.close()
            return fromApkDataSource
        } catch (Throwable th) {
            try {
                randomAccessFile.close()
            } catch (Throwable unused) {
            }
            throw th
        }
    }

    fun readFromDataSource(DataSource dataSource) throws IOException {
        if (dataSource == null) {
            throw NullPointerException("dataSource == null")
        }
        ByteBuffer byteBuffer = dataSource.getByteBuffer(0L, (Int) dataSource.size())
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        return read(byteBuffer)
    }

    fun readFromFile(File file) throws IOException {
        if (file != null) {
            return readFromDataSource(DataSources.asDataSource(RandomAccessFile(file, "r")))
        }
        throw NullPointerException("file == null")
    }

    fun readFromSignedData(ByteBuffer byteBuffer) throws IOException, ApkFormatException {
        ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer)
        ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer)
        byteBuffer.getInt()
        byteBuffer.getInt()
        ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer)
        ArrayList arrayList = ArrayList(1)
        while (lengthPrefixedSlice.hasRemaining()) {
            ByteBuffer lengthPrefixedSlice2 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice)
            if (lengthPrefixedSlice2.getInt() == 1000370060) {
                arrayList.add(readFromV3AttributeValue(ByteBufferUtils.toByteArray(lengthPrefixedSlice2)))
            }
        }
        if (arrayList.isEmpty()) {
            throw IllegalArgumentException("The signed data does not contain a valid lineage.")
        }
        return arrayList.size() > 1 ? consolidateLineages(arrayList) : (SigningCertificateLineage) arrayList.get(0)
    }

    fun readFromV3AttributeValue(Array<Byte> bArr) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        List<V3SigningCertificateLineage.SigningCertificateNode> signingCertificateLineage = V3SigningCertificateLineage.readSigningCertificateLineage(ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN))
        return SigningCertificateLineage(calculateMinSdkVersion(signingCertificateLineage), signingCertificateLineage)
    }

    public Array<Byte> encodeSigningCertificateLineage() {
        return V3SigningCertificateLineage.encodeSigningCertificateLineage(this.mSigningLineage)
    }

    public List<X509Certificate> getCertificatesInLineage() {
        ArrayList arrayList = ArrayList()
        for (Int i = 0; i < this.mSigningLineage.size(); i++) {
            arrayList.add(this.mSigningLineage.get(i).signingCert)
        }
        return arrayList
    }

    public final SignatureAlgorithm getSignatureAlgorithm(SignerConfig signerConfig) throws InvalidKeyException {
        return V3SchemeSigner.getSuggestedSignatureAlgorithms(signerConfig.getCertificate().getPublicKey(), this.mMinSdkVersion, false).get(0)
    }

    fun getSignerCapabilities(SignerConfig signerConfig) {
        if (signerConfig != null) {
            return getSignerCapabilities(signerConfig.getCertificate())
        }
        throw NullPointerException("config == null")
    }

    fun getSignerCapabilities(X509Certificate x509Certificate) {
        if (x509Certificate == null) {
            throw NullPointerException("cert == null")
        }
        for (Int i = 0; i < this.mSigningLineage.size(); i++) {
            V3SigningCertificateLineage.SigningCertificateNode signingCertificateNode = this.mSigningLineage.get(i)
            if (signingCertificateNode.signingCert.equals(x509Certificate)) {
                return new SignerCapabilities.Builder(signingCertificateNode.flags).build()
            }
        }
        throw IllegalArgumentException("Certificate (" + x509Certificate.getSubjectDN() + ") not found in the SigningCertificateLineage")
    }

    fun getSubLineage(X509Certificate x509Certificate) {
        if (x509Certificate == null) {
            throw NullPointerException("x509Certificate == null")
        }
        for (Int i = 0; i < this.mSigningLineage.size(); i++) {
            if (this.mSigningLineage.get(i).signingCert.equals(x509Certificate)) {
                return SigningCertificateLineage(this.mMinSdkVersion, ArrayList(this.mSigningLineage.subList(0, i + 1)))
            }
        }
        throw IllegalArgumentException("Certificate not found in SigningCertificateLineage")
    }

    fun isCertificateInLineage(X509Certificate x509Certificate) {
        if (x509Certificate == null) {
            throw NullPointerException("cert == null")
        }
        for (Int i = 0; i < this.mSigningLineage.size(); i++) {
            if (this.mSigningLineage.get(i).signingCert.equals(x509Certificate)) {
                return true
            }
        }
        return false
    }

    fun isSignerInLineage(SignerConfig signerConfig) {
        if (signerConfig != null) {
            return isCertificateInLineage(signerConfig.getCertificate())
        }
        throw NullPointerException("config == null")
    }

    fun size() {
        return this.mSigningLineage.size()
    }

    public List<DefaultApkSignerEngine.SignerConfig> sortSignerConfigs(List<DefaultApkSignerEngine.SignerConfig> list) {
        if (list == null) {
            throw NullPointerException("signerConfigs == null")
        }
        ArrayList arrayList = ArrayList(list.size())
        for (Int i = 0; i < this.mSigningLineage.size(); i++) {
            Int i2 = 0
            while (true) {
                if (i2 < list.size()) {
                    DefaultApkSignerEngine.SignerConfig signerConfig = list.get(i2)
                    if (this.mSigningLineage.get(i).signingCert.equals(signerConfig.getCertificates().get(0))) {
                        arrayList.add(signerConfig)
                        break
                    }
                    i2++
                }
            }
        }
        if (arrayList.size() == list.size()) {
            return arrayList
        }
        throw IllegalArgumentException("SignerConfigs supplied which are not present in the SigningCertificateLineage")
    }

    fun spawnDescendant(SignerConfig signerConfig, SignerConfig signerConfig2) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateEncodingException {
        if (signerConfig == null || signerConfig2 == null) {
            throw NullPointerException("can't add new descendant to lineage with null inputs")
        }
        return spawnDescendant(signerConfig, signerConfig2, new SignerCapabilities.Builder().build())
    }

    fun spawnDescendant(SignerConfig signerConfig, SignerConfig signerConfig2, SignerCapabilities signerCapabilities) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidAlgorithmParameterException, CertificateEncodingException {
        if (signerConfig == null) {
            throw NullPointerException("parent == null")
        }
        if (signerConfig2 == null) {
            throw NullPointerException("child == null")
        }
        if (signerCapabilities == null) {
            throw NullPointerException("childCapabilities == null")
        }
        if (this.mSigningLineage.isEmpty()) {
            throw IllegalArgumentException("Cannot spawn descendant signing certificate on an empty SigningCertificateLineage: no parent node")
        }
        List<V3SigningCertificateLineage.SigningCertificateNode> list = this.mSigningLineage
        V3SigningCertificateLineage.SigningCertificateNode signingCertificateNode = list.get(list.size() - 1)
        if (!Arrays.equals(signingCertificateNode.signingCert.getEncoded(), signerConfig.getCertificate().getEncoded())) {
            throw IllegalArgumentException("SignerConfig Certificate containing private key to sign the new SigningCertificateLineage record does not match the existing most recent record")
        }
        SignatureAlgorithm signatureAlgorithm = getSignatureAlgorithm(signerConfig)
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(V3SigningCertificateLineage.encodeSignedData(signerConfig2.getCertificate(), signatureAlgorithm.getId()))
        byteBufferWrap.position(4)
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(byteBufferWrap.remaining())
        byteBufferAllocate.put(byteBufferWrap)
        Array<Byte> bArrArray = byteBufferAllocate.array()
        ArrayList arrayList = ArrayList(1)
        arrayList.add(signerConfig.getCertificate())
        ApkSigningBlockUtils.SignerConfig signerConfig3 = new ApkSigningBlockUtils.SignerConfig()
        signerConfig3.privateKey = signerConfig.getPrivateKey()
        signerConfig3.certificates = arrayList
        signerConfig3.signatureAlgorithms = Collections.singletonList(signatureAlgorithm)
        List<Pair<Integer, Array<Byte>>> listGenerateSignaturesOverData = ApkSigningBlockUtils.generateSignaturesOverData(signerConfig3, bArrArray)
        SignatureAlgorithm signatureAlgorithmFindById = SignatureAlgorithm.findById(listGenerateSignaturesOverData.get(0).getFirst().intValue())
        Array<Byte> second = listGenerateSignaturesOverData.get(0).getSecond()
        signingCertificateNode.sigAlgorithm = signatureAlgorithmFindById
        V3SigningCertificateLineage.SigningCertificateNode signingCertificateNode2 = new V3SigningCertificateLineage.SigningCertificateNode(signerConfig2.getCertificate(), signatureAlgorithmFindById, null, second, signerCapabilities.getFlags())
        ArrayList arrayList2 = ArrayList(this.mSigningLineage)
        arrayList2.add(signingCertificateNode2)
        return SigningCertificateLineage(this.mMinSdkVersion, arrayList2)
    }

    public final SigningCertificateLineage spawnFirstDescendant(SignerConfig signerConfig, SignerCapabilities signerCapabilities) {
        if (!this.mSigningLineage.isEmpty()) {
            throw IllegalStateException("SigningCertificateLineage already has its first node")
        }
        try {
            getSignatureAlgorithm(signerConfig)
            return SigningCertificateLineage(this.mMinSdkVersion, Collections.singletonList(new V3SigningCertificateLineage.SigningCertificateNode(signerConfig.getCertificate(), null, null, new Byte[0], signerCapabilities.getFlags())))
        } catch (InvalidKeyException e) {
            throw IllegalArgumentException("Algorithm associated with first signing certificate invalid on desired platform versions", e)
        }
    }

    fun updateSignerCapabilities(SignerConfig signerConfig, SignerCapabilities signerCapabilities) {
        if (signerConfig == null) {
            throw NullPointerException("config == null")
        }
        X509Certificate certificate = signerConfig.getCertificate()
        for (Int i = 0; i < this.mSigningLineage.size(); i++) {
            V3SigningCertificateLineage.SigningCertificateNode signingCertificateNode = this.mSigningLineage.get(i)
            if (signingCertificateNode.signingCert.equals(certificate)) {
                signingCertificateNode.flags = new SignerCapabilities.Builder(signingCertificateNode.flags).setCallerConfiguredCapabilities(signerCapabilities).build().getFlags()
                return
            }
        }
        throw IllegalArgumentException("Certificate (" + certificate.getSubjectDN() + ") not found in the SigningCertificateLineage")
    }

    public final ByteBuffer write() {
        Array<Byte> bArrEncodeSigningCertificateLineage = V3SigningCertificateLineage.encodeSigningCertificateLineage(this.mSigningLineage)
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrEncodeSigningCertificateLineage.length + 12)
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        byteBufferAllocate.putInt(MAGIC)
        byteBufferAllocate.putInt(1)
        byteBufferAllocate.putInt(bArrEncodeSigningCertificateLineage.length)
        byteBufferAllocate.put(bArrEncodeSigningCertificateLineage)
        byteBufferAllocate.flip()
        return byteBufferAllocate
    }

    fun writeToDataSink(DataSink dataSink) throws IOException {
        if (dataSink == null) {
            throw NullPointerException("dataSink == null")
        }
        dataSink.consume(write())
    }

    fun writeToFile(File file) throws IOException {
        if (file == null) {
            throw NullPointerException("file == null")
        }
        writeToDataSink(RandomAccessFileDataSink(RandomAccessFile(file, "rw")))
    }
}
