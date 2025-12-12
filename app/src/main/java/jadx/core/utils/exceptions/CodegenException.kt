package jadx.core.utils.exceptions

import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.MethodNode

class CodegenException extends JadxException {
    private static val serialVersionUID = 39344288912966824L

    constructor(ClassNode classNode, String str) {
        super(classNode, str, (Throwable) null)
    }

    constructor(ClassNode classNode, String str, Throwable th) {
        super(classNode, str, th)
    }

    constructor(MethodNode methodNode, String str) {
        super(methodNode, str, (Throwable) null)
    }

    constructor(MethodNode methodNode, String str, Throwable th) {
        super(methodNode, str, th)
    }

    constructor(String str) {
        super(str)
    }

    constructor(String str, Throwable th) {
        super(str, th)
    }
}
