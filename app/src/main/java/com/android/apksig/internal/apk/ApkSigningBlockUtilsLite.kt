package com.android.apksig.internal.apk

import android.support.v7.widget.ActivityChooserView
import com.android.apksig.apk.ApkFormatException
import com.android.apksig.apk.ApkSigningBlockNotFoundException
import com.android.apksig.apk.ApkUtilsLite
import com.android.apksig.internal.util.Pair
import com.android.apksig.util.DataSource
import com.android.apksig.zip.ZipSections
import com.gmail.heagoo.neweditor.Token
import java.io.IOException
import java.nio.BufferUnderflowException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.HashMap
import java.util.Iterator
import java.util.List

class ApkSigningBlockUtilsLite {
    public static final Array<Char> HEX_DIGITS = "0123456789abcdef".toCharArray()

    /* renamed from: com.android.apksig.internal.apk.ApkSigningBlockUtilsLite$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ Array<Int> $SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm

        static {
            Array<Int> iArr = new Int[ContentDigestAlgorithm.values().length]
            $SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm = iArr
            try {
                iArr[ContentDigestAlgorithm.CHUNKED_SHA256.ordinal()] = 1
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm[ContentDigestAlgorithm.CHUNKED_SHA512.ordinal()] = 2
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm[ContentDigestAlgorithm.VERITY_CHUNKED_SHA256.ordinal()] = 3
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    fun checkByteOrderLittleEndian(ByteBuffer byteBuffer) {
        if (byteBuffer.order() != ByteOrder.LITTLE_ENDIAN) {
            throw IllegalArgumentException("ByteBuffer Byte order must be little endian")
        }
    }

    fun compareContentDigestAlgorithm(ContentDigestAlgorithm contentDigestAlgorithm, ContentDigestAlgorithm contentDigestAlgorithm2) {
        Array<Int> iArr = AnonymousClass2.$SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm
        Int i = iArr[contentDigestAlgorithm.ordinal()]
        if (i == 1) {
            Int i2 = iArr[contentDigestAlgorithm2.ordinal()]
            if (i2 == 1) {
                return 0
            }
            if (i2 == 2 || i2 == 3) {
                return -1
            }
            throw IllegalArgumentException("Unknown alg2: " + contentDigestAlgorithm2)
        }
        if (i == 2) {
            Int i3 = iArr[contentDigestAlgorithm2.ordinal()]
            if (i3 != 1) {
                if (i3 == 2) {
                    return 0
                }
                if (i3 != 3) {
                    throw IllegalArgumentException("Unknown alg2: " + contentDigestAlgorithm2)
                }
            }
            return 1
        }
        if (i != 3) {
            throw IllegalArgumentException("Unknown alg1: " + contentDigestAlgorithm)
        }
        Int i4 = iArr[contentDigestAlgorithm2.ordinal()]
        if (i4 == 1) {
            return 1
        }
        if (i4 == 2) {
            return -1
        }
        if (i4 == 3) {
            return 0
        }
        throw IllegalArgumentException("Unknown alg2: " + contentDigestAlgorithm2)
    }

    fun compareSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm, SignatureAlgorithm signatureAlgorithm2) {
        return compareContentDigestAlgorithm(signatureAlgorithm.getContentDigestAlgorithm(), signatureAlgorithm2.getContentDigestAlgorithm())
    }

    public static Array<Byte> encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(List<Pair<Integer, Array<Byte>>> list) {
        Iterator<Pair<Integer, Array<Byte>>> it = list.iterator()
        Int length = 0
        while (it.hasNext()) {
            length += it.next().getSecond().length + 12
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length)
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        for (Pair<Integer, Array<Byte>> pair : list) {
            Array<Byte> second = pair.getSecond()
            byteBufferAllocate.putInt(second.length + 8)
            byteBufferAllocate.putInt(pair.getFirst().intValue())
            byteBufferAllocate.putInt(second.length)
            byteBufferAllocate.put(second)
        }
        return byteBufferAllocate.array()
    }

    fun findApkSignatureSchemeBlock(ByteBuffer byteBuffer, Int i) throws SignatureNotFoundException {
        checkByteOrderLittleEndian(byteBuffer)
        ByteBuffer byteBufferSliceFromTo = sliceFromTo(byteBuffer, 8, byteBuffer.capacity() - 24)
        Int i2 = 0
        while (byteBufferSliceFromTo.hasRemaining()) {
            i2++
            if (byteBufferSliceFromTo.remaining() < 8) {
                throw SignatureNotFoundException("Insufficient data to read size of APK Signing Block entry #" + i2)
            }
            Long j = byteBufferSliceFromTo.getLong()
            if (j < 4 || j > 2147483647L) {
                throw SignatureNotFoundException("APK Signing Block entry #" + i2 + " size out of range: " + j)
            }
            Int i3 = (Int) j
            Int iPosition = byteBufferSliceFromTo.position() + i3
            if (i3 > byteBufferSliceFromTo.remaining()) {
                throw SignatureNotFoundException("APK Signing Block entry #" + i2 + " size out of range: " + i3 + ", available: " + byteBufferSliceFromTo.remaining())
            }
            if (byteBufferSliceFromTo.getInt() == i) {
                return getByteBuffer(byteBufferSliceFromTo, i3 - 4)
            }
            byteBufferSliceFromTo.position(iPosition)
        }
        throw SignatureNotFoundException("No APK Signature Scheme block in APK Signing Block with ID: " + i)
    }

    fun findSignature(DataSource dataSource, ZipSections zipSections, Int i) throws SignatureNotFoundException, IOException {
        try {
            ApkUtilsLite.ApkSigningBlock apkSigningBlockFindApkSigningBlock = ApkUtilsLite.findApkSigningBlock(dataSource, zipSections)
            Long startOffset = apkSigningBlockFindApkSigningBlock.getStartOffset()
            DataSource contents = apkSigningBlockFindApkSigningBlock.getContents()
            ByteBuffer byteBuffer = contents.getByteBuffer(0L, (Int) contents.size())
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
            return SignatureInfo(findApkSignatureSchemeBlock(byteBuffer, i), startOffset, zipSections.getZipCentralDirectoryOffset(), zipSections.getZipEndOfCentralDirectoryOffset(), zipSections.getZipEndOfCentralDirectory())
        } catch (ApkSigningBlockNotFoundException e) {
            throw SignatureNotFoundException(e.getMessage(), e)
        }
    }

    fun getByteBuffer(ByteBuffer byteBuffer, Int i) {
        if (i < 0) {
            throw IllegalArgumentException("size: " + i)
        }
        Int iLimit = byteBuffer.limit()
        Int iPosition = byteBuffer.position()
        Int i2 = i + iPosition
        if (i2 < iPosition || i2 > iLimit) {
            throw BufferUnderflowException()
        }
        byteBuffer.limit(i2)
        try {
            ByteBuffer byteBufferSlice = byteBuffer.slice()
            byteBufferSlice.order(byteBuffer.order())
            byteBuffer.position(i2)
            return byteBufferSlice
        } finally {
            byteBuffer.limit(iLimit)
        }
    }

    fun getLengthPrefixedSlice(ByteBuffer byteBuffer) throws ApkFormatException {
        if (byteBuffer.remaining() < 4) {
            throw ApkFormatException("Remaining buffer too Short to contain length of length-prefixed field. Remaining: " + byteBuffer.remaining())
        }
        Int i = byteBuffer.getInt()
        if (i < 0) {
            throw IllegalArgumentException("Negative length")
        }
        if (i <= byteBuffer.remaining()) {
            return getByteBuffer(byteBuffer, i)
        }
        throw ApkFormatException("Length-prefixed field longer than remaining buffer. Field length: " + i + ", remaining: " + byteBuffer.remaining())
    }

    public static <T extends ApkSupportedSignature> List<T> getSignaturesToVerify(List<T> list, Int i, Int i2) throws NoApkSupportedSignaturesException {
        return getSignaturesToVerify(list, i, i2, false)
    }

    public static <T extends ApkSupportedSignature> List<T> getSignaturesToVerify(List<T> list, Int i, Int i2, Boolean z) throws NoApkSupportedSignaturesException {
        HashMap map = HashMap()
        Int i3 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED
        for (T t : list) {
            SignatureAlgorithm signatureAlgorithm = t.algorithm
            Int jcaSigAlgMinSdkVersion = z ? signatureAlgorithm.getJcaSigAlgMinSdkVersion() : signatureAlgorithm.getMinSdkVersion()
            if (jcaSigAlgMinSdkVersion <= i2) {
                if (jcaSigAlgMinSdkVersion < i3) {
                    i3 = jcaSigAlgMinSdkVersion
                }
                ApkSupportedSignature apkSupportedSignature = (ApkSupportedSignature) map.get(Integer.valueOf(jcaSigAlgMinSdkVersion))
                if (apkSupportedSignature == null || compareSignatureAlgorithm(signatureAlgorithm, apkSupportedSignature.algorithm) > 0) {
                    map.put(Integer.valueOf(jcaSigAlgMinSdkVersion), t)
                }
            }
        }
        if (i >= i3) {
            if (map.isEmpty()) {
                throw NoApkSupportedSignaturesException("No supported signature")
            }
            ArrayList arrayList = ArrayList(map.values())
            Collections.sort(arrayList, new Comparator<T>() { // from class: com.android.apksig.internal.apk.ApkSigningBlockUtilsLite.1
                /* JADX WARN: Incorrect types in method signature: (TT;TT;)I */
                @Override // java.util.Comparator
                fun compare(ApkSupportedSignature apkSupportedSignature2, ApkSupportedSignature apkSupportedSignature3) {
                    return ApkSigningBlockUtilsLite$1$$ExternalSyntheticBackport0.m(apkSupportedSignature2.algorithm.getId(), apkSupportedSignature3.algorithm.getId())
                }
            })
            return arrayList
        }
        throw NoApkSupportedSignaturesException("Minimum provided signature version " + i3 + " > minSdkVersion " + i)
    }

    public static Array<Byte> readLengthPrefixedByteArray(ByteBuffer byteBuffer) throws ApkFormatException {
        Int i = byteBuffer.getInt()
        if (i < 0) {
            throw ApkFormatException("Negative length")
        }
        if (i <= byteBuffer.remaining()) {
            Array<Byte> bArr = new Byte[i]
            byteBuffer.get(bArr)
            return bArr
        }
        throw ApkFormatException("Underflow while reading length-prefixed value. Length: " + i + ", available: " + byteBuffer.remaining())
    }

    fun sliceFromTo(ByteBuffer byteBuffer, Int i, Int i2) {
        if (i < 0) {
            throw IllegalArgumentException("start: " + i)
        }
        if (i2 < i) {
            throw IllegalArgumentException("end < start: " + i2 + " < " + i)
        }
        Int iCapacity = byteBuffer.capacity()
        if (i2 > byteBuffer.capacity()) {
            throw IllegalArgumentException("end > capacity: " + i2 + " > " + iCapacity)
        }
        Int iLimit = byteBuffer.limit()
        Int iPosition = byteBuffer.position()
        try {
            byteBuffer.position(0)
            byteBuffer.limit(i2)
            byteBuffer.position(i)
            ByteBuffer byteBufferSlice = byteBuffer.slice()
            byteBufferSlice.order(byteBuffer.order())
            return byteBufferSlice
        } finally {
            byteBuffer.position(0)
            byteBuffer.limit(iLimit)
            byteBuffer.position(iPosition)
        }
    }

    fun toHex(Array<Byte> bArr) {
        StringBuilder sb = StringBuilder(bArr.length * 2)
        Int length = bArr.length
        for (Int i = 0; i < length; i++) {
            Int i2 = (bArr[i] & 255) >>> 4
            Int i3 = bArr[i] & Token.LITERAL3
            Array<Char> cArr = HEX_DIGITS
            sb.append(cArr[i2])
            sb.append(cArr[i3])
        }
        return sb.toString()
    }
}
