package com.a.b.c.b

import android.support.v7.widget.ActivityChooserView
import java.util.ArrayList
import java.util.Arrays
import java.util.Iterator

class v {

    /* renamed from: a, reason: collision with root package name */
    private final ArrayList f339a

    /* renamed from: b, reason: collision with root package name */
    private Int f340b = 0
    private com.a.b.f.b.v c = null
    private Array<Int> d = null
    private Int e = 0

    constructor(Int i) {
        this.f339a = ArrayList(i)
    }

    private static com.a.b.f.b.r a(com.a.b.f.b.r rVar) {
        return (rVar == null || rVar.a() != com.a.b.f.d.c.j) ? rVar : rVar.a(com.a.b.f.d.c.n)
    }

    private fun a(Int i, Int i2) {
        Boolean z = this.d == null
        if (i != 0 || z) {
            if (i < 0) {
                throw RuntimeException("shouldn't happen")
            }
            if (z || i2 >= this.d.length) {
                Int i3 = i2 + 1
                com.a.b.f.b.v vVar = new com.a.b.f.b.v(i3)
                Array<Int> iArr = new Int[i3]
                Arrays.fill(iArr, -1)
                if (!z) {
                    vVar.a(this.c)
                    System.arraycopy(this.d, 0, iArr, 0, this.d.length)
                }
                this.c = vVar
                this.d = iArr
            }
        }
    }

    private fun a(Int i, t tVar, com.a.b.f.b.r rVar) {
        Int iG = rVar.g()
        this.f339a.add(u(i, tVar, rVar))
        if (tVar == t.f335a) {
            this.c.d(rVar)
            this.d[iG] = -1
        } else {
            this.c.c(rVar)
            this.d[iG] = this.f339a.size() - 1
        }
    }

    private fun a(Int i, com.a.b.f.b.r rVar, t tVar) {
        Boolean z
        Boolean z2 = false
        Int iG = rVar.g()
        com.a.b.f.b.r rVarA = a(rVar)
        a(i, iG)
        if (this.d[iG] >= 0) {
            return
        }
        Int size = this.f339a.size() - 1
        while (size >= 0) {
            u uVar = (u) this.f339a.get(size)
            if (uVar != null) {
                if (uVar.a() == i) {
                    if (uVar.a(rVarA)) {
                        break
                    }
                } else {
                    z = false
                    break
                }
            }
            size--
        }
        this.c.c(rVarA)
        this.f339a.set(size, null)
        this.f340b++
        Int iG2 = rVarA.g()
        Int i2 = size - 1
        u uVar2 = null
        while (true) {
            if (i2 >= 0) {
                uVar2 = (u) this.f339a.get(i2)
                if (uVar2 != null && uVar2.h().g() == iG2) {
                    z2 = true
                    break
                }
                i2--
            } else {
                break
            }
        }
        if (z2) {
            this.d[iG2] = i2
            if (uVar2.a() == i) {
                this.f339a.set(i2, uVar2.a(t.f336b))
            }
        }
        z = true
        if (z) {
            return
        }
        a(i, tVar, rVarA)
    }

    private fun b(Int i, t tVar, com.a.b.f.b.r rVar) {
        if (tVar == t.f335a) {
            throw RuntimeException("shouldn't happen")
        }
        Int i2 = this.d[rVar.g()]
        if (i2 >= 0) {
            u uVar = (u) this.f339a.get(i2)
            if (uVar.a() == i && uVar.h().equals(rVar)) {
                this.f339a.set(i2, uVar.a(tVar))
                this.c.c(rVar)
                return
            }
        }
        a(i, rVar, tVar)
    }

    private fun b(Int i, com.a.b.f.b.r rVar) {
        a(i, rVar, t.f336b)
    }

    public final s a() {
        Int i
        a(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, 0)
        Int size = this.f339a.size()
        Int i2 = size - this.f340b
        if (i2 == 0) {
            return s.f334a
        }
        Array<u> uVarArr = new u[i2]
        if (size == i2) {
            this.f339a.toArray(uVarArr)
        } else {
            Iterator it = this.f339a.iterator()
            Int i3 = 0
            while (it.hasNext()) {
                u uVar = (u) it.next()
                if (uVar != null) {
                    uVarArr[i3] = uVar
                    i = i3 + 1
                } else {
                    i = i3
                }
                i3 = i
            }
        }
        Arrays.sort(uVarArr)
        s sVar = s(i2)
        for (Int i4 = 0; i4 < i2; i4++) {
            sVar.a(i4, uVarArr[i4])
        }
        sVar.b_()
        return sVar
    }

    public final Unit a(Int i, com.a.b.f.b.r rVar) {
        com.a.b.f.b.r rVarA
        com.a.b.f.b.r rVarA2
        Int iG = rVar.g()
        com.a.b.f.b.r rVarA3 = a(rVar)
        a(i, iG)
        com.a.b.f.b.r rVarA4 = this.c.a(iG)
        if (rVarA3.a(rVarA4)) {
            return
        }
        com.a.b.f.b.r rVarB = this.c.b(rVarA3)
        if (rVarB != null) {
            b(i, t.d, rVarB)
        }
        Int i2 = this.d[iG]
        if (rVarA4 != null) {
            a(i, t.c, rVarA4)
        } else if (i2 >= 0) {
            u uVar = (u) this.f339a.get(i2)
            if (uVar.a() == i) {
                if (uVar.a(rVarA3)) {
                    this.f339a.set(i2, null)
                    this.f340b++
                    this.c.d(rVarA3)
                    this.d[iG] = -1
                    return
                }
                this.f339a.set(i2, uVar.a(t.c))
            }
        }
        if (iG > 0 && (rVarA2 = this.c.a(iG - 1)) != null && rVarA2.l()) {
            b(i, t.f, rVarA2)
        }
        if (rVarA3.l() && (rVarA = this.c.a(iG + 1)) != null) {
            b(i, t.e, rVarA)
        }
        a(i, t.f335a, rVarA3)
    }

    public final Unit a(Int i, com.a.b.f.b.v vVar) {
        Int iB = vVar.b()
        a(i, iB - 1)
        for (Int i2 = 0; i2 < iB; i2++) {
            com.a.b.f.b.r rVarA = this.c.a(i2)
            com.a.b.f.b.r rVarA2 = a(vVar.a(i2))
            if (rVarA == null) {
                if (rVarA2 != null) {
                    a(i, rVarA2)
                }
            } else if (rVarA2 == null) {
                b(i, rVarA)
            } else if (!rVarA2.a(rVarA)) {
                b(i, rVarA)
                a(i, rVarA2)
            }
        }
    }
}
