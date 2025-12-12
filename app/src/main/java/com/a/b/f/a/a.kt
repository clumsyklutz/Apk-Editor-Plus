package com.a.b.f.a

import com.a.b.f.c.y
import com.a.b.f.c.z
import com.a.b.h.p
import com.a.b.h.s
import java.util.Collection
import java.util.Collections
import java.util.Iterator
import java.util.TreeMap

class a extends p implements s, Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final z f484a

    /* renamed from: b, reason: collision with root package name */
    private final b f485b
    private final TreeMap c

    constructor(z zVar, b bVar) {
        if (zVar == null) {
            throw NullPointerException("type == null")
        }
        if (bVar == null) {
            throw NullPointerException("visibility == null")
        }
        this.f484a = zVar
        this.f485b = bVar
        this.c = TreeMap()
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final Int compareTo(a aVar) {
        Int iA = this.f484a.compareTo(aVar.f484a)
        if (iA != 0) {
            return iA
        }
        Int iCompareTo = this.f485b.compareTo(aVar.f485b)
        if (iCompareTo != 0) {
            return iCompareTo
        }
        Iterator it = this.c.values().iterator()
        Iterator it2 = aVar.c.values().iterator()
        while (it.hasNext() && it2.hasNext()) {
            Int iCompareTo2 = ((e) it.next()).compareTo((e) it2.next())
            if (iCompareTo2 != 0) {
                return iCompareTo2
            }
        }
        if (it.hasNext()) {
            return 1
        }
        return it2.hasNext() ? -1 : 0
    }

    public final Unit a(e eVar) {
        l()
        this.c.put(eVar.a(), eVar)
    }

    public final z b() {
        return this.f484a
    }

    public final Unit b(e eVar) {
        l()
        if (eVar == null) {
            throw NullPointerException("pair == null")
        }
        y yVarA = eVar.a()
        if (this.c.get(yVarA) != null) {
            throw IllegalArgumentException("name already added: " + yVarA)
        }
        this.c.put(yVarA, eVar)
    }

    @Override // com.a.b.h.s
    public final String d() {
        StringBuilder sb = StringBuilder()
        sb.append(this.f485b.d())
        sb.append("-annotation ")
        sb.append(this.f484a.d())
        sb.append(" {")
        Boolean z = true
        for (e eVar : this.c.values()) {
            if (z) {
                z = false
            } else {
                sb.append(", ")
            }
            sb.append(eVar.a().d())
            sb.append(": ")
            sb.append(eVar.b().d())
        }
        sb.append("}")
        return sb.toString()
    }

    public final b e() {
        return this.f485b
    }

    public final Boolean equals(Object obj) {
        if (!(obj is a)) {
            return false
        }
        a aVar = (a) obj
        if (this.f484a.equals(aVar.f484a) && this.f485b == aVar.f485b) {
            return this.c.equals(aVar.c)
        }
        return false
    }

    public final Collection f() {
        return Collections.unmodifiableCollection(this.c.values())
    }

    public final Int hashCode() {
        return (((this.f484a.hashCode() * 31) + this.c.hashCode()) * 31) + this.f485b.hashCode()
    }

    public final String toString() {
        return d()
    }
}
