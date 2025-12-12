package com.a.b.c.a

import com.a.a.ClassA.d
import com.a.b.a.a.m
import com.a.b.a.a.u
import com.a.b.a.d.k
import com.a.b.a.e.g
import com.a.b.a.e.h
import com.a.b.c.b.ae
import com.a.b.c.c.ak
import com.a.b.c.c.j
import com.a.b.c.c.r
import com.a.b.c.c.t
import com.a.b.c.c.y
import com.a.b.f.a.e
import com.a.b.f.b.n
import com.a.b.f.b.x
import com.a.b.f.c.ab
import com.a.b.f.c.f
import com.a.b.f.c.i
import com.a.b.f.c.l
import com.a.b.f.c.o
import com.a.b.f.c.v
import com.a.b.f.c.w
import com.a.b.f.c.z
import com.a.b.g.aa
import jadx.core.deobf.Deobfuscator
import java.io.PrintStream
import java.util.ArrayList

class a {
    public Boolean g

    /* renamed from: a, reason: collision with root package name */
    public Int f264a = 2

    /* renamed from: b, reason: collision with root package name */
    public Boolean f265b = false
    public Boolean c = true
    public Boolean d = false
    public String e = null
    public String f = null
    public PrintStream h = System.err

    fun a(k kVar, Array<Byte> bArr, a aVar, com.a.b.c.a aVar2, r rVar) {
        try {
            com.gmail.heagoo.a.c.a.a(aVar.e, aVar.f)
            j jVar = j(kVar.e(), kVar.d() & (-33), kVar.f(), kVar.h(), aVar.f264a == 1 ? null : kVar.l())
            com.a.b.f.a.c cVarA = a(kVar, aVar)
            if (cVarA.b() != 0) {
                jVar.a(cVarA, rVar)
            }
            y yVarM = rVar.m()
            ak akVarN = rVar.n()
            a(kVar, jVar, rVar)
            a(kVar, aVar, aVar2, jVar, rVar)
            com.a.b.f.c.b bVarG = kVar.g()
            Int iA = bVarG.a()
            for (Int i = 0; i < iA; i++) {
                com.a.b.f.c.a aVarC = bVarG.c(i)
                if (aVarC is v) {
                    akVarN.a((f) aVarC)
                } else if (aVarC is o) {
                    akVarN.a((f) ((o) aVarC).j())
                } else if (aVarC is l) {
                    yVarM.a((l) aVarC)
                } else if (aVarC is com.a.b.f.c.k) {
                    yVarM.a(((com.a.b.f.c.k) aVarC).i())
                }
            }
            return jVar
        } catch (RuntimeException e) {
            throw d.a(e, "...while processing " + kVar.a())
        }
    }

    private static com.a.b.f.a.a a(k kVar) {
        Boolean z
        z zVarE = kVar.e()
        h hVarJ = kVar.j()
        Int iD_ = hVarJ.d_()
        com.a.b.f.a.a aVar = new com.a.b.f.a.a(zVarE, com.a.b.f.a.b.EMBEDDED)
        Int i = 0
        Boolean z2 = false
        while (i < iD_) {
            g gVarA = hVarJ.a(i)
            com.a.b.a.a.a aVar2 = (com.a.b.a.a.a) gVarA.e().a("AnnotationDefault")
            if (aVar2 != null) {
                aVar.b(e(gVarA.a().a(), aVar2.b()))
                z = true
            } else {
                z = z2
            }
            i++
            z2 = z
        }
        if (!z2) {
            return null
        }
        aVar.b_()
        return com.a.b.c.c.f.a(aVar)
    }

    public static com.a.b.f.a.c a(k kVar, a aVar) {
        com.a.b.f.a.a aVarA
        com.a.b.f.a.a aVarA2
        com.a.b.f.a.c cVar = null
        z zVarE = kVar.e()
        com.a.b.a.e.b bVarK = kVar.k()
        com.a.b.f.a.c cVarA = a(bVarK)
        com.a.b.a.a.e eVar = (com.a.b.a.a.e) bVarK.a("EnclosingMethod")
        if (eVar == null) {
            aVarA = null
        } else {
            z zVarB = eVar.b()
            w wVarC = eVar.c()
            aVarA = wVarC == null ? com.a.b.c.c.f.a(zVarB) : com.a.b.c.c.f.a(v(zVarB, wVarC))
        }
        Boolean z = aVarA == null
        try {
            com.a.b.a.a.g gVar = (com.a.b.a.a.g) bVarK.a("InnerClasses")
            if (gVar != null) {
                u uVarB = gVar.b()
                Int iD_ = uVarB.d_()
                ArrayList arrayList = ArrayList()
                Int i = 0
                com.a.b.a.a.v vVar = null
                while (i < iD_) {
                    com.a.b.a.a.v vVarA = uVarB.a(i)
                    z zVarA = vVarA.a()
                    if (!zVarA.equals(zVarE)) {
                        if (zVarE.equals(vVarA.b())) {
                            arrayList.add(zVarA.i())
                        }
                        vVarA = vVar
                    }
                    i++
                    vVar = vVarA
                }
                Int size = arrayList.size()
                if (vVar != null || size != 0) {
                    cVar = new com.a.b.f.a.c()
                    if (vVar != null) {
                        cVar.a(com.a.b.c.c.f.a(vVar.c(), vVar.d()))
                        if (z) {
                            if (vVar.b() == null) {
                                throw new com.a.b.h.u("Ignoring InnerClasses attribute for an anonymous inner class\n(" + zVarE.d() + ") that doesn't come with an\nassociated EnclosingMethod attribute. This class was probably produced by a\ncompiler that did not target the modern .class file format. The recommended\nsolution is to recompile the class from source, using an up-to-date compiler\nand without specifying any \"-target\" type options. The consequence of ignoring\nthis warning is that reflective operations on this class will incorrectly\nindicate that it is *not* an inner class.")
                            }
                            cVar.a(com.a.b.c.c.f.a(vVar.b()))
                        }
                    }
                    if (size != 0) {
                        com.a.b.f.d.b bVar = new com.a.b.f.d.b(size)
                        for (Int i2 = 0; i2 < size; i2++) {
                            bVar.a(i2, (com.a.b.f.d.c) arrayList.get(i2))
                        }
                        bVar.b_()
                        cVar.a(com.a.b.c.c.f.a(bVar))
                    }
                    cVar.b_()
                }
            }
        } catch (com.a.b.h.u e) {
            aVar.h.println("warning: " + e.getMessage())
        }
        com.a.b.f.a.c cVarA2 = cVar != null ? com.a.b.f.a.c.a(cVarA, cVar) : cVarA
        if (aVarA != null) {
            cVarA2 = com.a.b.f.a.c.a(cVarA2, aVarA)
        }
        return (!com.gmail.heagoo.a.c.a.p(kVar.d()) || (aVarA2 = a(kVar)) == null) ? cVarA2 : com.a.b.f.a.c.a(cVarA2, aVarA2)
    }

    public static com.a.b.f.a.c a(com.a.b.a.e.b bVar) {
        m mVar = (m) bVar.a("RuntimeVisibleAnnotations")
        com.a.b.a.a.k kVar = (com.a.b.a.a.k) bVar.a("RuntimeInvisibleAnnotations")
        com.a.b.f.a.c cVarB = mVar == null ? kVar == null ? com.a.b.f.a.c.f488a : kVar.b() : kVar == null ? mVar.b() : com.a.b.f.a.c.a(mVar.b(), kVar.b())
        com.a.b.a.a.o oVar = (com.a.b.a.a.o) bVar.a("Signature")
        com.a.b.f.a.a aVarA = oVar == null ? null : com.a.b.c.c.f.a(oVar.b())
        return aVarA != null ? com.a.b.f.a.c.a(cVarB, aVarA) : cVarB
    }

    public static com.a.b.f.d.e a(g gVar) {
        com.a.b.a.a.f fVar = (com.a.b.a.a.f) gVar.e().a("Exceptions")
        return fVar == null ? com.a.b.f.d.b.f565a : fVar.b()
    }

    private fun a(k kVar, a aVar, com.a.b.c.a aVar2, j jVar, r rVar) {
        com.a.b.c.b.j jVarA
        x xVar
        x xVar2
        z zVarE = kVar.e()
        h hVarJ = kVar.j()
        Int iD_ = hVarJ.d_()
        for (Int i = 0; i < iD_; i++) {
            g gVarA = hVarJ.a(i)
            try {
                v vVar = v(zVarE, gVarA.a())
                Int iD = gVarA.d()
                Boolean zL = com.gmail.heagoo.a.c.a.l(iD)
                Boolean zK = com.gmail.heagoo.a.c.a.k(iD)
                Boolean zO = com.gmail.heagoo.a.c.a.o(iD)
                Boolean zN = com.gmail.heagoo.a.c.a.n(iD)
                Boolean z = vVar.l().e() || vVar.l().f()
                if (zO || zN) {
                    jVarA = null
                } else {
                    com.a.b.a.b.l lVar = new com.a.b.a.b.l(gVarA, kVar, aVar.f264a != 1, aVar.f265b)
                    com.a.b.f.b.f fVar = com.a.b.f.b.f.f501a
                    x xVarA = com.a.b.a.b.x.a(lVar, fVar, hVarJ)
                    Int iB = vVar.b(zL)
                    String str = zVarE.i().g() + Deobfuscator.CLASS_NAME_SEPARATOR + gVarA.b().j()
                    if (aVar.d && com.gmail.heagoo.a.c.a.h(str)) {
                        x xVarA2 = aa.a(xVarA, iB, zL, aVar.f265b, fVar)
                        if (aVar.g) {
                            c.a(xVarA, xVarA2)
                        }
                        xVar = xVarA
                        xVar2 = xVarA2
                    } else {
                        xVar = null
                        xVar2 = xVarA
                    }
                    com.a.b.f.b.o oVarA = aVar.f265b ? n.a(xVar2) : null
                    jVarA = ae.a(xVar2, aVar.f264a, oVarA, iB, aVar2)
                    if (aVar.g && xVar != null) {
                        Int iB2 = lVar.k().b()
                        com.a.b.c.b.j jVarA2 = ae.a(xVar2, aVar.f264a, oVarA, iB, aVar2)
                        com.a.b.c.b.j jVarA3 = ae.a(xVar, aVar.f264a, oVarA, iB, aVar2)
                        b bVar = b()
                        jVarA2.a(bVar)
                        jVarA3.a(bVar)
                        c.a(jVarA3, jVarA2)
                        c.a(iB2)
                    }
                }
                if (com.gmail.heagoo.a.c.a.m(iD)) {
                    iD |= 131072
                    if (!zO) {
                        iD &= -33
                    }
                }
                if (z) {
                    iD |= 65536
                }
                com.a.b.c.c.v vVar2 = new com.a.b.c.c.v(vVar, iD, jVarA, a(gVarA))
                if (vVar.l().e() || vVar.l().f() || zL || zK) {
                    jVar.a(vVar2)
                } else {
                    jVar.b(vVar2)
                }
                com.a.b.f.a.c cVarA = a(gVarA.e())
                com.a.b.f.d.e eVarA = a(gVarA)
                if (eVarA.d_() != 0) {
                    cVarA = com.a.b.f.a.c.a(cVarA, com.a.b.c.c.f.b(eVarA))
                }
                if (cVarA.b() != 0) {
                    jVar.a(vVar, cVarA, rVar)
                }
                com.a.b.a.e.b bVarE = gVarA.e()
                com.a.b.a.a.n nVar = (com.a.b.a.a.n) bVarE.a("RuntimeVisibleParameterAnnotations")
                com.a.b.a.a.l lVar2 = (com.a.b.a.a.l) bVarE.a("RuntimeInvisibleParameterAnnotations")
                com.a.b.f.a.d dVarB = nVar == null ? lVar2 == null ? com.a.b.f.a.d.f490a : lVar2.b() : lVar2 == null ? nVar.b() : com.a.b.f.a.d.a(nVar.b(), lVar2.b())
                if (dVarB.d_() != 0) {
                    jVar.a(vVar, dVarB, rVar)
                }
                rVar.n().a((f) vVar)
            } catch (RuntimeException e) {
                throw d.a(e, "...while processing " + gVarA.b().d() + " " + gVarA.c().d())
            }
        }
    }

    private fun a(k kVar, j jVar, r rVar) {
        z zVarE = kVar.e()
        com.a.b.a.e.e eVarI = kVar.i()
        Int iD_ = eVarI.d_()
        for (Int i = 0; i < iD_; i++) {
            com.a.b.a.e.d dVarA = eVarI.a(i)
            try {
                l lVar = l(zVarE, dVarA.a())
                Int iD = dVarA.d()
                if (com.gmail.heagoo.a.c.a.l(iD)) {
                    ab abVarG = dVarA.g()
                    t tVar = t(lVar, iD)
                    if (abVarG != null) {
                        com.a.b.f.d.c cVarA = lVar.a()
                        if (!abVarG.a().equals(cVarA)) {
                            switch (cVarA.c()) {
                                case 1:
                                    abVarG = com.a.b.f.c.g.a(((com.a.b.f.c.n) abVarG).j())
                                    break
                                case 2:
                                    abVarG = com.a.b.f.c.h.a(((com.a.b.f.c.n) abVarG).j())
                                    break
                                case 3:
                                    abVarG = i.a(((com.a.b.f.c.n) abVarG).j())
                                    break
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                default:
                                    throw UnsupportedOperationException("can't coerce " + abVarG + " to " + cVarA)
                                case 8:
                                    abVarG = com.a.b.f.c.x.a(((com.a.b.f.c.n) abVarG).j())
                                    break
                            }
                        }
                    }
                    jVar.a(tVar, abVarG)
                } else {
                    jVar.a(t(lVar, iD))
                }
                com.a.b.f.a.c cVarA2 = a(dVarA.e())
                if (cVarA2.b() != 0) {
                    jVar.a(lVar, cVarA2, rVar)
                }
                rVar.m().a(lVar)
            } catch (RuntimeException e) {
                throw d.a(e, "...while processing " + dVarA.b().d() + " " + dVarA.c().d())
            }
        }
    }
}
