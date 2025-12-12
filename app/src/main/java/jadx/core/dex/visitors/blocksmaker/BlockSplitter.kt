package jadx.core.dex.visitors.blocksmaker

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.nodes.JumpInfo
import jadx.core.dex.instructions.IfNode
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.trycatch.CatchAttr
import jadx.core.dex.trycatch.ExceptionHandler
import jadx.core.dex.trycatch.SplitterBlockAttr
import jadx.core.dex.visitors.AbstractVisitor
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.EnumSet
import java.util.HashMap
import java.util.Iterator
import java.util.Map
import java.util.Set

class BlockSplitter extends AbstractVisitor {
    private static val SEPARATE_INSNS = EnumSet.of(InsnType.RETURN, InsnType.IF, InsnType.SWITCH, InsnType.MONITOR_ENTER, InsnType.MONITOR_EXIT, InsnType.THROW)

    static Unit connect(BlockNode blockNode, BlockNode blockNode2) {
        if (!blockNode.getSuccessors().contains(blockNode2)) {
            blockNode.getSuccessors().add(blockNode2)
        }
        if (blockNode2.getPredecessors().contains(blockNode)) {
            return
        }
        blockNode2.getPredecessors().add(blockNode)
    }

    private fun connectExceptionHandlers(Map map, BlockNode blockNode, InsnNode insnNode) {
        CatchAttr catchAttr = (CatchAttr) insnNode.get(AType.CATCH_BLOCK)
        SplitterBlockAttr splitterBlockAttr = (SplitterBlockAttr) blockNode.get(AType.SPLITTER_BLOCK)
        if (catchAttr == null || splitterBlockAttr == null) {
            return
        }
        BlockNode block = splitterBlockAttr.getBlock()
        Boolean zContains = insnNode.contains(AFlag.TRY_LEAVE)
        Iterator it = catchAttr.getTryBlock().getHandlers().iterator()
        while (it.hasNext()) {
            BlockNode block2 = getBlock(((ExceptionHandler) it.next()).getHandleOffset(), map)
            if (block != block2) {
                if (!block2.contains(AType.SPLITTER_BLOCK)) {
                    block2.addAttr(splitterBlockAttr)
                }
                connect(block, block2)
            }
            if (zContains) {
                connect(blockNode, block2)
            }
        }
    }

    private fun getBlock(Int i, Map map) {
        BlockNode blockNode = (BlockNode) map.get(Integer.valueOf(i))
        if (blockNode == null) {
            throw JadxRuntimeException("Missing block: " + i)
        }
        return blockNode
    }

    static BlockNode insertBlockBetween(MethodNode methodNode, BlockNode blockNode, BlockNode blockNode2) {
        BlockNode blockNodeStartNewBlock = startNewBlock(methodNode, blockNode2.getStartOffset())
        blockNodeStartNewBlock.add(AFlag.SYNTHETIC)
        removeConnection(blockNode, blockNode2)
        connect(blockNode, blockNodeStartNewBlock)
        connect(blockNodeStartNewBlock, blockNode2)
        return blockNodeStartNewBlock
    }

    private fun isDoWhile(Map map, BlockNode blockNode, InsnNode insnNode) {
        return insnNode.getType() == InsnType.IF && ((BlockNode) map.get(Integer.valueOf(((IfNode) insnNode).getTarget()))) == blockNode
    }

    private fun isSplitByJump(InsnNode insnNode, InsnNode insnNode2) {
        Iterator it = insnNode.getAll(AType.JUMP).iterator()
        while (it.hasNext()) {
            if (((JumpInfo) it.next()).getSrc() == insnNode.getOffset()) {
                return true
            }
        }
        Iterator it2 = insnNode2.getAll(AType.JUMP).iterator()
        while (it2.hasNext()) {
            if (((JumpInfo) it2.next()).getDest() == insnNode2.getOffset()) {
                return true
            }
        }
        return false
    }

    static Unit removeConnection(BlockNode blockNode, BlockNode blockNode2) {
        blockNode.getSuccessors().remove(blockNode2)
        blockNode2.getPredecessors().remove(blockNode)
    }

    private fun removeInsns(MethodNode methodNode) {
        Iterator it = methodNode.getBasicBlocks().iterator()
        while (it.hasNext()) {
            Iterator it2 = ((BlockNode) it.next()).getInstructions().iterator()
            while (it2.hasNext()) {
                InsnType type = ((InsnNode) it2.next()).getType()
                if (type == InsnType.GOTO || type == InsnType.NOP) {
                    it2.remove()
                }
            }
        }
    }

    private fun setupConnections(MethodNode methodNode, Map map) {
        for (BlockNode blockNode : methodNode.getBasicBlocks()) {
            for (InsnNode insnNode : blockNode.getInstructions()) {
                for (JumpInfo jumpInfo : insnNode.getAll(AType.JUMP)) {
                    connect(getBlock(jumpInfo.getSrc(), map), getBlock(jumpInfo.getDest(), map))
                }
                connectExceptionHandlers(map, blockNode, insnNode)
            }
        }
    }

    private fun splitBasicBlocks(MethodNode methodNode) {
        Boolean z
        BlockNode blockNodeStartNewBlock
        InsnNode insnNode = null
        HashMap map = HashMap()
        BlockNode blockNodeStartNewBlock2 = startNewBlock(methodNode, 0)
        methodNode.setEnterBlock(blockNodeStartNewBlock2)
        Array<InsnNode> instructions = methodNode.getInstructions()
        Int length = instructions.length
        Int i = 0
        while (i < length) {
            InsnNode insnNode2 = instructions[i]
            if (insnNode2 != null) {
                if (insnNode != null) {
                    InsnType type = insnNode.getType()
                    if (type == InsnType.GOTO || type == InsnType.THROW || SEPARATE_INSNS.contains(type)) {
                        if (type == InsnType.RETURN || type == InsnType.THROW) {
                            methodNode.addExitBlock(blockNodeStartNewBlock2)
                        }
                        BlockNode blockNodeStartNewBlock3 = startNewBlock(methodNode, insnNode2.getOffset())
                        if (type == InsnType.MONITOR_ENTER || type == InsnType.MONITOR_EXIT) {
                            connect(blockNodeStartNewBlock2, blockNodeStartNewBlock3)
                        }
                        blockNodeStartNewBlock2 = blockNodeStartNewBlock3
                        z = true
                    } else {
                        z = isSplitByJump(insnNode, insnNode2) || SEPARATE_INSNS.contains(insnNode2.getType()) || isDoWhile(map, blockNodeStartNewBlock2, insnNode2) || insnNode.contains(AFlag.TRY_LEAVE) || insnNode.getType() == InsnType.MOVE_EXCEPTION
                        if (z) {
                            BlockNode blockNodeStartNewBlock4 = startNewBlock(methodNode, insnNode2.getOffset())
                            connect(blockNodeStartNewBlock2, blockNodeStartNewBlock4)
                            blockNodeStartNewBlock2 = blockNodeStartNewBlock4
                        }
                    }
                } else {
                    z = false
                }
                if (insnNode2.contains(AFlag.TRY_ENTER)) {
                    if (insnNode2.getOffset() == 0 || z) {
                        blockNodeStartNewBlock = blockNodeStartNewBlock2
                    } else {
                        blockNodeStartNewBlock = startNewBlock(methodNode, insnNode2.getOffset())
                        connect(blockNodeStartNewBlock2, blockNodeStartNewBlock)
                    }
                    map.put(Integer.valueOf(insnNode2.getOffset()), blockNodeStartNewBlock)
                    blockNodeStartNewBlock2 = startNewBlock(methodNode, -1)
                    blockNodeStartNewBlock.add(AFlag.SYNTHETIC)
                    SplitterBlockAttr splitterBlockAttr = SplitterBlockAttr(blockNodeStartNewBlock)
                    blockNodeStartNewBlock2.addAttr(splitterBlockAttr)
                    blockNodeStartNewBlock.addAttr(splitterBlockAttr)
                    connect(blockNodeStartNewBlock, blockNodeStartNewBlock2)
                } else {
                    map.put(Integer.valueOf(insnNode2.getOffset()), blockNodeStartNewBlock2)
                }
                blockNodeStartNewBlock2.getInstructions().add(insnNode2)
            } else {
                insnNode2 = insnNode
            }
            i++
            insnNode = insnNode2
        }
        setupConnections(methodNode, map)
    }

    static BlockNode startNewBlock(MethodNode methodNode, Int i) {
        BlockNode blockNode = BlockNode(methodNode.getBasicBlocks().size(), i)
        methodNode.getBasicBlocks().add(blockNode)
        return blockNode
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        if (methodNode.isNoCode()) {
            return
        }
        methodNode.checkInstructions()
        methodNode.initBasicBlocks()
        splitBasicBlocks(methodNode)
        removeInsns(methodNode)
    }
}
