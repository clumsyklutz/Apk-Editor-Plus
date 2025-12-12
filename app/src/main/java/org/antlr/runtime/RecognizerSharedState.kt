package org.antlr.runtime

class RecognizerSharedState {
    public Boolean failed
    public Array<BitSet> following = new BitSet[100]
    public Int _fsp = -1
    public Boolean errorRecovery = false
    public Int lastErrorIndex = -1
    public Int syntaxErrors = 0
    public Int backtracking = 0
}
