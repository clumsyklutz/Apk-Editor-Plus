package jadx.core.dex.visitors.blocksmaker.helpers

import jadx.core.dex.nodes.BlockNode
import java.util.HashMap
import java.util.HashSet
import java.util.Map
import java.util.Set

class BlocksRemoveInfo {
    private BlocksPair end
    private Int endSplitIndex
    private BlocksPair start
    private BlockNode startPredecessor
    private Int startSplitIndex
    private val processed = HashSet()
    private val outs = HashSet()
    private val regMap = HashMap()

    constructor(BlocksPair blocksPair) {
        this.start = blocksPair
    }

    public final BlockNode getByFirst(BlockNode blockNode) {
        for (BlocksPair blocksPair : this.processed) {
            if (blocksPair.getFirst() == blockNode) {
                return blocksPair.getSecond()
            }
        }
        return null
    }

    public final BlockNode getBySecond(BlockNode blockNode) {
        for (BlocksPair blocksPair : this.processed) {
            if (blocksPair.getSecond() == blockNode) {
                return blocksPair.getSecond()
            }
        }
        return null
    }

    public final BlocksPair getEnd() {
        return this.end
    }

    public final Int getEndSplitIndex() {
        return this.endSplitIndex
    }

    public final Set getOuts() {
        return this.outs
    }

    public final Set getProcessed() {
        return this.processed
    }

    public final Map getRegMap() {
        return this.regMap
    }

    public final BlocksPair getStart() {
        return this.start
    }

    public final BlockNode getStartPredecessor() {
        return this.startPredecessor
    }

    public final Int getStartSplitIndex() {
        return this.startSplitIndex
    }

    public final Unit setEnd(BlocksPair blocksPair) {
        this.end = blocksPair
    }

    public final Unit setEndSplitIndex(Int i) {
        this.endSplitIndex = i
    }

    public final Unit setStart(BlocksPair blocksPair) {
        this.start = blocksPair
    }

    public final Unit setStartPredecessor(BlockNode blockNode) {
        this.startPredecessor = blockNode
    }

    public final Unit setStartSplitIndex(Int i) {
        this.startSplitIndex = i
    }

    public final String toString() {
        return "BRI start: " + this.start + ", end: " + this.end + ", list: " + this.processed + ", outs: " + this.outs + ", regMap: " + this.regMap + ", split: " + this.startSplitIndex
    }
}
