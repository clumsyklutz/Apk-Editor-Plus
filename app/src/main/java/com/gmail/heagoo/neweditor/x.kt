package com.gmail.heagoo.neweditor

import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.Hashtable
import java.util.List
import java.util.Map
import java.util.regex.Pattern

class x {

    /* renamed from: a, reason: collision with root package name */
    private static final Array<x> f1535a = new x[19]

    /* renamed from: b, reason: collision with root package name */
    private String f1536b
    private Boolean c
    private Byte d
    private Pattern e
    private w f
    private Boolean g
    private t j
    private final String k
    private String l
    private Int m
    private final String o
    private Boolean h = true
    private Int p = -1
    private val n = HashMap()
    private val i = ArrayList()

    static {
        for (Byte b2 = 0; b2 < 19; b2 = (Byte) (b2 + 1)) {
            f1535a[b2] = x(null, null)
            f1535a[b2].d = b2
            f1535a[b2].c = true
        }
    }

    constructor(String str, String str2) {
        this.k = str
        this.o = str2
    }

    fun a(Byte b2) {
        return f1535a[b2]
    }

    public final String a() {
        return this.k
    }

    public final List a(Character ch) {
        List list = (List) this.n.get(null)
        Boolean z = list == null || list.isEmpty()
        Character chValueOf = ch == null ? null : Character.valueOf(Character.toUpperCase(ch.charValue()))
        List list2 = chValueOf != null ? (List) this.n.get(chValueOf) : null
        Boolean z2 = list2 == null || list2.isEmpty()
        if (z && z2) {
            return Collections.emptyList()
        }
        if (z2) {
            return list
        }
        if (z) {
            return list2
        }
        ArrayList arrayList = ArrayList(list.size() + list2.size())
        arrayList.addAll(list2)
        arrayList.addAll(list)
        return arrayList
    }

    public final Unit a(Int i) {
        if (i < 0) {
            i = -1
        }
        this.p = i
    }

    public final Unit a(t tVar) {
        this.j = tVar
        this.f1536b = null
    }

    public final Unit a(w wVar) {
        Array<Character> chArr
        this.m++
        if (wVar.l == null) {
            Array<Character> chArr2 = new Character[1]
            if (wVar.k == null || wVar.k.length <= 0) {
                chArr2[0] = null
                chArr = chArr2
            } else {
                chArr2[0] = Character.valueOf(wVar.k[0])
                chArr = chArr2
            }
        } else {
            Array<Character> chArr3 = new Character[wVar.l.length]
            Array<Char> cArr = wVar.l
            Int length = cArr.length
            Int i = 0
            Int i2 = 0
            while (i2 < length) {
                chArr3[i] = Character.valueOf(cArr[i2])
                i2++
                i++
            }
            chArr = chArr3
        }
        for (Character ch : chArr) {
            List arrayList = (List) this.n.get(ch)
            if (arrayList == null) {
                arrayList = ArrayList()
                this.n.put(ch, arrayList)
            }
            arrayList.add(wVar)
        }
    }

    public final Unit a(x xVar) {
        this.i.add(xVar)
    }

    public final Unit a(String str) {
        this.l = str
        this.f1536b = null
    }

    public final Unit a(Hashtable hashtable) {
        this.f1536b = null
    }

    public final Unit a(Pattern pattern) {
        this.e = pattern
    }

    public final Unit a(Boolean z) {
        this.h = z
    }

    public final String b() {
        return this.o
    }

    public final Unit b(Byte b2) {
        this.d = b2
    }

    public final Unit b(w wVar) {
        this.f = wVar
    }

    public final Unit b(Boolean z) {
        this.g = z
    }

    public final Int c() {
        return this.m
    }

    public final Int d() {
        return this.p
    }

    public final Boolean e() {
        return this.h
    }

    public final t f() {
        return this.j
    }

    public final Boolean g() {
        return this.g
    }

    public final Pattern h() {
        return this.e
    }

    public final w i() {
        return this.f
    }

    public final Byte j() {
        return this.d
    }

    public final String k() {
        if (this.f1536b == null) {
            this.f1536b = this.l
            if (this.l == null) {
                this.l = ""
            }
            if (this.j != null) {
                this.l += this.j.a()
            }
        }
        return this.l
    }

    public final Boolean l() {
        return this.c
    }

    public final String toString() {
        return String.valueOf(getClass().getName()) + '[' + this.k + "::" + this.o + ']'
    }
}
