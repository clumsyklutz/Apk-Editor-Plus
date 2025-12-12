package jadx.core.dex.instructions

import com.a.b.d.a.f
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.PrimitiveType
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.utils.BlockUtils
import jadx.core.utils.InsnUtils

class IfNode extends GotoNode {
    private static val ARG_TYPE = ArgType.unknown(PrimitiveType.INT, PrimitiveType.OBJECT, PrimitiveType.ARRAY, PrimitiveType.BOOLEAN, PrimitiveType.BYTE, PrimitiveType.SHORT, PrimitiveType.CHAR)
    private BlockNode elseBlock
    protected IfOp op
    private BlockNode thenBlock

    constructor(f fVar, IfOp ifOp) {
        this(ifOp, fVar.g(), InsnArg.reg(fVar, 0, ARG_TYPE), fVar.m() == 1 ? InsnArg.lit(0L, ARG_TYPE) : InsnArg.reg(fVar, 1, ARG_TYPE))
    }

    constructor(IfOp ifOp, Int i, InsnArg insnArg, InsnArg insnArg2) {
        super(InsnType.IF, i, 2)
        this.op = ifOp
        addArg(insnArg)
        addArg(insnArg2)
    }

    fun changeCondition(IfOp ifOp, InsnArg insnArg, InsnArg insnArg2) {
        this.op = ifOp
        setArg(0, insnArg)
        setArg(1, insnArg2)
    }

    fun getElseBlock() {
        return this.elseBlock
    }

    fun getOp() {
        return this.op
    }

    fun getThenBlock() {
        return this.thenBlock
    }

    fun initBlocks(BlockNode blockNode) {
        this.thenBlock = BlockUtils.getBlockByOffset(this.target, blockNode.getSuccessors())
        if (blockNode.getSuccessors().size() == 1) {
            this.elseBlock = this.thenBlock
        } else {
            this.elseBlock = BlockUtils.selectOther(this.thenBlock, blockNode.getSuccessors())
        }
    }

    fun invertCondition() {
        this.op = this.op.invert()
        BlockNode blockNode = this.thenBlock
        this.thenBlock = this.elseBlock
        this.elseBlock = blockNode
        this.target = this.thenBlock.getStartOffset()
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun isSame(InsnNode insnNode) {
        if (this == insnNode) {
            return true
        }
        if ((insnNode is IfNode) && super.isSame(insnNode)) {
            return this.op == ((IfNode) insnNode).op
        }
        return false
    }

    @Override // jadx.core.dex.instructions.GotoNode, jadx.core.dex.nodes.InsnNode
    fun toString() {
        return InsnUtils.formatOffset(this.offset) + ": " + InsnUtils.insnTypeToString(this.insnType) + getArg(0) + " " + this.op.getSymbol() + " " + getArg(1) + "  -> " + (this.thenBlock != null ? this.thenBlock : InsnUtils.formatOffset(this.target))
    }
}
