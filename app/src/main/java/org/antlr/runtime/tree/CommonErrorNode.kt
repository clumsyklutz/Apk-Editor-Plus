package org.antlr.runtime.tree

import org.antlr.runtime.IntStream
import org.antlr.runtime.MismatchedTokenException
import org.antlr.runtime.MissingTokenException
import org.antlr.runtime.NoViableAltException
import org.antlr.runtime.RecognitionException
import org.antlr.runtime.Token
import org.antlr.runtime.TokenStream
import org.antlr.runtime.UnwantedTokenException

class CommonErrorNode extends CommonTree {
    public IntStream input
    public Token start
    public Token stop
    public RecognitionException trappedException

    constructor(TokenStream tokenStream, Token token, Token token2, RecognitionException recognitionException) {
        if (token2 == null || (token2.getTokenIndex() < token.getTokenIndex() && token2.getType() != -1)) {
            token2 = token
        }
        this.input = tokenStream
        this.start = token
        this.stop = token2
        this.trappedException = recognitionException
    }

    @Override // org.antlr.runtime.tree.CommonTree, org.antlr.runtime.tree.Tree
    fun getText() {
        Token token = this.start
        if (!(token is Token)) {
            return token is Tree ? ((TreeNodeStream) this.input).toString(token, this.stop) : "<unknown>"
        }
        Int tokenIndex = token.getTokenIndex()
        Int tokenIndex2 = this.stop.getTokenIndex()
        if (this.stop.getType() == -1) {
            tokenIndex2 = ((TokenStream) this.input).size()
        }
        return ((TokenStream) this.input).toString(tokenIndex, tokenIndex2)
    }

    @Override // org.antlr.runtime.tree.CommonTree, org.antlr.runtime.tree.Tree
    fun getType() {
        return 0
    }

    @Override // org.antlr.runtime.tree.CommonTree, org.antlr.runtime.tree.BaseTree, org.antlr.runtime.tree.Tree
    fun isNil() {
        return false
    }

    @Override // org.antlr.runtime.tree.CommonTree
    fun toString() {
        RecognitionException recognitionException = this.trappedException
        if (recognitionException is MissingTokenException) {
            return "<missing type: " + ((MissingTokenException) this.trappedException).getMissingType() + ">"
        }
        if (recognitionException is UnwantedTokenException) {
            return "<extraneous: " + ((UnwantedTokenException) this.trappedException).getUnexpectedToken() + ", resync=" + getText() + ">"
        }
        if (recognitionException is MismatchedTokenException) {
            return "<mismatched token: " + this.trappedException.token + ", resync=" + getText() + ">"
        }
        if (!(recognitionException is NoViableAltException)) {
            return "<error: " + getText() + ">"
        }
        return "<unexpected: " + this.trappedException.token + ", resync=" + getText() + ">"
    }
}
