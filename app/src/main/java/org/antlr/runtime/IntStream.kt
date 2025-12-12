package org.antlr.runtime

public interface IntStream {
    Int LA(Int i)

    Unit consume()

    String getSourceName()

    Int index()

    Int mark()

    Unit rewind()

    Unit rewind(Int i)

    Unit seek(Int i)

    Int size()
}
