package org.antlr.runtime.tree

import java.util.regex.Pattern
import org.antlr.runtime.BaseRecognizer
import org.antlr.runtime.BitSet
import org.antlr.runtime.CommonToken
import org.antlr.runtime.IntStream
import org.antlr.runtime.MismatchedTreeNodeException
import org.antlr.runtime.RecognitionException
import org.antlr.runtime.RecognizerSharedState
import org.antlr.runtime.Token

class TreeParser extends BaseRecognizer {
    public static String doubleEtc = ".*\\.\\.\\.\\s+\\.\\.\\..*"
    public TreeNodeStream input

    static {
        Pattern.compile(".*[^.]\\.\\.[^.].*")
        Pattern.compile(doubleEtc)
    }

    constructor(TreeNodeStream treeNodeStream, RecognizerSharedState recognizerSharedState) {
        super(recognizerSharedState)
        setTreeNodeStream(treeNodeStream)
    }

    @Override // org.antlr.runtime.BaseRecognizer
    fun getCurrentInputSymbol(IntStream intStream) {
        return ((TreeNodeStream) intStream).LT(1)
    }

    @Override // org.antlr.runtime.BaseRecognizer
    fun getErrorMessage(RecognitionException recognitionException, Array<String> strArr) {
        TreeAdaptor treeAdaptor = ((TreeNodeStream) recognitionException.input).getTreeAdaptor()
        Token token = treeAdaptor.getToken(recognitionException.node)
        recognitionException.token = token
        if (token == null) {
            recognitionException.token = CommonToken(treeAdaptor.getType(recognitionException.node), treeAdaptor.getText(recognitionException.node))
        }
        return super.getErrorMessage(recognitionException, strArr)
    }

    @Override // org.antlr.runtime.BaseRecognizer
    fun getMissingSymbol(IntStream intStream, RecognitionException recognitionException, Int i, BitSet bitSet) {
        return ((TreeNodeStream) recognitionException.input).getTreeAdaptor().create(CommonToken(i, "<missing " + getTokenNames()[i] + ">"))
    }

    fun getSourceName() {
        return this.input.getSourceName()
    }

    @Override // org.antlr.runtime.BaseRecognizer
    fun recoverFromMismatchedToken(IntStream intStream, Int i, BitSet bitSet) throws RecognitionException {
        throw MismatchedTreeNodeException(i, (TreeNodeStream) intStream)
    }

    fun setTreeNodeStream(TreeNodeStream treeNodeStream) {
        this.input = treeNodeStream
    }
}
