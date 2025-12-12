package jadx.core.dex.visitors.ssa

import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.BitSet
import java.util.List

class LiveVarAnalysis {
    private Array<BitSet> assignBlocks
    private Array<BitSet> defs
    private Array<BitSet> liveIn
    private final MethodNode mth
    private Array<BitSet> uses

    constructor(MethodNode methodNode) {
        this.mth = methodNode
    }

    private fun fillBasicBlockInfo() {
        for (BlockNode blockNode : this.mth.getBasicBlocks()) {
            Int id = blockNode.getId()
            BitSet bitSet = this.uses[id]
            BitSet bitSet2 = this.defs[id]
            for (InsnNode insnNode : blockNode.getInstructions()) {
                for (InsnArg insnArg : insnNode.getArguments()) {
                    if (insnArg.isRegister()) {
                        Int regNum = ((RegisterArg) insnArg).getRegNum()
                        if (!bitSet2.get(regNum)) {
                            bitSet.set(regNum)
                        }
                    }
                }
                RegisterArg result = insnNode.getResult()
                if (result != null) {
                    Int regNum2 = result.getRegNum()
                    bitSet2.set(regNum2)
                    this.assignBlocks[regNum2].set(id)
                }
            }
        }
    }

    private static Array<BitSet> initBitSetArray(Int i, Int i2) {
        Array<BitSet> bitSetArr = new BitSet[i]
        for (Int i3 = 0; i3 < i; i3++) {
            bitSetArr[i3] = BitSet(i2)
        }
        return bitSetArr
    }

    private fun processLiveInfo() {
        Boolean z
        Int size = this.mth.getBasicBlocks().size()
        Int regsCount = this.mth.getRegsCount()
        Array<BitSet> bitSetArrInitBitSetArray = initBitSetArray(size, regsCount)
        List basicBlocks = this.mth.getBasicBlocks()
        Int size2 = basicBlocks.size()
        Int i = 0
        while (true) {
            Int i2 = 0
            Boolean z2 = false
            while (i2 < size2) {
                BlockNode blockNode = (BlockNode) basicBlocks.get(i2)
                Int id = blockNode.getId()
                BitSet bitSet = bitSetArrInitBitSetArray[id]
                BitSet bitSet2 = BitSet(regsCount)
                List successors = blockNode.getSuccessors()
                Int size3 = successors.size()
                for (Int i3 = 0; i3 < size3; i3++) {
                    bitSet2.or(bitSetArrInitBitSetArray[((BlockNode) successors.get(i3)).getId()])
                }
                bitSet2.andNot(this.defs[id])
                bitSet2.or(this.uses[id])
                if (bitSet.equals(bitSet2)) {
                    z = z2
                } else {
                    z = true
                    bitSetArrInitBitSetArray[id] = bitSet2
                }
                i2++
                z2 = z
            }
            Int i4 = i + 1
            if (i > 1000) {
                throw JadxRuntimeException("Live variable analysis reach iterations limit")
            }
            if (!z2) {
                this.liveIn = bitSetArrInitBitSetArray
                return
            }
            i = i4
        }
    }

    fun getAssignBlocks(Int i) {
        return this.assignBlocks[i]
    }

    fun isLive(Int i, Int i2) {
        if (i >= this.liveIn.length) {
            return false
        }
        return this.liveIn[i].get(i2)
    }

    fun isLive(BlockNode blockNode, Int i) {
        return isLive(blockNode.getId(), i)
    }

    fun runAnalysis() {
        Int size = this.mth.getBasicBlocks().size()
        Int regsCount = this.mth.getRegsCount()
        this.uses = initBitSetArray(size, regsCount)
        this.defs = initBitSetArray(size, regsCount)
        this.assignBlocks = initBitSetArray(regsCount, size)
        fillBasicBlockInfo()
        processLiveInfo()
    }
}
