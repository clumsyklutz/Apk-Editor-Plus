package jadx.core.dex.visitors.blocksmaker

import jadx.core.dex.attributes.AType
import jadx.core.dex.instructions.IfNode
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.trycatch.ExcHandlerAttr
import jadx.core.dex.trycatch.SplitterBlockAttr
import jadx.core.dex.visitors.AbstractVisitor
import jadx.core.utils.BlockUtils
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map

class BlockFinish extends AbstractVisitor {
    private fun fixSplitterBlock(BlockNode blockNode) {
        BlockNode topBlock
        ExcHandlerAttr excHandlerAttr = (ExcHandlerAttr) blockNode.get(AType.EXC_HANDLER)
        if (excHandlerAttr == null) {
            return
        }
        BlockNode handlerBlock = excHandlerAttr.getHandler().getHandlerBlock()
        if (handlerBlock.getPredecessors().size() >= 2) {
            HashMap map = HashMap()
            Iterator it = handlerBlock.getPredecessors().iterator()
            while (it.hasNext()) {
                BlockNode blockNodeSkipSyntheticPredecessor = BlockUtils.skipSyntheticPredecessor((BlockNode) it.next())
                SplitterBlockAttr splitterBlockAttr = (SplitterBlockAttr) blockNodeSkipSyntheticPredecessor.get(AType.SPLITTER_BLOCK)
                if (splitterBlockAttr != null && blockNodeSkipSyntheticPredecessor == splitterBlockAttr.getBlock()) {
                    map.put(blockNodeSkipSyntheticPredecessor, splitterBlockAttr)
                }
            }
            if (map.size() < 2 || (topBlock = BlockUtils.getTopBlock(map.keySet())) == null) {
                return
            }
            for (Map.Entry entry : map.entrySet()) {
                BlockNode blockNode2 = (BlockNode) entry.getKey()
                SplitterBlockAttr splitterBlockAttr2 = (SplitterBlockAttr) entry.getValue()
                if (blockNode2 == topBlock) {
                    blockNode.addAttr(splitterBlockAttr2)
                } else {
                    blockNode2.remove(AType.SPLITTER_BLOCK)
                    Iterator it2 = blockNode2.getCleanSuccessors().iterator()
                    while (it2.hasNext()) {
                        ((BlockNode) it2.next()).remove(AType.SPLITTER_BLOCK)
                    }
                }
            }
        }
    }

    private fun initBlocksInIfNodes(BlockNode blockNode) {
        List instructions = blockNode.getInstructions()
        if (instructions.size() == 1) {
            InsnNode insnNode = (InsnNode) instructions.get(0)
            if (insnNode.getType() == InsnType.IF) {
                ((IfNode) insnNode).initBlocks(blockNode)
            }
        }
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        if (methodNode.isNoCode()) {
            return
        }
        for (BlockNode blockNode : methodNode.getBasicBlocks()) {
            blockNode.updateCleanSuccessors()
            initBlocksInIfNodes(blockNode)
            fixSplitterBlock(blockNode)
        }
        methodNode.finishBasicBlocks()
    }
}
