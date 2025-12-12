package com.android.apksig.internal.zip

import androidx.core.internal.view.SupportMenu
import com.android.apksig.apk.ApkFormatException
import com.android.apksig.internal.util.Pair
import com.android.apksig.util.DataSource
import com.android.apksig.zip.ZipFormatException
import com.android.apksig.zip.ZipSections
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.ArrayList
import java.util.List
import java.util.zip.CRC32
import java.util.zip.Deflater

abstract class ZipUtils {
    public static val COMPRESSION_METHOD_DEFLATED = 8
    public static val COMPRESSION_METHOD_STORED = 0
    public static val GP_FLAG_DATA_DESCRIPTOR_USED = 8
    public static val GP_FLAG_EFS = 2048

    public static class DeflateResult {
        public final Long inputCrc32
        public final Int inputSizeBytes
        public final Array<Byte> output

        constructor(Int i, Long j, Array<Byte> bArr) {
            this.inputSizeBytes = i
            this.inputCrc32 = j
            this.output = bArr
        }
    }

    fun assertByteOrderLittleEndian(ByteBuffer byteBuffer) {
        if (byteBuffer.order() != ByteOrder.LITTLE_ENDIAN) {
            throw IllegalArgumentException("ByteBuffer Byte order must be little endian")
        }
    }

    fun deflate(ByteBuffer byteBuffer) {
        Array<Byte> bArrArray
        Int iArrayOffset
        Int iRemaining = byteBuffer.remaining()
        if (byteBuffer.hasArray()) {
            bArrArray = byteBuffer.array()
            iArrayOffset = byteBuffer.arrayOffset() + byteBuffer.position()
            byteBuffer.position(byteBuffer.limit())
        } else {
            bArrArray = new Byte[iRemaining]
            byteBuffer.get(bArrArray)
            iArrayOffset = 0
        }
        CRC32 crc32 = CRC32()
        crc32.update(bArrArray, iArrayOffset, iRemaining)
        Long value = crc32.getValue()
        ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream()
        Deflater deflater = Deflater(9, true)
        deflater.setInput(bArrArray, iArrayOffset, iRemaining)
        deflater.finish()
        Array<Byte> bArr = new Byte[65536]
        while (!deflater.finished()) {
            byteArrayOutputStream.write(bArr, 0, deflater.deflate(bArr))
        }
        return DeflateResult(iRemaining, value, byteArrayOutputStream.toByteArray())
    }

    fun findZipEndOfCentralDirectoryRecord(ByteBuffer byteBuffer) {
        assertByteOrderLittleEndian(byteBuffer)
        Int iCapacity = byteBuffer.capacity()
        if (iCapacity < 22) {
            return -1
        }
        Int i = iCapacity - 22
        Int iMin = Math.min(i, SupportMenu.USER_MASK)
        for (Int i2 = 0; i2 <= iMin; i2++) {
            Int i3 = i - i2
            if (byteBuffer.getInt(i3) == 101010256 && getUnsignedInt16(byteBuffer, i3 + 20) == i2) {
                return i3
            }
        }
        return -1
    }

    public static Pair<ByteBuffer, Long> findZipEndOfCentralDirectoryRecord(DataSource dataSource) throws IOException {
        if (dataSource.size() < 22) {
            return null
        }
        Pair<ByteBuffer, Long> pairFindZipEndOfCentralDirectoryRecord = findZipEndOfCentralDirectoryRecord(dataSource, 0)
        return pairFindZipEndOfCentralDirectoryRecord != null ? pairFindZipEndOfCentralDirectoryRecord : findZipEndOfCentralDirectoryRecord(dataSource, SupportMenu.USER_MASK)
    }

    public static Pair<ByteBuffer, Long> findZipEndOfCentralDirectoryRecord(DataSource dataSource, Int i) throws IOException {
        if (i < 0 || i > 65535) {
            throw IllegalArgumentException("maxCommentSize: " + i)
        }
        Long size = dataSource.size()
        if (size < 22) {
            return null
        }
        Int iMin = ((Int) Math.min(i, size - 22)) + 22
        Long j = size - iMin
        ByteBuffer byteBuffer = dataSource.getByteBuffer(j, iMin)
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN
        byteBuffer.order(byteOrder)
        Int iFindZipEndOfCentralDirectoryRecord = findZipEndOfCentralDirectoryRecord(byteBuffer)
        if (iFindZipEndOfCentralDirectoryRecord == -1) {
            return null
        }
        byteBuffer.position(iFindZipEndOfCentralDirectoryRecord)
        ByteBuffer byteBufferSlice = byteBuffer.slice()
        byteBufferSlice.order(byteOrder)
        return Pair.of(byteBufferSlice, Long.valueOf(j + iFindZipEndOfCentralDirectoryRecord))
    }

    fun getUnsignedInt16(ByteBuffer byteBuffer) {
        return byteBuffer.getShort() & 65535
    }

    fun getUnsignedInt16(ByteBuffer byteBuffer, Int i) {
        return byteBuffer.getShort(i) & 65535
    }

    fun getUnsignedInt32(ByteBuffer byteBuffer) {
        return byteBuffer.getInt() & 4294967295L
    }

    fun getUnsignedInt32(ByteBuffer byteBuffer, Int i) {
        return byteBuffer.getInt(i) & 4294967295L
    }

    fun getZipEocdCentralDirectoryOffset(ByteBuffer byteBuffer) {
        assertByteOrderLittleEndian(byteBuffer)
        return getUnsignedInt32(byteBuffer, byteBuffer.position() + 16)
    }

    fun getZipEocdCentralDirectorySizeBytes(ByteBuffer byteBuffer) {
        assertByteOrderLittleEndian(byteBuffer)
        return getUnsignedInt32(byteBuffer, byteBuffer.position() + 12)
    }

    fun getZipEocdCentralDirectoryTotalRecordCount(ByteBuffer byteBuffer) {
        assertByteOrderLittleEndian(byteBuffer)
        return getUnsignedInt16(byteBuffer, byteBuffer.position() + 10)
    }

    public static List<CentralDirectoryRecord> parseZipCentralDirectory(DataSource dataSource, ZipSections zipSections) throws IOException, ApkFormatException {
        Long zipCentralDirectorySizeBytes = zipSections.getZipCentralDirectorySizeBytes()
        if (zipCentralDirectorySizeBytes > 2147483647L) {
            throw ApkFormatException("ZIP Central Directory too large: " + zipCentralDirectorySizeBytes)
        }
        Long zipCentralDirectoryOffset = zipSections.getZipCentralDirectoryOffset()
        ByteBuffer byteBuffer = dataSource.getByteBuffer(zipCentralDirectoryOffset, (Int) zipCentralDirectorySizeBytes)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        Int zipCentralDirectoryRecordCount = zipSections.getZipCentralDirectoryRecordCount()
        ArrayList arrayList = ArrayList(zipCentralDirectoryRecordCount)
        for (Int i = 0; i < zipCentralDirectoryRecordCount; i++) {
            Int iPosition = byteBuffer.position()
            try {
                CentralDirectoryRecord record = CentralDirectoryRecord.getRecord(byteBuffer)
                if (!record.getName().endsWith("/")) {
                    arrayList.add(record)
                }
            } catch (ZipFormatException e) {
                throw ApkFormatException("Malformed ZIP Central Directory record #" + (i + 1) + " at file offset " + (zipCentralDirectoryOffset + iPosition), e)
            }
        }
        return arrayList
    }

    fun putUnsignedInt16(ByteBuffer byteBuffer, Int i) {
        if (i >= 0 && i <= 65535) {
            byteBuffer.putShort((Short) i)
            return
        }
        throw IllegalArgumentException("uint16 value of out range: " + i)
    }

    fun putUnsignedInt32(ByteBuffer byteBuffer, Long j) {
        if (j >= 0 && j <= 4294967295L) {
            byteBuffer.putInt((Int) j)
            return
        }
        throw IllegalArgumentException("uint32 value of out range: " + j)
    }

    fun setUnsignedInt16(ByteBuffer byteBuffer, Int i, Int i2) {
        if (i2 >= 0 && i2 <= 65535) {
            byteBuffer.putShort(i, (Short) i2)
            return
        }
        throw IllegalArgumentException("uint16 value of out range: " + i2)
    }

    fun setUnsignedInt32(ByteBuffer byteBuffer, Int i, Long j) {
        if (j >= 0 && j <= 4294967295L) {
            byteBuffer.putInt(i, (Int) j)
            return
        }
        throw IllegalArgumentException("uint32 value of out range: " + j)
    }

    fun setZipEocdCentralDirectoryOffset(ByteBuffer byteBuffer, Long j) {
        assertByteOrderLittleEndian(byteBuffer)
        setUnsignedInt32(byteBuffer, byteBuffer.position() + 16, j)
    }
}
