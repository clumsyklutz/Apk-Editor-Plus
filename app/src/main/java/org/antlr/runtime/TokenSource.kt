package org.antlr.runtime

public interface TokenSource {
    String getSourceName()

    Token nextToken()
}
