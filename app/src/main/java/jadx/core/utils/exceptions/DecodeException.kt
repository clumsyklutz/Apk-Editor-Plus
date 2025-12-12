package jadx.core.utils.exceptions

import jadx.core.dex.nodes.MethodNode

class DecodeException extends JadxException {
    private static val serialVersionUID = -6611189094923499636L

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
