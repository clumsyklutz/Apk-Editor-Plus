package org.jf.dexlib2.writer

import java.io.BufferedOutputStream
import java.io.IOException
import java.io.OutputStream
import org.jf.util.ExceptionWithContext

class DexDataWriter extends BufferedOutputStream {
    public Int filePosition
    public Array<Byte> tempBuf
    public Array<Byte> zeroBuf

    constructor(OutputStream outputStream, Int i) {
        this(outputStream, i, 262144)
    }

    constructor(OutputStream outputStream, Int i, Int i2) {
        super(outputStream, i2)
        this.tempBuf = new Byte[8]
        this.zeroBuf = new Byte[3]
        this.filePosition = i
    }

    fun writeInt(OutputStream outputStream, Int i) throws IOException {
        outputStream.write(i)
        outputStream.write(i >> 8)
        outputStream.write(i >> 16)
        outputStream.write(i >> 24)
    }

    fun writeSleb128(OutputStream outputStream, Int i) throws IOException {
        if (i >= 0) {
            while (i > 63) {
                outputStream.write((i & 127) | 128)
                i >>>= 7
            }
            outputStream.write(i & 127)
            return
        }
        while (i < -64) {
            outputStream.write((i & 127) | 128)
            i >>= 7
        }
        outputStream.write(i & 127)
    }

    fun writeUleb128(OutputStream outputStream, Int i) throws IOException {
        while ((i & 4294967295L) > 127) {
            outputStream.write((i & 127) | 128)
            i >>>= 7
        }
        outputStream.write(i)
    }

    fun align() throws IOException {
        Int i = (-getPosition()) & 3
        if (i > 0) {
            write(this.zeroBuf, 0, i)
        }
    }

    fun getPosition() {
        return this.filePosition
    }

    @Override // java.io.BufferedOutputStream, java.io.FilterOutputStream, java.io.OutputStream
    fun write(Int i) throws IOException {
        this.filePosition++
        super.write(i)
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    fun write(Array<Byte> bArr) throws IOException {
        write(bArr, 0, bArr.length)
    }

    @Override // java.io.BufferedOutputStream, java.io.FilterOutputStream, java.io.OutputStream
    fun write(Array<Byte> bArr, Int i, Int i2) throws IOException {
        this.filePosition += i2
        super.write(bArr, i, i2)
    }

    fun writeEncodedDouble(Int i, Double d) throws IOException {
        writeRightZeroExtendedLong(i, Double.doubleToRawLongBits(d))
    }

    fun writeEncodedFloat(Int i, Float f) throws IOException {
        writeRightZeroExtendedInt(i, Float.floatToRawIntBits(f))
    }

    fun writeEncodedInt(Int i, Int i2) throws IOException {
        Int i3 = 0
        if (i2 >= 0) {
            while (i2 > 127) {
                this.tempBuf[i3] = (Byte) i2
                i2 >>= 8
                i3++
            }
        } else {
            while (i2 < -128) {
                this.tempBuf[i3] = (Byte) i2
                i2 >>= 8
                i3++
            }
        }
        Int i4 = i3 + 1
        this.tempBuf[i3] = (Byte) i2
        writeEncodedValueHeader(i, i4 - 1)
        write(this.tempBuf, 0, i4)
    }

    fun writeEncodedLong(Int i, Long j) throws IOException {
        Int i2 = 0
        if (j >= 0) {
            while (j > 127) {
                this.tempBuf[i2] = (Byte) j
                j >>= 8
                i2++
            }
        } else {
            while (j < -128) {
                this.tempBuf[i2] = (Byte) j
                j >>= 8
                i2++
            }
        }
        Int i3 = i2 + 1
        this.tempBuf[i2] = (Byte) j
        writeEncodedValueHeader(i, i3 - 1)
        write(this.tempBuf, 0, i3)
    }

    fun writeEncodedUint(Int i, Int i2) throws IOException {
        Int i3 = 0
        while (true) {
            Int i4 = i3 + 1
            this.tempBuf[i3] = (Byte) i2
            i2 >>>= 8
            if (i2 == 0) {
                writeEncodedValueHeader(i, i4 - 1)
                write(this.tempBuf, 0, i4)
                return
            }
            i3 = i4
        }
    }

    fun writeEncodedValueHeader(Int i, Int i2) throws IOException {
        write(i | (i2 << 5))
    }

    fun writeInt(Int i) throws IOException {
        writeInt(this, i)
    }

    fun writeLong(Long j) throws IOException {
        writeInt((Int) j)
        writeInt((Int) (j >> 32))
    }

    fun writeRightZeroExtendedInt(Int i, Int i2) throws IOException {
        Int i3 = 3
        while (true) {
            Int i4 = i3 - 1
            this.tempBuf[i3] = (Byte) (((-16777216) & i2) >>> 24)
            i2 <<= 8
            if (i2 == 0) {
                Int i5 = i4 + 1
                Int i6 = 4 - i5
                writeEncodedValueHeader(i, i6 - 1)
                write(this.tempBuf, i5, i6)
                return
            }
            i3 = i4
        }
    }

    fun writeRightZeroExtendedLong(Int i, Long j) throws IOException {
        Int i2 = 7
        while (true) {
            Int i3 = i2 - 1
            this.tempBuf[i2] = (Byte) (((-72057594037927936L) & j) >>> 56)
            j <<= 8
            if (j == 0) {
                Int i4 = i3 + 1
                Int i5 = 8 - i4
                writeEncodedValueHeader(i, i5 - 1)
                write(this.tempBuf, i4, i5)
                return
            }
            i2 = i3
        }
    }

    fun writeShort(Int i) throws IOException {
        if (i < -32768 || i > 32767) {
            throw ExceptionWithContext("Short value out of range: %d", Integer.valueOf(i))
        }
        write(i)
        write(i >> 8)
    }

    fun writeSleb128(Int i) throws IOException {
        writeSleb128(this, i)
    }

    fun writeString(String str) throws IOException {
        Int length = str.length()
        if (this.tempBuf.length <= str.length() * 3) {
            this.tempBuf = new Byte[str.length() * 3]
        }
        Array<Byte> bArr = this.tempBuf
        Int i = 0
        for (Int i2 = 0; i2 < length; i2++) {
            Char cCharAt = str.charAt(i2)
            if (cCharAt != 0 && cCharAt < 128) {
                bArr[i] = (Byte) cCharAt
                i++
            } else if (cCharAt < 2048) {
                Int i3 = i + 1
                bArr[i] = (Byte) (((cCharAt >> 6) & 31) | 192)
                i = i3 + 1
                bArr[i3] = (Byte) ((cCharAt & '?') | 128)
            } else {
                Int i4 = i + 1
                bArr[i] = (Byte) (((cCharAt >> '\f') & 15) | 224)
                Int i5 = i4 + 1
                bArr[i4] = (Byte) (((cCharAt >> 6) & 63) | 128)
                bArr[i5] = (Byte) ((cCharAt & '?') | 128)
                i = i5 + 1
            }
        }
        write(bArr, 0, i)
    }

    fun writeUbyte(Int i) throws IOException {
        if (i < 0 || i > 255) {
            throw ExceptionWithContext("Unsigned Byte value out of range: %d", Integer.valueOf(i))
        }
        write(i)
    }

    fun writeUleb128(Int i) throws IOException {
        writeUleb128(this, i)
    }

    fun writeUshort(Int i) throws IOException {
        if (i < 0 || i > 65535) {
            throw ExceptionWithContext("Unsigned Short value out of range: %d", Integer.valueOf(i))
        }
        write(i)
        write(i >> 8)
    }
}
