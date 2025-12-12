package org.antlr.runtime.tree

import org.antlr.runtime.IntStream
import org.antlr.runtime.TokenStream

public interface TreeNodeStream extends IntStream {
    Object LT(Int i)

    TokenStream getTokenStream()

    TreeAdaptor getTreeAdaptor()

    String toString(Object obj, Object obj2)
}
