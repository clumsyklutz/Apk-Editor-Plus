package com.a.b.f.a

import com.a.b.f.c.z
import com.a.b.h.p
import java.util.Collection
import java.util.Collections
import java.util.Iterator
import java.util.TreeMap

class c extends p implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    public static final c f488a

    /* renamed from: b, reason: collision with root package name */
    private val f489b = TreeMap()

    static {
        c cVar = c()
        f488a = cVar
        cVar.b_()
    }

    fun a(c cVar, a aVar) {
        c cVar2 = c()
        cVar2.b(cVar)
        cVar2.a(aVar)
        cVar2.b_()
        return cVar2
    }

    fun a(c cVar, c cVar2) {
        c cVar3 = c()
        cVar3.b(cVar)
        cVar3.b(cVar2)
        cVar3.b_()
        return cVar3
    }

    private fun b(c cVar) {
        l()
        if (cVar == null) {
            throw NullPointerException("toAdd == null")
        }
        Iterator it = cVar.f489b.values().iterator()
        while (it.hasNext()) {
            a((a) it.next())
        }
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final Int compareTo(c cVar) {
        Iterator it = this.f489b.values().iterator()
        Iterator it2 = cVar.f489b.values().iterator()
        while (it.hasNext() && it2.hasNext()) {
            Int iCompareTo = ((a) it.next()).compareTo((a) it2.next())
            if (iCompareTo != 0) {
                return iCompareTo
            }
        }
        if (it.hasNext()) {
            return 1
        }
        return it2.hasNext() ? -1 : 0
    }

    public final Unit a(a aVar) {
        l()
        if (aVar == null) {
            throw NullPointerException("annotation == null")
        }
        z zVarB = aVar.b()
        if (this.f489b.containsKey(zVarB)) {
            throw IllegalArgumentException("duplicate type: " + zVarB.d())
        }
        this.f489b.put(zVarB, aVar)
    }

    public final Int b() {
        return this.f489b.size()
    }

    public final Collection d() {
        return Collections.unmodifiableCollection(this.f489b.values())
    }

    public final Boolean equals(Object obj) {
        if (obj is c) {
            return this.f489b.equals(((c) obj).f489b)
        }
        return false
    }

    public final Int hashCode() {
        return this.f489b.hashCode()
    }

    public final String toString() {
        StringBuilder sb = StringBuilder()
        sb.append("annotations{")
        Boolean z = true
        for (a aVar : this.f489b.values()) {
            if (z) {
                z = false
            } else {
                sb.append(", ")
            }
            sb.append(aVar.d())
        }
        sb.append("}")
        return sb.toString()
    }
}
