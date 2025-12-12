package jadx.core.dex.visitors.regions

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.IBlock
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.regions.loops.LoopRegion
import jadx.core.dex.visitors.AbstractVisitor
import jadx.core.utils.ErrorsCounter
import java.util.HashSet
import org.d.b
import org.d.c

class CheckRegions extends AbstractVisitor {
    private static val LOG = c.a(CheckRegions.class)

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        if (methodNode.isNoCode() || methodNode.getBasicBlocks().isEmpty() || methodNode.contains(AType.JADX_ERROR)) {
            return
        }
        val hashSet = HashSet()
        DepthRegionTraversal.traverse(methodNode, AbstractRegionVisitor() { // from class: jadx.core.dex.visitors.regions.CheckRegions.1
            @Override // jadx.core.dex.visitors.regions.AbstractRegionVisitor, jadx.core.dex.visitors.regions.IRegionVisitor
            fun processBlock(MethodNode methodNode2, IBlock iBlock) {
                if (iBlock is BlockNode) {
                    BlockNode blockNode = (BlockNode) iBlock
                    if (hashSet.add(blockNode) || blockNode.contains(AFlag.RETURN) || blockNode.contains(AFlag.SKIP) || blockNode.contains(AFlag.SYNTHETIC) || blockNode.getInstructions().isEmpty()) {
                        return
                    }
                    CheckRegions.LOG.a(" Duplicated block: {} in {}", blockNode, methodNode2)
                }
            }
        })
        if (methodNode.getBasicBlocks().size() != hashSet.size()) {
            for (BlockNode blockNode : methodNode.getBasicBlocks()) {
                if (!hashSet.contains(blockNode) && !blockNode.getInstructions().isEmpty() && !blockNode.contains(AFlag.SKIP)) {
                    methodNode.add(AFlag.INCONSISTENT_CODE)
                    LOG.a(" Missing block: {} in {}", blockNode, methodNode)
                }
            }
        }
        DepthRegionTraversal.traverse(methodNode, AbstractRegionVisitor() { // from class: jadx.core.dex.visitors.regions.CheckRegions.2
            @Override // jadx.core.dex.visitors.regions.AbstractRegionVisitor, jadx.core.dex.visitors.regions.IRegionVisitor
            fun enterRegion(MethodNode methodNode2, IRegion iRegion) {
                BlockNode header
                if ((iRegion is LoopRegion) && (header = ((LoopRegion) iRegion).getHeader()) != null && header.getInstructions().size() != 1) {
                    ErrorsCounter.methodError(methodNode2, "Incorrect condition in loop: " + header)
                }
                return true
            }
        })
    }
}
