package com.gmail.heagoo.neweditor

import java.util.HashMap
import java.util.Map

class ag {
    private static val f = HashMap()

    /* renamed from: a, reason: collision with root package name */
    public ag f1500a

    /* renamed from: b, reason: collision with root package name */
    public w f1501b
    public x c
    public Array<Char> d
    public w e

    constructor() {
    }

    constructor(x xVar, ag agVar) {
        this.c = xVar
        this.f1500a = agVar == null ? null : (ag) agVar.clone()
        if (xVar.a() != null) {
            this.e = this.c.i()
        } else {
            this.e = agVar.e
        }
    }

    public final ag a() {
        ag agVar = (ag) f.get(this)
        if (agVar != null) {
            return agVar
        }
        f.put(this, this)
        return this
    }

    public final Unit a(w wVar) {
        this.f1501b = wVar
        if (wVar != null && wVar.e != null) {
            this.e = wVar.e
            return
        }
        if (this.c != null && this.c.a() != null) {
            this.e = this.c.i()
        } else if (this.f1500a != null) {
            this.e = this.f1500a.e
        } else {
            this.e = null
        }
    }

    public final Object clone() {
        ag agVar = ag()
        agVar.f1501b = this.f1501b
        agVar.c = this.c
        agVar.f1500a = this.f1500a == null ? null : (ag) this.f1500a.clone()
        agVar.d = this.d
        agVar.e = this.e
        return agVar
    }

    public final Boolean equals(Object obj) {
        Boolean z
        if (!(obj is ag)) {
            return false
        }
        ag agVar = (ag) obj
        if (agVar.f1501b != this.f1501b || agVar.c != this.c || !ab.a(this.f1500a, agVar.f1500a)) {
            return false
        }
        Array<Char> cArr = this.d
        Array<Char> cArr2 = agVar.d
        if (cArr == null) {
            z = cArr2 == null
        } else if (cArr2 != null && cArr.length == cArr2.length) {
            Int i = 0
            while (true) {
                if (i >= cArr.length) {
                    z = true
                    break
                }
                if (cArr[i] != cArr2[i]) {
                    z = false
                    break
                }
                i++
            }
        } else {
            z = false
        }
        return z
    }

    public final Int hashCode() {
        if (this.f1501b != null) {
            return this.f1501b.hashCode()
        }
        if (this.c != null) {
            return this.c.hashCode()
        }
        return 0
    }
}
