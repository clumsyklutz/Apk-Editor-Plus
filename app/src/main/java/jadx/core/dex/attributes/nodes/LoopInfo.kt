package jadx.core.dex.attributes.nodes

import jadx.core.dex.attributes.AType
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.Edge
import jadx.core.utils.BlockUtils
import java.util.Collections
import java.util.HashSet
import java.util.LinkedList
import java.util.List
import java.util.Set

class LoopInfo {
    private final BlockNode end
    private Int id
    private final Set loopBlocks
    private LoopInfo parentLoop
    private final BlockNode start

    constructor(BlockNode blockNode, BlockNode blockNode2) {
        this.start = blockNode
        this.end = blockNode2
        this.loopBlocks = Collections.unmodifiableSet(BlockUtils.getAllPathsBlocks(blockNode, blockNode2))
    }

    fun getEnd() {
        return this.end
    }

    fun getExitEdges() {
        LinkedList linkedList = LinkedList()
        Set<BlockNode> loopBlocks = getLoopBlocks()
        for (BlockNode blockNode : loopBlocks) {
            for (BlockNode blockNode2 : blockNode.getSuccessors()) {
                if (!loopBlocks.contains(blockNode2) && !blockNode2.contains(AType.EXC_HANDLER)) {
                    linkedList.add(Edge(blockNode, blockNode2))
                }
            }
        }
        return linkedList
    }

    fun getExitNodes() {
        HashSet hashSet = HashSet()
        Set<BlockNode> loopBlocks = getLoopBlocks()
        for (BlockNode blockNode : loopBlocks) {
            for (BlockNode blockNode2 : blockNode.getSuccessors()) {
                if (!loopBlocks.contains(blockNode2) && !blockNode2.contains(AType.EXC_HANDLER)) {
                    hashSet.add(blockNode)
                }
            }
        }
        return hashSet
    }

    fun getId() {
        return this.id
    }

    fun getLoopBlocks() {
        return this.loopBlocks
    }

    fun getParentLoop() {
        return this.parentLoop
    }

    fun getStart() {
        return this.start
    }

    fun setId(Int i) {
        this.id = i
    }

    fun setParentLoop(LoopInfo loopInfo) {
        this.parentLoop = loopInfo
    }

    fun toString() {
        return "LOOP:" + this.id + ": " + this.start + "->" + this.end
    }
}
