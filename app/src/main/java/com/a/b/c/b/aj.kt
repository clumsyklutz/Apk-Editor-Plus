package com.a.b.c.b

import java.util.ArrayList
import java.util.HashSet

class aj implements c {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.b.x f308a

    /* renamed from: b, reason: collision with root package name */
    private final Array<Int> f309b
    private final b c

    constructor(com.a.b.f.b.x xVar, Array<Int> iArr, b bVar) {
        if (xVar == null) {
            throw NullPointerException("method == null")
        }
        if (iArr == null) {
            throw NullPointerException("order == null")
        }
        if (bVar == null) {
            throw NullPointerException("addresses == null")
        }
        this.f308a = xVar
        this.f309b = iArr
        this.c = bVar
    }

    private fun a(com.a.b.f.b.a aVar, b bVar) {
        com.a.b.h.j jVarC = aVar.c()
        Int iB = jVarC.b()
        Int iD = aVar.d()
        com.a.b.f.d.e eVarB = aVar.g().b()
        Int iD_ = eVarB.d_()
        if (iD_ == 0) {
            return d.f315a
        }
        if ((iD == -1 && iB != iD_) || (iD != -1 && (iB != iD_ + 1 || iD != jVarC.b(iD_)))) {
            throw RuntimeException("shouldn't happen: weird successors list")
        }
        Int i = 0
        while (true) {
            if (i >= iD_) {
                break
            }
            if (eVarB.a(i).equals(com.a.b.f.d.c.n)) {
                iD_ = i + 1
                break
            }
            i++
        }
        d dVar = d(iD_)
        for (Int i2 = 0; i2 < iD_; i2++) {
            dVar.a(i2, new com.a.b.f.c.z(eVarB.a(i2)), bVar.a(jVarC.b(i2)).g())
        }
        dVar.b_()
        return dVar
    }

    private fun a(com.a.b.f.b.a aVar, com.a.b.f.b.a aVar2, d dVar, b bVar) {
        return g(bVar.b(aVar).g(), bVar.c(aVar2).g(), dVar)
    }

    @Override // com.a.b.c.b.c
    public final f a() {
        com.a.b.f.b.a aVar
        d dVar
        com.a.b.f.b.a aVar2 = null
        com.a.b.f.b.x xVar = this.f308a
        Array<Int> iArr = this.f309b
        b bVar = this.c
        Int length = iArr.length
        com.a.b.f.b.c cVarA = xVar.a()
        ArrayList arrayList = ArrayList(length)
        d dVar2 = d.f315a
        Int i = 0
        com.a.b.f.b.a aVar3 = null
        while (i < length) {
            com.a.b.f.b.a aVarB = cVarA.b(iArr[i])
            if (aVarB.h()) {
                d dVarA = a(aVarB, bVar)
                if (dVar2.d_() == 0) {
                    aVar = aVarB
                    dVar = dVarA
                } else {
                    if (dVar2.equals(dVarA)) {
                        if (aVar3 == null) {
                            throw NullPointerException("start == null")
                        }
                        if (aVarB == null) {
                            throw NullPointerException("end == null")
                        }
                        if (bVar.c(aVarB).g() - bVar.b(aVar3).g() <= 65535) {
                            aVar = aVar3
                            dVar = dVar2
                        }
                    }
                    if (dVar2.d_() != 0) {
                        arrayList.add(a(aVar3, aVar2, dVar2, bVar))
                    }
                    aVar = aVarB
                    dVar = dVarA
                }
            } else {
                aVarB = aVar2
                aVar = aVar3
                dVar = dVar2
            }
            i++
            dVar2 = dVar
            aVar3 = aVar
            aVar2 = aVarB
        }
        if (dVar2.d_() != 0) {
            arrayList.add(a(aVar3, aVar2, dVar2, bVar))
        }
        Int size = arrayList.size()
        if (size == 0) {
            return f.f318a
        }
        f fVar = f(size)
        for (Int i2 = 0; i2 < size; i2++) {
            fVar.a(i2, (g) arrayList.get(i2))
        }
        fVar.b_()
        return fVar
    }

    @Override // com.a.b.c.b.c
    public final Boolean b() {
        com.a.b.f.b.c cVarA = this.f308a.a()
        Int iD_ = cVarA.d_()
        for (Int i = 0; i < iD_; i++) {
            if (cVarA.a(i).g().b().d_() != 0) {
                return true
            }
        }
        return false
    }

    @Override // com.a.b.c.b.c
    public final HashSet c() {
        HashSet hashSet = HashSet(20)
        com.a.b.f.b.c cVarA = this.f308a.a()
        Int iD_ = cVarA.d_()
        for (Int i = 0; i < iD_; i++) {
            com.a.b.f.d.e eVarB = cVarA.a(i).g().b()
            Int iD_2 = eVarB.d_()
            for (Int i2 = 0; i2 < iD_2; i2++) {
                hashSet.add(eVarB.a(i2))
            }
        }
        return hashSet
    }
}
