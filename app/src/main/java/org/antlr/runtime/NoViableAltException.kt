package org.antlr.runtime

class NoViableAltException extends RecognitionException {
    public Int decisionNumber
    public String grammarDecisionDescription
    public Int stateNumber

    constructor() {
    }

    constructor(String str, Int i, Int i2, IntStream intStream) {
        super(intStream)
        this.grammarDecisionDescription = str
        this.decisionNumber = i
        this.stateNumber = i2
    }

    @Override // java.lang.Throwable
    fun toString() {
        if (this.input is CharStream) {
            return "NoViableAltException('" + ((Char) getUnexpectedType()) + "'@[" + this.grammarDecisionDescription + "])"
        }
        return "NoViableAltException(" + getUnexpectedType() + "@[" + this.grammarDecisionDescription + "])"
    }
}
