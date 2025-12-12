package org.antlr.runtime

class FailedPredicateException extends RecognitionException {
    public String predicateText
    public String ruleName

    constructor() {
    }

    constructor(IntStream intStream, String str, String str2) {
        super(intStream)
        this.ruleName = str
        this.predicateText = str2
    }

    @Override // java.lang.Throwable
    fun toString() {
        return "FailedPredicateException(" + this.ruleName + ",{" + this.predicateText + "}?)"
    }
}
