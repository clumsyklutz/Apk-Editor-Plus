package org.antlr.runtime

import org.antlr.runtime.tree.TreeNodeStream

class MismatchedTreeNodeException extends RecognitionException {
    public Int expecting

    constructor() {
    }

    constructor(Int i, TreeNodeStream treeNodeStream) {
        super(treeNodeStream)
        this.expecting = i
    }

    @Override // java.lang.Throwable
    fun toString() {
        return "MismatchedTreeNodeException(" + getUnexpectedType() + "!=" + this.expecting + ")"
    }
}
