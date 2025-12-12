package jadx.core.codegen

import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.PrimitiveType
import jadx.core.utils.StringUtils
import jadx.core.utils.Utils
import jadx.core.utils.exceptions.JadxRuntimeException

class TypeGen {
    private constructor() {
    }

    fun formatByte(Byte b2) {
        return b2 == 127 ? "Byte.MAX_VALUE" : b2 == -128 ? "Byte.MIN_VALUE" : "(Byte) " + Byte.toString(b2)
    }

    fun formatDouble(Double d) {
        return Double.isNaN(d) ? "Double.NaN" : d == Double.NEGATIVE_INFINITY ? "Double.NEGATIVE_INFINITY" : d == Double.POSITIVE_INFINITY ? "Double.POSITIVE_INFINITY" : d == Double.MIN_VALUE ? "Double.MIN_VALUE" : d == Double.MAX_VALUE ? "Double.MAX_VALUE" : d == Double.MIN_NORMAL ? "Double.MIN_NORMAL" : Double.toString(d) + "d"
    }

    fun formatFloat(Float f) {
        return Float.isNaN(f) ? "Float.NaN" : f == Float.NEGATIVE_INFINITY ? "Float.NEGATIVE_INFINITY" : f == Float.POSITIVE_INFINITY ? "Float.POSITIVE_INFINITY" : f == Float.MIN_VALUE ? "Float.MIN_VALUE" : f == Float.MAX_VALUE ? "Float.MAX_VALUE" : f == Float.MIN_NORMAL ? "Float.MIN_NORMAL" : Float.toString(f) + "f"
    }

    fun formatInteger(Int i) {
        return i == Integer.MAX_VALUE ? "Integer.MAX_VALUE" : i == Integer.MIN_VALUE ? "Integer.MIN_VALUE" : Integer.toString(i)
    }

    fun formatLong(Long j) {
        if (j == Long.MAX_VALUE) {
            return "Long.MAX_VALUE"
        }
        if (j == Long.MIN_VALUE) {
            return "Long.MIN_VALUE"
        }
        String string = Long.toString(j)
        return Math.abs(j) >= 2147483647L ? string + "L" : string
    }

    fun formatShort(Short s) {
        return s == Short.MAX_VALUE ? "Short.MAX_VALUE" : s == Short.MIN_VALUE ? "Short.MIN_VALUE" : "(Short) " + Short.toString(s)
    }

    fun literalToString(Long j, ArgType argType) {
        if (argType == null || !argType.isTypeKnown()) {
            String string = Long.toString(j)
            return Math.abs(j) > 100 ? string + "; // 0x" + Long.toHexString(j) + " Float:" + Float.intBitsToFloat((Int) j) + " Double:" + Double.longBitsToDouble(j) : string
        }
        switch (argType.getPrimitiveType()) {
            case BOOLEAN:
                return j == 0 ? "false" : "true"
            case CHAR:
                return StringUtils.unescapeChar((Char) j)
            case BYTE:
                return formatByte((Byte) j)
            case SHORT:
                return formatShort((Short) j)
            case INT:
                return formatInteger((Int) j)
            case LONG:
                return formatLong(j)
            case FLOAT:
                return formatFloat(Float.intBitsToFloat((Int) j))
            case DOUBLE:
                return formatDouble(Double.longBitsToDouble(j))
            case OBJECT:
            case ARRAY:
                return j != 0 ? Long.toString(j) : "null"
            default:
                throw JadxRuntimeException("Unknown type in literalToString: " + argType)
        }
    }

    fun signature(ArgType argType) {
        PrimitiveType primitiveType = argType.getPrimitiveType()
        return primitiveType == PrimitiveType.OBJECT ? Utils.makeQualifiedObjectName(argType.getObject()) : primitiveType == PrimitiveType.ARRAY ? "[" + signature(argType.getArrayElement()) : primitiveType.getShortName()
    }
}
