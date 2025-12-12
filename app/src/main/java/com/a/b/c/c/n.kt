package com.a.b.c.c

import androidx.core.view.InputDeviceCompat
import java.io.IOException
import java.io.PrintWriter
import java.util.ArrayList
import java.util.BitSet
import java.util.Collections
import java.util.Iterator

class n {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.c.b.ab f404a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.c.b.s f405b
    private final r d
    private final Int e
    private final Int f
    private final com.a.b.f.d.a g
    private final Boolean h
    private com.a.b.h.r k
    private PrintWriter l
    private String m
    private Boolean n
    private final com.a.b.c.b.Array<u> o
    private Int i = 0
    private Int j = 1
    private final com.a.b.h.r c = new com.a.b.h.r()

    constructor(com.a.b.c.b.ab abVar, com.a.b.c.b.s sVar, r rVar, Int i, Int i2, Boolean z, com.a.b.f.c.v vVar) {
        this.f404a = abVar
        this.f405b = sVar
        this.d = rVar
        this.g = vVar.i()
        this.h = z
        this.e = i
        this.f = i2
        this.o = new com.a.b.c.b.u[i2]
    }

    private fun a(Int i) {
        Int iD_ = this.f405b.d_()
        while (i < iD_ && this.f405b.a(i).a() == this.i) {
            Int i2 = i + 1
            com.a.b.c.b.u uVarA = this.f405b.a(i)
            Int iG = uVarA.g()
            com.a.b.c.b.u uVar = this.o[iG]
            if (uVarA != uVar) {
                this.o[iG] = uVarA
                if (uVarA.c()) {
                    if (uVar == null || !uVarA.a(uVar)) {
                        if (uVarA.e() != null) {
                            b(uVarA)
                        } else {
                            Int iA = this.c.a()
                            this.c.d(3)
                            d(uVarA.g())
                            a(uVarA.d())
                            a(uVarA.f())
                            if (this.k != null || this.l != null) {
                                a(this.c.a() - iA, String.format("%04x: +local %s", Integer.valueOf(this.i), a(uVarA)))
                            }
                            i = i2
                        }
                    } else {
                        if (uVar.c()) {
                            throw RuntimeException("shouldn't happen")
                        }
                        Int iA2 = this.c.a()
                        this.c.d(6)
                        d(uVarA.g())
                        if (this.k != null || this.l != null) {
                            a(this.c.a() - iA2, String.format("%04x: +local restart %s", Integer.valueOf(this.i), a(uVarA)))
                        }
                        i = i2
                    }
                } else if (uVarA.b() != com.a.b.c.b.t.c) {
                    Int iA3 = this.c.a()
                    this.c.d(5)
                    this.c.e(uVarA.g())
                    if (this.k != null || this.l != null) {
                        a(this.c.a() - iA3, String.format("%04x: -local %s", Integer.valueOf(this.i), a(uVarA)))
                    }
                }
                i = i2
            } else {
                i = i2
            }
        }
        return i
    }

    private fun a(Int i, Int i2) {
        if (i < -4 || i > 10) {
            throw RuntimeException("Parameter out of range")
        }
        return i + 4 + (i2 * 15) + 10
    }

    private fun a(Int i, ArrayList arrayList) {
        Int size = arrayList.size()
        while (i < size && ((com.a.b.c.b.ac) arrayList.get(i)).a() == this.i) {
            a((com.a.b.c.b.ac) arrayList.get(i))
            i++
        }
        return i
    }

    private fun a(com.a.b.c.b.u uVar) {
        StringBuilder sb = StringBuilder()
        sb.append("v")
        sb.append(uVar.g())
        sb.append(' ')
        com.a.b.f.c.y yVarD = uVar.d()
        if (yVarD == null) {
            sb.append("null")
        } else {
            sb.append(yVarD.d())
        }
        sb.append(' ')
        com.a.b.f.c.z zVarF = uVar.f()
        if (zVarF == null) {
            sb.append("null")
        } else {
            sb.append(zVarF.d())
        }
        com.a.b.f.c.y yVarE = uVar.e()
        if (yVarE != null) {
            sb.append(' ')
            sb.append(yVarE.d())
        }
        return sb.toString()
    }

    private fun a(Int i, String str) {
        if (this.m != null) {
            str = this.m + str
        }
        if (this.k != null) {
            com.a.b.h.r rVar = this.k
            if (!this.n) {
                i = 0
            }
            rVar.a(i, str)
        }
        if (this.l != null) {
            this.l.println(str)
        }
    }

    private fun a(com.a.b.c.b.ac acVar) {
        Int i
        Int i2
        Int iA = acVar.b().a()
        Int iA2 = acVar.a()
        Int i3 = iA - this.j
        Int i4 = iA2 - this.i
        if (i4 < 0) {
            throw RuntimeException("Position entries must be in ascending address order")
        }
        if (i3 < -4 || i3 > 10) {
            b(i3)
            i3 = 0
        }
        Int iA3 = a(i3, i4)
        if ((iA3 & InputDeviceCompat.SOURCE_ANY) > 0) {
            c(i4)
            Int iA4 = a(i3, 0)
            if ((iA4 & InputDeviceCompat.SOURCE_ANY) > 0) {
                b(i3)
                i = 0
                iA3 = a(0, 0)
                i2 = 0
            } else {
                iA3 = iA4
                i = i3
                i2 = 0
            }
        } else {
            i = i3
            i2 = i4
        }
        this.c.d(iA3)
        this.j = i + this.j
        this.i = i2 + this.i
        if (this.k == null && this.l == null) {
            return
        }
        a(1, String.format("%04x: line %d", Integer.valueOf(this.i), Integer.valueOf(this.j)))
    }

    private fun a(com.a.b.f.c.y yVar) {
        if (yVar == null || this.d == null) {
            this.c.e(0)
        } else {
            this.c.e(this.d.h().b(yVar) + 1)
        }
    }

    private fun a(com.a.b.f.c.z zVar) {
        if (zVar == null || this.d == null) {
            this.c.e(0)
        } else {
            this.c.e(this.d.k().b(zVar) + 1)
        }
    }

    private fun a(ArrayList arrayList, ArrayList arrayList2) {
        Int i
        com.a.b.c.b.u uVar
        Boolean z = (this.k == null && this.l == null) ? false : true
        Int iA = this.c.a()
        if (arrayList.size() > 0) {
            this.j = ((com.a.b.c.b.ac) arrayList.get(0)).b().a()
        }
        this.c.e(this.j)
        if (z) {
            a(this.c.a() - iA, "line_start: " + this.j)
        }
        Int iB = b()
        com.a.b.f.d.b bVarB = this.g.b()
        Int iD_ = bVarB.d_()
        if (this.h) {
            i = iB
        } else {
            Iterator it = arrayList2.iterator()
            while (true) {
                if (!it.hasNext()) {
                    break
                }
                com.a.b.c.b.u uVar2 = (com.a.b.c.b.u) it.next()
                if (iB == uVar2.g()) {
                    this.o[iB] = uVar2
                    break
                }
            }
            i = iB + 1
        }
        Int iA2 = this.c.a()
        this.c.e(iD_)
        if (z) {
            a(this.c.a() - iA2, String.format("parameters_size: %04x", Integer.valueOf(iD_)))
        }
        Int i2 = i
        for (Int i3 = 0; i3 < iD_; i3++) {
            com.a.b.f.d.c cVarB = bVarB.b(i3)
            Int iA3 = this.c.a()
            Iterator it2 = arrayList2.iterator()
            while (true) {
                if (!it2.hasNext()) {
                    uVar = null
                    break
                }
                uVar = (com.a.b.c.b.u) it2.next()
                if (i2 == uVar.g()) {
                    if (uVar.e() != null) {
                        a((com.a.b.f.c.y) null)
                    } else {
                        a(uVar.d())
                    }
                    this.o[i2] = uVar
                }
            }
            if (uVar == null) {
                a((com.a.b.f.c.y) null)
            }
            if (z) {
                a(this.c.a() - iA3, "parameter " + ((uVar == null || uVar.e() != null) ? "<unnamed>" : uVar.d().d()) + " v" + i2)
            }
            i2 += cVarB.i()
        }
        for (com.a.b.c.b.u uVar3 : this.o) {
            if (uVar3 != null && uVar3.e() != null) {
                b(uVar3)
            }
        }
    }

    private fun b() {
        return (this.f - this.g.b().e()) - (this.h ? 0 : 1)
    }

    private fun b(Int i) {
        Int iA = this.c.a()
        this.c.d(2)
        this.c.h(i)
        this.j += i
        if (this.k == null && this.l == null) {
            return
        }
        a(this.c.a() - iA, String.format("line = %d", Integer.valueOf(this.j)))
    }

    private fun b(com.a.b.c.b.u uVar) {
        Int iA = this.c.a()
        this.c.d(4)
        d(uVar.g())
        a(uVar.d())
        a(uVar.f())
        a(uVar.e())
        if (this.k == null && this.l == null) {
            return
        }
        a(this.c.a() - iA, String.format("%04x: +localx %s", Integer.valueOf(this.i), a(uVar)))
    }

    private fun c() {
        ArrayList arrayList = ArrayList(this.g.b().d_())
        Int iB = b()
        BitSet bitSet = BitSet(this.f - iB)
        Int iD_ = this.f405b.d_()
        for (Int i = 0; i < iD_; i++) {
            com.a.b.c.b.u uVarA = this.f405b.a(i)
            Int iG = uVarA.g()
            if (iG >= iB && !bitSet.get(iG - iB)) {
                bitSet.set(iG - iB)
                arrayList.add(uVarA)
            }
        }
        Collections.sort(arrayList, p(this))
        return arrayList
    }

    private fun c(Int i) {
        Int iA = this.c.a()
        this.c.d(1)
        this.c.e(i)
        this.i += i
        if (this.k == null && this.l == null) {
            return
        }
        a(this.c.a() - iA, String.format("%04x: advance pc", Integer.valueOf(this.i)))
    }

    private fun d(Int i) {
        if (i < 0) {
            throw RuntimeException("Signed value where unsigned required: " + i)
        }
        this.c.e(i)
    }

    public final Array<Byte> a() {
        Int i = 0
        try {
            Int iD_ = this.f404a == null ? 0 : this.f404a.d_()
            ArrayList arrayList = ArrayList(iD_)
            for (Int i2 = 0; i2 < iD_; i2++) {
                arrayList.add(this.f404a.a(i2))
            }
            Collections.sort(arrayList, o(this))
            a(arrayList, c())
            this.c.d(7)
            if (this.k != null || this.l != null) {
                a(1, String.format("%04x: prologue end", Integer.valueOf(this.i)))
            }
            Int size = arrayList.size()
            Int iD_2 = this.f405b.d_()
            Int i3 = 0
            while (true) {
                Int iA = a(i)
                Int iA2 = a(i3, arrayList)
                Int iA3 = iA < iD_2 ? this.f405b.a(iA).a() : Integer.MAX_VALUE
                Int iA4 = iA2 < size ? ((com.a.b.c.b.ac) arrayList.get(iA2)).a() : Integer.MAX_VALUE
                Int iMin = Math.min(iA4, iA3)
                if (iMin == Integer.MAX_VALUE || (iMin == this.e && iA3 == Integer.MAX_VALUE && iA4 == Integer.MAX_VALUE)) {
                    break
                }
                if (iMin == iA4) {
                    i3 = iA2 + 1
                    a((com.a.b.c.b.ac) arrayList.get(iA2))
                    i = iA
                } else {
                    c(iMin - this.i)
                    i = iA
                    i3 = iA2
                }
            }
            this.c.d(0)
            if (this.k != null || this.l != null) {
                a(1, "end sequence")
            }
            return this.c.g()
        } catch (IOException e) {
            throw com.a.a.a.d.a(e, "...while encoding debug info")
        }
    }

    public final Array<Byte> a(String str, PrintWriter printWriter, com.a.b.h.r rVar, Boolean z) {
        this.m = str
        this.l = printWriter
        this.k = rVar
        this.n = z
        return a()
    }
}
