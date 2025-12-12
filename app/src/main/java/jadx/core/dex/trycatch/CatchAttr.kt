package jadx.core.dex.trycatch

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute

class CatchAttr implements IAttribute {
    private final TryCatchBlock tryBlock

    constructor(TryCatchBlock tryCatchBlock) {
        this.tryBlock = tryCatchBlock
    }

    fun getTryBlock() {
        return this.tryBlock
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.CATCH_BLOCK
    }

    fun toString() {
        return this.tryBlock.toString()
    }
}
