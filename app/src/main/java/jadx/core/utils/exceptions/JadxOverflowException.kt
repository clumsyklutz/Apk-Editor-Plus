package jadx.core.utils.exceptions

class JadxOverflowException extends JadxRuntimeException {
    private static val serialVersionUID = 2568659798680154204L

    constructor(String str) {
        super(str)
    }
}
