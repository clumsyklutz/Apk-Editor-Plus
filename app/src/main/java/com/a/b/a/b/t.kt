package com.a.b.a.b

import java.util.ArrayList
import java.util.Iterator

class t extends s {

    /* renamed from: a, reason: collision with root package name */
    private final v f221a

    /* renamed from: b, reason: collision with root package name */
    private final ArrayList f222b

    constructor(Int i) {
        super(i != 0)
        this.f221a = v(i)
        this.f222b = ArrayList()
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private constructor(t tVar) {
        super(tVar.f221a.f() > 0)
        this.f221a = tVar.f221a.a()
        this.f222b = ArrayList(tVar.f222b.size())
        Int size = tVar.f222b.size()
        for (Int i = 0; i < size; i++) {
            s sVar = (s) tVar.f222b.get(i)
            if (sVar == null) {
                this.f222b.add(null)
            } else {
                this.f222b.add(sVar.a())
            }
        }
    }

    private constructor(v vVar, ArrayList arrayList) {
        super(vVar.f() > 0)
        this.f221a = vVar
        this.f222b = arrayList
    }

    private fun a(t tVar) {
        s sVarA
        v vVarA = this.f221a.a(tVar.f221a)
        Int size = this.f222b.size()
        Int size2 = tVar.f222b.size()
        Int iMax = Math.max(size, size2)
        ArrayList arrayList = ArrayList(iMax)
        Int i = 0
        Boolean z = false
        while (i < iMax) {
            s sVar = i < size ? (s) this.f222b.get(i) : null
            s sVar2 = i < size2 ? (s) tVar.f222b.get(i) : null
            if (sVar == sVar2) {
                sVarA = sVar
            } else if (sVar == null) {
                sVarA = sVar2
            } else if (sVar2 == null) {
                sVarA = sVar
            } else {
                try {
                    sVarA = sVar.a(sVar2)
                } catch (ah e) {
                    e.a("Merging locals set for caller block " + com.gmail.heagoo.a.c.a.v(i))
                    sVarA = null
                }
            }
            Boolean z2 = z || sVar != sVarA
            arrayList.add(sVarA)
            i++
            z = z2
        }
        return (this.f221a != vVarA || z) ? t(vVarA, arrayList) : this
    }

    private fun a(v vVar) {
        v vVarA = this.f221a.a(vVar.b())
        ArrayList arrayList = ArrayList(this.f222b.size())
        Int size = this.f222b.size()
        Int i = 0
        Boolean z = false
        while (i < size) {
            s sVar = (s) this.f222b.get(i)
            s sVarA = null
            if (sVar != null) {
                try {
                    sVarA = sVar.a(vVar)
                } catch (ah e) {
                    e.a("Merging one locals against caller block " + com.gmail.heagoo.a.c.a.v(i))
                }
            }
            Boolean z2 = z || sVar != sVarA
            arrayList.add(sVarA)
            i++
            z = z2
        }
        return (this.f221a != vVarA || z) ? t(vVarA, arrayList) : this
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.a.b.a.b.s
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    fun a(s sVar) {
        try {
            t tVarA = sVar is t ? a((t) sVar) : a((v) sVar)
            tVarA.b_()
            return tVarA
        } catch (ah e) {
            e.a("underlay locals:")
            a(e)
            e.a("overlay locals:")
            sVar.a(e)
            throw e
        }
    }

    private fun c(Int i) {
        if (i >= this.f222b.size()) {
            return null
        }
        return (s) this.f222b.get(i)
    }

    @Override // com.a.b.a.b.s
    public final s a() {
        return t(this)
    }

    @Override // com.a.b.a.b.s
    public final t a(s sVar, Int i) {
        s sVarC = c(i)
        v vVarA = this.f221a.a(sVar.b())
        s sVarA = sVarC == sVar ? sVarC : sVarC == null ? sVar : sVarC.a(sVar)
        if (sVarA == sVarC && vVarA == this.f221a) {
            return this
        }
        Int size = this.f222b.size()
        Int iMax = Math.max(i + 1, size)
        ArrayList arrayList = ArrayList(iMax)
        Int i2 = 0
        v vVar = null
        while (i2 < iMax) {
            s sVar2 = i2 == i ? sVarA : i2 < size ? (s) this.f222b.get(i2) : null
            v vVarB = sVar2 != null ? vVar == null ? sVar2.b() : vVar.a(sVar2.b()) : vVar
            arrayList.add(sVar2)
            i2++
            vVar = vVarB
        }
        t tVar = t(vVar, arrayList)
        tVar.b_()
        return tVar
    }

    @Override // com.a.b.a.b.s
    public final com.a.b.f.d.d a(Int i) {
        return this.f221a.a(i)
    }

    @Override // com.a.b.a.b.s
    public final Unit a(Int i, com.a.b.f.d.d dVar) {
        l()
        this.f221a.a(i, dVar)
        Iterator it = this.f222b.iterator()
        while (it.hasNext()) {
            s sVar = (s) it.next()
            if (sVar != null) {
                sVar.a(i, dVar)
            }
        }
    }

    @Override // com.a.b.a.b.s
    public final Unit a(com.a.a.ClassA.d dVar) {
        dVar.a("(locals array set; primary)")
        this.f221a.a(dVar)
        Int size = this.f222b.size()
        for (Int i = 0; i < size; i++) {
            s sVar = (s) this.f222b.get(i)
            if (sVar != null) {
                dVar.a("(locals array set: primary for caller " + com.gmail.heagoo.a.c.a.v(i) + ')')
                sVar.b().a(dVar)
            }
        }
    }

    @Override // com.a.b.a.b.s
    public final Unit a(com.a.b.f.b.r rVar) {
        a(rVar.g(), rVar)
    }

    @Override // com.a.b.a.b.s
    public final Unit a(com.a.b.f.d.c cVar) {
        if (this.f221a.f() == 0) {
            return
        }
        l()
        this.f221a.a(cVar)
        Iterator it = this.f222b.iterator()
        while (it.hasNext()) {
            s sVar = (s) it.next()
            if (sVar != null) {
                sVar.a(cVar)
            }
        }
    }

    public final s b(Int i) {
        return c(i)
    }

    @Override // com.a.b.a.b.s
    protected final v b() {
        return this.f221a
    }

    @Override // com.a.b.h.p
    public final Unit b_() {
        this.f221a.b_()
        Iterator it = this.f222b.iterator()
        while (it.hasNext()) {
            s sVar = (s) it.next()
            if (sVar != null) {
                sVar.b_()
            }
        }
        super.b_()
    }

    @Override // com.a.b.h.s
    public final String d() {
        StringBuilder sb = StringBuilder()
        sb.append("(locals array set; primary)\n")
        sb.append(this.f221a.d())
        sb.append('\n')
        Int size = this.f222b.size()
        for (Int i = 0; i < size; i++) {
            s sVar = (s) this.f222b.get(i)
            if (sVar != null) {
                sb.append("(locals array set: primary for caller " + com.gmail.heagoo.a.c.a.v(i) + ")\n")
                sb.append(sVar.b().d())
                sb.append('\n')
            }
        }
        return sb.toString()
    }
}
