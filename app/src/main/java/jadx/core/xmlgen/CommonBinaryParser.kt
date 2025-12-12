package jadx.core.xmlgen

import java.io.IOException
import java.util.Arrays

class CommonBinaryParser extends ParserConstants {
    protected ParserStream is

    private fun extractString16(Array<Byte> bArr, Int i) {
        Int length = bArr.length
        Int iSkipStrLen16 = i + skipStrLen16(bArr, i)
        Int i2 = iSkipStrLen16
        while (i2 + 1 < length && (bArr[i2] != 0 || bArr[i2 + 1] != 0)) {
            i2 += 2
        }
        return String(Arrays.copyOfRange(bArr, iSkipStrLen16, i2), ParserStream.STRING_CHARSET_UTF16)
    }

    private fun extractString8(Array<Byte> bArr, Int i) {
        Int i2
        Int iSkipStrLen8 = skipStrLen8(bArr, i) + i
        Int i3 = iSkipStrLen8 + 1
        Int i4 = bArr[iSkipStrLen8]
        if (i4 == 0) {
            return ""
        }
        if ((i4 & 128) != 0) {
            i2 = i3 + 1
            i4 = ((i4 & 127) << 8) | (bArr[i3] & 255)
        } else {
            i2 = i3
        }
        return String(Arrays.copyOfRange(bArr, i2, i4 + i2), ParserStream.STRING_CHARSET_UTF8)
    }

    private fun skipStrLen16(Array<Byte> bArr, Int i) {
        return (bArr[i + 1] & 128) == 0 ? 2 : 4
    }

    private fun skipStrLen8(Array<Byte> bArr, Int i) {
        return (bArr[i] & 128) == 0 ? 1 : 2
    }

    protected fun die(String str) throws IOException {
        throw IOException("Decode error: " + str + ", position: 0x" + Long.toHexString(this.is.getPos()))
    }

    protected Array<String> parseStringPool() throws IOException {
        this.is.checkInt16(1, "String pool expected")
        return parseStringPoolNoType()
    }

    protected Array<String> parseStringPoolNoType() throws IOException {
        Long pos = this.is.getPos() - 2
        this.is.checkInt16(28, "String pool header size not 0x001c")
        Long uInt32 = pos + this.is.readUInt32()
        Int int32 = this.is.readInt32()
        Int int322 = this.is.readInt32()
        Int int323 = this.is.readInt32()
        Long int324 = this.is.readInt32()
        Long int325 = this.is.readInt32()
        Array<Int> int32Array = this.is.readInt32Array(int32)
        this.is.readInt32Array(int322)
        this.is.checkPos(int324 + pos, "Expected strings start")
        Array<String> strArr = new String[int32]
        Array<Byte> int8Array = this.is.readInt8Array((Int) ((int325 == 0 ? uInt32 : pos + int325) - this.is.getPos()))
        if ((int323 & 256) != 0) {
            for (Int i = 0; i < int32; i++) {
                strArr[i] = extractString8(int8Array, int32Array[i])
            }
        } else {
            for (Int i2 = 0; i2 < int32; i2++) {
                strArr[i2] = extractString16(int8Array, int32Array[i2])
            }
        }
        if (int325 != 0) {
            this.is.checkPos(pos + int325, "Expected styles start")
        }
        this.is.skipToPos(uInt32, "Skip string pool padding")
        return strArr
    }
}
