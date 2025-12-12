package jadx.core.utils.exceptions

import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.utils.ErrorsCounter

class JadxException extends Exception {
    private static val serialVersionUID = 3577449089978463557L

    constructor(ClassNode classNode, String str, Throwable th) {
        super(ErrorsCounter.formatErrorMsg(classNode, str), th)
    }

    constructor(MethodNode methodNode, String str, Throwable th) {
        super(ErrorsCounter.formatErrorMsg(methodNode, str), th)
    }

    constructor(String str) {
        super(str)
    }

    constructor(String str, Throwable th) {
        super(str, th)
    }
}
