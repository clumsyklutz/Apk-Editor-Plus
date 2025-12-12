package org.antlr.runtime.tree

import org.antlr.runtime.Token

class CommonTree extends BaseTree {
    public Int childIndex
    public CommonTree parent
    public Int startIndex
    public Int stopIndex
    public Token token

    constructor() {
        this.startIndex = -1
        this.stopIndex = -1
        this.childIndex = -1
    }

    constructor(Token token) {
        this.startIndex = -1
        this.stopIndex = -1
        this.childIndex = -1
        this.token = token
    }

    constructor(CommonTree commonTree) {
        super(commonTree)
        this.startIndex = -1
        this.stopIndex = -1
        this.childIndex = -1
        this.token = commonTree.token
        this.startIndex = commonTree.startIndex
        this.stopIndex = commonTree.stopIndex
    }

    @Override // org.antlr.runtime.tree.Tree
    fun dupNode() {
        return CommonTree(this)
    }

    @Override // org.antlr.runtime.tree.BaseTree, org.antlr.runtime.tree.Tree
    fun getCharPositionInLine() {
        Token token = this.token
        if (token != null && token.getCharPositionInLine() != -1) {
            return this.token.getCharPositionInLine()
        }
        if (getChildCount() > 0) {
            return getChild(0).getCharPositionInLine()
        }
        return 0
    }

    @Override // org.antlr.runtime.tree.BaseTree, org.antlr.runtime.tree.Tree
    fun getChildIndex() {
        return this.childIndex
    }

    @Override // org.antlr.runtime.tree.BaseTree, org.antlr.runtime.tree.Tree
    fun getLine() {
        Token token = this.token
        if (token != null && token.getLine() != 0) {
            return this.token.getLine()
        }
        if (getChildCount() > 0) {
            return getChild(0).getLine()
        }
        return 0
    }

    @Override // org.antlr.runtime.tree.BaseTree, org.antlr.runtime.tree.Tree
    fun getParent() {
        return this.parent
    }

    @Override // org.antlr.runtime.tree.Tree
    fun getText() {
        Token token = this.token
        if (token == null) {
            return null
        }
        return token.getText()
    }

    fun getToken() {
        return this.token
    }

    @Override // org.antlr.runtime.tree.Tree
    fun getTokenStartIndex() {
        Token token
        Int i = this.startIndex
        return (i != -1 || (token = this.token) == null) ? i : token.getTokenIndex()
    }

    @Override // org.antlr.runtime.tree.Tree
    fun getTokenStopIndex() {
        Token token
        Int i = this.stopIndex
        return (i != -1 || (token = this.token) == null) ? i : token.getTokenIndex()
    }

    @Override // org.antlr.runtime.tree.Tree
    fun getType() {
        Token token = this.token
        if (token == null) {
            return 0
        }
        return token.getType()
    }

    @Override // org.antlr.runtime.tree.BaseTree, org.antlr.runtime.tree.Tree
    fun isNil() {
        return this.token == null
    }

    @Override // org.antlr.runtime.tree.BaseTree, org.antlr.runtime.tree.Tree
    fun setChildIndex(Int i) {
        this.childIndex = i
    }

    @Override // org.antlr.runtime.tree.BaseTree, org.antlr.runtime.tree.Tree
    fun setParent(Tree tree) {
        this.parent = (CommonTree) tree
    }

    @Override // org.antlr.runtime.tree.Tree
    fun setTokenStartIndex(Int i) {
        this.startIndex = i
    }

    @Override // org.antlr.runtime.tree.Tree
    fun setTokenStopIndex(Int i) {
        this.stopIndex = i
    }

    fun toString() {
        if (isNil()) {
            return "nil"
        }
        if (getType() == 0) {
            return "<errornode>"
        }
        Token token = this.token
        if (token == null) {
            return null
        }
        return token.getText()
    }
}
