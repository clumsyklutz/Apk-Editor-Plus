package jadx.core.dex.nodes

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.AttrNode
import jadx.core.dex.attributes.nodes.IgnoreEdgeAttr
import jadx.core.dex.attributes.nodes.LoopInfo
import jadx.core.utils.BlockUtils
import jadx.core.utils.EmptyBitSet
import jadx.core.utils.InsnUtils
import java.util.ArrayList
import java.util.BitSet
import java.util.Collections
import java.util.Iterator
import java.util.LinkedList
import java.util.List

class BlockNode extends AttrNode implements IBlock {
    private List cleanSuccessors
    private BitSet domFrontier
    private Int id
    private BlockNode idom
    private final Int startOffset
    private val instructions = ArrayList(2)
    private List predecessors = ArrayList(1)
    private List successors = ArrayList(1)
    private BitSet doms = EmptyBitSet.EMPTY
    private List dominatesOn = Collections.emptyList()

    constructor(Int i, Int i2) {
        this.id = i
        this.startOffset = i2
    }

    private fun cleanSuccessors(BlockNode blockNode) {
        List<BlockNode> successors = blockNode.getSuccessors()
        if (successors.isEmpty()) {
            return successors
        }
        LinkedList linkedList = LinkedList()
        for (BlockNode blockNode2 : successors) {
            if (BlockUtils.isBlockMustBeCleared(blockNode2)) {
                linkedList.add(blockNode2)
            }
        }
        if (blockNode.contains(AFlag.LOOP_END)) {
            Iterator it = blockNode.getAll(AType.LOOP).iterator()
            while (it.hasNext()) {
                linkedList.add(((LoopInfo) it.next()).getStart())
            }
        }
        IgnoreEdgeAttr ignoreEdgeAttr = (IgnoreEdgeAttr) blockNode.get(AType.IGNORE_EDGE)
        if (ignoreEdgeAttr != null) {
            linkedList.addAll(ignoreEdgeAttr.getBlocks())
        }
        if (linkedList.isEmpty()) {
            return successors
        }
        ArrayList arrayList = ArrayList(successors)
        arrayList.removeAll(linkedList)
        return arrayList
    }

    fun addDominatesOn(BlockNode blockNode) {
        if (this.dominatesOn.isEmpty()) {
            this.dominatesOn = LinkedList()
        }
        this.dominatesOn.add(blockNode)
    }

    @Override // jadx.core.dex.nodes.IContainer
    fun baseString() {
        return Integer.toString(this.id)
    }

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (!(obj is BlockNode)) {
            return false
        }
        BlockNode blockNode = (BlockNode) obj
        return this.id == blockNode.id && this.startOffset == blockNode.startOffset
    }

    fun getCleanSuccessors() {
        return this.cleanSuccessors
    }

    fun getDomFrontier() {
        return this.domFrontier
    }

    fun getDominatesOn() {
        return this.dominatesOn
    }

    fun getDoms() {
        return this.doms
    }

    fun getIDom() {
        return this.idom
    }

    fun getId() {
        return this.id
    }

    @Override // jadx.core.dex.nodes.IBlock
    fun getInstructions() {
        return this.instructions
    }

    fun getPredecessors() {
        return this.predecessors
    }

    fun getStartOffset() {
        return this.startOffset
    }

    fun getSuccessors() {
        return this.successors
    }

    fun hashCode() {
        return this.startOffset
    }

    fun isDominator(BlockNode blockNode) {
        return this.doms.get(blockNode.getId())
    }

    fun isReturnBlock() {
        return contains(AFlag.RETURN)
    }

    fun isSynthetic() {
        return contains(AFlag.SYNTHETIC)
    }

    fun lock() {
        this.cleanSuccessors = lockList(this.cleanSuccessors)
        this.successors = lockList(this.successors)
        this.predecessors = lockList(this.predecessors)
        this.dominatesOn = lockList(this.dominatesOn)
    }

    List lockList(List list) {
        return list.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(list)
    }

    fun setDomFrontier(BitSet bitSet) {
        this.domFrontier = bitSet
    }

    fun setDoms(BitSet bitSet) {
        this.doms = bitSet
    }

    fun setIDom(BlockNode blockNode) {
        this.idom = blockNode
    }

    fun setId(Int i) {
        this.id = i
    }

    fun toString() {
        return "B:" + this.id + ":" + InsnUtils.formatOffset(this.startOffset)
    }

    fun updateCleanSuccessors() {
        this.cleanSuccessors = cleanSuccessors(this)
    }
}
