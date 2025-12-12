package jadx.core.utils.exceptions

class JadxRuntimeException extends RuntimeException {
    private static val serialVersionUID = -7410848445429898248L

    constructor(String str) {
        super(str)
    }

    constructor(String str, Throwable th) {
        super(str, th)
    }
}
