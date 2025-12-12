package org.antlr.runtime.tree

import org.antlr.runtime.RecognitionException
import org.antlr.runtime.Token
import org.antlr.runtime.TokenStream

public interface TreeAdaptor {
    Unit addChild(Object obj, Object obj2)

    Object becomeRoot(Object obj, Object obj2)

    Object create(Int i, String str)

    Object create(Int i, Token token)

    Object create(Int i, Token token, String str)

    Object create(Token token)

    Object dupNode(Object obj)

    Object dupTree(Object obj)

    Object errorNode(TokenStream tokenStream, Token token, Token token2, RecognitionException recognitionException)

    Object getChild(Object obj, Int i)

    Int getChildCount(Object obj)

    Int getChildIndex(Object obj)

    Object getParent(Object obj)

    String getText(Object obj)

    Token getToken(Object obj)

    Int getTokenStartIndex(Object obj)

    Int getTokenStopIndex(Object obj)

    Int getType(Object obj)

    Boolean isNil(Object obj)

    Object nil()

    Object rulePostProcessing(Object obj)

    Unit setChildIndex(Object obj, Int i)

    Unit setParent(Object obj, Object obj2)

    Unit setTokenBoundaries(Object obj, Token token, Token token2)
}
