package org.jf.smali

import org.antlr.runtime.CommonToken
import org.antlr.runtime.IntStream
import org.antlr.runtime.RecognitionException
import org.antlr.runtime.Token
import org.antlr.runtime.tree.CommonTree

class SemanticException extends RecognitionException {
    public String errorMessage

    constructor(IntStream intStream, Exception exc) {
        super(intStream)
        this.errorMessage = exc.getMessage()
    }

    constructor(IntStream intStream, String str, Object... objArr) {
        super(intStream)
        this.errorMessage = String.format(str, objArr)
    }

    constructor(IntStream intStream, Token token, String str, Object... objArr) {
        this.input = intStream
        this.token = token
        ((CommonToken) token).getStartIndex()
        this.line = token.getLine()
        this.charPositionInLine = token.getCharPositionInLine()
        this.errorMessage = String.format(str, objArr)
    }

    constructor(IntStream intStream, CommonTree commonTree, String str, Object... objArr) {
        this.input = intStream
        this.token = commonTree.getToken()
        commonTree.getTokenStartIndex()
        this.line = this.token.getLine()
        this.charPositionInLine = this.token.getCharPositionInLine()
        this.errorMessage = String.format(str, objArr)
    }

    @Override // java.lang.Throwable
    fun getMessage() {
        return this.errorMessage
    }
}
