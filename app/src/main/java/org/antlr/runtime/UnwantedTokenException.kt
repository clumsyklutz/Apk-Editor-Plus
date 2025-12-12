package org.antlr.runtime

class UnwantedTokenException extends MismatchedTokenException {
    constructor() {
    }

    constructor(Int i, IntStream intStream) {
        super(i, intStream)
    }

    fun getUnexpectedToken() {
        return this.token
    }

    @Override // org.antlr.runtime.MismatchedTokenException, java.lang.Throwable
    fun toString() {
        String str = ", expected " + this.expecting
        if (this.expecting == 0) {
            str = ""
        }
        if (this.token == null) {
            return "UnwantedTokenException(found=" + ((Object) null) + str + ")"
        }
        return "UnwantedTokenException(found=" + this.token.getText() + str + ")"
    }
}
