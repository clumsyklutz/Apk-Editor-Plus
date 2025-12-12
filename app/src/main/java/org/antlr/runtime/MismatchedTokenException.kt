package org.antlr.runtime

class MismatchedTokenException extends RecognitionException {
    public Int expecting

    constructor() {
        this.expecting = 0
    }

    constructor(Int i, IntStream intStream) {
        super(intStream)
        this.expecting = 0
        this.expecting = i
    }

    @Override // java.lang.Throwable
    fun toString() {
        return "MismatchedTokenException(" + getUnexpectedType() + "!=" + this.expecting + ")"
    }
}
