package jadx.core.dex.instructions.args

import jadx.core.codegen.TypeGen
import jadx.core.utils.exceptions.JadxRuntimeException

class LiteralArg extends InsnArg {
    private final Long literal
    public static val TRUE = LiteralArg(1, ArgType.BOOLEAN)
    public static val FALSE = LiteralArg(0, ArgType.BOOLEAN)

    constructor(Long j, ArgType argType) {
        ArgType argTypeMerge
        if (j != 0) {
            if (argType.isObject()) {
                throw JadxRuntimeException("Wrong literal type: " + argType + " for value: " + j)
            }
            if (!argType.isTypeKnown() && !argType.contains(PrimitiveType.LONG) && !argType.contains(PrimitiveType.DOUBLE) && (argTypeMerge = ArgType.merge(null, argType, ArgType.NARROW_NUMBERS)) != null) {
                argType = argTypeMerge
            }
        }
        this.literal = j
        this.type = argType
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        LiteralArg literalArg = (LiteralArg) obj
        return this.literal == literalArg.literal && getType().equals(literalArg.getType())
    }

    public final Long getLiteral() {
        return this.literal
    }

    public final Int hashCode() {
        return ((Int) (this.literal ^ (this.literal >>> 32))) + (getType().hashCode() * 31)
    }

    public final Boolean isInteger() {
        PrimitiveType primitiveType = this.type.getPrimitiveType()
        return primitiveType == PrimitiveType.INT || primitiveType == PrimitiveType.BYTE || primitiveType == PrimitiveType.CHAR || primitiveType == PrimitiveType.SHORT || primitiveType == PrimitiveType.LONG
    }

    @Override // jadx.core.dex.instructions.args.InsnArg
    public final Boolean isLiteral() {
        return true
    }

    public final String toString() {
        try {
            String strLiteralToString = TypeGen.literalToString(this.literal, getType())
            return (getType().equals(ArgType.BOOLEAN) && (strLiteralToString.equals("true") || strLiteralToString.equals("false"))) ? strLiteralToString : "(" + strLiteralToString + " " + this.type + ")"
        } catch (JadxRuntimeException e) {
            return "(" + this.literal + " " + this.type + ")"
        }
    }
}
