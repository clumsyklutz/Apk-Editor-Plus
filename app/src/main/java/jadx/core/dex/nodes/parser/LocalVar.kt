package jadx.core.dex.nodes.parser

import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.DexNode
import jadx.core.utils.InsnUtils
import org.d.b
import org.d.c

final class LocalVar {
    private static val LOG = c.a(LocalVar.class)
    private Int endAddr
    private Boolean isEnd
    private String name
    private final Int regNum
    private Int startAddr
    private ArgType type

    constructor(RegisterArg registerArg) {
        this.regNum = registerArg.getRegNum()
        init(registerArg.getName(), registerArg.getType(), null)
    }

    constructor(DexNode dexNode, Int i, Int i2, Int i3, Int i4) {
        this.regNum = i
        init(i2 == -1 ? null : dexNode.getString(i2), i3 == -1 ? null : dexNode.getType(i3), i4 != -1 ? dexNode.getString(i4) : null)
    }

    private fun checkSignature(ArgType argType, String str, ArgType argType2) {
        ArgType arrayRootElement = argType2.getArrayRootElement()
        if (!arrayRootElement.isGeneric()) {
            return arrayRootElement.isGenericType()
        }
        if (!argType.getArrayRootElement().getObject().equals(arrayRootElement.getObject())) {
            LOG.b("Generic type in debug info not equals: {} != {}", argType, argType2)
        }
        return true
    }

    private fun init(String str, ArgType argType, String str2) {
        if (str2 != null) {
            try {
                ArgType argTypeGeneric = ArgType.generic(str2)
                if (checkSignature(argType, str2, argTypeGeneric)) {
                    argType = argTypeGeneric
                }
            } catch (Exception e) {
                LOG.c("Can't parse signature for local variable: {}", str2, e)
            }
        }
        this.name = str
        this.type = argType
    }

    public final Boolean end(Int i, Int i2) {
        if (this.isEnd) {
            return false
        }
        this.isEnd = true
        this.endAddr = i
        return true
    }

    public final Boolean equals(Object obj) {
        return super.equals(obj)
    }

    public final Int getEndAddr() {
        return this.endAddr
    }

    public final String getName() {
        return this.name
    }

    public final Int getRegNum() {
        return this.regNum
    }

    public final Int getStartAddr() {
        return this.startAddr
    }

    public final ArgType getType() {
        return this.type
    }

    public final Int hashCode() {
        return super.hashCode()
    }

    public final Boolean isEnd() {
        return this.isEnd
    }

    public final Unit start(Int i, Int i2) {
        this.isEnd = false
        this.startAddr = i
    }

    public final String toString() {
        return super.toString() + " " + (this.isEnd ? "end: " + InsnUtils.formatOffset(this.startAddr) + "-" + InsnUtils.formatOffset(this.endAddr) : "active: " + InsnUtils.formatOffset(this.startAddr))
    }
}
