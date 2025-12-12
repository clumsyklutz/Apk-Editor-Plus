package org.antlr.runtime

public interface CharStream extends IntStream {
    Int getCharPositionInLine()

    Int getLine()

    String substring(Int i, Int i2)
}
