package com.a.b.c.c

import java.util.Collection

class be {

    /* renamed from: a, reason: collision with root package name */
    private final r f384a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.h.r f385b

    constructor(r rVar, com.a.b.h.r rVar2) {
        if (rVar == null) {
            throw NullPointerException("file == null")
        }
        if (rVar2 == null) {
            throw NullPointerException("out == null")
        }
        this.f384a = rVar
        this.f385b = rVar2
    }

    fun a(com.a.b.f.c.a aVar) {
        if (c(aVar) == 30) {
            return "null"
        }
        return aVar.h() + ' ' + aVar.d()
    }

    fun a(r rVar, com.a.b.f.a.a aVar) {
        ba baVarK = rVar.k()
        ay ayVarH = rVar.h()
        baVarK.a(aVar.b())
        for (com.a.b.f.a.e eVar : aVar.f()) {
            ayVarH.a(eVar.a())
            a(rVar, eVar.b())
        }
    }

    fun a(r rVar, com.a.b.f.c.a aVar) {
        if (aVar is com.a.b.f.c.c) {
            a(rVar, ((com.a.b.f.c.c) aVar).a())
            return
        }
        if (!(aVar is com.a.b.f.c.d)) {
            rVar.a(aVar)
            return
        }
        com.a.b.f.c.e eVarA = ((com.a.b.f.c.d) aVar).a()
        Int iD_ = eVarA.d_()
        for (Int i = 0; i < iD_; i++) {
            a(rVar, eVarA.a(i))
        }
    }

    private fun b(com.a.b.f.c.a aVar) {
        Int iC = c(aVar)
        switch (iC) {
            case 0:
            case 2:
            case 4:
            case 6:
                com.gmail.heagoo.a.c.a.a(this.f385b, iC, ((com.a.b.f.c.s) aVar).k())
                return
            case 1:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            default:
                throw RuntimeException("Shouldn't happen")
            case 3:
                com.gmail.heagoo.a.c.a.b(this.f385b, iC, ((com.a.b.f.c.s) aVar).k())
                return
            case 16:
                com.gmail.heagoo.a.c.a.c(this.f385b, iC, ((com.a.b.f.c.m) aVar).k() << 32)
                return
            case 17:
                com.gmail.heagoo.a.c.a.c(this.f385b, iC, ((com.a.b.f.c.j) aVar).k())
                return
            case 23:
                com.gmail.heagoo.a.c.a.b(this.f385b, iC, this.f384a.h().b((com.a.b.f.c.y) aVar))
                return
            case 24:
                com.gmail.heagoo.a.c.a.b(this.f385b, iC, this.f384a.k().b((com.a.b.f.c.z) aVar))
                return
            case 25:
                com.gmail.heagoo.a.c.a.b(this.f385b, iC, this.f384a.m().b((com.a.b.f.c.l) aVar))
                return
            case 26:
                com.gmail.heagoo.a.c.a.b(this.f385b, iC, this.f384a.n().b((com.a.b.f.c.v) aVar))
                return
            case 27:
                com.gmail.heagoo.a.c.a.b(this.f385b, iC, this.f384a.m().b(((com.a.b.f.c.k) aVar).i()))
                return
            case 28:
                this.f385b.d(iC)
                a((com.a.b.f.c.d) aVar, false)
                return
            case 29:
                this.f385b.d(iC)
                a(((com.a.b.f.c.c) aVar).a(), false)
                return
            case 30:
                this.f385b.d(iC)
                return
            case 31:
                this.f385b.d(iC | (((com.a.b.f.c.g) aVar).j() << 5))
                return
        }
    }

    private fun c(com.a.b.f.c.a aVar) {
        if (aVar is com.a.b.f.c.h) {
            return 0
        }
        if (aVar is com.a.b.f.c.x) {
            return 2
        }
        if (aVar is com.a.b.f.c.i) {
            return 3
        }
        if (aVar is com.a.b.f.c.n) {
            return 4
        }
        if (aVar is com.a.b.f.c.t) {
            return 6
        }
        if (aVar is com.a.b.f.c.m) {
            return 16
        }
        if (aVar is com.a.b.f.c.j) {
            return 17
        }
        if (aVar is com.a.b.f.c.y) {
            return 23
        }
        if (aVar is com.a.b.f.c.z) {
            return 24
        }
        if (aVar is com.a.b.f.c.l) {
            return 25
        }
        if (aVar is com.a.b.f.c.v) {
            return 26
        }
        if (aVar is com.a.b.f.c.k) {
            return 27
        }
        if (aVar is com.a.b.f.c.d) {
            return 28
        }
        if (aVar is com.a.b.f.c.c) {
            return 29
        }
        if (aVar is com.a.b.f.c.p) {
            return 30
        }
        if (aVar is com.a.b.f.c.g) {
            return 31
        }
        throw RuntimeException("Shouldn't happen")
    }

    public final Unit a(com.a.b.f.a.a aVar, Boolean z) {
        Int i
        Boolean z2 = z && this.f385b.b()
        ay ayVarH = this.f384a.h()
        ba baVarK = this.f384a.k()
        com.a.b.f.c.z zVarB = aVar.b()
        Int iB = baVarK.b(zVarB)
        if (z2) {
            this.f385b.a("  type_idx: " + com.gmail.heagoo.a.c.a.t(iB) + " // " + zVarB.d())
        }
        this.f385b.e(baVarK.b(aVar.b()))
        Collection<com.a.b.f.a.e> collectionF = aVar.f()
        Int size = collectionF.size()
        if (z2) {
            this.f385b.a("  size: " + com.gmail.heagoo.a.c.a.t(size))
        }
        this.f385b.e(size)
        Int i2 = 0
        for (com.a.b.f.a.e eVar : collectionF) {
            com.a.b.f.c.y yVarA = eVar.a()
            Int iB2 = ayVarH.b(yVarA)
            com.a.b.f.c.a aVarB = eVar.b()
            if (z2) {
                this.f385b.a(0, "  elements[" + i2 + "]:")
                i = i2 + 1
                this.f385b.a("    name_idx: " + com.gmail.heagoo.a.c.a.t(iB2) + " // " + yVarA.d())
            } else {
                i = i2
            }
            this.f385b.e(iB2)
            if (z2) {
                this.f385b.a("    value: " + a(aVarB))
            }
            b(aVarB)
            i2 = i
        }
        if (z2) {
            this.f385b.d()
        }
    }

    public final Unit a(com.a.b.f.c.d dVar, Boolean z) {
        Boolean z2 = z && this.f385b.b()
        com.a.b.f.c.e eVarA = dVar.a()
        Int iD_ = eVarA.d_()
        if (z2) {
            this.f385b.a("  size: " + com.gmail.heagoo.a.c.a.t(iD_))
        }
        this.f385b.e(iD_)
        for (Int i = 0; i < iD_; i++) {
            com.a.b.f.c.a aVarA = eVarA.a(i)
            if (z2) {
                this.f385b.a("  [" + Integer.toHexString(i) + "] " + a(aVarA))
            }
            b(aVarA)
        }
        if (z2) {
            this.f385b.d()
        }
    }
}
