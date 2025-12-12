package com.gmail.heagoo.neweditor

class Token {
    public static val COMMENT1 = 1
    public static val COMMENT2 = 2
    public static val COMMENT3 = 3
    public static val COMMENT4 = 4
    public static val DIGIT = 5
    public static val END = 127
    public static val FUNCTION = 6
    public static val ID_COUNT = 19
    public static val INVALID = 7
    public static val KEYWORD1 = 8
    public static val KEYWORD2 = 9
    public static val KEYWORD3 = 10
    public static val KEYWORD4 = 11
    public static val LABEL = 12
    public static val LITERAL1 = 13
    public static val LITERAL2 = 14
    public static val LITERAL3 = 15
    public static val LITERAL4 = 16
    public static val MARKUP = 17
    public static val NULL = 0
    public static val OPERATOR = 18
    public static final Array<String> TOKEN_TYPES = {"NULL", "COMMENT1", "COMMENT2", "COMMENT3", "COMMENT4", "DIGIT", "FUNCTION", "INVALID", "KEYWORD1", "KEYWORD2", "KEYWORD3", "KEYWORD4", "LABEL", "LITERAL1", "LITERAL2", "LITERAL3", "LITERAL4", "MARKUP", "OPERATOR"}
    public Byte id
    public Int length
    public Token next
    public Int offset
    public x rules

    constructor(Byte b2, Int i, Int i2, x xVar) {
        this.id = b2
        this.offset = i
        this.length = i2
        this.rules = xVar
    }

    fun stringToToken(String str) {
        try {
            return Token.class.getField(str).getByte(null)
        } catch (Exception e) {
            return (Byte) -1
        }
    }

    fun tokenToString(Byte b2) {
        return b2 == 127 ? "END" : TOKEN_TYPES[b2]
    }

    fun toString() {
        return "[id=" + ((Int) this.id) + ",offset=" + this.offset + ",length=" + this.length + "]"
    }
}
