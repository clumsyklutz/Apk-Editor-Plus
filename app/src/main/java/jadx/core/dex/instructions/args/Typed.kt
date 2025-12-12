package jadx.core.dex.instructions.args

import jadx.core.dex.attributes.AttrNode
import jadx.core.dex.nodes.DexNode

abstract class Typed extends AttrNode {
    protected ArgType type

    fun getType() {
        return this.type
    }

    fun isTypeImmutable() {
        return false
    }

    fun merge(DexNode dexNode, ArgType argType) {
        ArgType argTypeMerge = ArgType.merge(dexNode, this.type, argType)
        if (argTypeMerge == null || argTypeMerge.equals(this.type)) {
            return false
        }
        setType(argTypeMerge)
        return true
    }

    fun merge(DexNode dexNode, InsnArg insnArg) {
        return merge(dexNode, insnArg.getType())
    }

    fun setType(ArgType argType) {
        this.type = argType
    }
}
