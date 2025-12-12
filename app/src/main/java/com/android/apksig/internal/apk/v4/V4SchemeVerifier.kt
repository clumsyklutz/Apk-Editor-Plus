package com.android.apksig.internal.apk.v4

import com.android.apksig.ApkVerifier
import com.android.apksig.internal.apk.ApkSigningBlockUtils
import com.android.apksig.internal.apk.ContentDigestAlgorithm
import com.android.apksig.internal.apk.SignatureAlgorithm
import com.android.apksig.internal.apk.v4.V4Signature
import com.android.apksig.internal.util.GuaranteedEncodedFormX509Certificate
import com.android.apksig.internal.util.X509CertificateUtils
import com.android.apksig.util.DataSource
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.Signature
import java.security.SignatureException
import java.security.cert.CertificateException
import java.security.spec.AlgorithmParameterSpec
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import java.util.Arrays

abstract class V4SchemeVerifier {
    public static ApkSigningBlockUtils.Result.SignerInfo parseAndVerifySignatureBlock(V4Signature.SigningInfo signingInfo, Array<Byte> bArr) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidAlgorithmParameterException {
        Array<Byte> encoded
        ApkSigningBlockUtils.Result.SignerInfo signerInfo = new ApkSigningBlockUtils.Result.SignerInfo()
        signerInfo.index = 0
        Int i = signingInfo.signatureAlgorithmId
        Array<Byte> bArr2 = signingInfo.signature
        signerInfo.signatures.add(new ApkSigningBlockUtils.Result.SignerInfo.Signature(i, bArr2))
        SignatureAlgorithm signatureAlgorithmFindById = SignatureAlgorithm.findById(i)
        if (signatureAlgorithmFindById == null) {
            signerInfo.addError(ApkVerifier.Issue.V4_SIG_UNKNOWN_SIG_ALGORITHM, Integer.valueOf(i))
            return signerInfo
        }
        String first = signatureAlgorithmFindById.getJcaSignatureAlgorithmAndParams().getFirst()
        AlgorithmParameterSpec second = signatureAlgorithmFindById.getJcaSignatureAlgorithmAndParams().getSecond()
        String jcaKeyAlgorithm = signatureAlgorithmFindById.getJcaKeyAlgorithm()
        Array<Byte> bArr3 = signingInfo.publicKey
        try {
            PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(jcaKeyAlgorithm).generatePublic(X509EncodedKeySpec(bArr3))
            try {
                Signature signature = Signature.getInstance(first)
                signature.initVerify(publicKeyGeneratePublic)
                if (second != null) {
                    signature.setParameter(second)
                }
                signature.update(bArr)
                if (!signature.verify(bArr2)) {
                    signerInfo.addError(ApkVerifier.Issue.V4_SIG_DID_NOT_VERIFY, signatureAlgorithmFindById)
                    return signerInfo
                }
                signerInfo.verifiedSignatures.put(signatureAlgorithmFindById, bArr2)
                if (signingInfo.certificate == null) {
                    signerInfo.addError(ApkVerifier.Issue.V4_SIG_NO_CERTIFICATE, new Object[0])
                    return signerInfo
                }
                try {
                    GuaranteedEncodedFormX509Certificate guaranteedEncodedFormX509Certificate = GuaranteedEncodedFormX509Certificate(X509CertificateUtils.generateCertificate(signingInfo.certificate), signingInfo.certificate)
                    signerInfo.certs.add(guaranteedEncodedFormX509Certificate)
                    try {
                        encoded = ApkSigningBlockUtils.encodePublicKey(guaranteedEncodedFormX509Certificate.getPublicKey())
                    } catch (InvalidKeyException e) {
                        System.out.println("Caught an exception encoding the public key: " + e)
                        e.printStackTrace()
                        encoded = guaranteedEncodedFormX509Certificate.getPublicKey().getEncoded()
                    }
                    if (Arrays.equals(bArr3, encoded)) {
                        signerInfo.contentDigests.add(new ApkSigningBlockUtils.Result.SignerInfo.ContentDigest(0, signingInfo.apkDigest))
                        return signerInfo
                    }
                    signerInfo.addError(ApkVerifier.Issue.V4_SIG_PUBLIC_KEY_MISMATCH_BETWEEN_CERTIFICATE_AND_SIGNATURES_RECORD, ApkSigningBlockUtils.toHex(encoded), ApkSigningBlockUtils.toHex(bArr3))
                    return signerInfo
                } catch (CertificateException e2) {
                    signerInfo.addError(ApkVerifier.Issue.V4_SIG_MALFORMED_CERTIFICATE, e2)
                    return signerInfo
                }
            } catch (InvalidAlgorithmParameterException | InvalidKeyException | SignatureException e3) {
                signerInfo.addError(ApkVerifier.Issue.V4_SIG_VERIFY_EXCEPTION, signatureAlgorithmFindById, e3)
                return signerInfo
            }
        } catch (Exception e4) {
            signerInfo.addError(ApkVerifier.Issue.V4_SIG_MALFORMED_PUBLIC_KEY, e4)
            return signerInfo
        }
    }

    public static ApkSigningBlockUtils.Result verify(DataSource dataSource, File file) throws NoSuchAlgorithmException, IOException {
        FileInputStream fileInputStream = FileInputStream(file)
        try {
            V4Signature from = V4Signature.readFrom(fileInputStream)
            Array<Byte> bytes = V4Signature.readBytes(fileInputStream)
            fileInputStream.close()
            ApkSigningBlockUtils.Result result = new ApkSigningBlockUtils.Result(4)
            if (from == null) {
                result.addError(ApkVerifier.Issue.V4_SIG_NO_SIGNATURES, "Signature file does not contain a v4 signature.")
                return result
            }
            Int i = from.version
            if (i != 2) {
                result.addWarning(ApkVerifier.Issue.V4_SIG_VERSION_NOT_CURRENT, Integer.valueOf(i), 2)
            }
            V4Signature.HashingInfo hashingInfoFromByteArray = V4Signature.HashingInfo.fromByteArray(from.hashingInfo)
            V4Signature.SigningInfo signingInfoFromByteArray = V4Signature.SigningInfo.fromByteArray(from.signingInfo)
            ApkSigningBlockUtils.Result.SignerInfo andVerifySignatureBlock = parseAndVerifySignatureBlock(signingInfoFromByteArray, V4Signature.getSigningData(dataSource.size(), hashingInfoFromByteArray, signingInfoFromByteArray))
            result.signers.add(andVerifySignatureBlock)
            if (result.containsErrors()) {
                return result
            }
            verifyRootHashAndTree(dataSource, andVerifySignatureBlock, hashingInfoFromByteArray.rawRootHash, bytes)
            if (!result.containsErrors()) {
                result.verified = true
            }
            return result
        } catch (Throwable th) {
            try {
                fileInputStream.close()
            } catch (Throwable unused) {
            }
            throw th
        }
    }

    fun verifyRootHashAndTree(DataSource dataSource, ApkSigningBlockUtils.Result.SignerInfo signerInfo, Array<Byte> bArr, Array<Byte> bArr2) throws NoSuchAlgorithmException, IOException {
        ApkSigningBlockUtils.VerityTreeAndDigest verityTreeAndDigestComputeChunkVerityTreeAndDigest = ApkSigningBlockUtils.computeChunkVerityTreeAndDigest(dataSource)
        ContentDigestAlgorithm contentDigestAlgorithm = verityTreeAndDigestComputeChunkVerityTreeAndDigest.contentDigestAlgorithm
        Array<Byte> bArr3 = verityTreeAndDigestComputeChunkVerityTreeAndDigest.rootHash
        Array<Byte> bArr4 = verityTreeAndDigestComputeChunkVerityTreeAndDigest.tree
        if (!Arrays.equals(bArr, bArr3)) {
            signerInfo.addError(ApkVerifier.Issue.V4_SIG_APK_ROOT_DID_NOT_VERIFY, contentDigestAlgorithm, ApkSigningBlockUtils.toHex(bArr), ApkSigningBlockUtils.toHex(bArr3))
        } else if (bArr2 == null || Arrays.equals(bArr2, bArr4)) {
            signerInfo.verifiedContentDigests.put(contentDigestAlgorithm, bArr3)
        } else {
            signerInfo.addError(ApkVerifier.Issue.V4_SIG_APK_TREE_DID_NOT_VERIFY, contentDigestAlgorithm, ApkSigningBlockUtils.toHex(bArr), ApkSigningBlockUtils.toHex(bArr3))
        }
    }
}
