package jadx.core.dex.nodes.parser

import com.a.a.o
import com.gmail.heagoo.a.c.a
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.info.MethodInfo
import jadx.core.dex.nodes.DexNode
import jadx.core.utils.exceptions.DecodeException
import java.util.ArrayList

class EncValueParser {
    private static val ENCODED_ANNOTATION = 29
    private static val ENCODED_ARRAY = 28
    private static val ENCODED_BOOLEAN = 31
    private static val ENCODED_BYTE = 0
    private static val ENCODED_CHAR = 3
    private static val ENCODED_DOUBLE = 17
    private static val ENCODED_ENUM = 27
    private static val ENCODED_FIELD = 25
    private static val ENCODED_FLOAT = 16
    private static val ENCODED_INT = 4
    private static val ENCODED_LONG = 6
    private static val ENCODED_METHOD = 26
    private static val ENCODED_NULL = 30
    private static val ENCODED_SHORT = 2
    private static val ENCODED_STRING = 23
    private static val ENCODED_TYPE = 24
    private final DexNode dex
    protected final o in

    constructor(DexNode dexNode, o oVar) {
        this.in = oVar
        this.dex = dexNode
    }

    private fun parseNumber(Int i, Boolean z) {
        return parseNumber(i, z, 0)
    }

    private fun parseNumber(Int i, Boolean z, Int i2) {
        Int i3 = 0
        Long j = 0
        Long j2 = 0
        while (i3 < i) {
            j = readByte()
            Long j3 = (j << (i3 << 3)) | j2
            i3++
            j2 = j3
        }
        if (i2 != 0) {
            while (i < i2) {
                j2 <<= 8
                i++
            }
        } else if (z && (128 & j) != 0) {
            while (i < 8) {
                j2 |= 255 << (i << 3)
                i++
            }
        }
        return j2
    }

    private fun parseUnsignedInt(Int i) {
        return (Int) parseNumber(i, false, 0)
    }

    private fun readByte() {
        return this.in.d() & 255
    }

    fun parseValue() throws DecodeException {
        Int i = readByte()
        Int i2 = i & 31
        Int i3 = (i & 224) >> 5
        Int i4 = i3 + 1
        switch (i2) {
            case 0:
                return Byte.valueOf(this.in.d())
            case 1:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            default:
                throw DecodeException("Unknown encoded value type: 0x" + Integer.toHexString(i2))
            case 2:
                return Short.valueOf((Short) parseNumber(i4, true))
            case 3:
                return Character.valueOf((Char) parseUnsignedInt(i4))
            case 4:
                return Integer.valueOf((Int) parseNumber(i4, true))
            case 6:
                return Long.valueOf(parseNumber(i4, true))
            case 16:
                return Float.valueOf(Float.intBitsToFloat((Int) parseNumber(i4, false, 4)))
            case 17:
                return Double.valueOf(Double.longBitsToDouble(parseNumber(i4, false, 8)))
            case 23:
                return this.dex.getString(parseUnsignedInt(i4))
            case 24:
                return this.dex.getType(parseUnsignedInt(i4))
            case 25:
            case 27:
                return FieldInfo.fromDex(this.dex, parseUnsignedInt(i4))
            case 26:
                return MethodInfo.fromDex(this.dex, parseUnsignedInt(i4))
            case 28:
                Int iB = a.b(this.in)
                ArrayList arrayList = ArrayList(iB)
                for (Int i5 = 0; i5 < iB; i5++) {
                    arrayList.add(parseValue())
                }
                return arrayList
            case 29:
                return AnnotationsParser.readAnnotation(this.dex, this.in, false)
            case 30:
                return null
            case 31:
                return Boolean.valueOf(i3 == 1)
        }
    }
}
