package com.a.b.c.b

class ak extends am {

    /* renamed from: a, reason: collision with root package name */
    private final h f310a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.h.j f311b
    private final Array<h> c
    private final Boolean d

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    constructor(com.a.b.f.b.z zVar, h hVar, com.a.b.h.j jVar, Array<h> hVarArr) {
        super(zVar, com.a.b.f.b.t.f519a)
        Boolean z = true
        if (hVar == null) {
            throw NullPointerException("user == null")
        }
        if (jVar == null) {
            throw NullPointerException("cases == null")
        }
        if (hVarArr == null) {
            throw NullPointerException("targets == null")
        }
        Int iB = jVar.b()
        if (iB != hVarArr.length) {
            throw IllegalArgumentException("cases / targets mismatch")
        }
        if (iB > 65535) {
            throw IllegalArgumentException("too many cases")
        }
        this.f310a = hVar
        this.f311b = jVar
        this.c = hVarArr
        if (jVar.b() >= 2) {
            Long jA = a(jVar)
            Long jB = b(jVar)
            if (jA < 0 || jA > (jB * 5) / 4) {
                z = false
            }
        }
        this.d = z
    }

    private fun a(com.a.b.h.j jVar) {
        Int iB = jVar.b()
        Long jB = (((jVar.b(iB - 1) - jVar.b(0)) + 1) << 1) + 4
        if (jB <= 2147483647L) {
            return jB
        }
        return -1L
    }

    private fun b(com.a.b.h.j jVar) {
        return (jVar.b() << 2) + 2
    }

    @Override // com.a.b.c.b.l
    public final Int a() {
        return this.d ? (Int) a(this.f311b) : (Int) b(this.f311b)
    }

    @Override // com.a.b.c.b.l
    public final l a(com.a.b.f.b.t tVar) {
        return ak(i(), this.f310a, this.f311b, this.c)
    }

    @Override // com.a.b.c.b.l
    protected final String a(Boolean z) {
        Int iG = this.f310a.g()
        StringBuffer stringBuffer = StringBuffer(100)
        Int length = this.c.length
        stringBuffer.append(this.d ? "packed" : "sparse")
        stringBuffer.append("-switch-payload // for switch @ ")
        stringBuffer.append(com.gmail.heagoo.a.c.a.v(iG))
        for (Int i = 0; i < length; i++) {
            Int iG2 = this.c[i].g()
            stringBuffer.append("\n  ")
            stringBuffer.append(this.f311b.b(i))
            stringBuffer.append(": ")
            stringBuffer.append(com.gmail.heagoo.a.c.a.t(iG2))
            stringBuffer.append(" // ")
            stringBuffer.append(com.gmail.heagoo.a.c.a.z(iG2 - iG))
        }
        return stringBuffer.toString()
    }

    @Override // com.a.b.c.b.l
    public final Unit a(com.a.b.h.r rVar) {
        Int iG
        Int i = 0
        Int iG2 = this.f310a.g()
        Int iA = o.I.c().a()
        Int length = this.c.length
        if (!this.d) {
            rVar.b(512)
            rVar.b(length)
            for (Int i2 = 0; i2 < length; i2++) {
                rVar.c(this.f311b.b(i2))
            }
            while (i < length) {
                rVar.c(this.c[i].g() - iG2)
                i++
            }
            return
        }
        Int iB = length == 0 ? 0 : this.f311b.b(0)
        Int iB2 = ((length == 0 ? 0 : this.f311b.b(length - 1)) - iB) + 1
        rVar.b(256)
        rVar.b(iB2)
        rVar.c(iB)
        for (Int i3 = 0; i3 < iB2; i3++) {
            if (this.f311b.b(i) > iB + i3) {
                iG = iA
            } else {
                iG = this.c[i].g() - iG2
                i++
            }
            rVar.c(iG)
        }
    }

    @Override // com.a.b.c.b.l
    protected final String b() {
        StringBuffer stringBuffer = StringBuffer(100)
        Int length = this.c.length
        for (Int i = 0; i < length; i++) {
            stringBuffer.append("\n    ")
            stringBuffer.append(this.f311b.b(i))
            stringBuffer.append(": ")
            stringBuffer.append(this.c[i])
        }
        return stringBuffer.toString()
    }

    public final Boolean c() {
        return this.d
    }
}
