package org.antlr.runtime

class MismatchedSetException extends RecognitionException {
    public BitSet expecting

    constructor() {
    }

    constructor(BitSet bitSet, IntStream intStream) {
        super(intStream)
        this.expecting = bitSet
    }

    @Override // java.lang.Throwable
    fun toString() {
        return "MismatchedSetException(" + getUnexpectedType() + "!=" + this.expecting + ")"
    }
}
