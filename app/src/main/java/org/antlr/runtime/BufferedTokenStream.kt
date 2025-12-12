package org.antlr.runtime

import java.util.ArrayList
import java.util.List

class BufferedTokenStream implements TokenStream {
    public Int lastMarker
    public TokenSource tokenSource
    public List<Token> tokens = ArrayList(100)
    public Int p = -1
    public Int range = -1

    constructor(TokenSource tokenSource) {
        this.tokenSource = tokenSource
    }

    @Override // org.antlr.runtime.IntStream
    fun LA(Int i) {
        return LT(i).getType()
    }

    @Override // org.antlr.runtime.TokenStream
    fun LT(Int i) {
        throw null
    }

    fun fetch(Int i) {
        for (Int i2 = 1; i2 <= i; i2++) {
            Token tokenNextToken = this.tokenSource.nextToken()
            tokenNextToken.setTokenIndex(this.tokens.size())
            this.tokens.add(tokenNextToken)
            if (tokenNextToken.getType() == -1) {
                return
            }
        }
    }

    fun fill() {
        if (this.p == -1) {
            setup()
        }
        if (this.tokens.get(this.p).getType() == -1) {
            return
        }
        Int i = this.p + 1
        sync(i)
        while (this.tokens.get(i).getType() != -1) {
            i++
            sync(i)
        }
    }

    @Override // org.antlr.runtime.IntStream
    fun getSourceName() {
        return this.tokenSource.getSourceName()
    }

    @Override // org.antlr.runtime.IntStream
    fun index() {
        return this.p
    }

    @Override // org.antlr.runtime.IntStream
    fun mark() {
        if (this.p == -1) {
            setup()
        }
        Int iIndex = index()
        this.lastMarker = iIndex
        return iIndex
    }

    @Override // org.antlr.runtime.IntStream
    fun rewind() {
        seek(this.lastMarker)
    }

    @Override // org.antlr.runtime.IntStream
    fun rewind(Int i) {
        seek(i)
    }

    @Override // org.antlr.runtime.IntStream
    fun seek(Int i) {
        this.p = i
    }

    fun setup() {
        throw null
    }

    @Override // org.antlr.runtime.IntStream
    fun size() {
        return this.tokens.size()
    }

    fun sync(Int i) {
        Int size = (i - this.tokens.size()) + 1
        if (size > 0) {
            fetch(size)
        }
    }

    fun toString() {
        if (this.p == -1) {
            setup()
        }
        fill()
        return toString(0, this.tokens.size() - 1)
    }

    @Override // org.antlr.runtime.TokenStream
    fun toString(Int i, Int i2) {
        if (i < 0 || i2 < 0) {
            return null
        }
        if (this.p == -1) {
            setup()
        }
        if (i2 >= this.tokens.size()) {
            i2 = this.tokens.size() - 1
        }
        StringBuilder sb = StringBuilder()
        while (i <= i2) {
            Token token = this.tokens.get(i)
            if (token.getType() == -1) {
                break
            }
            sb.append(token.getText())
            i++
        }
        return sb.toString()
    }

    @Override // org.antlr.runtime.TokenStream
    fun toString(Token token, Token token2) {
        if (token == null || token2 == null) {
            return null
        }
        return toString(token.getTokenIndex(), token2.getTokenIndex())
    }
}
