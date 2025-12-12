package jadx.core.dex.instructions.mods

import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.LiteralArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.regions.conditions.IfCondition
import jadx.core.utils.InsnUtils
import jadx.core.utils.Utils
import java.util.Collection

class TernaryInsn extends InsnNode {
    private IfCondition condition

    constructor(IfCondition ifCondition, RegisterArg registerArg) {
        this(ifCondition, registerArg, LiteralArg.TRUE, LiteralArg.FALSE)
    }

    constructor(IfCondition ifCondition, RegisterArg registerArg, InsnArg insnArg, InsnArg insnArg2) {
        super(InsnType.TERNARY, 2)
        setResult(registerArg)
        if (insnArg.equals(LiteralArg.FALSE) && insnArg2.equals(LiteralArg.TRUE)) {
            this.condition = IfCondition.invert(ifCondition)
            addArg(insnArg2)
            addArg(insnArg)
        } else {
            this.condition = ifCondition
            addArg(insnArg)
            addArg(insnArg2)
        }
    }

    private fun invert() {
        this.condition = IfCondition.invert(this.condition)
        InsnArg arg = getArg(0)
        setArg(0, getArg(1))
        setArg(1, arg)
    }

    public final IfCondition getCondition() {
        return this.condition
    }

    @Override // jadx.core.dex.nodes.InsnNode
    public final Unit getRegisterArgs(Collection collection) {
        super.getRegisterArgs(collection)
        collection.addAll(this.condition.getRegisterArgs())
    }

    @Override // jadx.core.dex.nodes.InsnNode
    public final Boolean isSame(InsnNode insnNode) {
        if (this == insnNode) {
            return true
        }
        if ((insnNode is TernaryInsn) && super.isSame(insnNode)) {
            return this.condition.equals(((TernaryInsn) insnNode).condition)
        }
        return false
    }

    public final Unit simplifyCondition() {
        this.condition = IfCondition.simplify(this.condition)
        if (this.condition.getMode() == IfCondition.Mode.NOT) {
            invert()
        }
    }

    @Override // jadx.core.dex.nodes.InsnNode
    public final String toString() {
        return InsnUtils.formatOffset(this.offset) + ": TERNARY" + getResult() + " = " + Utils.listToString(getArguments())
    }
}
