package com.a.b.c.c

import java.util.Iterator
import java.util.List

class bd extends ap {

    /* renamed from: a, reason: collision with root package name */
    private final ae f382a

    /* renamed from: b, reason: collision with root package name */
    private final List f383b

    /* JADX WARN: Illegal instructions before constructor call */
    constructor(ae aeVar, List list) {
        Int iA = a(list)
        ap apVar = (ap) list.get(0)
        super(iA, (apVar.e_() * list.size()) + a(list))
        if (aeVar == null) {
            throw NullPointerException("itemType == null")
        }
        this.f383b = list
        this.f382a = aeVar
    }

    private fun a(List list) {
        try {
            return Math.max(4, ((ap) list.get(0)).g())
        } catch (IndexOutOfBoundsException e) {
            throw IllegalArgumentException("items.size() == 0")
        } catch (NullPointerException e2) {
            throw NullPointerException("items == null")
        }
    }

    @Override // com.a.b.c.c.ad
    public final ae a() {
        return this.f382a
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a(at atVar, Int i) {
        Int iG = i + g()
        Int i2 = 0
        Boolean z = true
        Int iB = iG
        Int iG2 = 0
        for (ap apVar : this.f383b) {
            Int iE_ = apVar.e_()
            if (z) {
                iG2 = apVar.g()
                i2 = iE_
                z = false
            } else {
                if (iE_ != i2) {
                    throw UnsupportedOperationException("item size mismatch")
                }
                if (apVar.g() != iG2) {
                    throw UnsupportedOperationException("item alignment mismatch")
                }
            }
            iB = apVar.b(atVar, iB) + iE_
        }
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
        Iterator it = this.f383b.iterator()
        while (it.hasNext()) {
            ((ap) it.next()).a(rVar)
        }
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a_(r rVar, com.a.b.h.r rVar2) {
        Int size = this.f383b.size()
        if (rVar2.b()) {
            rVar2.a(0, h() + " " + a().d())
            rVar2.a(4, "  size: " + com.gmail.heagoo.a.c.a.t(size))
        }
        rVar2.c(size)
        Iterator it = this.f383b.iterator()
        while (it.hasNext()) {
            ((ap) it.next()).a(rVar, rVar2)
        }
    }

    @Override // com.a.b.c.c.ap
    public final String b() {
        StringBuffer stringBuffer = StringBuffer(100)
        stringBuffer.append("{")
        Boolean z = true
        for (ap apVar : this.f383b) {
            if (z) {
                z = false
            } else {
                stringBuffer.append(", ")
            }
            stringBuffer.append(apVar.b())
        }
        stringBuffer.append("}")
        return stringBuffer.toString()
    }

    public final List c() {
        return this.f383b
    }

    public final String toString() {
        StringBuffer stringBuffer = StringBuffer(100)
        stringBuffer.append(getClass().getName())
        stringBuffer.append(this.f383b)
        return stringBuffer.toString()
    }
}
