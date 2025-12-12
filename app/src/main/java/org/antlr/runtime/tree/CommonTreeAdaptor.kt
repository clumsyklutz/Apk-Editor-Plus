package org.antlr.runtime.tree

import org.antlr.runtime.CommonToken
import org.antlr.runtime.Token

class CommonTreeAdaptor extends BaseTreeAdaptor {
    @Override // org.antlr.runtime.tree.TreeAdaptor
    fun create(Token token) {
        return CommonTree(token)
    }

    @Override // org.antlr.runtime.tree.BaseTreeAdaptor
    fun createToken(Int i, String str) {
        return CommonToken(i, str)
    }

    @Override // org.antlr.runtime.tree.BaseTreeAdaptor
    fun createToken(Token token) {
        return CommonToken(token)
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    fun dupNode(Object obj) {
        if (obj == null) {
            return null
        }
        return ((Tree) obj).dupNode()
    }

    @Override // org.antlr.runtime.tree.BaseTreeAdaptor, org.antlr.runtime.tree.TreeAdaptor
    fun getChild(Object obj, Int i) {
        if (obj == null) {
            return null
        }
        return ((Tree) obj).getChild(i)
    }

    @Override // org.antlr.runtime.tree.BaseTreeAdaptor, org.antlr.runtime.tree.TreeAdaptor
    fun getChildCount(Object obj) {
        if (obj == null) {
            return 0
        }
        return ((Tree) obj).getChildCount()
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    fun getChildIndex(Object obj) {
        if (obj == null) {
            return 0
        }
        return ((Tree) obj).getChildIndex()
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    fun getParent(Object obj) {
        if (obj == null) {
            return null
        }
        return ((Tree) obj).getParent()
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    fun getText(Object obj) {
        if (obj == null) {
            return null
        }
        return ((Tree) obj).getText()
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    fun getToken(Object obj) {
        if (obj is CommonTree) {
            return ((CommonTree) obj).getToken()
        }
        return null
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    fun getTokenStartIndex(Object obj) {
        if (obj == null) {
            return -1
        }
        return ((Tree) obj).getTokenStartIndex()
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    fun getTokenStopIndex(Object obj) {
        if (obj == null) {
            return -1
        }
        return ((Tree) obj).getTokenStopIndex()
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    fun getType(Object obj) {
        if (obj == null) {
            return 0
        }
        return ((Tree) obj).getType()
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    fun setChildIndex(Object obj, Int i) {
        if (obj != null) {
            ((Tree) obj).setChildIndex(i)
        }
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    fun setParent(Object obj, Object obj2) {
        if (obj != null) {
            ((Tree) obj).setParent((Tree) obj2)
        }
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    fun setTokenBoundaries(Object obj, Token token, Token token2) {
        if (obj == null) {
            return
        }
        Int tokenIndex = token != null ? token.getTokenIndex() : 0
        Int tokenIndex2 = token2 != null ? token2.getTokenIndex() : 0
        Tree tree = (Tree) obj
        tree.setTokenStartIndex(tokenIndex)
        tree.setTokenStopIndex(tokenIndex2)
    }
}
