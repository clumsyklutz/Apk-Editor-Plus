package com.a.b.g.a

import com.a.b.f.b.t
import com.a.b.f.b.w
import com.a.b.g.ac
import com.a.b.g.ag
import com.a.b.g.ai
import com.a.b.g.al
import com.a.b.g.an
import com.a.b.g.r
import com.a.b.g.z
import java.util.ArrayList
import java.util.BitSet
import java.util.Iterator
import java.util.Map
import java.util.TreeMap

class a extends m {

    /* renamed from: b, reason: collision with root package name */
    private final Map f571b
    private final ArrayList c
    private final ArrayList d
    private final ArrayList e
    private final BitSet f
    private final r g
    private final Int h
    private final BitSet i
    private final BitSet j

    constructor(an anVar, i iVar, Boolean z) {
        super(anVar, iVar)
        this.f = BitSet(anVar.g())
        this.g = r(iVar, anVar.g())
        this.h = anVar.h()
        this.i = BitSet(this.h << 1)
        this.i.set(0, this.h)
        this.j = BitSet(this.h << 1)
        this.f571b = TreeMap()
        this.c = ArrayList()
        this.d = ArrayList()
        this.e = ArrayList()
    }

    private fun a(Int i, Int i2, c cVar) {
        Int iA = cVar.a(this.i, i)
        while (true) {
            Int i3 = 1
            while (i3 < i2 && !this.i.get(iA + i3)) {
                i3++
            }
            if (i3 == i2) {
                return iA
            }
            iA = cVar.a(this.i, iA + i3)
        }
    }

    private fun a(Int i, z zVar, Array<Int> iArr, BitSet bitSet) {
        t tVarA = zVar.a()
        Int iD_ = tVarA.d_()
        com.a.b.h.k kVarP = zVar.o().p()
        t tVar = t(kVarP.a())
        com.a.b.h.i iVarB = kVarP.b()
        Int i2 = 0
        while (iVarB.a()) {
            tVar.a(i2, b(iVarB.b()))
            i2++
        }
        BitSet bitSet2 = BitSet(this.f585a.g())
        Int i3 = 0
        Int i4 = i
        for (Int i5 = 0; i5 < iD_; i5++) {
            com.a.b.f.b.r rVarB = tVarA.b(i5)
            Int iG = rVarB.g()
            Int i6 = iArr[i5]
            if (i5 != 0) {
                i4 += iArr[i5 - 1]
            }
            if (this.f.get(iG) && this.g.a(iG) == i4) {
                i3 += i6
            } else {
                if (b(i4, i6)) {
                    return -1
                }
                if (this.f.get(iG) || !a(rVarB, i4) || bitSet2.get(iG)) {
                    if (this.g.a(tVar, i4, i6) || this.g.a(tVarA, i4, i6)) {
                        return -1
                    }
                    bitSet.set(i5)
                } else {
                    i3 += i6
                }
            }
            bitSet2.set(iG)
        }
        return i3
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun a(com.a.b.g.z r9, Int r10, Array<Int> r11, java.util.BitSet r12) {
        /*
            r8 = this
            r0 = 0
            com.a.b.g.a.c r3 = com.a.b.g.a.c.c
            Int r5 = r11.length
            r4 = r0
            r1 = r0
            r2 = r0
        L7:
            if (r4 >= r5) goto L21
            r6 = r11[r4]
            r7 = 2
            if (r6 != r7) goto L1e
            Boolean r6 = f(r2)
            if (r6 == 0) goto L1b
            Int r1 = r1 + 1
        L16:
            Int r2 = r2 + 2
        L18:
            Int r4 = r4 + 1
            goto L7
        L1b:
            Int r0 = r0 + 1
            goto L16
        L1e:
            Int r2 = r2 + 1
            goto L18
        L21:
            if (r0 <= r1) goto L3f
            Int r0 = r8.h
            Boolean r0 = f(r0)
            if (r0 != 0) goto L4c
            com.a.b.g.a.c r0 = com.a.b.g.a.c.f573a
        L2d:
            Int r1 = r8.h
        L2f:
            Int r1 = r8.a(r1, r10, r0)
            Int r2 = r8.a(r1, r9, r11, r12)
            if (r2 >= 0) goto L4f
            Int r1 = r1 + 1
            r12.clear()
            goto L2f
        L3f:
            if (r1 <= 0) goto L50
            Int r0 = r8.h
            Boolean r0 = f(r0)
            if (r0 == 0) goto L4c
            com.a.b.g.a.c r0 = com.a.b.g.a.c.f573a
            goto L2d
        L4c:
            com.a.b.g.a.c r0 = com.a.b.g.a.c.f574b
            goto L2d
        L4f:
            return r1
        L50:
            r0 = r3
            goto L2d
        */
        throw UnsupportedOperationException("Method not decompiled: com.a.b.g.a.a.a(com.a.b.g.z, Int, Array<Int>, java.util.BitSet):Int")
    }

    private fun a(Int i, Int i2) {
        this.i.set(i, i + i2, true)
    }

    private fun a(com.a.b.f.b.r rVar, Int i) {
        return (d(i, rVar.k()) || this.g.a(rVar, i)) ? false : true
    }

    private fun a(com.a.b.f.b.r rVar, Int i, Int i2) {
        if (rVar.k() > i2 || this.f.get(rVar.g()) || !a(rVar, i)) {
            return false
        }
        b(rVar, i)
        return true
    }

    private fun a(ArrayList arrayList, Int i, Int i2, Boolean z) {
        Iterator it = arrayList.iterator()
        Boolean z2 = false
        while (it.hasNext()) {
            com.a.b.f.b.r rVar = (com.a.b.f.b.r) it.next()
            if (!this.f.get(rVar.g())) {
                Boolean zA = a(rVar, i, i2)
                z2 = !zA || z2
                if (zA && z) {
                    a(i, rVar.k())
                }
            }
        }
        return !z2
    }

    private fun b() {
        Int i
        Boolean z
        for (ArrayList arrayList : this.f571b.values()) {
            Boolean z2 = false
            Int i2 = this.h
            while (true) {
                Int size = arrayList.size()
                Int i3 = 0
                Int i4 = 1
                while (i3 < size) {
                    com.a.b.f.b.r rVar = (com.a.b.f.b.r) arrayList.get(i3)
                    Int iK = rVar.k()
                    i3++
                    i4 = (this.f.get(rVar.g()) || iK <= i4) ? i4 : iK
                }
                c cVarD = d(i4)
                Int iA = cVarD.a(this.j, i2)
                while (true) {
                    i = iA
                    Int i5 = 1
                    while (i5 < i4 && !this.j.get(i + i5)) {
                        i5++
                    }
                    if (i5 == i4) {
                        break
                    } else {
                        iA = cVarD.a(this.j, i5 + i)
                    }
                }
                Iterator it = arrayList.iterator()
                while (true) {
                    if (!it.hasNext()) {
                        z = true
                        break
                    }
                    com.a.b.f.b.r rVar2 = (com.a.b.f.b.r) it.next()
                    if (!this.f.get(rVar2.g()) && !a(rVar2, i)) {
                        z = false
                        break
                    }
                }
                Boolean zA = z ? a(arrayList, i, i4, true) : z2
                Int i6 = i + 1
                if (!zA) {
                    i2 = i6
                    z2 = zA
                }
            }
        }
    }

    private fun b(com.a.b.f.b.r rVar, Int i) {
        Int iG = rVar.g()
        if (this.f.get(iG) || !a(rVar, i)) {
            throw RuntimeException("attempt to add invalid register mapping")
        }
        Int iK = rVar.k()
        this.g.a(rVar.g(), i, iK)
        this.f.set(iG)
        this.j.set(i, iK + i)
    }

    private fun b(Int i, Int i2) {
        for (Int i3 = i; i3 < i + i2; i3++) {
            if (this.i.get(i3)) {
                return true
            }
        }
        return false
    }

    private fun c(Int i) {
        w wVarC
        al alVarC = this.f585a.c(i)
        if (alVarC == null || (wVarC = alVarC.c()) == null || wVarC.a() != 3) {
            return -1
        }
        return ((com.a.b.f.c.n) ((com.a.b.f.b.e) alVarC.e()).g_()).j()
    }

    private fun c(Int i, Int i2) {
        return a(i, i2, d(i2))
    }

    private fun c() {
        Iterator it = this.c.iterator()
        while (it.hasNext()) {
            z zVar = (z) it.next()
            com.a.b.f.b.r rVarN = zVar.n()
            Int iG = rVarN.g()
            BitSet bitSetG = zVar.o().g()
            if (bitSetG.cardinality() == 1) {
                al alVar = (al) ((ai) this.f585a.j().get(bitSetG.nextSetBit(0))).c().get(r0.size() - 1)
                if (alVar.c().a() == 43) {
                    com.a.b.f.b.r rVarB = alVar.a().b(0)
                    Int iG2 = rVarB.g()
                    Int iK = rVarB.k()
                    Boolean zA = this.f.get(iG)
                    Boolean z = this.f.get(iG2)
                    Boolean zA2 = (!z) & zA ? a(rVarB, this.g.a(iG), iK) : z
                    if ((!zA) & zA2) {
                        zA = a(rVarN, this.g.a(iG2), iK)
                    }
                    if (!zA || !zA2) {
                        Int iC = c(this.h, iK)
                        ArrayList arrayList = ArrayList(2)
                        arrayList.add(rVarN)
                        arrayList.add(rVarB)
                        while (!a(arrayList, iC, iK, false)) {
                            iC = c(iC + 1, iK)
                        }
                    }
                    Boolean z2 = alVar.e().b().d_() != 0
                    Int iA = this.g.a(iG)
                    if (iA != this.g.a(iG2) && !z2) {
                        ((z) alVar).a(0, a(alVar, rVarB))
                        b(alVar.a().b(0), iA)
                    }
                }
            }
        }
    }

    private fun d(Int i) {
        return i == 2 ? f(this.h) ? c.f573a : c.f574b : c.c
    }

    private fun d() {
        Iterator it = this.e.iterator()
        while (it.hasNext()) {
            ac acVar = (ac) it.next()
            com.a.b.f.b.r rVarN = acVar.n()
            Int iG = rVarN.g()
            Int iK = rVarN.k()
            t tVarA = acVar.a()
            Int iD_ = tVarA.d_()
            ArrayList arrayList = ArrayList()
            g gVar = g(iD_ + 1)
            if (this.f.get(iG)) {
                gVar.a(this.g.a(iG))
            } else {
                arrayList.add(rVarN)
            }
            for (Int i = 0; i < iD_; i++) {
                com.a.b.f.b.r rVarN2 = this.f585a.c(tVarA.b(i).g()).n()
                Int iG2 = rVarN2.g()
                if (this.f.get(iG2)) {
                    gVar.a(this.g.a(iG2))
                } else {
                    arrayList.add(rVarN2)
                }
            }
            for (Int i2 = 0; i2 < gVar.b(); i2++) {
                a(arrayList, gVar.a(), iK, false)
            }
            Int iC = c(this.h, iK)
            while (!a(arrayList, iC, iK, false)) {
                iC = c(iC + 1, iK)
            }
        }
    }

    private fun d(Int i, Int i2) {
        return i < this.h && i + i2 > this.h
    }

    private com.a.b.f.b.m e(Int i) {
        for (Map.Entry entry : this.f571b.entrySet()) {
            Iterator it = ((ArrayList) entry.getValue()).iterator()
            while (it.hasNext()) {
                if (((com.a.b.f.b.r) it.next()).g() == i) {
                    return (com.a.b.f.b.m) entry.getKey()
                }
            }
        }
        return null
    }

    private fun e() {
        com.a.b.f.b.r rVarB
        Int iG = this.f585a.g()
        for (Int i = 0; i < iG; i++) {
            if (!this.f.get(i) && (rVarB = b(i)) != null) {
                Int iK = rVarB.k()
                Int iC = c(this.h, iK)
                while (!a(rVarB, iC)) {
                    iC = c(iC + 1, iK)
                }
                b(rVarB, iC)
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun f(Int i) {
        return (i & 1) == 0
    }

    @Override // com.a.b.g.a.m
    public final ag a() {
        Int iA
        BitSet bitSet
        Int iA2
        Int i
        Int i2
        this.f585a.a(b(this))
        for (ArrayList arrayList : this.f571b.values()) {
            Int size = arrayList.size()
            Int i3 = 0
            Int i4 = -1
            while (true) {
                if (i3 >= size) {
                    i = i4
                    i2 = 0
                    break
                }
                com.a.b.f.b.r rVar = (com.a.b.f.b.r) arrayList.get(i3)
                Int iC = c(rVar.g())
                if (iC >= 0) {
                    Int iK = rVar.k()
                    b(rVar, iC)
                    i2 = iK
                    i = iC
                    break
                }
                i3++
                i4 = iC
            }
            if (i >= 0) {
                a(arrayList, i, i2, true)
            }
        }
        Int iG = this.f585a.g()
        for (Int i5 = 0; i5 < iG; i5++) {
            if (!this.f.get(i5)) {
                Int iC2 = c(i5)
                com.a.b.f.b.r rVarB = b(i5)
                if (iC2 >= 0) {
                    b(rVarB, iC2)
                }
            }
        }
        Iterator it = this.d.iterator()
        while (it.hasNext()) {
            z zVar = (z) it.next()
            t tVarA = zVar.a()
            Int iD_ = tVarA.d_()
            Array<Int> iArr = new Int[iD_]
            Int i6 = 0
            Int i7 = 0
            while (i6 < iD_) {
                iArr[i6] = tVarA.b(i6).k()
                Int i8 = iArr[i6] + i7
                i6++
                i7 = i8
            }
            Int i9 = 0
            BitSet bitSet2 = null
            Int iA3 = -1
            Int i10 = Integer.MIN_VALUE
            for (Int i11 = 0; i11 < iD_; i11++) {
                Int iG2 = tVarA.b(i11).g()
                if (i11 != 0) {
                    i9 -= iArr[i11 - 1]
                }
                if (this.f.get(iG2) && (iA = this.g.a(iG2) + i9) >= 0 && !d(iA, i7) && (iA2 = a(iA, zVar, iArr, (bitSet = BitSet(iD_)))) >= 0) {
                    Int iCardinality = iA2 - bitSet.cardinality()
                    if (iCardinality > i10) {
                        bitSet2 = bitSet
                        iA3 = iA
                        i10 = iCardinality
                    }
                    if (iA2 == i7) {
                        break
                    }
                }
            }
            if (iA3 == -1) {
                bitSet2 = BitSet(iD_)
                iA3 = a(zVar, i7, iArr, bitSet2)
            }
            for (Int iNextSetBit = bitSet2.nextSetBit(0); iNextSetBit >= 0; iNextSetBit = bitSet2.nextSetBit(iNextSetBit + 1)) {
                zVar.a(iNextSetBit, a(zVar, tVarA.b(iNextSetBit)))
            }
            t tVarA2 = zVar.a()
            Int iD_2 = tVarA2.d_()
            for (Int i12 = 0; i12 < iD_2; i12++) {
                com.a.b.f.b.r rVarB2 = tVarA2.b(i12)
                Int iG3 = rVarB2.g()
                Int iK2 = rVarB2.k()
                Int i13 = iA3 + iK2
                if (!this.f.get(iG3)) {
                    com.a.b.f.b.m mVarE = e(iG3)
                    b(rVarB2, iA3)
                    if (mVarE != null) {
                        a(iA3, iK2)
                        ArrayList arrayList2 = (ArrayList) this.f571b.get(mVarE)
                        Int size2 = arrayList2.size()
                        for (Int i14 = 0; i14 < size2; i14++) {
                            com.a.b.f.b.r rVar2 = (com.a.b.f.b.r) arrayList2.get(i14)
                            if (-1 == tVarA2.c(rVar2.g())) {
                                a(rVar2, iA3, iK2)
                            }
                        }
                    }
                }
                iA3 = i13
            }
        }
        b()
        c()
        d()
        e()
        return this.g
    }
}
