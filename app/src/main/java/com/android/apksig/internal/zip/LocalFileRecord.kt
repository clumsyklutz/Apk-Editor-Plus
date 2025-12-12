package com.android.apksig.internal.zip

import com.android.apksig.internal.util.ByteBufferSink
import com.android.apksig.util.DataSink
import com.android.apksig.util.DataSource
import com.android.apksig.zip.ZipFormatException
import java.io.Closeable
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.Charset
import java.util.zip.DataFormatException
import java.util.zip.Inflater

class LocalFileRecord {
    public static val EMPTY_BYTE_BUFFER = ByteBuffer.allocate(0)
    public final Boolean mDataCompressed
    public final Long mDataSize
    public final Int mDataStartOffset
    public final ByteBuffer mExtra
    public final String mName
    public final Int mNameSizeBytes
    public final Long mSize
    public final Long mStartOffsetInArchive
    public final Long mUncompressedDataSize

    public static class InflateSinkAdapter implements DataSink, Closeable {
        public Boolean mClosed
        public final DataSink mDelegate
        public Inflater mInflater
        public Array<Byte> mInputBuffer
        public Array<Byte> mOutputBuffer
        public Long mOutputByteCount

        constructor(DataSink dataSink) {
            this.mInflater = Inflater(true)
            this.mDelegate = dataSink
        }

        public final Unit checkNotClosed() {
            if (this.mClosed) {
                throw IllegalStateException("Closed")
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        fun close() throws IOException {
            this.mClosed = true
            this.mInputBuffer = null
            this.mOutputBuffer = null
            Inflater inflater = this.mInflater
            if (inflater != null) {
                inflater.end()
                this.mInflater = null
            }
        }

        @Override // com.android.apksig.util.DataSink
        fun consume(ByteBuffer byteBuffer) throws DataFormatException, IOException {
            checkNotClosed()
            if (byteBuffer.hasArray()) {
                consume(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining())
                byteBuffer.position(byteBuffer.limit())
                return
            }
            if (this.mInputBuffer == null) {
                this.mInputBuffer = new Byte[65536]
            }
            while (byteBuffer.hasRemaining()) {
                Int iMin = Math.min(byteBuffer.remaining(), this.mInputBuffer.length)
                byteBuffer.get(this.mInputBuffer, 0, iMin)
                consume(this.mInputBuffer, 0, iMin)
            }
        }

        @Override // com.android.apksig.util.DataSink
        fun consume(Array<Byte> bArr, Int i, Int i2) throws DataFormatException, IOException {
            checkNotClosed()
            this.mInflater.setInput(bArr, i, i2)
            if (this.mOutputBuffer == null) {
                this.mOutputBuffer = new Byte[65536]
            }
            while (!this.mInflater.finished()) {
                try {
                    Int iInflate = this.mInflater.inflate(this.mOutputBuffer)
                    if (iInflate == 0) {
                        return
                    }
                    this.mDelegate.consume(this.mOutputBuffer, 0, iInflate)
                    this.mOutputByteCount += iInflate
                } catch (DataFormatException e) {
                    throw IOException("Failed to inflate data", e)
                }
            }
        }

        fun getOutputByteCount() {
            return this.mOutputByteCount
        }
    }

    constructor(String str, Int i, ByteBuffer byteBuffer, Long j, Long j2, Int i2, Long j3, Boolean z, Long j4) {
        this.mName = str
        this.mNameSizeBytes = i
        this.mExtra = byteBuffer
        this.mStartOffsetInArchive = j
        this.mSize = j2
        this.mDataStartOffset = i2
        this.mDataSize = j3
        this.mDataCompressed = z
        this.mUncompressedDataSize = j4
    }

    fun getRecord(DataSource dataSource, CentralDirectoryRecord centralDirectoryRecord, Long j) throws IOException, ZipFormatException {
        return getRecord(dataSource, centralDirectoryRecord, j, true, true)
    }

    fun getRecord(DataSource dataSource, CentralDirectoryRecord centralDirectoryRecord, Long j, Boolean z, Boolean z2) throws IOException, ZipFormatException {
        Long j2
        DataSource dataSource2
        String name = centralDirectoryRecord.getName()
        Int nameSizeBytes = centralDirectoryRecord.getNameSizeBytes()
        Int i = nameSizeBytes + 30
        Long localFileHeaderOffset = centralDirectoryRecord.getLocalFileHeaderOffset()
        Long j3 = i + localFileHeaderOffset
        if (j3 > j) {
            throw ZipFormatException("Local File Header of " + name + " extends beyond start of Central Directory. LFH end: " + j3 + ", CD start: " + j)
        }
        try {
            ByteBuffer byteBuffer = dataSource.getByteBuffer(localFileHeaderOffset, i)
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN
            byteBuffer.order(byteOrder)
            Int i2 = byteBuffer.getInt()
            if (i2 != 67324752) {
                throw ZipFormatException("Not a Local File Header record for entry " + name + ". Signature: 0x" + Long.toHexString(i2 & 4294967295L))
            }
            Int i3 = byteBuffer.getShort(6) & 8
            Boolean z3 = i3 != 0
            Boolean z4 = (centralDirectoryRecord.getGpFlags() & 8) != 0
            if (z3 != z4) {
                throw ZipFormatException("Data Descriptor presence mismatch between Local File Header and Central Directory for entry " + name + ". LFH: " + z3 + ", CD: " + z4)
            }
            Boolean z5 = z3
            Long crc32 = centralDirectoryRecord.getCrc32()
            Long compressedSize = centralDirectoryRecord.getCompressedSize()
            Long uncompressedSize = centralDirectoryRecord.getUncompressedSize()
            if (z5) {
                j2 = localFileHeaderOffset
            } else {
                j2 = localFileHeaderOffset
                Long unsignedInt32 = ZipUtils.getUnsignedInt32(byteBuffer, 14)
                if (unsignedInt32 != crc32) {
                    throw ZipFormatException("CRC-32 mismatch between Local File Header and Central Directory for entry " + name + ". LFH: " + unsignedInt32 + ", CD: " + crc32)
                }
                Long unsignedInt322 = ZipUtils.getUnsignedInt32(byteBuffer, 18)
                if (unsignedInt322 != compressedSize) {
                    throw ZipFormatException("Compressed size mismatch between Local File Header and Central Directory for entry " + name + ". LFH: " + unsignedInt322 + ", CD: " + compressedSize)
                }
                Long unsignedInt323 = ZipUtils.getUnsignedInt32(byteBuffer, 22)
                if (unsignedInt323 != uncompressedSize) {
                    throw ZipFormatException("Uncompressed size mismatch between Local File Header and Central Directory for entry " + name + ". LFH: " + unsignedInt323 + ", CD: " + uncompressedSize)
                }
            }
            Int unsignedInt16 = ZipUtils.getUnsignedInt16(byteBuffer, 26)
            if (unsignedInt16 > nameSizeBytes) {
                throw ZipFormatException("Name mismatch between Local File Header and Central Directory for entry" + name + ". LFH: " + unsignedInt16 + " bytes, CD: " + nameSizeBytes + " bytes")
            }
            String name2 = CentralDirectoryRecord.getName(byteBuffer, 30, unsignedInt16)
            if (!name.equals(name2)) {
                throw ZipFormatException("Name mismatch between Local File Header and Central Directory. LFH: \"" + name2 + "\", CD: \"" + name + "\"")
            }
            Int unsignedInt162 = ZipUtils.getUnsignedInt16(byteBuffer, 28)
            Long j4 = j2 + 30 + unsignedInt16
            Long j5 = unsignedInt162 + j4
            Boolean z6 = centralDirectoryRecord.getCompressionMethod() != 0
            Long j6 = z6 ? compressedSize : uncompressedSize
            Long j7 = j5 + j6
            if (j7 > j) {
                throw ZipFormatException("Local File Header data of " + name + " overlaps with Central Directory. LFH data start: " + j5 + ", LFH data end: " + j7 + ", CD start: " + j)
            }
            ByteBuffer byteBuffer2 = EMPTY_BYTE_BUFFER
            if (!z || unsignedInt162 <= 0) {
                dataSource2 = dataSource
            } else {
                dataSource2 = dataSource
                byteBuffer2 = dataSource2.getByteBuffer(j4, unsignedInt162)
            }
            if (z2 && i3 != 0) {
                Long j8 = 12 + j7
                if (j8 > j) {
                    throw ZipFormatException("Data Descriptor of " + name + " overlaps with Central Directory. Data Descriptor end: " + j7 + ", CD start: " + j)
                }
                ByteBuffer byteBuffer3 = dataSource2.getByteBuffer(j7, 4)
                byteBuffer3.order(byteOrder)
                if (byteBuffer3.getInt() == 134695760) {
                    j8 += 4
                    if (j8 > j) {
                        throw ZipFormatException("Data Descriptor of " + name + " overlaps with Central Directory. Data Descriptor end: " + j7 + ", CD start: " + j)
                    }
                }
                j7 = j8
            }
            return LocalFileRecord(name, nameSizeBytes, byteBuffer2, j2, j7 - j2, unsignedInt16 + 30 + unsignedInt162, j6, z6, uncompressedSize)
        } catch (IOException e) {
            throw IOException("Failed to read Local File Header of " + name, e)
        }
    }

    public static Array<Byte> getUncompressedData(DataSource dataSource, CentralDirectoryRecord centralDirectoryRecord, Long j) throws IOException, ZipFormatException {
        if (centralDirectoryRecord.getUncompressedSize() <= 2147483647L) {
            Array<Byte> bArr = new Byte[(Int) centralDirectoryRecord.getUncompressedSize()]
            outputUncompressedData(dataSource, centralDirectoryRecord, j, ByteBufferSink(ByteBuffer.wrap(bArr)))
            return bArr
        }
        throw IOException(centralDirectoryRecord.getName() + " too large: " + centralDirectoryRecord.getUncompressedSize())
    }

    fun outputRecordWithDeflateCompressedData(String str, Int i, Int i2, Array<Byte> bArr, Long j, Long j2, DataSink dataSink) throws IOException {
        Array<Byte> bytes = str.getBytes(Charset.forName("UTF-8"))
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bytes.length + 30)
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        byteBufferAllocate.putInt(67324752)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, 20)
        byteBufferAllocate.putShort(ZipUtils.GP_FLAG_EFS)
        byteBufferAllocate.putShort((Short) 8)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, i)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, i2)
        ZipUtils.putUnsignedInt32(byteBufferAllocate, j)
        ZipUtils.putUnsignedInt32(byteBufferAllocate, bArr.length)
        ZipUtils.putUnsignedInt32(byteBufferAllocate, j2)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, bytes.length)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, 0)
        byteBufferAllocate.put(bytes)
        if (byteBufferAllocate.hasRemaining()) {
            throw RuntimeException("pos: " + byteBufferAllocate.position() + ", limit: " + byteBufferAllocate.limit())
        }
        byteBufferAllocate.flip()
        Long jRemaining = byteBufferAllocate.remaining()
        dataSink.consume(byteBufferAllocate)
        Long length = jRemaining + bArr.length
        dataSink.consume(bArr, 0, bArr.length)
        return length
    }

    fun outputUncompressedData(DataSource dataSource, CentralDirectoryRecord centralDirectoryRecord, Long j, DataSink dataSink) throws IOException, ZipFormatException {
        getRecord(dataSource, centralDirectoryRecord, j, false, false).outputUncompressedData(dataSource, dataSink)
    }

    fun getDataStartOffsetInRecord() {
        return this.mDataStartOffset
    }

    fun getExtra() {
        return this.mExtra.capacity() > 0 ? this.mExtra.slice() : this.mExtra
    }

    fun getExtraFieldStartOffsetInsideRecord() {
        return this.mNameSizeBytes + 30
    }

    fun getName() {
        return this.mName
    }

    fun getSize() {
        return this.mSize
    }

    fun getStartOffsetInArchive() {
        return this.mStartOffsetInArchive
    }

    fun isDataCompressed() {
        return this.mDataCompressed
    }

    fun outputRecord(DataSource dataSource, DataSink dataSink) throws IOException {
        Long size = getSize()
        dataSource.feed(getStartOffsetInArchive(), size, dataSink)
        return size
    }

    fun outputRecordWithModifiedExtra(DataSource dataSource, ByteBuffer byteBuffer, DataSink dataSink) throws IOException {
        Long startOffsetInArchive = getStartOffsetInArchive()
        Int extraFieldStartOffsetInsideRecord = getExtraFieldStartOffsetInsideRecord()
        Int iRemaining = byteBuffer.remaining()
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(extraFieldStartOffsetInsideRecord + iRemaining)
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        dataSource.copyTo(startOffsetInArchive, extraFieldStartOffsetInsideRecord, byteBufferAllocate)
        byteBufferAllocate.put(byteBuffer.slice())
        byteBufferAllocate.flip()
        ZipUtils.setUnsignedInt16(byteBufferAllocate, 28, iRemaining)
        Long jRemaining = byteBufferAllocate.remaining()
        dataSink.consume(byteBufferAllocate)
        Long size = getSize()
        Int i = this.mDataStartOffset
        Long j = size - i
        dataSource.feed(startOffsetInArchive + i, j, dataSink)
        return jRemaining + j
    }

    fun outputUncompressedData(DataSource dataSource, DataSink dataSink) throws IOException, ZipFormatException {
        Long j = this.mStartOffsetInArchive + this.mDataStartOffset
        try {
            if (!this.mDataCompressed) {
                dataSource.feed(j, this.mDataSize, dataSink)
                return
            }
            try {
                InflateSinkAdapter inflateSinkAdapter = InflateSinkAdapter(dataSink)
                try {
                    dataSource.feed(j, this.mDataSize, inflateSinkAdapter)
                    Long outputByteCount = inflateSinkAdapter.getOutputByteCount()
                    if (outputByteCount == this.mUncompressedDataSize) {
                        return
                    }
                    throw ZipFormatException("Unexpected size of uncompressed data of " + this.mName + ". Expected: " + this.mUncompressedDataSize + " bytes, actual: " + outputByteCount + " bytes")
                } finally {
                    try {
                        inflateSinkAdapter.close()
                    } catch (Throwable unused) {
                    }
                }
            } catch (IOException e) {
                if (!(e.getCause() instanceof DataFormatException)) {
                    throw e
                }
                throw ZipFormatException("Data of entry " + this.mName + " malformed", e)
            }
        } catch (IOException e2) {
            StringBuilder sb = StringBuilder()
            sb.append("Failed to read data of ")
            sb.append(this.mDataCompressed ? "compressed" : "uncompressed")
            sb.append(" entry ")
            sb.append(this.mName)
            throw IOException(sb.toString(), e2)
        }
    }
}
