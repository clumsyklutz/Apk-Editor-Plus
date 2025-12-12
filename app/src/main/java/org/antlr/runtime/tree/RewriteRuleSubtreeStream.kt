package org.antlr.runtime.tree

class RewriteRuleSubtreeStream extends RewriteRuleElementStream {
    constructor(TreeAdaptor treeAdaptor, String str) {
        super(treeAdaptor, str)
    }

    constructor(TreeAdaptor treeAdaptor, String str, Object obj) {
        super(treeAdaptor, str, obj)
    }

    @Override // org.antlr.runtime.tree.RewriteRuleElementStream
    fun dup(Object obj) {
        return this.adaptor.dupTree(obj)
    }
}
