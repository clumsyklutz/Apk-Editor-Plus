package org.antlr.runtime

public interface TokenStream extends IntStream {
    Token LT(Int i)

    String toString(Int i, Int i2)

    String toString(Token token, Token token2)
}
