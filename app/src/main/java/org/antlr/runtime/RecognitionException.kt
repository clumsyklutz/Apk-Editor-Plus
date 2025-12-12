package org.antlr.runtime

import org.antlr.runtime.tree.CommonTree
import org.antlr.runtime.tree.PositionTrackingStream
import org.antlr.runtime.tree.Tree
import org.antlr.runtime.tree.TreeAdaptor
import org.antlr.runtime.tree.TreeNodeStream

class RecognitionException extends Exception {
    public Int c
    public Int charPositionInLine
    public transient IntStream input
    public Int line
    public Object node
    public Token token

    constructor() {
    }

    constructor(IntStream intStream) {
        this.input = intStream
        intStream.index()
        if (intStream is TokenStream) {
            Token tokenLT = ((TokenStream) intStream).LT(1)
            this.token = tokenLT
            this.line = tokenLT.getLine()
            this.charPositionInLine = this.token.getCharPositionInLine()
        }
        if (intStream is TreeNodeStream) {
            extractInformationFromTreeNodeStream(intStream)
            return
        }
        if (!(intStream is CharStream)) {
            this.c = intStream.LA(1)
            return
        }
        this.c = intStream.LA(1)
        CharStream charStream = (CharStream) intStream
        this.line = charStream.getLine()
        this.charPositionInLine = charStream.getCharPositionInLine()
    }

    fun extractInformationFromTreeNodeStream(IntStream intStream) {
        Object knownPositionElement
        TreeNodeStream treeNodeStream = (TreeNodeStream) intStream
        this.node = treeNodeStream.LT(1)
        if (treeNodeStream is PositionTrackingStream) {
            PositionTrackingStream positionTrackingStream = (PositionTrackingStream) treeNodeStream
            knownPositionElement = positionTrackingStream.getKnownPositionElement(false)
            if (knownPositionElement == null) {
                knownPositionElement = positionTrackingStream.getKnownPositionElement(true)
            }
        } else {
            knownPositionElement = null
        }
        TreeAdaptor treeAdaptor = treeNodeStream.getTreeAdaptor()
        if (knownPositionElement == null) {
            knownPositionElement = this.node
        }
        Token token = treeAdaptor.getToken(knownPositionElement)
        if (token == null) {
            Object obj = this.node
            if (!(obj is Tree)) {
                this.token = CommonToken(treeAdaptor.getType(obj), treeAdaptor.getText(this.node))
                return
            }
            this.line = ((Tree) obj).getLine()
            this.charPositionInLine = ((Tree) this.node).getCharPositionInLine()
            Object obj2 = this.node
            if (obj2 is CommonTree) {
                this.token = ((CommonTree) obj2).token
                return
            }
            return
        }
        this.token = token
        if (token.getLine() > 0) {
            this.line = token.getLine()
            this.charPositionInLine = token.getCharPositionInLine()
            return
        }
        Object objLT = treeNodeStream.LT(-1)
        Int i = -1
        while (objLT != null) {
            Token token2 = treeAdaptor.getToken(objLT)
            if (token2 != null && token2.getLine() > 0) {
                this.line = token2.getLine()
                this.charPositionInLine = token2.getCharPositionInLine()
                return
            } else {
                i--
                try {
                    objLT = treeNodeStream.LT(i)
                } catch (UnsupportedOperationException unused) {
                    objLT = null
                }
            }
        }
    }

    fun getUnexpectedType() {
        IntStream intStream = this.input
        return intStream is TokenStream ? this.token.getType() : intStream is TreeNodeStream ? ((TreeNodeStream) intStream).getTreeAdaptor().getType(this.node) : this.c
    }
}
