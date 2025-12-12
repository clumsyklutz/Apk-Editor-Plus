package com.a.b.e

import com.a.a.aa
import com.a.a.z
import java.util.ArrayList
import java.util.Collections
import java.util.List

abstract class j {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.a.o f465a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ b f466b

    protected constructor(b bVar, com.a.a.o oVar) {
        this.f466b = bVar
        this.f465a = oVar
    }

    private fun a(com.a.a.i iVar, m mVar) {
        aa aaVarA = a(iVar.a())
        if (!aaVarA.a()) {
            return Collections.emptyList()
        }
        ArrayList arrayList = ArrayList()
        com.a.a.o oVarA = iVar.a(aaVarA.c)
        for (Int i = 0; i < aaVarA.f114b; i++) {
            arrayList.add(k(this, iVar, mVar, a(oVarA, mVar, 0), i, oVarA.a()))
        }
        return arrayList
    }

    abstract aa a(z zVar)

    abstract Comparable a(com.a.a.o oVar, m mVar, Int i)

    public final Unit a() {
        Int iA
        Comparable comparableA
        Int i
        Boolean z
        Boolean z2
        Int i2
        Comparable comparable
        Comparable comparable2
        Int i3
        Int i4
        aa aaVarA = a(this.f466b.f456a.a())
        aa aaVarA2 = a(this.f466b.f457b.a())
        a(this.f466b.s).c = this.f465a.a()
        com.a.a.o oVarA = aaVarA.a() ? this.f466b.f456a.a(aaVarA.c) : null
        com.a.a.o oVarA2 = aaVarA2.a() ? this.f466b.f457b.a(aaVarA2.c) : null
        Int i5 = -1
        Int i6 = 0
        Comparable comparableA2 = null
        Comparable comparable3 = null
        Int i7 = -1
        Int i8 = 0
        Int i9 = 0
        while (true) {
            if (comparable3 != null || i8 >= aaVarA.f114b) {
                iA = i7
                comparableA = comparable3
            } else {
                iA = oVarA.a()
                comparableA = a(oVarA, this.f466b.t, i8)
            }
            if (comparableA2 != null || i6 >= aaVarA2.f114b) {
                i = i5
            } else {
                Int iA2 = oVarA2.a()
                comparableA2 = a(oVarA2, this.f466b.u, i6)
                i = iA2
            }
            if (comparableA == null || comparableA2 == null) {
                z = comparableA != null
                z2 = comparableA2 != null
            } else {
                Int iCompareTo = comparableA.compareTo(comparableA2)
                z = iCompareTo <= 0
                z2 = iCompareTo >= 0
            }
            Comparable comparable4 = null
            if (z) {
                a(iA, this.f466b.t, i8, i9)
                i8++
                i2 = -1
                Comparable comparable5 = comparableA
                comparableA = null
                comparable4 = comparable5
            } else {
                i2 = iA
            }
            if (z2) {
                Int i10 = i6 + 1
                a(i, this.f466b.u, i6, i9)
                comparable2 = null
                i4 = -1
                Comparable comparable6 = comparableA2
                i3 = i10
                comparable = comparable6
            } else {
                comparable = comparable4
                comparable2 = comparableA2
                i3 = i6
                i4 = i
            }
            if (comparable == null) {
                a(this.f466b.s).f114b = i9
                return
            }
            a(comparable)
            i9++
            Comparable comparable7 = comparableA
            i7 = i2
            i5 = i4
            i6 = i3
            comparableA2 = comparable2
            comparable3 = comparable7
        }
    }

    abstract Unit a(Int i, m mVar, Int i2, Int i3)

    abstract Unit a(Comparable comparable)

    public final Unit b() {
        Int i
        a(this.f466b.s).c = this.f465a.a()
        ArrayList arrayList = ArrayList()
        arrayList.addAll(a(this.f466b.f456a, this.f466b.t))
        arrayList.addAll(a(this.f466b.f457b, this.f466b.u))
        Collections.sort(arrayList)
        Int i2 = 0
        for (Int i3 = 0; i3 < arrayList.size(); i3 = i) {
            i = i3 + 1
            k kVar = (k) arrayList.get(i3)
            a(kVar.d, b.a(this.f466b, kVar.f467a), kVar.c, i2 - 1)
            while (i < arrayList.size() && kVar.compareTo((k) arrayList.get(i)) == 0) {
                k kVar2 = (k) arrayList.get(i)
                a(kVar2.d, b.a(this.f466b, kVar2.f467a), kVar2.c, i2 - 1)
                i++
            }
            a(kVar.f468b)
            i2++
        }
        a(this.f466b.s).f114b = i2
    }
}
