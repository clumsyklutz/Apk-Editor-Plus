package com.android.apksig.internal.apk.stamp

import com.android.apksig.apk.ApkFormatException
import com.android.apksig.internal.apk.ApkSigResult
import com.android.apksig.internal.apk.ApkSignerInfo
import com.android.apksig.internal.apk.ApkSigningBlockUtilsLite
import com.android.apksig.internal.apk.ContentDigestAlgorithm
import com.android.apksig.internal.apk.SignatureNotFoundException
import com.android.apksig.internal.util.Pair
import com.android.apksig.util.DataSource
import com.android.apksig.zip.ZipSections
import java.io.IOException
import java.nio.BufferUnderflowException
import java.nio.ByteBuffer
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.HashMap
import java.util.List
import java.util.Map

abstract class V2SourceStampVerifier {
    public static List<Pair<Integer, Array<Byte>>> getApkDigests(Map<ContentDigestAlgorithm, Array<Byte>> map) {
        ArrayList arrayList = ArrayList()
        for (Map.Entry<ContentDigestAlgorithm, Array<Byte>> entry : map.entrySet()) {
            arrayList.add(Pair.of(Integer.valueOf(entry.getKey().getId()), entry.getValue()))
        }
        Collections.sort(arrayList, new Comparator<Pair<Integer, Array<Byte>>>() { // from class: com.android.apksig.internal.apk.stamp.V2SourceStampVerifier.1
            @Override // java.util.Comparator
            fun compare(Pair<Integer, Array<Byte>> pair, Pair<Integer, Array<Byte>> pair2) {
                return pair.getFirst().compareTo(pair2.getFirst())
            }
        })
        return arrayList
    }

    public static Map<Integer, Array<Byte>> getSignatureSchemeDigests(Map<Integer, Map<ContentDigestAlgorithm, Array<Byte>>> map) {
        HashMap map2 = HashMap()
        for (Map.Entry<Integer, Map<ContentDigestAlgorithm, Array<Byte>>> entry : map.entrySet()) {
            map2.put(entry.getKey(), ApkSigningBlockUtilsLite.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(getApkDigests(entry.getValue())))
        }
        return map2
    }

    fun verify(DataSource dataSource, ZipSections zipSections, Array<Byte> bArr, Map<Integer, Map<ContentDigestAlgorithm, Array<Byte>>> map, Int i, Int i2) throws SignatureNotFoundException, NoSuchAlgorithmException, IOException {
        ApkSigResult apkSigResult = ApkSigResult(0)
        verify(ApkSigningBlockUtilsLite.findSignature(dataSource, zipSections, 1845461005).signatureBlock, bArr, map, i, i2, apkSigResult)
        return apkSigResult
    }

    fun verify(ByteBuffer byteBuffer, Array<Byte> bArr, Map<Integer, Map<ContentDigestAlgorithm, Array<Byte>>> map, Int i, Int i2, ApkSigResult apkSigResult) throws NoSuchAlgorithmException {
        ApkSignerInfo apkSignerInfo = ApkSignerInfo()
        apkSigResult.mSigners.add(apkSignerInfo)
        try {
            SourceStampVerifier.verifyV2SourceStamp(ApkSigningBlockUtilsLite.getLengthPrefixedSlice(byteBuffer), CertificateFactory.getInstance("X.509"), apkSignerInfo, getSignatureSchemeDigests(map), bArr, i, i2)
            apkSigResult.verified = (apkSigResult.containsErrors() || apkSigResult.containsWarnings()) ? false : true
        } catch (ApkFormatException | BufferUnderflowException unused) {
            apkSignerInfo.addWarning(20, new Object[0])
        } catch (CertificateException e) {
            throw IllegalStateException("Failed to obtain X.509 CertificateFactory", e)
        }
    }
}
