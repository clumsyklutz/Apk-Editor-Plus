package org.antlr.runtime.tree

import java.util.ArrayList
import java.util.List

abstract class BaseTree implements Tree {
    public List<Object> children

    constructor() {
    }

    constructor(Tree tree) {
    }

    @Override // org.antlr.runtime.tree.Tree
    fun addChild(Tree tree) {
        if (tree == null) {
            return
        }
        BaseTree baseTree = (BaseTree) tree
        if (!baseTree.isNil()) {
            if (this.children == null) {
                this.children = createChildrenList()
            }
            this.children.add(tree)
            baseTree.setParent(this)
            baseTree.setChildIndex(this.children.size() - 1)
            return
        }
        List<Object> list = this.children
        if (list != null && list == baseTree.children) {
            throw RuntimeException("attempt to add child list to itself")
        }
        List<Object> list2 = baseTree.children
        if (list2 != null) {
            if (list == null) {
                this.children = list2
                freshenParentAndChildIndexes()
                return
            }
            Int size = list2.size()
            for (Int i = 0; i < size; i++) {
                Tree tree2 = (Tree) baseTree.children.get(i)
                this.children.add(tree2)
                tree2.setParent(this)
                tree2.setChildIndex(this.children.size() - 1)
            }
        }
    }

    public List<Object> createChildrenList() {
        return ArrayList()
    }

    fun freshenParentAndChildIndexes() {
        freshenParentAndChildIndexes(0)
    }

    fun freshenParentAndChildIndexes(Int i) {
        Int childCount = getChildCount()
        while (i < childCount) {
            Tree child = getChild(i)
            child.setChildIndex(i)
            child.setParent(this)
            i++
        }
    }

    @Override // org.antlr.runtime.tree.Tree
    fun getCharPositionInLine() {
        return 0
    }

    @Override // org.antlr.runtime.tree.Tree
    fun getChild(Int i) {
        List<Object> list = this.children
        if (list == null || i >= list.size()) {
            return null
        }
        return (Tree) this.children.get(i)
    }

    @Override // org.antlr.runtime.tree.Tree
    fun getChildCount() {
        List<Object> list = this.children
        if (list == null) {
            return 0
        }
        return list.size()
    }

    @Override // org.antlr.runtime.tree.Tree
    fun getChildIndex() {
        return 0
    }

    @Override // org.antlr.runtime.tree.Tree
    fun getLine() {
        return 0
    }

    @Override // org.antlr.runtime.tree.Tree
    fun getParent() {
        return null
    }

    @Override // org.antlr.runtime.tree.Tree
    fun isNil() {
        return false
    }

    @Override // org.antlr.runtime.tree.Tree
    fun setChildIndex(Int i) {
    }

    @Override // org.antlr.runtime.tree.Tree
    fun setParent(Tree tree) {
    }
}
