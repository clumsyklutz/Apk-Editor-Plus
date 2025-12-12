package jadx.core.dex.visitors.typeinference

import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.visitors.AbstractVisitor
import java.util.Iterator

class FinishTypeInference extends AbstractVisitor {
    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        if (methodNode.isNoCode()) {
            return
        }
        Int i = 0
        while (true) {
            Iterator it = methodNode.getBasicBlocks().iterator()
            Boolean z = false
            while (it.hasNext()) {
                Iterator it2 = ((BlockNode) it.next()).getInstructions().iterator()
                while (it2.hasNext()) {
                    if (PostTypeInference.process(methodNode, (InsnNode) it2.next())) {
                        z = true
                    }
                }
            }
            Int i2 = i + 1
            if (i2 > 1000 || !z) {
                break
            } else {
                i = i2
            }
        }
        DexNode dexNodeDex = methodNode.dex()
        Iterator it3 = methodNode.getBasicBlocks().iterator()
        while (it3.hasNext()) {
            Iterator it4 = ((BlockNode) it3.next()).getInstructions().iterator()
            while (it4.hasNext()) {
                SelectTypeVisitor.visit(dexNodeDex, (InsnNode) it4.next())
            }
        }
        Iterator it5 = methodNode.getBasicBlocks().iterator()
        while (it5.hasNext()) {
            Iterator it6 = ((BlockNode) it5.next()).getInstructions().iterator()
            while (it6.hasNext()) {
                CheckTypeVisitor.visit(methodNode, (InsnNode) it6.next())
            }
        }
    }
}
