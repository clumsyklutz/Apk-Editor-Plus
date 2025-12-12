package com.gmail.heagoo.neweditor

import org.xml.sax.Attributes

final class ai {
    final /* synthetic */ ah A

    /* renamed from: a, reason: collision with root package name */
    public Boolean f1504a

    /* renamed from: b, reason: collision with root package name */
    public Boolean f1505b
    public Boolean c
    public Byte d
    public x e
    public String f
    public StringBuffer g
    public Int h
    public Boolean i
    public String j
    public String k
    public String l
    public Boolean m
    public Boolean n
    public StringBuffer o
    public Byte p
    public Boolean q
    public Boolean r
    public String s
    public Boolean t
    public String u
    public StringBuffer v
    public Int w
    public Byte x
    public String y
    public Int z

    constructor(ah ahVar, String str, Attributes attributes) {
        String strSubstring
        String strSubstring2
        Boolean z = true
        this.A = ahVar
        this.d = (Byte) 0
        this.n = true
        this.s = "_"
        this.z = -1
        this.y = str
        ahVar.e = attributes.getValue("NAME")
        ahVar.f = attributes.getValue("VALUE")
        String value = attributes.getValue("TYPE")
        if (value != null) {
            this.x = Token.stringToToken(value)
            if (this.x == -1) {
                ah.a("token-invalid", value)
            }
        }
        this.p = (Byte) -2
        String value2 = attributes.getValue("EXCLUDE_MATCH")
        if (value2 != null && "TRUE".equalsIgnoreCase(value2)) {
            this.p = (Byte) -1
        }
        String value3 = attributes.getValue("MATCH_TYPE")
        if (value3 != null) {
            if ("CONTEXT".equals(value3)) {
                this.p = (Byte) -1
            } else if ("RULE".equals(value3)) {
                this.p = (Byte) -2
            } else {
                this.p = Token.stringToToken(value3)
                if (this.p == -1) {
                    ah.a("token-invalid", value3)
                }
            }
        }
        this.f1504a = "TRUE".equals(attributes.getValue("AT_LINE_START"))
        this.f1505b = "TRUE".equals(attributes.getValue("AT_WHITESPACE_END"))
        this.c = "TRUE".equals(attributes.getValue("AT_WORD_START"))
        this.q = "TRUE".equals(attributes.getValue("NO_LINE_BREAK"))
        this.r = "TRUE".equals(attributes.getValue("NO_WORD_BREAK"))
        if (attributes.getValue("IGNORE_CASE") != null && !"TRUE".equals(attributes.getValue("IGNORE_CASE"))) {
            z = false
        }
        this.n = z
        this.m = "TRUE".equals(attributes.getValue("HIGHLIGHT_DIGITS"))
        this.t = "TRUE".equals(attributes.getValue("REGEXP"))
        this.f = attributes.getValue("DIGIT_RE")
        String value4 = attributes.getValue("NO_WORD_SEP")
        if (value4 != null) {
            this.s = value4
        }
        String value5 = attributes.getValue("AT_CHAR")
        if (value5 != null) {
            try {
                this.z = Integer.parseInt(value5)
            } catch (NumberFormatException e) {
                ah.a("termchar-invalid", value5)
                this.z = -1
            }
        }
        this.j = attributes.getValue("ESCAPE")
        this.u = attributes.getValue("SET")
        String value6 = attributes.getValue("DELEGATE")
        if (value6 != null) {
            Int iIndexOf = value6.indexOf("::")
            if (iIndexOf != -1) {
                strSubstring = value6.substring(0, iIndexOf)
                strSubstring2 = value6.substring(iIndexOf + 2)
            } else {
                strSubstring = ahVar.d
                strSubstring2 = value6
            }
            af afVarA = ahVar.a()
            if (afVarA == null) {
                ah.a("delegate-invalid", value6)
            } else {
                this.e = afVarA.a(strSubstring2)
                if (afVarA == ahVar.f1502a && this.e == null) {
                    this.e = x(strSubstring, strSubstring2)
                    this.e.b((Byte) 7)
                    ahVar.f1502a.a(this.e)
                } else if (this.e == null) {
                    ah.a("delegate-invalid", value6)
                }
            }
        }
        String value7 = attributes.getValue("DEFAULT")
        if (value7 != null) {
            this.d = Token.stringToToken(value7)
            if (this.d == -1) {
                ah.a("token-invalid", value7)
                this.d = (Byte) 0
            }
        }
        this.k = attributes.getValue("HASH_CHAR")
        this.l = attributes.getValue("HASH_CHARS")
        if (this.k == null || this.l == null) {
            return
        }
        ah.a("hash-Char-and-hash-chars-mutually-exclusive", (Object) null)
        this.l = null
    }
}
