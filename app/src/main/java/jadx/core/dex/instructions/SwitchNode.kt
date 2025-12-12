package jadx.core.dex.instructions

import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.nodes.InsnNode
import jadx.core.utils.InsnUtils
import java.util.Arrays

class SwitchNode extends InsnNode {
    private final Int def
    private final Array<Object> keys
    private final Array<Int> targets

    constructor(InsnArg insnArg, Array<Object> objArr, Array<Int> iArr, Int i) {
        super(InsnType.SWITCH, 1)
        this.keys = objArr
        this.targets = iArr
        this.def = i
        addArg(insnArg)
    }

    fun getCasesCount() {
        return this.keys.length
    }

    fun getDefaultCaseOffset() {
        return this.def
    }

    public Array<Object> getKeys() {
        return this.keys
    }

    public Array<Int> getTargets() {
        return this.targets
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun isSame(InsnNode insnNode) {
        if (this == insnNode) {
            return true
        }
        if (!(insnNode is SwitchNode) || !super.isSame(insnNode)) {
            return false
        }
        SwitchNode switchNode = (SwitchNode) insnNode
        return this.def == switchNode.def && Arrays.equals(this.keys, switchNode.keys) && Arrays.equals(this.targets, switchNode.targets)
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun toString() {
        StringBuilder sb = StringBuilder()
        sb.append('[')
        for (Int i = 0; i < this.targets.length; i++) {
            sb.append(InsnUtils.formatOffset(this.targets[i]))
            if (i < this.targets.length - 1) {
                sb.append(", ")
            }
        }
        sb.append(']')
        return super.toString() + " k:" + Arrays.toString(this.keys) + " t:" + ((Object) sb)
    }
}
