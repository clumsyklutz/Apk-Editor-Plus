package jadx.core.dex.visitors

import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.nodes.RootNode

public interface IDexTreeVisitor {
    Unit init(RootNode rootNode)

    Unit visit(MethodNode methodNode)

    Boolean visit(ClassNode classNode)
}
