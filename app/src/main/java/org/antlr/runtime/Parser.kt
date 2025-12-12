package org.antlr.runtime

class Parser extends BaseRecognizer {
    public TokenStream input

    constructor(TokenStream tokenStream, RecognizerSharedState recognizerSharedState) {
        super(recognizerSharedState)
        this.input = tokenStream
    }

    @Override // org.antlr.runtime.BaseRecognizer
    fun getCurrentInputSymbol(IntStream intStream) {
        return ((TokenStream) intStream).LT(1)
    }

    @Override // org.antlr.runtime.BaseRecognizer
    fun getMissingSymbol(IntStream intStream, RecognitionException recognitionException, Int i, BitSet bitSet) {
        String str
        if (i == -1) {
            str = "<missing EOF>"
        } else {
            str = "<missing " + getTokenNames()[i] + ">"
        }
        CommonToken commonToken = CommonToken(i, str)
        TokenStream tokenStream = (TokenStream) intStream
        Token tokenLT = tokenStream.LT(1)
        if (tokenLT.getType() == -1) {
            tokenLT = tokenStream.LT(-1)
        }
        commonToken.line = tokenLT.getLine()
        commonToken.charPositionInLine = tokenLT.getCharPositionInLine()
        commonToken.channel = 0
        commonToken.input = tokenLT.getInputStream()
        return commonToken
    }

    fun getSourceName() {
        return this.input.getSourceName()
    }
}
