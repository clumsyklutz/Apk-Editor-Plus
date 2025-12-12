package jadx.core.dex.instructions

import jadx.core.utils.exceptions.JadxRuntimeException

public enum IfOp {
    EQ("=="),
    NE("!="),
    LT("<"),
    LE("<="),
    GT(">"),
    GE(">=")

    private final String symbol

    IfOp(String str) {
        this.symbol = str
    }

    public final String getSymbol() {
        return this.symbol
    }

    public final IfOp invert() {
        switch (this) {
            case EQ:
                return NE
            case NE:
                return EQ
            case LT:
                return GE
            case LE:
                return GT
            case GT:
                return LE
            case GE:
                return LT
            default:
                throw JadxRuntimeException("Unknown if operations type: " + this)
        }
    }
}
