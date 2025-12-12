package org.jf.smali

import org.antlr.runtime.IntStream
import org.antlr.runtime.RecognitionException

class OdexedInstructionException extends RecognitionException {
    public String odexedInstruction

    constructor(IntStream intStream, String str) {
        super(intStream)
        this.odexedInstruction = str
    }

    @Override // java.lang.Throwable
    fun getMessage() {
        return this.odexedInstruction + " is an odexed instruction. You cannot reassemble a disassembled odex file unless it has been deodexed."
    }
}
