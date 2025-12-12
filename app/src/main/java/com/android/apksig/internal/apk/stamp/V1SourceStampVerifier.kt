package com.android.apksig.internal.apk.stamp

import com.android.apksig.ApkVerifier
import com.android.apksig.apk.ApkFormatException
import com.android.apksig.apk.ApkUtils
import com.android.apksig.internal.apk.ApkSigningBlockUtils
import com.android.apksig.internal.apk.ContentDigestAlgorithm
import com.android.apksig.internal.util.Pair
import com.android.apksig.util.DataSource
import java.io.IOException
import java.nio.BufferUnderflowException
import java.nio.ByteBuffer
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.List
import java.util.Map

abstract class V1SourceStampVerifier {
    public static List<Pair<Integer, Array<Byte>>> getApkDigests(Map<ContentDigestAlgorithm, Array<Byte>> map) {
        ArrayList arrayList = ArrayList()
        for (Map.Entry<ContentDigestAlgorithm, Array<Byte>> entry : map.entrySet()) {
            arrayList.add(Pair.of(Integer.valueOf(entry.getKey().getId()), entry.getValue()))
        }
        Collections.sort(arrayList, new Comparator<Pair<Integer, Array<Byte>>>() { // from class: com.android.apksig.internal.apk.stamp.V1SourceStampVerifier.1
            @Override // java.util.Comparator
            fun compare(Pair<Integer, Array<Byte>> pair, Pair<Integer, Array<Byte>> pair2) {
                return pair.getFirst().compareTo(pair2.getFirst())
            }
        })
        return arrayList
    }

    public static ApkSigningBlockUtils.Result verify(DataSource dataSource, ApkUtils.ZipSections zipSections, Array<Byte> bArr, Map<ContentDigestAlgorithm, Array<Byte>> map, Int i, Int i2) throws ApkSigningBlockUtils.SignatureNotFoundException, NoSuchAlgorithmException, IOException {
        ApkSigningBlockUtils.Result result = new ApkSigningBlockUtils.Result(0)
        verify(ApkSigningBlockUtils.findSignature(dataSource, zipSections, 722016414, result).signatureBlock, bArr, map, i, i2, result)
        return result
    }

    fun verify(ByteBuffer byteBuffer, Array<Byte> bArr, Map<ContentDigestAlgorithm, Array<Byte>> map, Int i, Int i2, ApkSigningBlockUtils.Result result) throws NoSuchAlgorithmException {
        ApkSigningBlockUtils.Result.SignerInfo signerInfo = new ApkSigningBlockUtils.Result.SignerInfo()
        result.signers.add(signerInfo)
        try {
            SourceStampVerifier.verifyV1SourceStamp(ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer), CertificateFactory.getInstance("X.509"), signerInfo, ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(getApkDigests(map)), bArr, i, i2)
            result.verified = (result.containsErrors() || result.containsWarnings()) ? false : true
        } catch (ApkFormatException | BufferUnderflowException unused) {
            signerInfo.addWarning(ApkVerifier.Issue.SOURCE_STAMP_MALFORMED_SIGNATURE, new Object[0])
        } catch (CertificateException e) {
            throw IllegalStateException("Failed to obtain X.509 CertificateFactory", e)
        }
    }
}
