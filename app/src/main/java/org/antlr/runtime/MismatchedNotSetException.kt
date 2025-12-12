package org.antlr.runtime

class MismatchedNotSetException extends MismatchedSetException {
    @Override // org.antlr.runtime.MismatchedSetException, java.lang.Throwable
    fun toString() {
        return "MismatchedNotSetException(" + getUnexpectedType() + "!=" + this.expecting + ")"
    }
}
