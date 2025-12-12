package org.jf.smali

import org.antlr.runtime.CommonToken

class InvalidToken extends CommonToken {
    public final String message

    constructor(String str, String str2) {
        super(96, str2)
        this.message = str
        this.channel = 100
    }

    fun getMessage() {
        return this.message
    }
}
