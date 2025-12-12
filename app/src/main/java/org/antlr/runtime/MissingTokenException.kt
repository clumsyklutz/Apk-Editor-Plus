package org.antlr.runtime

class MissingTokenException extends MismatchedTokenException {
    public Object inserted

    constructor() {
    }

    constructor(Int i, IntStream intStream, Object obj) {
        super(i, intStream)
        this.inserted = obj
    }

    fun getMissingType() {
        return this.expecting
    }

    @Override // org.antlr.runtime.MismatchedTokenException, java.lang.Throwable
    fun toString() {
        if (this.inserted == null || this.token == null) {
            if (this.token == null) {
                return "MissingTokenException"
            }
            return "MissingTokenException(at " + this.token.getText() + ")"
        }
        return "MissingTokenException(inserted " + this.inserted + " at " + this.token.getText() + ")"
    }
}
