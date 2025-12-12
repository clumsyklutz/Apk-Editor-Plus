package com.a.b.f.b

class x {

    /* renamed from: a, reason: collision with root package name */
    private final c f526a

    /* renamed from: b, reason: collision with root package name */
    private final Int f527b
    private com.a.b.h.Array<j> c
    private com.a.b.h.j d

    constructor(c cVar, Int i) {
        if (cVar == null) {
            throw NullPointerException("blocks == null")
        }
        if (i < 0) {
            throw IllegalArgumentException("firstLabel < 0")
        }
        this.f526a = cVar
        this.f527b = i
        this.c = null
        this.d = null
    }

    public final c a() {
        return this.f526a
    }

    public final com.a.b.h.j a(Int i) {
        if (this.d == null) {
            Int iJ = this.f526a.j()
            com.a.b.h.Array<j> jVarArr = new com.a.b.h.j[iJ]
            com.a.b.h.j jVar = new com.a.b.h.j(10)
            Int iD_ = this.f526a.d_()
            for (Int i2 = 0; i2 < iD_; i2++) {
                a aVarA = this.f526a.a(i2)
                Int iA = aVarA.a()
                com.a.b.h.j jVarC = aVarA.c()
                Int iB = jVarC.b()
                if (iB == 0) {
                    jVar.c(iA)
                } else {
                    for (Int i3 = 0; i3 < iB; i3++) {
                        Int iB2 = jVarC.b(i3)
                        com.a.b.h.j jVar2 = jVarArr[iB2]
                        if (jVar2 == null) {
                            jVar2 = new com.a.b.h.j(10)
                            jVarArr[iB2] = jVar2
                        }
                        jVar2.c(iA)
                    }
                }
            }
            for (Int i4 = 0; i4 < iJ; i4++) {
                com.a.b.h.j jVar3 = jVarArr[i4]
                if (jVar3 != null) {
                    jVar3.g()
                    jVar3.b_()
                }
            }
            jVar.g()
            jVar.b_()
            if (jVarArr[this.f527b] == null) {
                jVarArr[this.f527b] = com.a.b.h.j.f673a
            }
            this.c = jVarArr
            this.d = jVar
        }
        com.a.b.h.j jVar4 = this.c[i]
        if (jVar4 == null) {
            throw RuntimeException("no such block: " + com.gmail.heagoo.a.c.a.v(i))
        }
        return jVar4
    }

    public final Int b() {
        return this.f527b
    }
}
