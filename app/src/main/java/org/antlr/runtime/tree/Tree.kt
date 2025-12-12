package org.antlr.runtime.tree

public interface Tree {
    Unit addChild(Tree tree)

    Tree dupNode()

    Int getCharPositionInLine()

    Tree getChild(Int i)

    Int getChildCount()

    Int getChildIndex()

    Int getLine()

    Tree getParent()

    String getText()

    Int getTokenStartIndex()

    Int getTokenStopIndex()

    Int getType()

    Boolean isNil()

    Unit setChildIndex(Int i)

    Unit setParent(Tree tree)

    Unit setTokenStartIndex(Int i)

    Unit setTokenStopIndex(Int i)
}
