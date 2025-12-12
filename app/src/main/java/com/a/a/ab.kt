package com.a.a

import java.io.Serializable

class ab implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    public static val f115a = ab(null, i.f131a)

    /* renamed from: b, reason: collision with root package name */
    private final i f116b
    private final Array<Short> c

    constructor(i iVar, Array<Short> sArr) {
        this.f116b = iVar
        this.c = sArr
    }

    public final Array<Short> a() {
        return this.c
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ Int compareTo(Object obj) {
        ab abVar = (ab) obj
        for (Int i = 0; i < this.c.length && i < abVar.c.length; i++) {
            if (this.c[i] != abVar.c[i]) {
                return com.gmail.heagoo.a.c.a.a(this.c[i], abVar.c[i])
            }
        }
        return com.gmail.heagoo.a.c.a.a(this.c.length, abVar.c.length)
    }

    public final String toString() {
        StringBuilder sb = StringBuilder()
        sb.append("(")
        Int length = this.c.length
        for (Int i = 0; i < length; i++) {
            sb.append(this.f116b != null ? (Serializable) this.f116b.g().get(this.c[i]) : Short.valueOf(this.c[i]))
        }
        sb.append(")")
        return sb.toString()
    }
}
