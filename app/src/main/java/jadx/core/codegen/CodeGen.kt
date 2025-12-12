package jadx.core.codegen

import jadx.api.IJadxArgs
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.visitors.AbstractVisitor

class CodeGen extends AbstractVisitor {
    private final IJadxArgs args

    constructor(IJadxArgs iJadxArgs) {
        this.args = iJadxArgs
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(ClassNode classNode) {
        CodeWriter codeWriterMakeClass = ClassGen(classNode, this.args).makeClass()
        codeWriterMakeClass.finish()
        classNode.setCode(codeWriterMakeClass)
        return false
    }
}
