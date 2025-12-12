package com.android.apksig.internal.apk.v3

import com.android.apksig.ApkVerifier
import com.android.apksig.SigningCertificateLineage
import com.android.apksig.apk.ApkFormatException
import com.android.apksig.apk.ApkUtils
import com.android.apksig.internal.apk.ApkSigningBlockUtils
import com.android.apksig.internal.apk.ContentDigestAlgorithm
import com.android.apksig.internal.apk.SignatureAlgorithm
import com.android.apksig.internal.apk.SignatureInfo
import com.android.apksig.internal.util.ByteBufferUtils
import com.android.apksig.internal.util.GuaranteedEncodedFormX509Certificate
import com.android.apksig.internal.util.X509CertificateUtils
import com.android.apksig.util.DataSource
import com.android.apksig.util.RunnablesExecutor
import java.io.IOException
import java.nio.BufferUnderflowException
import java.nio.ByteBuffer
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.Signature
import java.security.SignatureException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.security.spec.AlgorithmParameterSpec
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import java.util.ArrayList
import java.util.Arrays
import java.util.HashSet
import java.util.Iterator
import java.util.Set
import java.util.TreeMap

abstract class V3SchemeVerifier {
    fun parseSigner(ByteBuffer byteBuffer, CertificateFactory certificateFactory, ApkSigningBlockUtils.Result.SignerInfo signerInfo, Set<ContentDigestAlgorithm> set) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, ApkFormatException, InvalidAlgorithmParameterException {
        Array<Byte> encoded
        ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer)
        Array<Byte> bArr = new Byte[lengthPrefixedSlice.remaining()]
        lengthPrefixedSlice.get(bArr)
        lengthPrefixedSlice.flip()
        signerInfo.signedData = bArr
        Int i = byteBuffer.getInt()
        Int i2 = byteBuffer.getInt()
        signerInfo.minSdkVersion = i
        signerInfo.maxSdkVersion = i2
        if (i < 0 || i > i2) {
            signerInfo.addError(ApkVerifier.Issue.V3_SIG_INVALID_SDK_VERSIONS, Integer.valueOf(i), Integer.valueOf(i2))
        }
        ByteBuffer lengthPrefixedSlice2 = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer)
        Array<Byte> lengthPrefixedByteArray = ApkSigningBlockUtils.readLengthPrefixedByteArray(byteBuffer)
        ArrayList arrayList = ArrayList(1)
        Int i3 = 0
        while (lengthPrefixedSlice2.hasRemaining()) {
            i3++
            try {
                ByteBuffer lengthPrefixedSlice3 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice2)
                Int i4 = lengthPrefixedSlice3.getInt()
                Array<Byte> lengthPrefixedByteArray2 = ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice3)
                signerInfo.signatures.add(new ApkSigningBlockUtils.Result.SignerInfo.Signature(i4, lengthPrefixedByteArray2))
                SignatureAlgorithm signatureAlgorithmFindById = SignatureAlgorithm.findById(i4)
                if (signatureAlgorithmFindById == null) {
                    signerInfo.addWarning(ApkVerifier.Issue.V3_SIG_UNKNOWN_SIG_ALGORITHM, Integer.valueOf(i4))
                } else {
                    arrayList.add(new ApkSigningBlockUtils.SupportedSignature(signatureAlgorithmFindById, lengthPrefixedByteArray2))
                }
            } catch (ApkFormatException | BufferUnderflowException unused) {
                signerInfo.addError(ApkVerifier.Issue.V3_SIG_MALFORMED_SIGNATURE, Integer.valueOf(i3))
                return
            }
        }
        if (signerInfo.signatures.isEmpty()) {
            signerInfo.addError(ApkVerifier.Issue.V3_SIG_NO_SIGNATURES, new Object[0])
            return
        }
        try {
            for (ApkSigningBlockUtils.SupportedSignature supportedSignature : ApkSigningBlockUtils.getSignaturesToVerify(arrayList, signerInfo.minSdkVersion, signerInfo.maxSdkVersion)) {
                SignatureAlgorithm signatureAlgorithm = supportedSignature.algorithm
                String first = signatureAlgorithm.getJcaSignatureAlgorithmAndParams().getFirst()
                AlgorithmParameterSpec second = signatureAlgorithm.getJcaSignatureAlgorithmAndParams().getSecond()
                try {
                    PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(signatureAlgorithm.getJcaKeyAlgorithm()).generatePublic(X509EncodedKeySpec(lengthPrefixedByteArray))
                    try {
                        Signature signature = Signature.getInstance(first)
                        signature.initVerify(publicKeyGeneratePublic)
                        if (second != null) {
                            signature.setParameter(second)
                        }
                        lengthPrefixedSlice.position(0)
                        signature.update(lengthPrefixedSlice)
                        Array<Byte> bArr2 = supportedSignature.signature
                        if (!signature.verify(bArr2)) {
                            signerInfo.addError(ApkVerifier.Issue.V3_SIG_DID_NOT_VERIFY, signatureAlgorithm)
                            return
                        } else {
                            signerInfo.verifiedSignatures.put(signatureAlgorithm, bArr2)
                            set.add(signatureAlgorithm.getContentDigestAlgorithm())
                        }
                    } catch (InvalidAlgorithmParameterException | InvalidKeyException | SignatureException e) {
                        signerInfo.addError(ApkVerifier.Issue.V3_SIG_VERIFY_EXCEPTION, signatureAlgorithm, e)
                        return
                    }
                } catch (Exception e2) {
                    signerInfo.addError(ApkVerifier.Issue.V3_SIG_MALFORMED_PUBLIC_KEY, e2)
                    return
                }
            }
            lengthPrefixedSlice.position(0)
            ByteBuffer lengthPrefixedSlice4 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice)
            ByteBuffer lengthPrefixedSlice5 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice)
            Int i5 = lengthPrefixedSlice.getInt()
            if (i5 != i) {
                signerInfo.addError(ApkVerifier.Issue.V3_MIN_SDK_VERSION_MISMATCH_BETWEEN_SIGNER_AND_SIGNED_DATA_RECORD, Integer.valueOf(i), Integer.valueOf(i5))
            }
            Int i6 = lengthPrefixedSlice.getInt()
            if (i6 != i2) {
                signerInfo.addError(ApkVerifier.Issue.V3_MAX_SDK_VERSION_MISMATCH_BETWEEN_SIGNER_AND_SIGNED_DATA_RECORD, Integer.valueOf(i2), Integer.valueOf(i6))
            }
            ByteBuffer lengthPrefixedSlice6 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice)
            Int i7 = -1
            while (lengthPrefixedSlice5.hasRemaining()) {
                Int i8 = i7 + 1
                Array<Byte> lengthPrefixedByteArray3 = ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice5)
                try {
                    signerInfo.certs.add(GuaranteedEncodedFormX509Certificate(X509CertificateUtils.generateCertificate(lengthPrefixedByteArray3, certificateFactory), lengthPrefixedByteArray3))
                    i7 = i8
                } catch (CertificateException e3) {
                    signerInfo.addError(ApkVerifier.Issue.V3_SIG_MALFORMED_CERTIFICATE, Integer.valueOf(i8), Integer.valueOf(i8 + 1), e3)
                    return
                }
            }
            if (signerInfo.certs.isEmpty()) {
                signerInfo.addError(ApkVerifier.Issue.V3_SIG_NO_CERTIFICATES, new Object[0])
                return
            }
            X509Certificate x509Certificate = signerInfo.certs.get(0)
            try {
                encoded = ApkSigningBlockUtils.encodePublicKey(x509Certificate.getPublicKey())
            } catch (InvalidKeyException e4) {
                System.out.println("Caught an exception encoding the public key: " + e4)
                e4.printStackTrace()
                encoded = x509Certificate.getPublicKey().getEncoded()
            }
            if (!Arrays.equals(lengthPrefixedByteArray, encoded)) {
                signerInfo.addError(ApkVerifier.Issue.V3_SIG_PUBLIC_KEY_MISMATCH_BETWEEN_CERTIFICATE_AND_SIGNATURES_RECORD, ApkSigningBlockUtils.toHex(encoded), ApkSigningBlockUtils.toHex(lengthPrefixedByteArray))
                return
            }
            Int i9 = 0
            while (lengthPrefixedSlice4.hasRemaining()) {
                i9++
                try {
                    ByteBuffer lengthPrefixedSlice7 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice4)
                    signerInfo.contentDigests.add(new ApkSigningBlockUtils.Result.SignerInfo.ContentDigest(lengthPrefixedSlice7.getInt(), ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice7)))
                } catch (ApkFormatException | BufferUnderflowException unused2) {
                    signerInfo.addError(ApkVerifier.Issue.V3_SIG_MALFORMED_DIGEST, Integer.valueOf(i9))
                    return
                }
            }
            ArrayList arrayList2 = ArrayList(signerInfo.signatures.size())
            Iterator<ApkSigningBlockUtils.Result.SignerInfo.Signature> it = signerInfo.signatures.iterator()
            while (it.hasNext()) {
                arrayList2.add(Integer.valueOf(it.next().getAlgorithmId()))
            }
            ArrayList arrayList3 = ArrayList(signerInfo.contentDigests.size())
            Iterator<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> it2 = signerInfo.contentDigests.iterator()
            while (it2.hasNext()) {
                arrayList3.add(Integer.valueOf(it2.next().getSignatureAlgorithmId()))
            }
            if (!arrayList2.equals(arrayList3)) {
                signerInfo.addError(ApkVerifier.Issue.V3_SIG_SIG_ALG_MISMATCH_BETWEEN_SIGNATURES_AND_DIGESTS_RECORDS, arrayList2, arrayList3)
                return
            }
            Int i10 = 0
            while (lengthPrefixedSlice6.hasRemaining()) {
                i10++
                try {
                    ByteBuffer lengthPrefixedSlice8 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice6)
                    Int i11 = lengthPrefixedSlice8.getInt()
                    Array<Byte> byteArray = ByteBufferUtils.toByteArray(lengthPrefixedSlice8)
                    signerInfo.additionalAttributes.add(new ApkSigningBlockUtils.Result.SignerInfo.AdditionalAttribute(i11, byteArray))
                    if (i11 == 1000370060) {
                        try {
                            SigningCertificateLineage fromV3AttributeValue = SigningCertificateLineage.readFromV3AttributeValue(byteArray)
                            signerInfo.signingCertificateLineage = fromV3AttributeValue
                            if (signerInfo.signingCertificateLineage.size() != fromV3AttributeValue.getSubLineage(signerInfo.certs.get(0)).size()) {
                                signerInfo.addError(ApkVerifier.Issue.V3_SIG_POR_CERT_MISMATCH, new Object[0])
                            }
                        } catch (IllegalArgumentException unused3) {
                            signerInfo.addError(ApkVerifier.Issue.V3_SIG_POR_CERT_MISMATCH, new Object[0])
                        } catch (SecurityException unused4) {
                            signerInfo.addError(ApkVerifier.Issue.V3_SIG_POR_DID_NOT_VERIFY, new Object[0])
                        } catch (Exception unused5) {
                            signerInfo.addError(ApkVerifier.Issue.V3_SIG_MALFORMED_LINEAGE, new Object[0])
                        }
                    } else {
                        signerInfo.addWarning(ApkVerifier.Issue.V3_SIG_UNKNOWN_ADDITIONAL_ATTRIBUTE, Integer.valueOf(i11))
                    }
                } catch (ApkFormatException | BufferUnderflowException unused6) {
                    signerInfo.addError(ApkVerifier.Issue.V3_SIG_MALFORMED_ADDITIONAL_ATTRIBUTE, Integer.valueOf(i10))
                    return
                }
            }
        } catch (ApkSigningBlockUtils.NoSupportedSignaturesException unused7) {
            signerInfo.addError(ApkVerifier.Issue.V3_SIG_NO_SUPPORTED_SIGNATURES, new Object[0])
        }
    }

    fun parseSigners(ByteBuffer byteBuffer, Set<ContentDigestAlgorithm> set, ApkSigningBlockUtils.Result result) throws NoSuchAlgorithmException {
        try {
            ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer)
            if (!lengthPrefixedSlice.hasRemaining()) {
                result.addError(ApkVerifier.Issue.V3_SIG_NO_SIGNERS, new Object[0])
                return
            }
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509")
                Int i = 0
                while (lengthPrefixedSlice.hasRemaining()) {
                    Int i2 = i + 1
                    ApkSigningBlockUtils.Result.SignerInfo signerInfo = new ApkSigningBlockUtils.Result.SignerInfo()
                    signerInfo.index = i
                    result.signers.add(signerInfo)
                    try {
                        parseSigner(ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice), certificateFactory, signerInfo, set)
                        i = i2
                    } catch (ApkFormatException | BufferUnderflowException unused) {
                        signerInfo.addError(ApkVerifier.Issue.V3_SIG_MALFORMED_SIGNER, new Object[0])
                        return
                    }
                }
            } catch (CertificateException e) {
                throw RuntimeException("Failed to obtain X.509 CertificateFactory", e)
            }
        } catch (ApkFormatException unused2) {
            result.addError(ApkVerifier.Issue.V3_SIG_MALFORMED_SIGNERS, new Object[0])
        }
    }

    public static ApkSigningBlockUtils.Result verify(RunnablesExecutor runnablesExecutor, DataSource dataSource, ApkUtils.ZipSections zipSections, Int i, Int i2) throws ApkSigningBlockUtils.SignatureNotFoundException, NoSuchAlgorithmException, IOException {
        ApkSigningBlockUtils.Result result = new ApkSigningBlockUtils.Result(3)
        SignatureInfo signatureInfoFindSignature = ApkSigningBlockUtils.findSignature(dataSource, zipSections, -262969152, result)
        DataSource dataSourceSlice = dataSource.slice(0L, signatureInfoFindSignature.apkSigningBlockOffset)
        Long j = signatureInfoFindSignature.centralDirOffset
        verify(runnablesExecutor, dataSourceSlice, signatureInfoFindSignature.signatureBlock, dataSource.slice(j, signatureInfoFindSignature.eocdOffset - j), signatureInfoFindSignature.eocd, i < 28 ? 28 : i, i2, result)
        return result
    }

    fun verify(RunnablesExecutor runnablesExecutor, DataSource dataSource, ByteBuffer byteBuffer, DataSource dataSource2, ByteBuffer byteBuffer2, Int i, Int i2, ApkSigningBlockUtils.Result result) throws NoSuchAlgorithmException, IOException {
        HashSet hashSet = HashSet(1)
        parseSigners(byteBuffer, hashSet, result)
        if (result.containsErrors()) {
            return
        }
        ApkSigningBlockUtils.verifyIntegrity(runnablesExecutor, dataSource, dataSource2, byteBuffer2, hashSet, result)
        TreeMap treeMap = TreeMap()
        for (ApkSigningBlockUtils.Result.SignerInfo signerInfo : result.signers) {
            treeMap.put(Integer.valueOf(signerInfo.minSdkVersion), signerInfo)
        }
        ArrayList arrayList = ArrayList(result.signers.size())
        Iterator it = treeMap.values().iterator()
        Int i3 = 0
        Int i4 = 0
        Int i5 = 0
        while (true) {
            if (!it.hasNext()) {
                break
            }
            ApkSigningBlockUtils.Result.SignerInfo signerInfo2 = (ApkSigningBlockUtils.Result.SignerInfo) it.next()
            Int i6 = signerInfo2.minSdkVersion
            Int i7 = signerInfo2.maxSdkVersion
            if (i3 != 0) {
                if (i6 != i4 + 1) {
                    result.addError(ApkVerifier.Issue.V3_INCONSISTENT_SDK_VERSIONS, new Object[0])
                    break
                }
            } else {
                i3 = i6
            }
            SigningCertificateLineage signingCertificateLineage = signerInfo2.signingCertificateLineage
            if (signingCertificateLineage != null) {
                Int size = signingCertificateLineage.size()
                if (size < i5) {
                    result.addError(ApkVerifier.Issue.V3_INCONSISTENT_LINEAGES, new Object[0])
                    i4 = i7
                    break
                } else {
                    arrayList.add(signerInfo2.signingCertificateLineage)
                    i5 = size
                }
            }
            i4 = i7
        }
        if (i3 > i || i4 < i2) {
            result.addError(ApkVerifier.Issue.V3_MISSING_SDK_VERSIONS, Integer.valueOf(i3), Integer.valueOf(i4))
        }
        try {
            result.signingCertificateLineage = SigningCertificateLineage.consolidateLineages(arrayList)
        } catch (IllegalArgumentException unused) {
            result.addError(ApkVerifier.Issue.V3_INCONSISTENT_LINEAGES, new Object[0])
        }
        if (result.containsErrors()) {
            return
        }
        result.verified = true
    }
}
