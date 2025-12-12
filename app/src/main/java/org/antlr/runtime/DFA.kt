package org.antlr.runtime

class DFA {
    public Array<Short> accept
    public Int decisionNumber
    public Array<Short> eof
    public Array<Short> eot
    public Array<Char> max
    public Array<Char> min
    public BaseRecognizer recognizer
    public Array<Short> special
    public Array<Short>[] transition

    public static Array<Short> unpackEncodedString(String str) {
        Int iCharAt = 0
        for (Int i = 0; i < str.length(); i += 2) {
            iCharAt += str.charAt(i)
        }
        Array<Short> sArr = new Short[iCharAt]
        Int i2 = 0
        for (Int i3 = 0; i3 < str.length(); i3 += 2) {
            Char cCharAt = str.charAt(i3)
            Char cCharAt2 = str.charAt(i3 + 1)
            Int i4 = 1
            while (i4 <= cCharAt) {
                sArr[i2] = (Short) cCharAt2
                i4++
                i2++
            }
        }
        return sArr
    }

    public static Array<Char> unpackEncodedStringToUnsignedChars(String str) {
        Int iCharAt = 0
        for (Int i = 0; i < str.length(); i += 2) {
            iCharAt += str.charAt(i)
        }
        Array<Char> cArr = new Char[iCharAt]
        Int i2 = 0
        for (Int i3 = 0; i3 < str.length(); i3 += 2) {
            Char cCharAt = str.charAt(i3)
            Char cCharAt2 = str.charAt(i3 + 1)
            Int i4 = 1
            while (i4 <= cCharAt) {
                cArr[i2] = cCharAt2
                i4++
                i2++
            }
        }
        return cArr
    }

    fun error(NoViableAltException noViableAltException) {
    }

    fun getDescription() {
        throw null
    }

    fun noViableAlt(Int i, IntStream intStream) throws NoViableAltException {
        RecognizerSharedState recognizerSharedState = this.recognizer.state
        if (recognizerSharedState.backtracking > 0) {
            recognizerSharedState.failed = true
        } else {
            NoViableAltException noViableAltException = NoViableAltException(getDescription(), this.decisionNumber, i, intStream)
            error(noViableAltException)
            throw noViableAltException
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [Int] */
    /* JADX WARN: Type inference failed for: r2v2, types: [Int] */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v5, types: [Short] */
    /* JADX WARN: Type inference failed for: r2v6, types: [Short] */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r6v0, types: [org.antlr.runtime.DFA] */
    fun predict(IntStream intStream) throws RecognitionException {
        Int iMark = intStream.mark()
        Short sSpecialStateTransition = 0
        while (true) {
            try {
                Short s = this.special[sSpecialStateTransition]
                if (s >= 0) {
                    sSpecialStateTransition = specialStateTransition(s, intStream)
                    if (sSpecialStateTransition == -1) {
                        noViableAlt(sSpecialStateTransition, intStream)
                        return 0
                    }
                    intStream.consume()
                } else {
                    Array<Short> sArr = this.accept
                    if (sArr[sSpecialStateTransition] >= 1) {
                        return sArr[sSpecialStateTransition]
                    }
                    Char cLA = (Char) intStream.LA(1)
                    Array<Char> cArr = this.min
                    if (cLA < cArr[sSpecialStateTransition] || cLA > this.max[sSpecialStateTransition]) {
                        Array<Short> sArr2 = this.eot
                        if (sArr2[sSpecialStateTransition] < 0) {
                            if (cLA == 65535) {
                                Array<Short> sArr3 = this.eof
                                if (sArr3[sSpecialStateTransition] >= 0) {
                                    return this.accept[sArr3[sSpecialStateTransition]]
                                }
                            }
                            noViableAlt(sSpecialStateTransition, intStream)
                            return 0
                        }
                        sSpecialStateTransition = sArr2[sSpecialStateTransition]
                        intStream.consume()
                    } else {
                        Short s2 = this.transition[sSpecialStateTransition][cLA - cArr[sSpecialStateTransition]]
                        if (s2 < 0) {
                            Array<Short> sArr4 = this.eot
                            if (sArr4[sSpecialStateTransition] < 0) {
                                noViableAlt(sSpecialStateTransition, intStream)
                                return 0
                            }
                            sSpecialStateTransition = sArr4[sSpecialStateTransition]
                            intStream.consume()
                        } else {
                            intStream.consume()
                            sSpecialStateTransition = s2
                        }
                    }
                }
            } finally {
                intStream.rewind(iMark)
            }
        }
    }

    fun specialStateTransition(Int i, IntStream intStream) throws NoViableAltException {
        return -1
    }
}
