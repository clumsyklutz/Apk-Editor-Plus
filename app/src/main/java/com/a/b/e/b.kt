package com.a.b.e

import androidx.appcompat.R
import com.a.a.aa
import com.a.a.z
import java.util.Arrays

class b {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.a.i f456a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.a.i f457b
    private final Int c
    private final l d
    private final com.a.a.i e
    private final com.a.a.o f
    private final com.a.a.o g
    private final com.a.a.o h
    private final com.a.a.o i
    private final com.a.a.o j
    private final com.a.a.o k
    private final com.a.a.o l
    private final com.a.a.o m
    private final com.a.a.o n
    private final com.a.a.o o
    private final com.a.a.o p
    private final com.a.a.o q
    private final com.a.a.o r
    private final z s
    private final m t
    private final m u
    private final o v
    private final o w
    private Int x

    constructor(com.a.a.i iVar, com.a.a.i iVar2, Int i) {
        this(iVar, iVar2, i, l(iVar, iVar2))
    }

    private constructor(com.a.a.i iVar, com.a.a.i iVar2, Int i, l lVar) {
        this.x = 1048576
        this.f456a = iVar
        this.f457b = iVar2
        this.c = i
        this.d = lVar
        this.e = new com.a.a.i(lVar.a())
        z zVarA = iVar.a()
        z zVarA2 = iVar2.a()
        this.t = m(this.e, zVarA)
        this.u = m(this.e, zVarA2)
        this.v = o(this.t)
        this.w = o(this.u)
        this.f = this.e.a(lVar.f469a, "header")
        this.g = this.e.a(lVar.f470b, "ids defs")
        this.s = this.e.a()
        this.s.z = this.e.c()
        this.s.h.c = this.e.c()
        this.s.h.f114b = 1
        this.h = this.e.a(lVar.c, "map list")
        this.s.i.c = this.e.c()
        this.i = this.e.a(lVar.d, "type list")
        this.s.j.c = this.e.c()
        this.q = this.e.a(lVar.l, "annotation set ref list")
        this.s.k.c = this.e.c()
        this.p = this.e.a(lVar.k, "annotation sets")
        this.s.l.c = this.e.c()
        this.j = this.e.a(lVar.e, "class data")
        this.s.m.c = this.e.c()
        this.k = this.e.a(lVar.f, "code")
        this.s.n.c = this.e.c()
        this.l = this.e.a(lVar.g, "string data")
        this.s.o.c = this.e.c()
        this.m = this.e.a(lVar.h, "debug info")
        this.s.p.c = this.e.c()
        this.r = this.e.a(lVar.m, "annotation")
        this.s.q.c = this.e.c()
        this.n = this.e.a(lVar.i, "encoded array")
        this.s.r.c = this.e.c()
        this.o = this.e.a(lVar.j, "annotations directory")
        this.s.y = this.e.c() - this.s.z
    }

    static /* synthetic */ m a(b bVar, com.a.a.i iVar) {
        if (iVar == bVar.f456a) {
            return bVar.t
        }
        if (iVar == bVar.f457b) {
            return bVar.u
        }
        throw IllegalArgumentException()
    }

    private fun a(com.a.a.i iVar, m mVar) {
        aa aaVar = iVar.a().k
        if (aaVar.a()) {
            com.a.a.o oVarA = iVar.a(aaVar.c)
            for (Int i = 0; i < aaVar.f114b; i++) {
                this.s.k.f114b++
                this.p.m()
                mVar.c(oVarA.a(), this.p.a())
                Int iB = oVarA.b()
                this.p.f(iB)
                for (Int i2 = 0; i2 < iB; i2++) {
                    this.p.f(mVar.e(oVarA.b()))
                }
            }
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:14:0x00fd. Please report as an issue. */
    private fun a(com.a.a.i iVar, m mVar, com.a.a.Array<d> dVarArr) {
        Int length = dVarArr.length
        Int i = 0
        Int i2 = 0
        while (i < length) {
            com.a.a.d dVar = dVarArr[i]
            Int iD = mVar.d(dVar.a())
            this.j.g(iD - i2)
            this.j.g(dVar.b())
            if (dVar.c() == 0) {
                this.j.g(0)
            } else {
                this.k.l()
                this.j.g(this.k.a())
                com.a.a.f fVarA = iVar.a(dVar)
                this.s.m.f114b++
                this.k.m()
                this.k.e(fVarA.a())
                this.k.e(fVarA.b())
                this.k.e(fVarA.c())
                com.a.a.Array<h> hVarArrF = fVarA.f()
                com.a.a.Array<g> gVarArrG = fVarA.g()
                this.k.e(hVarArrF.length)
                Int iD2 = fVarA.d()
                if (iD2 != 0) {
                    this.k.f(this.m.a())
                    com.a.a.o oVarA = iVar.a(iD2)
                    this.s.o.f114b++
                    this.m.g(com.gmail.heagoo.a.c.a.b(oVarA))
                    Int iB = com.gmail.heagoo.a.c.a.b(oVarA)
                    this.m.g(iB)
                    for (Int i3 = 0; i3 < iB; i3++) {
                        this.m.b(mVar.a(com.gmail.heagoo.a.c.a.b(oVarA) - 1))
                    }
                    while (true) {
                        Byte bD = oVarA.d()
                        this.m.d(bD)
                        switch (bD) {
                            case 1:
                                this.m.g(com.gmail.heagoo.a.c.a.b(oVarA))
                            case 2:
                                this.m.h(com.gmail.heagoo.a.c.a.a(oVarA))
                            case 3:
                            case 4:
                                this.m.g(com.gmail.heagoo.a.c.a.b(oVarA))
                                this.m.b(mVar.a(com.gmail.heagoo.a.c.a.b(oVarA) - 1))
                                this.m.b(mVar.b(com.gmail.heagoo.a.c.a.b(oVarA) - 1))
                                if (bD == 4) {
                                    this.m.b(mVar.a(com.gmail.heagoo.a.c.a.b(oVarA) - 1))
                                }
                            case 5:
                            case 6:
                                this.m.g(com.gmail.heagoo.a.c.a.b(oVarA))
                            case 9:
                                this.m.b(mVar.a(com.gmail.heagoo.a.c.a.b(oVarA) - 1))
                        }
                    }
                } else {
                    this.k.f(0)
                }
                Array<Short> sArrA = (iVar == this.f456a ? this.v : this.w).a(fVarA.e())
                this.k.f(sArrA.length)
                this.k.a(sArrA)
                if (hVarArrF.length > 0) {
                    if (sArrA.length % 2 == 1) {
                        this.k.a((Short) 0)
                    }
                    com.a.a.o oVarA2 = this.e.a(this.k.a())
                    this.k.c(hVarArrF.length << 3)
                    Int iA = this.k.a()
                    this.k.g(gVarArrG.length)
                    Array<Int> iArr = new Int[gVarArrG.length]
                    Int i4 = 0
                    while (true) {
                        Int i5 = i4
                        if (i5 < gVarArrG.length) {
                            iArr[i5] = this.k.a() - iA
                            com.a.a.g gVar = gVarArrG[i5]
                            Int iC = gVar.c()
                            Array<Int> iArrA = gVar.a()
                            Array<Int> iArrB = gVar.b()
                            if (iC != -1) {
                                this.k.h(-iArrA.length)
                            } else {
                                this.k.h(iArrA.length)
                            }
                            for (Int i6 = 0; i6 < iArrA.length; i6++) {
                                this.k.g(mVar.b(iArrA[i6]))
                                this.k.g(iArrB[i6])
                            }
                            if (iC != -1) {
                                this.k.g(iC)
                            }
                            i4 = i5 + 1
                        } else {
                            for (com.a.a.h hVar : hVarArrF) {
                                oVarA2.f(hVar.a())
                                oVarA2.e(hVar.b())
                                oVarA2.e(iArr[hVar.c()])
                            }
                        }
                    }
                }
            }
            i++
            i2 = iD
        }
    }

    private fun a(m mVar, com.a.a.Array<c> cVarArr) {
        Int i = 0
        Int length = cVarArr.length
        Int i2 = 0
        while (i < length) {
            com.a.a.c cVar = cVarArr[i]
            Int iC = mVar.c(cVar.a())
            this.j.g(iC - i2)
            this.j.g(cVar.b())
            i++
            i2 = iC
        }
    }

    private fun a(Array<u> uVarArr, com.a.a.i iVar, m mVar) {
        for (com.a.a.e eVar : iVar.k()) {
            u uVar = u(iVar, eVar)
            u uVar2 = u(uVar.a(), mVar.a(uVar.b()))
            Int iC = uVar2.c()
            if (uVarArr[iC] == null) {
                uVarArr[iC] = uVar2
            } else if (this.c != a.f454a) {
                throw new com.a.a.s("Multiple dex files define " + ((String) iVar.g().get(eVar.b())))
            }
        }
    }

    private com.a.a.i b() {
        Boolean zA
        c(this, this.g).a()
        d(this, this.g).a()
        e(this, this.i).b()
        f(this, this.g).a()
        g(this, this.g).a()
        h(this, this.g).a()
        i(this, this.r).b()
        a(this.f456a, this.t)
        a(this.f457b, this.u)
        b(this.f456a, this.t)
        b(this.f457b, this.u)
        c(this.f456a, this.t)
        c(this.f457b, this.u)
        d(this.f456a, this.t)
        d(this.f457b, this.u)
        Array<u> uVarArr = new u[this.s.c.f114b]
        a(uVarArr, this.f456a, this.t)
        a(uVarArr, this.f457b, this.u)
        do {
            zA = true
            for (u uVar : uVarArr) {
                if (uVar != null && !uVar.d()) {
                    zA &= uVar.a(uVarArr)
                }
            }
        } while (!zA);
        Arrays.sort(uVarArr, u.f482a)
        Int iIndexOf = Arrays.asList(uVarArr).indexOf(null)
        Array<u> uVarArr2 = iIndexOf != -1 ? (Array<u>) Arrays.copyOfRange(uVarArr, 0, iIndexOf) : uVarArr
        this.s.g.c = this.g.a()
        this.s.g.f114b = uVarArr2.length
        for (u uVar2 : uVarArr2) {
            com.a.a.i iVarA = uVar2.a()
            m mVar = iVarA == this.f456a ? this.t : this.u
            com.a.a.e eVarB = uVar2.b()
            this.g.m()
            this.g.f(eVarB.b())
            this.g.f(eVarB.f())
            this.g.f(eVarB.c())
            this.g.f(eVarB.d())
            this.g.f(mVar.a(eVarB.g()))
            this.g.f(mVar.h(eVarB.h()))
            if (eVarB.i() == 0) {
                this.g.f(0)
            } else {
                this.g.f(this.j.a())
                com.a.a.b bVarA = iVarA.a(eVarB)
                this.s.l.f114b++
                com.a.a.Array<c> cVarArrA = bVarA.a()
                com.a.a.Array<c> cVarArrB = bVarA.b()
                com.a.a.Array<d> dVarArrC = bVarA.c()
                com.a.a.Array<d> dVarArrD = bVarA.d()
                this.j.g(cVarArrA.length)
                this.j.g(cVarArrB.length)
                this.j.g(dVarArrC.length)
                this.j.g(dVarArrD.length)
                a(mVar, cVarArrA)
                a(mVar, cVarArrB)
                a(iVarA, mVar, dVarArrC)
                a(iVarA, mVar, dVarArrD)
            }
            this.g.f(mVar.i(eVarB.j()))
        }
        this.s.f153a.c = 0
        this.s.f153a.f114b = 1
        this.s.v = this.e.b()
        this.s.a()
        z zVar = this.s
        com.a.a.o oVar = this.f
        oVar.a(com.gmail.heagoo.a.c.a.c(13).getBytes("UTF-8"))
        oVar.f(zVar.t)
        oVar.a(zVar.u)
        oVar.f(zVar.v)
        oVar.f(R.styleable.AppCompatTheme_ratingBarStyleSmall)
        oVar.f(305419896)
        oVar.f(zVar.w)
        oVar.f(zVar.x)
        oVar.f(zVar.h.c)
        oVar.f(zVar.f154b.f114b)
        oVar.f(zVar.f154b.c)
        oVar.f(zVar.c.f114b)
        oVar.f(zVar.c.c)
        oVar.f(zVar.d.f114b)
        oVar.f(zVar.d.c)
        oVar.f(zVar.e.f114b)
        oVar.f(zVar.e.c)
        oVar.f(zVar.f.f114b)
        oVar.f(zVar.f.c)
        oVar.f(zVar.g.f114b)
        oVar.f(zVar.g.c)
        oVar.f(zVar.y)
        oVar.f(zVar.z)
        z zVar2 = this.s
        com.a.a.o oVar2 = this.h
        Array<aa> aaVarArr = zVar2.s
        Int i = 0
        for (Int i2 = 0; i2 < 18; i2++) {
            if (aaVarArr[i2].a()) {
                i++
            }
        }
        oVar2.f(i)
        Array<aa> aaVarArr2 = zVar2.s
        for (Int i3 = 0; i3 < 18; i3++) {
            aa aaVar = aaVarArr2[i3]
            if (aaVar.a()) {
                oVar2.a(aaVar.f113a)
                oVar2.a((Short) 0)
                oVar2.f(aaVar.f114b)
                oVar2.f(aaVar.c)
            }
        }
        this.e.l()
        return this.e
    }

    private fun b(com.a.a.i iVar, m mVar) {
        aa aaVar = iVar.a().j
        if (aaVar.a()) {
            com.a.a.o oVarA = iVar.a(aaVar.c)
            for (Int i = 0; i < aaVar.f114b; i++) {
                this.s.j.f114b++
                this.q.m()
                mVar.d(oVarA.a(), this.q.a())
                Int iB = oVarA.b()
                this.q.f(iB)
                for (Int i2 = 0; i2 < iB; i2++) {
                    this.q.f(mVar.f(oVarA.b()))
                }
            }
        }
    }

    private fun c(com.a.a.i iVar, m mVar) {
        aa aaVar = iVar.a().r
        if (aaVar.a()) {
            com.a.a.o oVarA = iVar.a(aaVar.c)
            for (Int i = 0; i < aaVar.f114b; i++) {
                this.s.r.f114b++
                this.o.m()
                mVar.e(oVarA.a(), this.o.a())
                this.o.f(mVar.f(oVarA.b()))
                Int iB = oVarA.b()
                this.o.f(iB)
                Int iB2 = oVarA.b()
                this.o.f(iB2)
                Int iB3 = oVarA.b()
                this.o.f(iB3)
                for (Int i2 = 0; i2 < iB; i2++) {
                    this.o.f(mVar.c(oVarA.b()))
                    this.o.f(mVar.f(oVarA.b()))
                }
                for (Int i3 = 0; i3 < iB2; i3++) {
                    this.o.f(mVar.d(oVarA.b()))
                    this.o.f(mVar.f(oVarA.b()))
                }
                for (Int i4 = 0; i4 < iB3; i4++) {
                    this.o.f(mVar.d(oVarA.b()))
                    this.o.f(mVar.g(oVarA.b()))
                }
            }
        }
    }

    private fun d(com.a.a.i iVar, m mVar) {
        aa aaVar = iVar.a().q
        if (aaVar.a()) {
            com.a.a.o oVarA = iVar.a(aaVar.c)
            for (Int i = 0; i < aaVar.f114b; i++) {
                this.s.q.f114b++
                mVar.f(oVarA.a(), this.n.a())
                com.a.a.u uVarK = oVarA.k()
                com.a.b.h.r rVar = new com.a.b.h.r(32)
                n(mVar, rVar).c(new com.a.a.v(uVarK, 28))
                new com.a.a.u(rVar.g()).a(this.n)
            }
        }
    }

    public final com.a.a.i a() {
        Long jNanoTime = System.nanoTime()
        com.a.a.i iVarB = b()
        l lVar = l(this)
        Int iA = this.d.a() - lVar.a()
        if (iA > this.x) {
            iVarB = b(this.e, new com.a.a.i(0), a.f455b, lVar).b()
            System.out.printf("Result compacted from %.1fKiB to %.1fKiB to save %.1fKiB%n", Float.valueOf(this.e.b() / 1024.0f), Float.valueOf(iVarB.b() / 1024.0f), Float.valueOf(iA / 1024.0f))
        }
        System.out.printf("Merged dex A (%d defs/%.1fKiB) with dex B (%d defs/%.1fKiB). Result is %d defs/%.1fKiB. Took %.1fs%n", Integer.valueOf(this.f456a.a().g.f114b), Float.valueOf(this.f456a.b() / 1024.0f), Integer.valueOf(this.f457b.a().g.f114b), Float.valueOf(this.f457b.b() / 1024.0f), Integer.valueOf(iVarB.a().g.f114b), Float.valueOf(iVarB.b() / 1024.0f), Float.valueOf((System.nanoTime() - jNanoTime) / 1.0E9f))
        return iVarB
    }
}
