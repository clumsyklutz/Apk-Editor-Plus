package jadx.core.dex.attributes.nodes

import jadx.core.utils.InsnUtils

class JumpInfo {
    private final Int dest
    private final Int src

    constructor(Int i, Int i2) {
        this.src = i
        this.dest = i2
    }

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj != null && getClass() == obj.getClass()) {
            JumpInfo jumpInfo = (JumpInfo) obj
            return this.dest == jumpInfo.dest && this.src == jumpInfo.src
        }
        return false
    }

    fun getDest() {
        return this.dest
    }

    fun getSrc() {
        return this.src
    }

    fun hashCode() {
        return (this.dest * 31) + this.src
    }

    fun toString() {
        return "JUMP: " + InsnUtils.formatOffset(this.src) + " -> " + InsnUtils.formatOffset(this.dest)
    }
}
