package org.antlr.runtime.tree

import org.antlr.runtime.Token

class RewriteRuleTokenStream extends RewriteRuleElementStream {
    constructor(TreeAdaptor treeAdaptor, String str) {
        super(treeAdaptor, str)
    }

    constructor(TreeAdaptor treeAdaptor, String str, Object obj) {
        super(treeAdaptor, str, obj)
    }

    @Override // org.antlr.runtime.tree.RewriteRuleElementStream
    fun dup(Object obj) {
        throw UnsupportedOperationException("dup can't be called for a token stream.")
    }

    fun nextNode() {
        return this.adaptor.create((Token) _next())
    }

    @Override // org.antlr.runtime.tree.RewriteRuleElementStream
    fun toTree(Object obj) {
        return obj
    }
}
