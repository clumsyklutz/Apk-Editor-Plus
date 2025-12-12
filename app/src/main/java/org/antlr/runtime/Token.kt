package org.antlr.runtime

public interface Token {
    Int getChannel()

    Int getCharPositionInLine()

    CharStream getInputStream()

    Int getLine()

    String getText()

    Int getTokenIndex()

    Int getType()

    Unit setText(String str)

    Unit setTokenIndex(Int i)

    Unit setType(Int i)
}
