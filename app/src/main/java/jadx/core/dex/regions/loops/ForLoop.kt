package jadx.core.dex.regions.loops

import jadx.core.dex.nodes.InsnNode

class ForLoop extends LoopType {
    private final InsnNode incrInsn
    private final InsnNode initInsn

    constructor(InsnNode insnNode, InsnNode insnNode2) {
        this.initInsn = insnNode
        this.incrInsn = insnNode2
    }

    public final InsnNode getIncrInsn() {
        return this.incrInsn
    }

    public final InsnNode getInitInsn() {
        return this.initInsn
    }
}
