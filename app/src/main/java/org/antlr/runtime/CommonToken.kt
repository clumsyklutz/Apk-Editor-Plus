package org.antlr.runtime

import java.io.Serializable

class CommonToken implements Token, Serializable {
    public Int channel
    public Int charPositionInLine
    public Int index
    public transient CharStream input
    public Int line
    public Int start
    public Int stop
    public String text
    public Int type

    constructor(Int i, String str) {
        this.charPositionInLine = -1
        this.channel = 0
        this.index = -1
        this.type = i
        this.channel = 0
        this.text = str
    }

    constructor(Token token) {
        this.charPositionInLine = -1
        this.channel = 0
        this.index = -1
        this.text = token.getText()
        this.type = token.getType()
        this.line = token.getLine()
        this.index = token.getTokenIndex()
        this.charPositionInLine = token.getCharPositionInLine()
        this.channel = token.getChannel()
        this.input = token.getInputStream()
        if (token is CommonToken) {
            CommonToken commonToken = (CommonToken) token
            this.start = commonToken.start
            this.stop = commonToken.stop
        }
    }

    @Override // org.antlr.runtime.Token
    fun getChannel() {
        return this.channel
    }

    @Override // org.antlr.runtime.Token
    fun getCharPositionInLine() {
        return this.charPositionInLine
    }

    @Override // org.antlr.runtime.Token
    fun getInputStream() {
        return this.input
    }

    @Override // org.antlr.runtime.Token
    fun getLine() {
        return this.line
    }

    fun getStartIndex() {
        return this.start
    }

    fun getStopIndex() {
        return this.stop
    }

    @Override // org.antlr.runtime.Token
    fun getText() {
        Int i
        String str = this.text
        if (str != null) {
            return str
        }
        CharStream charStream = this.input
        if (charStream == null) {
            return null
        }
        Int size = charStream.size()
        Int i2 = this.start
        return (i2 >= size || (i = this.stop) >= size) ? "<EOF>" : this.input.substring(i2, i)
    }

    @Override // org.antlr.runtime.Token
    fun getTokenIndex() {
        return this.index
    }

    @Override // org.antlr.runtime.Token
    fun getType() {
        return this.type
    }

    fun setChannel(Int i) {
        this.channel = i
    }

    fun setCharPositionInLine(Int i) {
        this.charPositionInLine = i
    }

    fun setLine(Int i) {
        this.line = i
    }

    fun setStartIndex(Int i) {
        this.start = i
    }

    fun setStopIndex(Int i) {
        this.stop = i
    }

    @Override // org.antlr.runtime.Token
    fun setText(String str) {
        this.text = str
    }

    @Override // org.antlr.runtime.Token
    fun setTokenIndex(Int i) {
        this.index = i
    }

    @Override // org.antlr.runtime.Token
    fun setType(Int i) {
        this.type = i
    }

    fun toString() {
        String str
        if (this.channel > 0) {
            str = ",channel=" + this.channel
        } else {
            str = ""
        }
        String text = getText()
        return "[@" + getTokenIndex() + "," + this.start + ":" + this.stop + "='" + (text != null ? text.replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r").replaceAll("\t", "\\\\t") : "<no text>") + "',<" + this.type + ">" + str + "," + this.line + ":" + getCharPositionInLine() + "]"
    }
}
