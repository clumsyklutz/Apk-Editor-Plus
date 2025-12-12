package com.gmail.heagoo.neweditor

import android.util.Log
import java.util.Hashtable
import java.util.Stack
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class ah extends DefaultHandler {

    /* renamed from: b, reason: collision with root package name */
    private t f1503b
    private x c
    private String e
    private String f
    private Stack g
    private Hashtable h
    private String d = "xml"

    /* renamed from: a, reason: collision with root package name */
    private af f1502a = af()

    constructor() {
        this.f1502a.a(x("xml", "MAIN"))
        this.g = Stack()
        this.h = Hashtable()
        a((String) null, (Attributes) null)
    }

    private fun a(String str) {
        for (Int size = this.g.size() - 1; size >= 0; size--) {
            ai aiVar = (ai) this.g.get(size)
            if (aiVar.y.equals(str)) {
                return aiVar
            }
        }
        return null
    }

    private fun a(String str, Attributes attributes) {
        ai aiVar = str != null ? ai(this, str, attributes) : null
        this.g.push(aiVar)
        return aiVar
    }

    protected static Unit a(String str, Object obj) {
        Log.d("DEBUG", "error occurred: " + str + ", " + obj.toString())
    }

    private fun b() {
        return (ai) this.g.peek()
    }

    public final af a() {
        return this.f1502a
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final Unit characters(Array<Char> cArr, Int i, Int i2) {
        ai aiVarB = b()
        if (aiVarB.y.equals("EOL_SPAN") || aiVarB.y.equals("EOL_SPAN_REGEXP") || aiVarB.y.equals("MARK_PREVIOUS") || aiVarB.y.equals("MARK_FOLLOWING") || aiVarB.y.equals("SEQ") || aiVarB.y.equals("SEQ_REGEXP") || aiVarB.y.equals("BEGIN")) {
            ai aiVar = aiVarB.y.equals("BEGIN") ? (ai) aiVarB.A.g.get(aiVarB.A.g.size() - 2) : aiVarB
            if (aiVar.v != null) {
                aiVar.v.append(cArr, i, i2)
                return
            }
            aiVar.v = StringBuffer()
            aiVar.v.append(cArr, i, i2)
            aiVar.w = (aiVar.c ? 8 : 0) | (!aiVar.f1505b ? 0 : 4) | (!aiVar.f1504a ? 0 : 2)
            aiVar.f1504a = false
            aiVar.c = false
            aiVar.f1505b = false
            return
        }
        if (!aiVarB.y.equals("END")) {
            if (aiVarB.o == null) {
                aiVarB.o = StringBuffer()
            }
            aiVarB.o.append(cArr, i, i2)
            return
        }
        ai aiVar2 = (ai) aiVarB.A.g.get(aiVarB.A.g.size() - 2)
        if (aiVar2.g != null) {
            aiVar2.g.append(cArr, i, i2)
            return
        }
        aiVar2.g = StringBuffer()
        aiVar2.g.append(cArr, i, i2)
        aiVar2.h = (aiVarB.f1504a ? 2 : 0) | (aiVarB.f1505b ? 4 : 0) | (aiVarB.c ? 8 : 0)
        aiVar2.i = aiVarB.t
        aiVar2.f1504a = false
        aiVar2.c = false
        aiVar2.f1505b = false
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final Unit endElement(String str, String str2, String str3) {
        Byte bStringToToken
        ai aiVar = (ai) this.g.pop()
        if (!str3.equals(aiVar.y)) {
            throw InternalError()
        }
        if (aiVar.y.equals("PROPERTY")) {
            this.h.put(this.e, this.f)
            return
        }
        if (aiVar.y.equals("PROPS")) {
            if (b().y.equals("RULES")) {
                this.c.a(this.h)
            }
            this.h = Hashtable()
            return
        }
        if (aiVar.y.equals("RULES")) {
            this.c.a(this.f1503b)
            this.f1503b = null
            this.c = null
            return
        }
        if (aiVar.y.equals("IMPORT")) {
            if (this.c.equals(aiVar.e)) {
                return
            }
            this.c.a(aiVar.e)
            return
        }
        if (aiVar.y.equals("TERMINATE")) {
            this.c.a(aiVar.z)
            return
        }
        if (aiVar.y.equals("SEQ")) {
            if (aiVar.v == null) {
                a("empty-tag", "SEQ")
                return
            } else {
                this.c.a(w.a(aiVar.w, aiVar.v.toString(), aiVar.e, aiVar.x))
                return
            }
        }
        if (aiVar.y.equals("SEQ_REGEXP")) {
            if (aiVar.v == null) {
                a("empty-tag", "SEQ_REGEXP")
                return
            }
            try {
                if (aiVar.l != null) {
                    this.c.a(w.a(aiVar.w, aiVar.l.toCharArray(), aiVar.v.toString(), aiVar.e, aiVar.x, a("RULES").n))
                } else {
                    this.c.a(w.a(aiVar.k, aiVar.w, aiVar.v.toString(), aiVar.e, aiVar.x, a("RULES").n))
                }
                return
            } catch (PatternSyntaxException e) {
                a("regexp", e)
                return
            }
        }
        if (aiVar.y.equals("SPAN")) {
            if (aiVar.v == null) {
                a("empty-tag", "BEGIN")
                return
            } else if (aiVar.g == null) {
                a("empty-tag", "END")
                return
            } else {
                this.c.a(w.a(aiVar.w, aiVar.v.toString(), aiVar.h, aiVar.g.toString(), aiVar.e, aiVar.x, aiVar.p, aiVar.q, aiVar.r, aiVar.j))
                return
            }
        }
        if (aiVar.y.equals("SPAN_REGEXP")) {
            if (aiVar.v == null) {
                a("empty-tag", "BEGIN")
                return
            }
            if (aiVar.g == null) {
                a("empty-tag", "END")
                return
            }
            try {
                if (aiVar.l != null) {
                    this.c.a(w.a(aiVar.w, aiVar.l.toCharArray(), aiVar.v.toString(), aiVar.h, aiVar.g.toString(), aiVar.e, aiVar.x, aiVar.p, aiVar.q, aiVar.r, a("RULES").n, aiVar.j, aiVar.i))
                } else {
                    this.c.a(w.a(aiVar.k, aiVar.w, aiVar.v.toString(), aiVar.h, aiVar.g.toString(), aiVar.e, aiVar.x, aiVar.p, aiVar.q, aiVar.r, a("RULES").n, aiVar.j, aiVar.i))
                }
                return
            } catch (PatternSyntaxException e2) {
                a("regexp", e2)
                return
            }
        }
        if (aiVar.y.equals("EOL_SPAN")) {
            if (aiVar.v == null) {
                a("empty-tag", "EOL_SPAN")
                return
            } else {
                this.c.a(w.a(aiVar.w, aiVar.v.toString(), aiVar.e, aiVar.x, aiVar.p))
                return
            }
        }
        if (aiVar.y.equals("EOL_SPAN_REGEXP")) {
            if (aiVar.v == null) {
                a("empty-tag", "EOL_SPAN_REGEXP")
                return
            }
            try {
                if (aiVar.l != null) {
                    this.c.a(w.a(aiVar.w, aiVar.l.toCharArray(), aiVar.v.toString(), aiVar.e, aiVar.x, aiVar.p, a("RULES").n))
                } else {
                    this.c.a(w.a(aiVar.k, aiVar.w, aiVar.v.toString(), aiVar.e, aiVar.x, aiVar.p, a("RULES").n))
                }
                return
            } catch (PatternSyntaxException e3) {
                a("regexp", e3)
                return
            }
        }
        if (aiVar.y.equals("MARK_FOLLOWING")) {
            if (aiVar.v == null) {
                a("empty-tag", "MARK_FOLLOWING")
                return
            } else {
                this.c.a(w.a(aiVar.w, aiVar.v.toString(), aiVar.x, aiVar.p))
                return
            }
        }
        if (aiVar.y.equals("MARK_PREVIOUS")) {
            if (aiVar.v == null) {
                a("empty-tag", "MARK_PREVIOUS")
                return
            } else {
                this.c.a(w.b(aiVar.w, aiVar.v.toString(), aiVar.x, aiVar.p))
                return
            }
        }
        if (aiVar.y.equals("END") || aiVar.y.equals("BEGIN") || aiVar.y.equals("KEYWORDS") || aiVar.y.equals("MODE") || (bStringToToken = Token.stringToToken(aiVar.y)) == -1) {
            return
        }
        if (aiVar.o == null || aiVar.o.length() == 0) {
            a("empty-keyword", (Object) null)
            return
        }
        String string = aiVar.o.toString()
        if (this.f1503b != null) {
            this.f1503b.a(string, bStringToToken)
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final Unit startElement(String str, String str2, String str3, Attributes attributes) {
        ai aiVarA = a(str3, attributes)
        if (str3.equals("WHITESPACE")) {
            return
        }
        if (str3.equals("KEYWORDS")) {
            this.f1503b = t(this.c.e())
            return
        }
        if (str3.equals("RULES")) {
            if (aiVarA.u == null) {
                aiVarA.u = "MAIN"
            }
            this.c = this.f1502a.a(aiVarA.u)
            if (this.c == null) {
                this.c = x(this.d, aiVarA.u)
                this.f1502a.a(this.c)
            }
            this.c.a(aiVarA.n)
            this.c.b(aiVarA.m)
            if (aiVarA.f != null) {
                try {
                    this.c.a(Pattern.compile(aiVarA.f, aiVarA.n ? 2 : 0))
                } catch (Exception e) {
                    a("regexp", e)
                }
            }
            if (aiVarA.j != null) {
                this.c.b(w.a(aiVarA.j))
            }
            this.c.b(aiVarA.d)
            this.c.a(aiVarA.s)
        }
    }
}
