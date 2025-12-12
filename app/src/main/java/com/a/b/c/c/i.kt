package com.a.b.c.c

import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.Iterator

class i extends ap {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.z f395a

    /* renamed from: b, reason: collision with root package name */
    private final ArrayList f396b
    private final HashMap c
    private final ArrayList d
    private final ArrayList e
    private final ArrayList f
    private com.a.b.f.c.d g
    private Array<Byte> h

    constructor(com.a.b.f.c.z zVar) {
        super(1, -1)
        if (zVar == null) {
            throw NullPointerException("thisClass == null")
        }
        this.f395a = zVar
        this.f396b = ArrayList(20)
        this.c = HashMap(40)
        this.d = ArrayList(20)
        this.e = ArrayList(20)
        this.f = ArrayList(20)
        this.g = null
    }

    private fun a(r rVar, com.a.b.h.r rVar2, String str, ArrayList arrayList) {
        Int size = arrayList.size()
        if (size == 0) {
            return
        }
        if (rVar2.b()) {
            rVar2.a(0, "  " + str + ":")
        }
        Int i = 0
        Int i2 = 0
        while (i2 < size) {
            Int iA = ((u) arrayList.get(i2)).a(rVar, rVar2, i, i2)
            i2++
            i = iA
        }
    }

    private fun a(com.a.b.h.r rVar, String str, Int i) {
        if (rVar.b()) {
            rVar.a(String.format("  %-21s %08x", str + "_size:", Integer.valueOf(i)))
        }
        rVar.e(i)
    }

    private fun b(r rVar, com.a.b.h.r rVar2) {
        Boolean zB = rVar2.b()
        if (zB) {
            rVar2.a(0, h() + " class data for " + this.f395a.d())
        }
        a(rVar2, "static_fields", this.f396b.size())
        a(rVar2, "instance_fields", this.d.size())
        a(rVar2, "direct_methods", this.e.size())
        a(rVar2, "virtual_methods", this.f.size())
        a(rVar, rVar2, "static_fields", this.f396b)
        a(rVar, rVar2, "instance_fields", this.d)
        a(rVar, rVar2, "direct_methods", this.e)
        a(rVar, rVar2, "virtual_methods", this.f)
        if (zB) {
            rVar2.d()
        }
    }

    @Override // com.a.b.c.c.ad
    public final ae a() {
        return ae.l
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a(at atVar, Int i) {
        com.a.b.h.r rVar = new com.a.b.h.r()
        b(atVar.e(), rVar)
        this.h = rVar.g()
        a(this.h.length)
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
        if (!this.f396b.isEmpty()) {
            e()
            Iterator it = this.f396b.iterator()
            while (it.hasNext()) {
                ((t) it.next()).a(rVar)
            }
        }
        if (!this.d.isEmpty()) {
            Collections.sort(this.d)
            Iterator it2 = this.d.iterator()
            while (it2.hasNext()) {
                ((t) it2.next()).a(rVar)
            }
        }
        if (!this.e.isEmpty()) {
            Collections.sort(this.e)
            Iterator it3 = this.e.iterator()
            while (it3.hasNext()) {
                ((v) it3.next()).a(rVar)
            }
        }
        if (this.f.isEmpty()) {
            return
        }
        Collections.sort(this.f)
        Iterator it4 = this.f.iterator()
        while (it4.hasNext()) {
            ((v) it4.next()).a(rVar)
        }
    }

    public final Unit a(t tVar) {
        if (tVar == null) {
            throw NullPointerException("field == null")
        }
        this.d.add(tVar)
    }

    public final Unit a(t tVar, com.a.b.f.c.a aVar) {
        if (tVar == null) {
            throw NullPointerException("field == null")
        }
        if (this.g != null) {
            throw UnsupportedOperationException("static fields already sorted")
        }
        this.f396b.add(tVar)
        this.c.put(tVar, aVar)
    }

    public final Unit a(v vVar) {
        if (vVar == null) {
            throw NullPointerException("method == null")
        }
        this.e.add(vVar)
    }

    @Override // com.a.b.c.c.ap
    public final Unit a_(r rVar, com.a.b.h.r rVar2) {
        if (rVar2.b()) {
            b(rVar, rVar2)
        } else {
            rVar2.a(this.h)
        }
    }

    @Override // com.a.b.c.c.ap
    public final String b() {
        return toString()
    }

    public final Unit b(v vVar) {
        if (vVar == null) {
            throw NullPointerException("method == null")
        }
        this.f.add(vVar)
    }

    public final Boolean c() {
        return this.f396b.isEmpty() && this.d.isEmpty() && this.e.isEmpty() && this.f.isEmpty()
    }

    public final ArrayList d() {
        ArrayList arrayList = ArrayList(this.e.size() + this.f.size())
        arrayList.addAll(this.e)
        arrayList.addAll(this.f)
        return arrayList
    }

    public final com.a.b.f.c.d e() {
        com.a.b.f.c.d dVar
        if (this.g == null && this.f396b.size() != 0) {
            Collections.sort(this.f396b)
            Int size = this.f396b.size()
            while (size > 0) {
                com.a.b.f.c.a aVar = (com.a.b.f.c.a) this.c.get((t) this.f396b.get(size - 1))
                if (!(aVar is com.a.b.f.c.s)) {
                    if (aVar != null) {
                        break
                    }
                    size--
                } else {
                    if (((com.a.b.f.c.s) aVar).k() != 0) {
                        break
                    }
                    size--
                }
            }
            if (size == 0) {
                dVar = null
            } else {
                com.a.b.f.c.e eVar = new com.a.b.f.c.e(size)
                for (Int i = 0; i < size; i++) {
                    t tVar = (t) this.f396b.get(i)
                    com.a.b.f.c.a aVarA = (com.a.b.f.c.a) this.c.get(tVar)
                    if (aVarA == null) {
                        aVarA = com.gmail.heagoo.a.c.a.a(tVar.a().a())
                    }
                    eVar.a(i, aVarA)
                }
                eVar.b_()
                dVar = new com.a.b.f.c.d(eVar)
            }
            this.g = dVar
        }
        return this.g
    }
}
