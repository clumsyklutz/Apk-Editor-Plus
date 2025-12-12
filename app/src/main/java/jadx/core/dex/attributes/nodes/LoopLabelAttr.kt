package jadx.core.dex.attributes.nodes

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute

class LoopLabelAttr implements IAttribute {
    private final LoopInfo loop

    constructor(LoopInfo loopInfo) {
        this.loop = loopInfo
    }

    fun getLoop() {
        return this.loop
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.LOOP_LABEL
    }

    fun toString() {
        return "LOOP_LABEL: " + this.loop
    }
}
