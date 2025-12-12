package jadx.core.dex.visitors

import jadx.core.dex.attributes.AType
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.utils.ErrorsCounter
import java.util.Iterator

class DepthTraversal {
    fun visit(IDexTreeVisitor iDexTreeVisitor, ClassNode classNode) {
        try {
            if (iDexTreeVisitor.visit(classNode)) {
                Iterator it = classNode.getInnerClasses().iterator()
                while (it.hasNext()) {
                    visit(iDexTreeVisitor, (ClassNode) it.next())
                }
                Iterator it2 = classNode.getMethods().iterator()
                while (it2.hasNext()) {
                    visit(iDexTreeVisitor, (MethodNode) it2.next())
                }
            }
        } catch (Throwable th) {
            ErrorsCounter.classError(classNode, th.getClass().getSimpleName() + " in pass: " + iDexTreeVisitor.getClass().getSimpleName(), th)
        }
    }

    fun visit(IDexTreeVisitor iDexTreeVisitor, MethodNode methodNode) {
        if (methodNode.contains(AType.JADX_ERROR)) {
            return
        }
        try {
            iDexTreeVisitor.visit(methodNode)
        } catch (Throwable th) {
            ErrorsCounter.methodError(methodNode, th.getClass().getSimpleName() + " in pass: " + iDexTreeVisitor.getClass().getSimpleName(), th)
        }
    }
}
