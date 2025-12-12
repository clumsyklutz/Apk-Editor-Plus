package jadx.core.dex.visitors

import jadx.api.IJadxArgs
import jadx.core.codegen.CodeWriter
import jadx.core.dex.nodes.ClassNode
import java.io.File

class SaveCode extends AbstractVisitor {
    private final IJadxArgs args
    private final File dir

    constructor(File file, IJadxArgs iJadxArgs) {
        this.args = iJadxArgs
        this.dir = file
    }

    fun save(File file, IJadxArgs iJadxArgs, ClassNode classNode) {
        CodeWriter code = classNode.getCode()
        String str = classNode.getClassInfo().getFullPath() + ".java"
        if (iJadxArgs.isFallbackMode()) {
            str = str + ".jadx"
        }
        code.save(file, str)
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(ClassNode classNode) {
        save(this.dir, this.args, classNode)
        return false
    }
}
