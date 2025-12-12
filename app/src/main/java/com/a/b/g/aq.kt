package com.a.b.g

import java.util.ArrayList

class aq implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private final an f611a

    /* renamed from: b, reason: collision with root package name */
    private Int f612b
    private final Int c
    private Int d
    private final com.a.b.f.b.Array<r>[] e
    private final ArrayList f

    constructor(an anVar) {
        this.c = anVar.g()
        this.f611a = anVar
        this.f612b = this.c
        this.d = 0
        this.e = new com.a.b.f.b.r[anVar.j().size()][]
        this.f = ArrayList()
        com.a.b.f.b.Array<r> rVarArr = new com.a.b.f.b.r[this.c]
        for (Int i = 0; i < this.c; i++) {
            rVarArr[i] = com.a.b.f.b.r.a(i, com.a.b.f.d.c.i)
        }
        this.e[anVar.c()] = rVarArr
    }

    constructor(an anVar, Int i) {
        this(anVar)
        this.d = i
    }

    static /* synthetic */ com.a.b.f.b.m a(aq aqVar, Int i) {
        if (i < aqVar.f.size()) {
            return (com.a.b.f.b.m) aqVar.f.get(i)
        }
        return null
    }

    static /* synthetic */ Unit a(aq aqVar, com.a.b.f.b.r rVar) {
        Int iG = rVar.g()
        com.a.b.f.b.m mVarI = rVar.i()
        aqVar.f.ensureCapacity(iG + 1)
        while (aqVar.f.size() <= iG) {
            aqVar.f.add(null)
        }
        aqVar.f.set(iG, mVarI)
    }

    static /* synthetic */ Boolean a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2))
    }

    static /* synthetic */ com.a.b.f.b.Array<r> a(com.a.b.f.b.Array<r> rVarArr) {
        com.a.b.f.b.Array<r> rVarArr2 = new com.a.b.f.b.r[rVarArr.length]
        System.arraycopy(rVarArr, 0, rVarArr2, 0, rVarArr.length)
        return rVarArr2
    }

    static /* synthetic */ Boolean b(aq aqVar, Int i) {
        return i < aqVar.d
    }

    static /* synthetic */ Boolean c(aq aqVar, Int i) {
        return i < aqVar.c
    }

    static /* synthetic */ Int d(aq aqVar) {
        Int i = aqVar.f612b
        aqVar.f612b = i + 1
        return i
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        this.f611a.a(ar(this))
        this.f611a.e(this.f612b)
        this.f611a.m()
    }
}
