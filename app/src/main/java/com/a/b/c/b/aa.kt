package com.a.b.c.b

import java.util.ArrayList
import java.util.BitSet
import java.util.HashSet
import java.util.Iterator

class aa {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.c.a f295a

    /* renamed from: b, reason: collision with root package name */
    private final Int f296b
    private ArrayList c
    private Int g
    private final Int h
    private Int f = -1
    private Boolean d = false
    private Boolean e = false

    constructor(com.a.b.c.a aVar, Int i, Int i2, Int i3) {
        this.f295a = aVar
        this.f296b = i2
        this.c = ArrayList(i)
        this.h = i3
    }

    private fun a(l lVar, n nVar) {
        while (nVar != null && (!nVar.c().b(lVar) || (this.f295a.c && nVar.a() == 26))) {
            nVar = o.a(nVar)
        }
        return nVar
    }

    private fun a(Int i) {
        Int size = this.c.size()
        Int i2 = this.g + this.f296b + this.f
        Int i3 = i2 - this.h
        com.a.b.g.a aVar = new com.a.b.g.a(i2)
        for (Int i4 = 0; i4 < i2; i4++) {
            if (i4 >= i3) {
                aVar.a(i4, i4 + 1, 1)
            } else {
                aVar.a(i4, i4, 1)
            }
        }
        for (Int i5 = 0; i5 < size; i5++) {
            l lVar = (l) this.c.get(i5)
            if (!(lVar is h)) {
                this.c.set(i5, lVar.a(aVar))
            }
        }
        this.g++
    }

    private fun a(HashSet hashSet, l lVar) {
        if (lVar is i) {
            hashSet.add(((i) lVar).c())
            return
        }
        if (!(lVar is w)) {
            if (lVar is x) {
                a(hashSet, ((x) lVar).c())
            }
        } else {
            com.a.b.f.b.v vVarC = ((w) lVar).c()
            Int iD = vVarC.d()
            for (Int i = 0; i < iD; i++) {
                a(hashSet, vVarC.a(i))
            }
        }
    }

    private fun a(HashSet hashSet, com.a.b.f.b.r rVar) {
        if (rVar == null) {
            return
        }
        com.a.b.f.b.m mVarI = rVar.i()
        com.a.b.f.c.y yVarA = mVarI.a()
        com.a.b.f.c.y yVarB = mVarI.b()
        com.a.b.f.d.c cVarA = rVar.a()
        if (cVarA != com.a.b.f.d.c.j) {
            hashSet.add(com.a.b.f.c.z.b(cVarA))
        }
        if (yVarA != null) {
            hashSet.add(yVarA)
        }
        if (yVarB != null) {
            hashSet.add(yVarB)
        }
    }

    private fun a(com.a.b.f.b.r rVar) {
        return (rVar == null || rVar.i().a() == null) ? false : true
    }

    private fun a(Array<n> nVarArr) {
        Int i
        Boolean z
        if (this.f < 0) {
            i = 0
            z = false
        } else {
            i = this.f
            z = false
        }
        while (true) {
            Int size = this.c.size()
            Int i2 = this.f
            for (Int i3 = 0; i3 < size; i3++) {
                l lVar = (l) this.c.get(i3)
                n nVar = nVarArr[i3]
                n nVarA = a(lVar, nVar)
                if (nVarA == null) {
                    Int iA = lVar.a(b(lVar).c().c(lVar))
                    if (iA > i2) {
                        i2 = iA
                    }
                } else {
                    if (nVar != nVarA) {
                    }
                }
                nVarArr[i3] = nVarA
            }
            if (i >= i2) {
                this.f = i
                return z
            }
            z = true
            Int i4 = i2 - i
            Int size2 = this.c.size()
            for (Int i5 = 0; i5 < size2; i5++) {
                l lVar2 = (l) this.c.get(i5)
                if (!(lVar2 is h)) {
                    this.c.set(i5, lVar2.d(i4))
                }
            }
            i = i2
        }
    }

    private fun b(l lVar) {
        n nVarA = a(lVar.k(), lVar.h())
        if (nVarA == null) {
            throw new com.a.a.s("No expanded opcode for " + lVar)
        }
        return nVarA
    }

    private fun b(Int i) {
        Int size = this.c.size()
        for (Int i2 = 0; i2 < size; i2++) {
            l lVar = (l) this.c.get(i2)
            if (!(lVar is h)) {
                this.c.set(i2, lVar.d(1))
            }
        }
        this.f++
    }

    private fun b(Array<n> nVarArr) {
        n nVar
        l lVar
        l lVar2
        l lVarA
        if (this.f == 0) {
            Int size = this.c.size()
            for (Int i = 0; i < size; i++) {
                l lVar3 = (l) this.c.get(i)
                n nVarH = lVar3.h()
                n nVar2 = nVarArr[i]
                if (nVarH != nVar2) {
                    this.c.set(i, lVar3.a(nVar2))
                }
            }
            return
        }
        Int size2 = this.c.size()
        ArrayList arrayList = ArrayList(size2 << 1)
        ArrayList arrayList2 = ArrayList()
        for (Int i2 = 0; i2 < size2; i2++) {
            l lVar4 = (l) this.c.get(i2)
            n nVarH2 = lVar4.h()
            n nVar3 = nVarArr[i2]
            if (nVar3 != null) {
                lVar2 = null
                lVar = null
                nVar = nVar3
                lVarA = lVar4
            } else {
                n nVarB = b(lVar4)
                BitSet bitSetC = nVarB.c().c(lVar4)
                l lVarB = lVar4.b(bitSetC)
                l lVarC = lVar4.c(bitSetC)
                l lVarD = lVar4.d(bitSetC)
                nVar = nVarB
                lVar = lVarB
                lVar2 = lVarC
                lVarA = lVarD
            }
            if ((lVarA is h) && ((h) lVarA).c()) {
                arrayList2.add((h) lVarA)
            } else {
                if (lVar != null) {
                    arrayList.add(lVar)
                }
                if (!(lVarA is an) && arrayList2.size() > 0) {
                    Iterator it = arrayList2.iterator()
                    while (it.hasNext()) {
                        arrayList.add((h) it.next())
                    }
                    arrayList2.clear()
                }
                if (nVar != nVarH2) {
                    lVarA = lVarA.a(nVar)
                }
                arrayList.add(lVarA)
                if (lVar2 != null) {
                    arrayList.add(lVar2)
                }
            }
        }
        this.c = arrayList
    }

    private fun c(Array<n> nVarArr) {
        do {
            Int i = ((this.f296b + this.f) + this.g) - this.h
            Iterator it = this.c.iterator()
            Int i2 = 0
            Int i3 = 0
            Int i4 = 0
            Int i5 = 0
            while (it.hasNext()) {
                com.a.b.f.b.t tVarJ = ((l) it.next()).j()
                Int i6 = i2
                Int i7 = i3
                Int i8 = i4
                Int i9 = i5
                Int i10 = 0
                while (i10 < tVarJ.d_()) {
                    com.a.b.f.b.r rVarB = tVarJ.b(i10)
                    if (rVarB.l()) {
                        Boolean z = rVarB.g() >= i
                        if ((rVarB.g() & 1) == 0) {
                            if (z) {
                                i6++
                            } else {
                                i8++
                            }
                        } else if (z) {
                            i7++
                        } else {
                            i9++
                        }
                    }
                    Int i11 = i9
                    i10++
                    i6 = i6
                    i7 = i7
                    i8 = i8
                    i9 = i11
                }
                i5 = i9
                i4 = i8
                i3 = i7
                i2 = i6
            }
            if (i3 > i2 && i5 > i4) {
                b(1)
            } else if (i3 > i2) {
                a(1)
            } else {
                if (i5 <= i4) {
                    return
                }
                b(1)
                if (this.h != 0 && i2 > i3) {
                    a(1)
                }
            }
        } while (a(nVarArr));
    }

    private Array<n> e() {
        Int size = this.c.size()
        Array<n> nVarArr = new n[size]
        for (Int i = 0; i < size; i++) {
            nVarArr[i] = ((l) this.c.get(i)).h()
        }
        return nVarArr
    }

    private fun f() {
        do {
            Int size = this.c.size()
            Int iA = 0
            for (Int i = 0; i < size; i++) {
                l lVar = (l) this.c.get(i)
                lVar.c(iA)
                iA += lVar.a()
            }
        } while (g());
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0092  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun g() {
        /*
            r10 = this
            r2 = 0
            java.util.ArrayList r0 = r10.c
            Int r3 = r0.size()
            r4 = r2
        L8:
            if (r2 >= r3) goto L91
            java.util.ArrayList r0 = r10.c
            java.lang.Object r0 = r0.get(r2)
            com.a.b.c.b.l r0 = (com.a.b.c.b.l) r0
            Boolean r1 = r0 is com.a.b.c.b.al
            if (r1 == 0) goto L92
            com.a.b.c.b.n r5 = r0.h()
            r1 = r0
            com.a.b.c.b.al r1 = (com.a.b.c.b.al) r1
            com.a.b.c.b.r r6 = r5.c()
            Boolean r6 = r6.a(r1)
            if (r6 != 0) goto L92
            Int r4 = r5.b()
            r6 = 40
            if (r4 != r6) goto L51
            com.a.b.c.b.n r1 = r10.a(r0, r5)
            if (r1 != 0) goto L3d
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
            java.lang.String r1 = "method too Long"
            r0.<init>(r1)
            throw r0
        L3d:
            java.util.ArrayList r4 = r10.c
            com.a.b.c.b.l r0 = r0.a(r1)
            r4.set(r2, r0)
            r0 = r2
            r1 = r3
        L48:
            r2 = 1
            r3 = r1
            r9 = r2
            r2 = r0
            r0 = r9
        L4d:
            Int r2 = r2 + 1
            r4 = r0
            goto L8
        L51:
            java.util.ArrayList r0 = r10.c     // Catch: java.lang.IndexOutOfBoundsException -> L7f java.lang.ClassCastException -> L88
            Int r4 = r2 + 1
            java.lang.Object r0 = r0.get(r4)     // Catch: java.lang.IndexOutOfBoundsException -> L7f java.lang.ClassCastException -> L88
            com.a.b.c.b.h r0 = (com.a.b.c.b.h) r0     // Catch: java.lang.IndexOutOfBoundsException -> L7f java.lang.ClassCastException -> L88
            com.a.b.c.b.al r4 = new com.a.b.c.b.al
            com.a.b.c.b.n r5 = com.a.b.c.b.o.H
            com.a.b.f.b.z r6 = r1.i()
            com.a.b.f.b.t r7 = com.a.b.f.b.t.f519a
            com.a.b.c.b.h r8 = r1.c()
            r4.<init>(r5, r6, r7, r8)
            java.util.ArrayList r5 = r10.c
            r5.set(r2, r4)
            java.util.ArrayList r4 = r10.c
            com.a.b.c.b.al r0 = r1.a(r0)
            r4.add(r2, r0)
            Int r1 = r3 + 1
            Int r0 = r2 + 1
            goto L48
        L7f:
            r0 = move-exception
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "unpaired TargetInsn (dangling)"
            r0.<init>(r1)
            throw r0
        L88:
            r0 = move-exception
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "unpaired TargetInsn"
            r0.<init>(r1)
            throw r0
        L91:
            return r4
        L92:
            r0 = r4
            goto L4d
        */
        throw UnsupportedOperationException("Method not decompiled: com.a.b.c.b.aa.g():Boolean")
    }

    public final Unit a(Int i, h hVar) {
        Int size = (this.c.size() - i) - 1
        try {
            this.c.set(size, ((al) this.c.get(size)).a(hVar))
        } catch (ClassCastException e) {
            throw IllegalArgumentException("non-reversible instruction")
        } catch (IndexOutOfBoundsException e2) {
            throw IllegalArgumentException("too few instructions")
        }
    }

    public final Unit a(k kVar) {
        Int iA
        Iterator it = this.c.iterator()
        while (it.hasNext()) {
            l lVar = (l) it.next()
            if (lVar is i) {
                i iVar = (i) lVar
                com.a.b.f.c.a aVarC = iVar.c()
                Int iA2 = kVar.a(aVarC)
                if (iA2 >= 0) {
                    iVar.a(iA2)
                }
                if ((aVarC is com.a.b.f.c.u) && (iA = kVar.a(((com.a.b.f.c.u) aVarC).k())) >= 0) {
                    iVar.b(iA)
                }
            }
        }
    }

    public final Unit a(l lVar) {
        Boolean z = false
        this.c.add(lVar)
        if (!this.d && lVar.i().a() >= 0) {
            this.d = true
        }
        if (this.e) {
            return
        }
        if (lVar is w) {
            com.a.b.f.b.v vVarC = ((w) lVar).c()
            Int iD = vVarC.d()
            Int i = 0
            while (true) {
                if (i >= iD) {
                    break
                }
                if (a(vVarC.a(i))) {
                    z = true
                    break
                }
                i++
            }
        } else if ((lVar is x) && a(((x) lVar).c())) {
            z = true
        }
        if (z) {
            this.e = true
        }
    }

    public final Boolean a() {
        return this.d
    }

    public final Boolean b() {
        return this.e
    }

    public final HashSet c() {
        HashSet hashSet = HashSet(20)
        Iterator it = this.c.iterator()
        while (it.hasNext()) {
            a(hashSet, (l) it.next())
        }
        return hashSet
    }

    public final m d() {
        if (this.f >= 0) {
            throw UnsupportedOperationException("already processed")
        }
        Array<n> nVarArrE = e()
        a(nVarArrE)
        if (this.f295a.f262a) {
            c(nVarArrE)
        }
        b(nVarArrE)
        f()
        return m.a(this.c, this.f + this.f296b + this.g)
    }
}
