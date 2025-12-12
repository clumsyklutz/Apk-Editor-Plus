package com.android.apksig.internal.zip

import com.android.apksig.zip.ZipFormatException
import java.nio.BufferUnderflowException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.Charset
import java.util.Comparator

class CentralDirectoryRecord {
    public static final Comparator<CentralDirectoryRecord> BY_LOCAL_FILE_HEADER_OFFSET_COMPARATOR = ByLocalFileHeaderOffsetComparator()
    public final Long mCompressedSize
    public final Short mCompressionMethod
    public final Long mCrc32
    public final ByteBuffer mData
    public final Short mGpFlags
    public final Int mLastModificationDate
    public final Int mLastModificationTime
    public final Long mLocalFileHeaderOffset
    public final String mName
    public final Int mNameSizeBytes
    public final Long mUncompressedSize

    public static class ByLocalFileHeaderOffsetComparator implements Comparator<CentralDirectoryRecord> {
        constructor() {
        }

        @Override // java.util.Comparator
        fun compare(CentralDirectoryRecord centralDirectoryRecord, CentralDirectoryRecord centralDirectoryRecord2) {
            Long localFileHeaderOffset = centralDirectoryRecord.getLocalFileHeaderOffset()
            Long localFileHeaderOffset2 = centralDirectoryRecord2.getLocalFileHeaderOffset()
            if (localFileHeaderOffset > localFileHeaderOffset2) {
                return 1
            }
            return localFileHeaderOffset < localFileHeaderOffset2 ? -1 : 0
        }
    }

    constructor(ByteBuffer byteBuffer, Short s, Short s2, Int i, Int i2, Long j, Long j2, Long j3, Long j4, String str, Int i3) {
        this.mData = byteBuffer
        this.mGpFlags = s
        this.mCompressionMethod = s2
        this.mLastModificationDate = i2
        this.mLastModificationTime = i
        this.mCrc32 = j
        this.mCompressedSize = j2
        this.mUncompressedSize = j3
        this.mLocalFileHeaderOffset = j4
        this.mName = str
        this.mNameSizeBytes = i3
    }

    fun createWithDeflateCompressedData(String str, Int i, Int i2, Long j, Long j2, Long j3, Long j4) {
        Array<Byte> bytes = str.getBytes(Charset.forName("UTF-8"))
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bytes.length + 46)
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        byteBufferAllocate.putInt(33639248)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, 20)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, 20)
        byteBufferAllocate.putShort(ZipUtils.GP_FLAG_EFS)
        byteBufferAllocate.putShort((Short) 8)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, i)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, i2)
        ZipUtils.putUnsignedInt32(byteBufferAllocate, j)
        ZipUtils.putUnsignedInt32(byteBufferAllocate, j2)
        ZipUtils.putUnsignedInt32(byteBufferAllocate, j3)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, bytes.length)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, 0)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, 0)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, 0)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, 0)
        ZipUtils.putUnsignedInt32(byteBufferAllocate, 0L)
        ZipUtils.putUnsignedInt32(byteBufferAllocate, j4)
        byteBufferAllocate.put(bytes)
        if (!byteBufferAllocate.hasRemaining()) {
            byteBufferAllocate.flip()
            return CentralDirectoryRecord(byteBufferAllocate, ZipUtils.GP_FLAG_EFS, (Short) 8, i, i2, j, j2, j3, j4, str, bytes.length)
        }
        throw RuntimeException("pos: " + byteBufferAllocate.position() + ", limit: " + byteBufferAllocate.limit())
    }

    fun getName(ByteBuffer byteBuffer, Int i, Int i2) {
        Array<Byte> bArrArray
        Int iArrayOffset
        if (byteBuffer.hasArray()) {
            bArrArray = byteBuffer.array()
            iArrayOffset = byteBuffer.arrayOffset() + i
        } else {
            bArrArray = new Byte[i2]
            Int iPosition = byteBuffer.position()
            try {
                byteBuffer.position(i)
                byteBuffer.get(bArrArray)
                byteBuffer.position(iPosition)
                iArrayOffset = 0
            } catch (Throwable th) {
                byteBuffer.position(iPosition)
                throw th
            }
        }
        return String(bArrArray, iArrayOffset, i2, Charset.forName("UTF-8"))
    }

    fun getRecord(ByteBuffer byteBuffer) throws ZipFormatException {
        ZipUtils.assertByteOrderLittleEndian(byteBuffer)
        if (byteBuffer.remaining() < 46) {
            throw ZipFormatException("Input too Short. Need at least: 46 bytes, available: " + byteBuffer.remaining() + " bytes", BufferUnderflowException())
        }
        Int iPosition = byteBuffer.position()
        Int i = byteBuffer.getInt()
        if (i != 33639248) {
            throw ZipFormatException("Not a Central Directory record. Signature: 0x" + Long.toHexString(i & 4294967295L))
        }
        byteBuffer.position(iPosition + 8)
        Short s = byteBuffer.getShort()
        Short s2 = byteBuffer.getShort()
        Int unsignedInt16 = ZipUtils.getUnsignedInt16(byteBuffer)
        Int unsignedInt162 = ZipUtils.getUnsignedInt16(byteBuffer)
        Long unsignedInt32 = ZipUtils.getUnsignedInt32(byteBuffer)
        Long unsignedInt322 = ZipUtils.getUnsignedInt32(byteBuffer)
        Long unsignedInt323 = ZipUtils.getUnsignedInt32(byteBuffer)
        Int unsignedInt163 = ZipUtils.getUnsignedInt16(byteBuffer)
        Int unsignedInt164 = ZipUtils.getUnsignedInt16(byteBuffer)
        Int unsignedInt165 = ZipUtils.getUnsignedInt16(byteBuffer)
        byteBuffer.position(iPosition + 42)
        Long unsignedInt324 = ZipUtils.getUnsignedInt32(byteBuffer)
        byteBuffer.position(iPosition)
        Int i2 = unsignedInt163 + 46 + unsignedInt164 + unsignedInt165
        if (i2 > byteBuffer.remaining()) {
            throw ZipFormatException("Input too Short. Need: " + i2 + " bytes, available: " + byteBuffer.remaining() + " bytes", BufferUnderflowException())
        }
        String name = getName(byteBuffer, iPosition + 46, unsignedInt163)
        byteBuffer.position(iPosition)
        Int iLimit = byteBuffer.limit()
        Int i3 = iPosition + i2
        try {
            byteBuffer.limit(i3)
            ByteBuffer byteBufferSlice = byteBuffer.slice()
            byteBuffer.limit(iLimit)
            byteBuffer.position(i3)
            return CentralDirectoryRecord(byteBufferSlice, s, s2, unsignedInt16, unsignedInt162, unsignedInt32, unsignedInt322, unsignedInt323, unsignedInt324, name, unsignedInt163)
        } catch (Throwable th) {
            byteBuffer.limit(iLimit)
            throw th
        }
    }

    fun copyTo(ByteBuffer byteBuffer) {
        byteBuffer.put(this.mData.slice())
    }

    fun createWithModifiedLocalFileHeaderOffset(Long j) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(this.mData.remaining())
        byteBufferAllocate.put(this.mData.slice())
        byteBufferAllocate.flip()
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        ZipUtils.setUnsignedInt32(byteBufferAllocate, 42, j)
        return CentralDirectoryRecord(byteBufferAllocate, this.mGpFlags, this.mCompressionMethod, this.mLastModificationTime, this.mLastModificationDate, this.mCrc32, this.mCompressedSize, this.mUncompressedSize, j, this.mName, this.mNameSizeBytes)
    }

    fun getCompressedSize() {
        return this.mCompressedSize
    }

    fun getCompressionMethod() {
        return this.mCompressionMethod
    }

    fun getCrc32() {
        return this.mCrc32
    }

    fun getGpFlags() {
        return this.mGpFlags
    }

    fun getLastModificationDate() {
        return this.mLastModificationDate
    }

    fun getLastModificationTime() {
        return this.mLastModificationTime
    }

    fun getLocalFileHeaderOffset() {
        return this.mLocalFileHeaderOffset
    }

    fun getName() {
        return this.mName
    }

    fun getNameSizeBytes() {
        return this.mNameSizeBytes
    }

    fun getSize() {
        return this.mData.remaining()
    }

    fun getUncompressedSize() {
        return this.mUncompressedSize
    }
}
