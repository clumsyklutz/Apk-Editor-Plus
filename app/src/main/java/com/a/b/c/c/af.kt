package com.a.b.c.c

import java.util.ArrayList

class af extends ap {

    /* renamed from: a, reason: collision with root package name */
    private final ae f352a

    /* renamed from: b, reason: collision with root package name */
    private final at f353b
    private final ad c
    private final Int d

    private constructor(ae aeVar, at atVar, ad adVar, ad adVar2, Int i) {
        super(4, 12)
        if (aeVar == null) {
            throw NullPointerException("type == null")
        }
        if (atVar == null) {
            throw NullPointerException("section == null")
        }
        if (adVar == null) {
            throw NullPointerException("firstItem == null")
        }
        if (adVar2 == null) {
            throw NullPointerException("lastItem == null")
        }
        if (i <= 0) {
            throw IllegalArgumentException("itemCount <= 0")
        }
        this.f352a = aeVar
        this.f353b = atVar
        this.c = adVar
        this.d = i
    }

    private constructor(at atVar) {
        super(4, 12)
        if (atVar == null) {
            throw NullPointerException("section == null")
        }
        this.f352a = ae.h
        this.f353b = atVar
        this.c = null
        this.d = 1
    }

    fun a(Array<at> atVarArr, al alVar) {
        if (atVarArr == null) {
            throw NullPointerException("sections == null")
        }
        if (alVar.a().size() != 0) {
            throw IllegalArgumentException("mapSection.items().size() != 0")
        }
        ArrayList arrayList = ArrayList(50)
        for (at atVar : atVarArr) {
            Int i = 0
            ad adVar = null
            ad adVar2 = null
            ae aeVar = null
            for (ad adVar3 : atVar.a()) {
                ae aeVarA = adVar3.a()
                if (aeVarA != aeVar) {
                    if (i != 0) {
                        arrayList.add(af(aeVar, atVar, adVar2, adVar, i))
                    }
                    i = 0
                    adVar2 = adVar3
                    aeVar = aeVarA
                }
                i++
                adVar = adVar3
            }
            if (i != 0) {
                arrayList.add(af(aeVar, atVar, adVar2, adVar, i))
            } else if (atVar == alVar) {
                arrayList.add(af(alVar))
            }
        }
        alVar.a((ap) bd(ae.h, arrayList))
    }

    @Override // com.a.b.c.c.ad
    public final ae a() {
        return ae.s
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a_(r rVar, com.a.b.h.r rVar2) {
        Int iA = this.f352a.a()
        Int iG = this.c == null ? this.f353b.g() : this.f353b.a(this.c)
        if (rVar2.b()) {
            rVar2.a(0, h() + ' ' + this.f352a.b() + " map")
            rVar2.a(2, "  type:   " + com.gmail.heagoo.a.c.a.v(iA) + " // " + this.f352a.toString())
            rVar2.a(2, "  unused: 0")
            rVar2.a(4, "  size:   " + com.gmail.heagoo.a.c.a.t(this.d))
            rVar2.a(4, "  offset: " + com.gmail.heagoo.a.c.a.t(iG))
        }
        rVar2.b(iA)
        rVar2.b(0)
        rVar2.c(this.d)
        rVar2.c(iG)
    }

    @Override // com.a.b.c.c.ap
    public final String b() {
        return toString()
    }

    public final String toString() {
        StringBuffer stringBuffer = StringBuffer(100)
        stringBuffer.append(getClass().getName())
        stringBuffer.append('{')
        stringBuffer.append(this.f353b.toString())
        stringBuffer.append(' ')
        stringBuffer.append(this.f352a.d())
        stringBuffer.append('}')
        return stringBuffer.toString()
    }
}
