package org.antlr.runtime

class EarlyExitException extends RecognitionException {
    constructor(Int i, IntStream intStream) {
        super(intStream)
    }
}
