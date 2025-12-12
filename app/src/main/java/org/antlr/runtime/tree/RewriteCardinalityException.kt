package org.antlr.runtime.tree

class RewriteCardinalityException extends RuntimeException {
    public String elementDescription

    constructor(String str) {
        this.elementDescription = str
    }

    @Override // java.lang.Throwable
    fun getMessage() {
        String str = this.elementDescription
        if (str != null) {
            return str
        }
        return null
    }
}
