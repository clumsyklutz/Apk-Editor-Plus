package jadx.core.dex.visitors

import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.nodes.RootNode

class AbstractVisitor implements IDexTreeVisitor {
    @Override // jadx.core.dex.visitors.IDexTreeVisitor
    fun init(RootNode rootNode) {
    }

    @Override // jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
    }

    @Override // jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(ClassNode classNode) {
        return true
    }
}
