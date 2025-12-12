package org.antlr.runtime.tree

import org.antlr.runtime.Token
import org.antlr.runtime.TokenStream
import org.antlr.runtime.misc.LookaheadStream

class CommonTreeNodeStream extends LookaheadStream<Object> implements TreeNodeStream, PositionTrackingStream<Object> {
    public TreeAdaptor adaptor
    public Boolean hasNilRoot
    public TreeIterator it
    public Int level
    public Object previousLocationElement
    public Object root
    public TokenStream tokens

    constructor(Object obj) {
        this(CommonTreeAdaptor(), obj)
    }

    constructor(TreeAdaptor treeAdaptor, Object obj) {
        this.hasNilRoot = false
        this.level = 0
        this.root = obj
        this.adaptor = treeAdaptor
        this.it = TreeIterator(treeAdaptor, obj)
    }

    @Override // org.antlr.runtime.IntStream
    fun LA(Int i) {
        return this.adaptor.getType(LT(i))
    }

    @Override // org.antlr.runtime.tree.PositionTrackingStream
    fun getKnownPositionElement(Boolean z) {
        Object obj = this.data.get(this.p)
        if (hasPositionInformation(obj)) {
            return obj
        }
        if (!z) {
            return null
        }
        for (Int i = this.p - 1; i >= 0; i--) {
            Object obj2 = this.data.get(i)
            if (hasPositionInformation(obj2)) {
                return obj2
            }
        }
        return this.previousLocationElement
    }

    @Override // org.antlr.runtime.IntStream
    fun getSourceName() {
        return getTokenStream().getSourceName()
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    fun getTokenStream() {
        return this.tokens
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    fun getTreeAdaptor() {
        return this.adaptor
    }

    fun hasPositionInformation(Object obj) {
        Token token = this.adaptor.getToken(obj)
        return token != null && token.getLine() > 0
    }

    @Override // org.antlr.runtime.misc.LookaheadStream
    fun isEOF(Object obj) {
        return this.adaptor.getType(obj) == -1
    }

    @Override // org.antlr.runtime.misc.LookaheadStream
    fun nextElement() {
        Object next = this.it.next()
        TreeIterator treeIterator = this.it
        if (next == treeIterator.up) {
            Int i = this.level - 1
            this.level = i
            if (i == 0 && this.hasNilRoot) {
                return treeIterator.next()
            }
        } else if (next == treeIterator.down) {
            this.level++
        }
        if (this.level != 0 || !this.adaptor.isNil(next)) {
            return next
        }
        this.hasNilRoot = true
        this.it.next()
        this.level++
        return this.it.next()
    }

    @Override // org.antlr.runtime.misc.LookaheadStream, org.antlr.runtime.misc.FastQueue
    fun remove() {
        Object objRemove = super.remove()
        if (this.p == 0 && hasPositionInformation(this.prevElement)) {
            this.previousLocationElement = this.prevElement
        }
        return objRemove
    }

    fun setTokenStream(TokenStream tokenStream) {
        this.tokens = tokenStream
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    fun toString(Object obj, Object obj2) {
        return "n/a"
    }
}
