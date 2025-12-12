package com.a.b.f.b

import java.util.HashMap

class r implements com.a.b.f.d.d, com.a.b.h.s, Comparable {

    /* renamed from: a, reason: collision with root package name */
    private static val f515a = HashMap(1000)

    /* renamed from: b, reason: collision with root package name */
    private static val f516b = s(0)
    private final Int c
    private final com.a.b.f.d.d d
    private final m e

    private constructor(Int i, com.a.b.f.d.d dVar, m mVar) {
        if (i < 0) {
            throw IllegalArgumentException("reg < 0")
        }
        if (dVar == null) {
            throw NullPointerException("type == null")
        }
        this.c = i
        this.d = dVar
        this.e = mVar
    }

    /* synthetic */ r(Int i, com.a.b.f.d.d dVar, m mVar, Byte b2) {
        this(i, dVar, mVar)
    }

    fun a(Int i, com.a.b.f.d.d dVar) {
        return d(i, dVar, null)
    }

    fun a(Int i, com.a.b.f.d.d dVar, m mVar) {
        if (mVar == null) {
            throw NullPointerException("local  == null")
        }
        return d(i, dVar, mVar)
    }

    private fun a(Boolean z) {
        StringBuffer stringBuffer = StringBuffer(40)
        stringBuffer.append(m())
        stringBuffer.append(":")
        if (this.e != null) {
            stringBuffer.append(this.e.toString())
        }
        com.a.b.f.d.c cVarA = this.d.a()
        stringBuffer.append(cVarA)
        if (cVarA != this.d) {
            stringBuffer.append("=")
            if (z && (this.d is com.a.b.f.c.y)) {
                stringBuffer.append(((com.a.b.f.c.y) this.d).i())
            } else if (z && (this.d is com.a.b.f.c.a)) {
                stringBuffer.append(this.d.d())
            } else {
                stringBuffer.append(this.d)
            }
        }
        return stringBuffer.toString()
    }

    fun b(Int i, com.a.b.f.d.d dVar, m mVar) {
        return d(i, dVar, mVar)
    }

    private fun d(Int i, com.a.b.f.d.d dVar, m mVar) {
        r rVarA
        synchronized (f515a) {
            f516b.a(i, dVar, mVar)
            rVarA = (r) f515a.get(f516b)
            if (rVarA == null) {
                rVarA = f516b.a()
                f515a.put(rVarA, rVarA)
            }
        }
        return rVarA
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun e(Int i, com.a.b.f.d.d dVar, m mVar) {
        return this.c == i && this.d.equals(dVar) && (this.e == mVar || (this.e != null && this.e.equals(mVar)))
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun f(Int i, com.a.b.f.d.d dVar, m mVar) {
        return ((((mVar != null ? mVar.hashCode() : 0) * 31) + dVar.hashCode()) * 31) + i
    }

    public final r a(Int i) {
        return this.c == i ? this : d(i, this.d, this.e)
    }

    public final r a(m mVar) {
        return this.e != mVar ? (this.e == null || !this.e.equals(mVar)) ? d(this.c, this.d, mVar) : this : this
    }

    public final r a(r rVar, Boolean z) {
        com.a.b.f.d.d dVarA
        if (this == rVar) {
            return this
        }
        if (rVar == null || this.c != rVar.c) {
            return null
        }
        m mVar = (this.e == null || !this.e.equals(rVar.e)) ? null : this.e
        Boolean z2 = mVar == this.e
        if ((z && !z2) || (dVarA = a()) != rVar.a()) {
            return null
        }
        if (this.d.equals(rVar.d)) {
            dVarA = this.d
        }
        return (dVarA == this.d && z2) ? this : mVar == null ? d(this.c, dVarA, null) : a(this.c, dVarA, mVar)
    }

    public final r a(com.a.b.f.d.d dVar) {
        return d(this.c, dVar, this.e)
    }

    @Override // com.a.b.f.d.d
    public final com.a.b.f.d.c a() {
        return this.d.a()
    }

    public final Boolean a(r rVar) {
        return b(rVar) && this.c == rVar.c
    }

    public final r b(Int i) {
        return i == 0 ? this : a(this.c + i)
    }

    @Override // com.a.b.f.d.d
    public final com.a.b.f.d.d b() {
        return this.d.b()
    }

    public final Boolean b(r rVar) {
        if (rVar != null && this.d.a().equals(rVar.d.a())) {
            return this.e == rVar.e || (this.e != null && this.e.equals(rVar.e))
        }
        return false
    }

    @Override // com.a.b.f.d.d
    public final Int c() {
        return this.d.c()
    }

    @Override // java.lang.Comparable
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public final Int compareTo(r rVar) {
        if (this.c < rVar.c) {
            return -1
        }
        if (this.c > rVar.c) {
            return 1
        }
        Int iCompareTo = this.d.a().compareTo(rVar.d.a())
        if (iCompareTo != 0) {
            return iCompareTo
        }
        if (this.e == null) {
            return rVar.e == null ? 0 : -1
        }
        if (rVar.e == null) {
            return 1
        }
        return this.e.compareTo(rVar.e)
    }

    @Override // com.a.b.h.s
    public final String d() {
        return a(true)
    }

    @Override // com.a.b.f.d.d
    public final Int e() {
        return this.d.e()
    }

    public final Boolean equals(Object obj) {
        if (obj is r) {
            r rVar = (r) obj
            return e(rVar.c, rVar.d, rVar.e)
        }
        if (!(obj is s)) {
            return false
        }
        s sVar = (s) obj
        return e(sVar.f517a, sVar.f518b, sVar.c)
    }

    @Override // com.a.b.f.d.d
    public final Boolean f() {
        return false
    }

    public final Int g() {
        return this.c
    }

    public final com.a.b.f.d.d h() {
        return this.d
    }

    public final Int hashCode() {
        return f(this.c, this.d, this.e)
    }

    public final m i() {
        return this.e
    }

    public final Int j() {
        return this.c + k()
    }

    public final Int k() {
        return this.d.a().i()
    }

    public final Boolean l() {
        return this.d.a().k()
    }

    public final String m() {
        return "v" + this.c
    }

    public final r n() {
        com.a.b.f.d.d dVar = this.d
        com.a.b.f.d.c cVarA = dVar is com.a.b.f.d.c ? (com.a.b.f.d.c) dVar : dVar.a()
        if (cVarA.p()) {
            cVarA = cVarA.q()
        }
        return cVarA == dVar ? this : d(this.c, cVarA, this.e)
    }

    public final String toString() {
        return a(false)
    }
}
