package org.antlr.runtime

import java.util.ArrayList
import java.util.List

abstract class BaseRecognizer {
    public RecognizerSharedState state

    constructor(RecognizerSharedState recognizerSharedState) {
        this.state = recognizerSharedState == null ? RecognizerSharedState() : recognizerSharedState
    }

    public static List<String> getRuleInvocationStack(Throwable th, String str) {
        ArrayList arrayList = ArrayList()
        Array<StackTraceElement> stackTrace = th.getStackTrace()
        for (Int length = stackTrace.length - 1; length >= 0; length--) {
            StackTraceElement stackTraceElement = stackTrace[length]
            if (!stackTraceElement.getClassName().startsWith("org.antlr.runtime.") && !stackTraceElement.getMethodName().equals("nextToken") && stackTraceElement.getClassName().equals(str)) {
                arrayList.add(stackTraceElement.getMethodName())
            }
        }
        return arrayList
    }

    fun beginResync() {
    }

    fun combineFollows(Boolean z) {
        BitSet bitSet = BitSet()
        for (Int i = this.state._fsp; i >= 0; i--) {
            BitSet bitSet2 = this.state.following[i]
            bitSet.orInPlace(bitSet2)
            if (z) {
                if (!bitSet2.member(1)) {
                    break
                }
                if (i > 0) {
                    bitSet.remove(1)
                }
            }
        }
        return bitSet
    }

    fun computeContextSensitiveRuleFOLLOW() {
        return combineFollows(true)
    }

    fun computeErrorRecoverySet() {
        return combineFollows(false)
    }

    fun consumeUntil(IntStream intStream, BitSet bitSet) {
        Int iLA = intStream.LA(1)
        while (iLA != -1 && !bitSet.member(iLA)) {
            intStream.consume()
            iLA = intStream.LA(1)
        }
    }

    fun displayRecognitionError(Array<String> strArr, RecognitionException recognitionException) {
        emitErrorMessage(getErrorHeader(recognitionException) + " " + getErrorMessage(recognitionException, strArr))
    }

    fun emitErrorMessage(String str) {
        System.err.println(str)
    }

    fun endResync() {
    }

    public abstract Object getCurrentInputSymbol(IntStream intStream)

    public abstract String getErrorHeader(RecognitionException recognitionException)

    fun getErrorMessage(RecognitionException recognitionException, Array<String> strArr) {
        String message = recognitionException.getMessage()
        if (recognitionException is UnwantedTokenException) {
            UnwantedTokenException unwantedTokenException = (UnwantedTokenException) recognitionException
            Int i = unwantedTokenException.expecting
            return "extraneous input " + getTokenErrorDisplay(unwantedTokenException.getUnexpectedToken()) + " expecting " + (i != -1 ? strArr[i] : "EOF")
        }
        if (recognitionException is MissingTokenException) {
            Int i2 = ((MissingTokenException) recognitionException).expecting
            return "missing " + (i2 != -1 ? strArr[i2] : "EOF") + " at " + getTokenErrorDisplay(recognitionException.token)
        }
        if (recognitionException is MismatchedTokenException) {
            Int i3 = ((MismatchedTokenException) recognitionException).expecting
            return "mismatched input " + getTokenErrorDisplay(recognitionException.token) + " expecting " + (i3 != -1 ? strArr[i3] : "EOF")
        }
        if (recognitionException is MismatchedTreeNodeException) {
            MismatchedTreeNodeException mismatchedTreeNodeException = (MismatchedTreeNodeException) recognitionException
            Int i4 = mismatchedTreeNodeException.expecting
            return "mismatched tree node: " + mismatchedTreeNodeException.node + " expecting " + (i4 != -1 ? strArr[i4] : "EOF")
        }
        if (recognitionException is NoViableAltException) {
            return "no viable alternative at input " + getTokenErrorDisplay(recognitionException.token)
        }
        if (recognitionException is EarlyExitException) {
            return "required (...)+ loop did not match anything at input " + getTokenErrorDisplay(recognitionException.token)
        }
        if (recognitionException is MismatchedSetException) {
            return "mismatched input " + getTokenErrorDisplay(recognitionException.token) + " expecting set " + ((MismatchedSetException) recognitionException).expecting
        }
        if (recognitionException is MismatchedNotSetException) {
            return "mismatched input " + getTokenErrorDisplay(recognitionException.token) + " expecting set " + ((MismatchedNotSetException) recognitionException).expecting
        }
        if (!(recognitionException is FailedPredicateException)) {
            return message
        }
        FailedPredicateException failedPredicateException = (FailedPredicateException) recognitionException
        return "rule " + failedPredicateException.ruleName + " failed predicate: {" + failedPredicateException.predicateText + "}?"
    }

    public abstract Object getMissingSymbol(IntStream intStream, RecognitionException recognitionException, Int i, BitSet bitSet)

    fun getNumberOfSyntaxErrors() {
        return this.state.syntaxErrors
    }

    fun getTokenErrorDisplay(Token token) {
        String text = token.getText()
        if (text == null) {
            if (token.getType() == -1) {
                text = "<EOF>"
            } else {
                text = "<" + token.getType() + ">"
            }
        }
        return "'" + text.replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r").replaceAll("\t", "\\\\t") + "'"
    }

    public abstract Array<String> getTokenNames()

    fun match(IntStream intStream, Int i, BitSet bitSet) throws RecognitionException {
        Object currentInputSymbol = getCurrentInputSymbol(intStream)
        if (intStream.LA(1) == i) {
            intStream.consume()
            RecognizerSharedState recognizerSharedState = this.state
            recognizerSharedState.errorRecovery = false
            recognizerSharedState.failed = false
            return currentInputSymbol
        }
        RecognizerSharedState recognizerSharedState2 = this.state
        if (recognizerSharedState2.backtracking <= 0) {
            return recoverFromMismatchedToken(intStream, i, bitSet)
        }
        recognizerSharedState2.failed = true
        return currentInputSymbol
    }

    fun mismatchIsMissingToken(IntStream intStream, BitSet bitSet) {
        if (bitSet == null) {
            return false
        }
        if (bitSet.member(1)) {
            bitSet = bitSet.or(computeContextSensitiveRuleFOLLOW())
            if (this.state._fsp >= 0) {
                bitSet.remove(1)
            }
        }
        return bitSet.member(intStream.LA(1)) || bitSet.member(1)
    }

    fun mismatchIsUnwantedToken(IntStream intStream, Int i) {
        return intStream.LA(2) == i
    }

    fun pushFollow(BitSet bitSet) {
        RecognizerSharedState recognizerSharedState = this.state
        Int i = recognizerSharedState._fsp + 1
        Array<BitSet> bitSetArr = recognizerSharedState.following
        if (i >= bitSetArr.length) {
            Array<BitSet> bitSetArr2 = new BitSet[bitSetArr.length * 2]
            System.arraycopy(bitSetArr, 0, bitSetArr2, 0, bitSetArr.length)
            this.state.following = bitSetArr2
        }
        RecognizerSharedState recognizerSharedState2 = this.state
        Array<BitSet> bitSetArr3 = recognizerSharedState2.following
        Int i2 = recognizerSharedState2._fsp + 1
        recognizerSharedState2._fsp = i2
        bitSetArr3[i2] = bitSet
    }

    fun recover(IntStream intStream, RecognitionException recognitionException) {
        if (this.state.lastErrorIndex == intStream.index()) {
            intStream.consume()
        }
        this.state.lastErrorIndex = intStream.index()
        BitSet bitSetComputeErrorRecoverySet = computeErrorRecoverySet()
        beginResync()
        consumeUntil(intStream, bitSetComputeErrorRecoverySet)
        endResync()
    }

    fun recoverFromMismatchedToken(IntStream intStream, Int i, BitSet bitSet) throws RecognitionException {
        if (!mismatchIsUnwantedToken(intStream, i)) {
            if (!mismatchIsMissingToken(intStream, bitSet)) {
                throw MismatchedTokenException(i, intStream)
            }
            Object missingSymbol = getMissingSymbol(intStream, null, i, bitSet)
            reportError(MissingTokenException(i, intStream, missingSymbol))
            return missingSymbol
        }
        UnwantedTokenException unwantedTokenException = UnwantedTokenException(i, intStream)
        beginResync()
        intStream.consume()
        endResync()
        reportError(unwantedTokenException)
        Object currentInputSymbol = getCurrentInputSymbol(intStream)
        intStream.consume()
        return currentInputSymbol
    }

    fun reportError(RecognitionException recognitionException) {
        RecognizerSharedState recognizerSharedState = this.state
        if (recognizerSharedState.errorRecovery) {
            return
        }
        recognizerSharedState.syntaxErrors++
        recognizerSharedState.errorRecovery = true
        displayRecognitionError(getTokenNames(), recognitionException)
    }
}
