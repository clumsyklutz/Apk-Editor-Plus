package jadx.core.xmlgen

import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class ParserStream {
    private final InputStream input
    private Long readPos = 0
    protected static val STRING_CHARSET_UTF16 = Charset.forName("UTF-16LE")
    protected static val STRING_CHARSET_UTF8 = Charset.forName("UTF-8")
    private static final Array<Int> EMPTY_INT_ARRAY = new Int[0]
    private static final Array<Byte> EMPTY_BYTE_ARRAY = new Byte[0]

    constructor(InputStream inputStream) {
        this.input = inputStream
    }

    private fun throwException(String str, Int i, Int i2) throws IOException {
        throw IOException(str + ", expected: 0x" + Integer.toHexString(i) + ", actual: 0x" + Integer.toHexString(i2) + ", offset: 0x" + Long.toHexString(getPos()))
    }

    fun checkInt16(Int i, String str) throws IOException {
        Int int16 = readInt16()
        if (int16 != i) {
            throwException(str, i, int16)
        }
    }

    fun checkInt8(Int i, String str) throws IOException {
        Int int8 = readInt8()
        if (int8 != i) {
            throwException(str, i, int8)
        }
    }

    fun checkPos(Long j, String str) throws IOException {
        if (getPos() != j) {
            throw IOException(str + ", expected offset: 0x" + Long.toHexString(j) + ", actual: 0x" + Long.toHexString(getPos()))
        }
    }

    fun getPos() {
        return this.readPos
    }

    fun mark(Int i) throws IOException {
        if (!this.input.markSupported()) {
            throw IOException("Mark not supported for input stream " + this.input.getClass())
        }
        this.input.mark(i)
    }

    fun readInt16() throws IOException {
        this.readPos += 2
        return (this.input.read() & 255) | ((this.input.read() & 255) << 8)
    }

    fun readInt32() throws IOException {
        this.readPos += 4
        InputStream inputStream = this.input
        Int i = inputStream.read()
        return (inputStream.read() << 24) | ((inputStream.read() & 255) << 16) | ((inputStream.read() & 255) << 8) | (i & 255)
    }

    public Array<Int> readInt32Array(Int i) {
        if (i == 0) {
            return EMPTY_INT_ARRAY
        }
        Array<Int> iArr = new Int[i]
        for (Int i2 = 0; i2 < i; i2++) {
            iArr[i2] = readInt32()
        }
        return iArr
    }

    fun readInt8() {
        this.readPos++
        return this.input.read()
    }

    public Array<Byte> readInt8Array(Int i) throws IOException {
        if (i == 0) {
            return EMPTY_BYTE_ARRAY
        }
        this.readPos += i
        Array<Byte> bArr = new Byte[i]
        Int i2 = this.input.read(bArr, 0, i)
        while (i2 < i) {
            Int i3 = this.input.read(bArr, i2, i - i2)
            if (i3 == -1) {
                throw IOException("No data, can't read " + i + " bytes")
            }
            i2 += i3
        }
        return bArr
    }

    fun readString16Fixed(Int i) {
        return String(readInt8Array(i << 1), STRING_CHARSET_UTF16).trim()
    }

    fun readUInt32() {
        return readInt32() & 4294967295L
    }

    fun reset() throws IOException {
        this.input.reset()
    }

    fun skip(Long j) throws IOException {
        this.readPos += j
        Long jSkip = this.input.skip(j)
        while (jSkip < j) {
            Long jSkip2 = this.input.skip(j - jSkip)
            if (jSkip2 == -1) {
                throw IOException("No data, can't skip " + j + " bytes")
            }
            jSkip += jSkip2
        }
    }

    fun skipToPos(Long j, String str) throws IOException {
        Long pos = getPos()
        if (pos < j) {
            skip(j - pos)
        }
        checkPos(j, str)
    }

    fun toString() {
        return "pos: 0x" + Long.toHexString(this.readPos)
    }
}
