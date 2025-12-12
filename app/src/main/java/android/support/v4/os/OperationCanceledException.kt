package android.support.v4.os

class OperationCanceledException extends RuntimeException {
    constructor() {
        this(null)
    }

    constructor(String str) {
        super(str == null ? "The operation has been canceled." : str)
    }
}
