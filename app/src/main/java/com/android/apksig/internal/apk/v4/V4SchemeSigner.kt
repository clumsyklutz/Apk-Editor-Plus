package com.android.apksig.internal.apk.v4

import android.support.v7.widget.ActivityChooserView
import com.android.apksig.apk.ApkUtils
import com.android.apksig.internal.apk.ApkSigningBlockUtils
import com.android.apksig.internal.apk.ContentDigestAlgorithm
import com.android.apksig.internal.apk.SignatureAlgorithm
import com.android.apksig.internal.apk.v2.V2SchemeVerifier
import com.android.apksig.internal.apk.v3.V3SchemeSigner
import com.android.apksig.internal.apk.v3.V3SchemeVerifier
import com.android.apksig.internal.apk.v4.V4Signature
import com.android.apksig.internal.util.Pair
import com.android.apksig.util.DataSource
import com.android.apksig.zip.ZipFormatException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.SignatureException
import java.security.cert.CertificateEncodingException
import java.util.Collections
import java.util.HashSet
import java.util.List
import java.util.ListIterator

abstract class V4SchemeSigner {

    /* renamed from: com.android.apksig.internal.apk.v4.V4SchemeSigner$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ Array<Int> $SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm

        static {
            Array<Int> iArr = new Int[ContentDigestAlgorithm.values().length]
            $SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm = iArr
            try {
                iArr[ContentDigestAlgorithm.CHUNKED_SHA256.ordinal()] = 1
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm[ContentDigestAlgorithm.VERITY_CHUNKED_SHA256.ordinal()] = 2
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm[ContentDigestAlgorithm.CHUNKED_SHA512.ordinal()] = 3
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static Pair<Integer, Byte> convertToV4HashingInfo(ContentDigestAlgorithm contentDigestAlgorithm) throws NoSuchAlgorithmException {
        if (AnonymousClass1.$SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm[contentDigestAlgorithm.ordinal()] == 2) {
            return Pair.of(1, (Byte) 12)
        }
        throw NoSuchAlgorithmException("Invalid hash algorithm, only SHA2-256 over 4 KB chunks supported.")
    }

    fun digestAlgorithmSortingOrder(ContentDigestAlgorithm contentDigestAlgorithm) {
        Int i = AnonymousClass1.$SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm[contentDigestAlgorithm.ordinal()]
        if (i == 1) {
            return 0
        }
        if (i != 2) {
            return i != 3 ? -1 : 2
        }
        return 1
    }

    fun generateSignature(ApkSigningBlockUtils.SignerConfig signerConfig, V4Signature.HashingInfo hashingInfo, Array<Byte> bArr, Array<Byte> bArr2, Long j) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidAlgorithmParameterException, CertificateEncodingException {
        if (signerConfig.certificates.isEmpty()) {
            throw SignatureException("No certificates configured for signer")
        }
        if (signerConfig.certificates.size() != 1) {
            throw CertificateEncodingException("Should only have one certificate")
        }
        PublicKey publicKey = signerConfig.certificates.get(0).getPublicKey()
        Array<Byte> bArr3 = ApkSigningBlockUtils.encodeCertificates(signerConfig.certificates).get(0)
        List<Pair<Integer, Array<Byte>>> listGenerateSignaturesOverData = ApkSigningBlockUtils.generateSignaturesOverData(signerConfig, V4Signature.getSigningData(j, hashingInfo, new V4Signature.SigningInfo(bArr, bArr3, bArr2, publicKey.getEncoded(), -1, null)))
        if (listGenerateSignaturesOverData.size() != 1) {
            throw SignatureException("Should only be one signature generated")
        }
        return V4Signature(2, hashingInfo.toByteArray(), new V4Signature.SigningInfo(bArr, bArr3, bArr2, publicKey.getEncoded(), listGenerateSignaturesOverData.get(0).getFirst().intValue(), listGenerateSignaturesOverData.get(0).getSecond()).toByteArray())
    }

    public static Pair<V4Signature, Array<Byte>> generateV4Signature(DataSource dataSource, ApkSigningBlockUtils.SignerConfig signerConfig) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        Long size = dataSource.size()
        Array<Byte> apkDigest = getApkDigest(dataSource)
        ApkSigningBlockUtils.VerityTreeAndDigest verityTreeAndDigestComputeChunkVerityTreeAndDigest = ApkSigningBlockUtils.computeChunkVerityTreeAndDigest(dataSource)
        ContentDigestAlgorithm contentDigestAlgorithm = verityTreeAndDigestComputeChunkVerityTreeAndDigest.contentDigestAlgorithm
        Array<Byte> bArr = verityTreeAndDigestComputeChunkVerityTreeAndDigest.rootHash
        Array<Byte> bArr2 = verityTreeAndDigestComputeChunkVerityTreeAndDigest.tree
        Pair<Integer, Byte> pairConvertToV4HashingInfo = convertToV4HashingInfo(contentDigestAlgorithm)
        try {
            return Pair.of(generateSignature(signerConfig, new V4Signature.HashingInfo(pairConvertToV4HashingInfo.getFirst().intValue(), pairConvertToV4HashingInfo.getSecond().byteValue(), null, bArr), apkDigest, null, size), bArr2)
        } catch (InvalidKeyException | SignatureException | CertificateEncodingException e) {
            throw InvalidKeyException("Signer failed", e)
        }
    }

    fun generateV4Signature(DataSource dataSource, ApkSigningBlockUtils.SignerConfig signerConfig, File file) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        Pair<V4Signature, Array<Byte>> pairGenerateV4Signature = generateV4Signature(dataSource, signerConfig)
        try {
            FileOutputStream fileOutputStream = FileOutputStream(file)
            try {
                pairGenerateV4Signature.getFirst().writeTo(fileOutputStream)
                V4Signature.writeBytes(fileOutputStream, pairGenerateV4Signature.getSecond())
                fileOutputStream.close()
            } catch (Throwable th) {
                try {
                    fileOutputStream.close()
                } catch (Throwable unused) {
                }
                throw th
            }
        } catch (IOException e) {
            file.delete()
            throw e
        }
    }

    public static Array<Byte> getApkDigest(DataSource dataSource) throws IOException {
        try {
            ApkUtils.ZipSections zipSectionsFindZipSections = ApkUtils.findZipSections(dataSource)
            try {
                return getBestV3Digest(dataSource, zipSectionsFindZipSections)
            } catch (SignatureException e) {
                try {
                    return getBestV2Digest(dataSource, zipSectionsFindZipSections)
                } catch (SignatureException e2) {
                    throw IOException("Failed to obtain v2/v3 digest, v3 exception: " + e + ", v2 exception: " + e2)
                }
            }
        } catch (ZipFormatException e3) {
            throw IOException("Malformed APK: not a ZIP archive", e3)
        }
    }

    public static Array<Byte> getBestV2Digest(DataSource dataSource, ApkUtils.ZipSections zipSections) throws SignatureException {
        HashSet hashSet = HashSet(1)
        HashSet hashSet2 = HashSet(1)
        ApkSigningBlockUtils.Result result = new ApkSigningBlockUtils.Result(2)
        try {
            V2SchemeVerifier.parseSigners(ApkSigningBlockUtils.findSignature(dataSource, zipSections, 1896449818, result).signatureBlock, hashSet, Collections.emptyMap(), hashSet2, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, result)
            if (result.signers.size() != 1) {
                throw SignatureException("Should only have one signer, errors: " + result.getErrors())
            }
            ApkSigningBlockUtils.Result.SignerInfo signerInfo = result.signers.get(0)
            if (!signerInfo.containsErrors()) {
                return pickBestDigest(signerInfo.contentDigests)
            }
            throw SignatureException("Parsing failed: " + signerInfo.getErrors())
        } catch (Exception e) {
            throw SignatureException("Failed to extract and parse v2 block", e)
        }
    }

    public static Array<Byte> getBestV3Digest(DataSource dataSource, ApkUtils.ZipSections zipSections) throws SignatureException {
        HashSet hashSet = HashSet(1)
        ApkSigningBlockUtils.Result result = new ApkSigningBlockUtils.Result(3)
        try {
            V3SchemeVerifier.parseSigners(ApkSigningBlockUtils.findSignature(dataSource, zipSections, -262969152, result).signatureBlock, hashSet, result)
            if (result.signers.size() != 1) {
                throw SignatureException("Should only have one signer, errors: " + result.getErrors())
            }
            ApkSigningBlockUtils.Result.SignerInfo signerInfo = result.signers.get(0)
            if (!signerInfo.containsErrors()) {
                return pickBestDigest(result.signers.get(0).contentDigests)
            }
            throw SignatureException("Parsing failed: " + signerInfo.getErrors())
        } catch (Exception e) {
            throw SignatureException("Failed to extract and parse v3 block", e)
        }
    }

    public static List<SignatureAlgorithm> getSuggestedSignatureAlgorithms(PublicKey publicKey, Int i, Boolean z) throws InvalidKeyException {
        List<SignatureAlgorithm> suggestedSignatureAlgorithms = V3SchemeSigner.getSuggestedSignatureAlgorithms(publicKey, i, z)
        ListIterator<SignatureAlgorithm> listIterator = suggestedSignatureAlgorithms.listIterator()
        while (listIterator.hasNext()) {
            if (!isSupported(listIterator.next().getContentDigestAlgorithm(), false)) {
                listIterator.remove()
            }
        }
        return suggestedSignatureAlgorithms
    }

    fun isSupported(ContentDigestAlgorithm contentDigestAlgorithm, Boolean z) {
        if (contentDigestAlgorithm == null) {
            return false
        }
        if (contentDigestAlgorithm == ContentDigestAlgorithm.CHUNKED_SHA256 || contentDigestAlgorithm == ContentDigestAlgorithm.CHUNKED_SHA512) {
            return true
        }
        return z && contentDigestAlgorithm == ContentDigestAlgorithm.VERITY_CHUNKED_SHA256
    }

    public static Array<Byte> pickBestDigest(List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> list) throws SignatureException {
        Int iDigestAlgorithmSortingOrder
        if (list == null || list.isEmpty()) {
            throw SignatureException("Should have at least one digest")
        }
        Int i = -1
        Array<Byte> value = null
        for (ApkSigningBlockUtils.Result.SignerInfo.ContentDigest contentDigest : list) {
            ContentDigestAlgorithm contentDigestAlgorithm = SignatureAlgorithm.findById(contentDigest.getSignatureAlgorithmId()).getContentDigestAlgorithm()
            if (isSupported(contentDigestAlgorithm, true) && i < (iDigestAlgorithmSortingOrder = digestAlgorithmSortingOrder(contentDigestAlgorithm))) {
                value = contentDigest.getValue()
                i = iDigestAlgorithmSortingOrder
            }
        }
        if (value != null) {
            return value
        }
        throw SignatureException("Failed to find a supported digest in the source APK")
    }
}
