package jadx.core.dex.regions.conditions

import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.IBranchRegion
import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.regions.AbstractRegion
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.ArrayList
import java.util.Collections
import java.util.List

class IfRegion extends AbstractRegion implements IBranchRegion {
    private IfCondition condition
    private IContainer elseRegion
    private final BlockNode header
    private IContainer thenRegion

    constructor(IRegion iRegion, BlockNode blockNode) {
        super(iRegion)
        if (blockNode.getInstructions().size() != 1) {
            throw JadxRuntimeException("Expected only one instruction in 'if' header")
        }
        this.header = blockNode
        this.condition = IfCondition.fromIfBlock(blockNode)
    }

    @Override // jadx.core.dex.nodes.IContainer
    public final String baseString() {
        StringBuilder sb = StringBuilder()
        if (this.thenRegion != null) {
            sb.append(this.thenRegion.baseString())
        }
        if (this.elseRegion != null) {
            sb.append(this.elseRegion.baseString())
        }
        return sb.toString()
    }

    @Override // jadx.core.dex.nodes.IBranchRegion
    public final List getBranches() {
        ArrayList arrayList = ArrayList(2)
        arrayList.add(this.thenRegion)
        arrayList.add(this.elseRegion)
        return Collections.unmodifiableList(arrayList)
    }

    public final IfCondition getCondition() {
        return this.condition
    }

    public final IContainer getElseRegion() {
        return this.elseRegion
    }

    public final BlockNode getHeader() {
        return this.header
    }

    public final Int getSourceLine() {
        if (this.header.getInstructions().isEmpty()) {
            return 0
        }
        return ((InsnNode) this.header.getInstructions().get(0)).getSourceLine()
    }

    @Override // jadx.core.dex.nodes.IRegion
    public final List getSubBlocks() {
        ArrayList arrayList = ArrayList(3)
        arrayList.add(this.header)
        if (this.thenRegion != null) {
            arrayList.add(this.thenRegion)
        }
        if (this.elseRegion != null) {
            arrayList.add(this.elseRegion)
        }
        return Collections.unmodifiableList(arrayList)
    }

    public final IContainer getThenRegion() {
        return this.thenRegion
    }

    public final Unit invert() {
        this.condition = IfCondition.invert(this.condition)
        IContainer iContainer = this.thenRegion
        this.thenRegion = this.elseRegion
        this.elseRegion = iContainer
    }

    @Override // jadx.core.dex.regions.AbstractRegion, jadx.core.dex.nodes.IRegion
    public final Boolean replaceSubBlock(IContainer iContainer, IContainer iContainer2) {
        if (iContainer == this.thenRegion) {
            this.thenRegion = iContainer2
            return true
        }
        if (iContainer != this.elseRegion) {
            return false
        }
        this.elseRegion = iContainer2
        return true
    }

    public final Unit setCondition(IfCondition ifCondition) {
        this.condition = ifCondition
    }

    public final Unit setElseRegion(IContainer iContainer) {
        this.elseRegion = iContainer
    }

    public final Unit setThenRegion(IContainer iContainer) {
        this.thenRegion = iContainer
    }

    public final Boolean simplifyCondition() {
        IfCondition ifConditionSimplify = IfCondition.simplify(this.condition)
        if (ifConditionSimplify == this.condition) {
            return false
        }
        this.condition = ifConditionSimplify
        return true
    }

    public final String toString() {
        return "IF " + this.header + " then " + this.thenRegion + " else " + this.elseRegion
    }
}
