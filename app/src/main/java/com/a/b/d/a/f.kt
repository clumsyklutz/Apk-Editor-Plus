package com.a.b.d.a

import java.io.EOFException

abstract class f {

    /* renamed from: a, reason: collision with root package name */
    private final j f437a

    /* renamed from: b, reason: collision with root package name */
    private final Int f438b
    private final Int c
    private final Int d
    private final Int e
    private final Long f

    constructor(j jVar, Int i, Int i2, Int i3, Int i4, Long j) {
        if (jVar == null) {
            throw NullPointerException("format == null")
        }
        if (!com.gmail.heagoo.a.c.a.e(i)) {
            throw IllegalArgumentException("invalid opcode")
        }
        this.f437a = jVar
        this.f438b = i
        this.c = i2
        this.d = i3
        this.e = i4
        this.f = j
    }

    fun a(d dVar) {
        Int iC = dVar.c()
        return com.a.b.d.e.b(com.gmail.heagoo.a.c.a.f(iC)).a(iC, dVar)
    }

    public static Array<f> a(Array<Short> sArr) {
        Array<f> fVarArr = new f[sArr.length]
        av avVar = av(sArr)
        while (avVar.f()) {
            try {
                fVarArr[avVar.a()] = a(avVar)
            } catch (EOFException e) {
                throw new com.a.a.s(e)
            }
        }
        return fVarArr
    }

    public final Int a(Int i) {
        return this.e - i
    }

    public final j a() {
        return this.f437a
    }

    public final Unit a(e eVar) {
        this.f437a.a(this, eVar)
    }

    public final Int b() {
        return this.f438b
    }

    public final Short b(Int i) {
        Int i2 = this.e - i
        if (i2 != ((Short) i2)) {
            throw new com.a.a.s("Target out of range: " + com.gmail.heagoo.a.c.a.z(i2))
        }
        return (Short) i2
    }

    public final Int c(Int i) {
        Int i2 = this.e - i
        if (i2 != ((Byte) i2)) {
            throw new com.a.a.s("Target out of range: " + com.gmail.heagoo.a.c.a.z(i2))
        }
        return i2 & 255
    }

    public final Short c() {
        return (Short) this.f438b
    }

    public final Int d() {
        return this.c
    }

    public abstract f d(Int i)

    public final Short e() {
        return (Short) this.c
    }

    public final Int f() {
        return this.d
    }

    public final Int g() {
        return this.e
    }

    public final Long h() {
        return this.f
    }

    public final Int i() {
        if (this.f != ((Int) this.f)) {
            throw new com.a.a.s("Literal out of range: " + com.gmail.heagoo.a.c.a.a(this.f))
        }
        return (Int) this.f
    }

    public final Short j() {
        if (this.f != ((Short) this.f)) {
            throw new com.a.a.s("Literal out of range: " + com.gmail.heagoo.a.c.a.a(this.f))
        }
        return (Short) this.f
    }

    public final Int k() {
        if (this.f != ((Byte) this.f)) {
            throw new com.a.a.s("Literal out of range: " + com.gmail.heagoo.a.c.a.a(this.f))
        }
        return ((Int) this.f) & 255
    }

    public final Int l() {
        if (this.f < -8 || this.f > 7) {
            throw new com.a.a.s("Literal out of range: " + com.gmail.heagoo.a.c.a.a(this.f))
        }
        return ((Int) this.f) & 15
    }

    public abstract Int m()

    fun n() {
        return 0
    }

    fun o() {
        return 0
    }

    fun p() {
        return 0
    }

    fun q() {
        return 0
    }

    fun r() {
        return 0
    }

    public final Short s() {
        Int iN = n()
        if (((-65536) & iN) != 0) {
            throw new com.a.a.s("Register A out of range: " + com.gmail.heagoo.a.c.a.a(iN))
        }
        return (Short) iN
    }

    public final Short t() {
        Int iO = o()
        if (((-65536) & iO) != 0) {
            throw new com.a.a.s("Register B out of range: " + com.gmail.heagoo.a.c.a.a(iO))
        }
        return (Short) iO
    }
}
