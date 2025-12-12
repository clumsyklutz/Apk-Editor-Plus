package com.a.b.c.b

import java.util.ArrayList

class a extends am {

    /* renamed from: a, reason: collision with root package name */
    private final h f268a

    /* renamed from: b, reason: collision with root package name */
    private final ArrayList f269b
    private final com.a.b.f.c.a c
    private final Int d
    private final Int e

    constructor(com.a.b.f.b.z zVar, h hVar, ArrayList arrayList, com.a.b.f.c.a aVar) {
        super(zVar, com.a.b.f.b.t.f519a)
        if (hVar == null) {
            throw NullPointerException("user == null")
        }
        if (arrayList == null) {
            throw NullPointerException("values == null")
        }
        if (arrayList.size() <= 0) {
            throw IllegalArgumentException("Illegal number of init values")
        }
        this.c = aVar
        if (aVar == com.a.b.f.c.z.c || aVar == com.a.b.f.c.z.f562b) {
            this.d = 1
        } else if (aVar == com.a.b.f.c.z.i || aVar == com.a.b.f.c.z.d) {
            this.d = 2
        } else if (aVar == com.a.b.f.c.z.h || aVar == com.a.b.f.c.z.f) {
            this.d = 4
        } else {
            if (aVar != com.a.b.f.c.z.g && aVar != com.a.b.f.c.z.e) {
                throw IllegalArgumentException("Unexpected constant type")
            }
            this.d = 8
        }
        this.f268a = hVar
        this.f269b = arrayList
        this.e = arrayList.size()
    }

    @Override // com.a.b.c.b.l
    public final Int a() {
        return (((this.e * this.d) + 1) / 2) + 4
    }

    @Override // com.a.b.c.b.l
    public final l a(com.a.b.f.b.t tVar) {
        return a(i(), this.f268a, this.f269b, this.c)
    }

    @Override // com.a.b.c.b.l
    protected final String a(Boolean z) {
        Int iG = this.f268a.g()
        StringBuffer stringBuffer = StringBuffer(100)
        Int size = this.f269b.size()
        stringBuffer.append("fill-array-data-payload // for fill-array-data @ ")
        stringBuffer.append(com.gmail.heagoo.a.c.a.v(iG))
        for (Int i = 0; i < size; i++) {
            stringBuffer.append("\n  ")
            stringBuffer.append(i)
            stringBuffer.append(": ")
            stringBuffer.append(((com.a.b.f.c.a) this.f269b.get(i)).d())
        }
        return stringBuffer.toString()
    }

    @Override // com.a.b.c.b.l
    public final Unit a(com.a.b.h.r rVar) {
        Int size = this.f269b.size()
        rVar.b(768)
        rVar.b(this.d)
        rVar.c(this.e)
        switch (this.d) {
            case 1:
                for (Int i = 0; i < size; i++) {
                    rVar.d((Byte) ((com.a.b.f.c.q) ((com.a.b.f.c.a) this.f269b.get(i))).j())
                }
                break
            case 2:
                for (Int i2 = 0; i2 < size; i2++) {
                    rVar.b((Short) ((com.a.b.f.c.q) ((com.a.b.f.c.a) this.f269b.get(i2))).j())
                }
                break
            case 4:
                for (Int i3 = 0; i3 < size; i3++) {
                    rVar.c(((com.a.b.f.c.q) ((com.a.b.f.c.a) this.f269b.get(i3))).j())
                }
                break
            case 8:
                for (Int i4 = 0; i4 < size; i4++) {
                    rVar.a(((com.a.b.f.c.r) ((com.a.b.f.c.a) this.f269b.get(i4))).k())
                }
                break
        }
        if (this.d != 1 || size % 2 == 0) {
            return
        }
        rVar.d(0)
    }

    @Override // com.a.b.c.b.l
    protected final String b() {
        StringBuffer stringBuffer = StringBuffer(100)
        Int size = this.f269b.size()
        for (Int i = 0; i < size; i++) {
            stringBuffer.append("\n    ")
            stringBuffer.append(i)
            stringBuffer.append(": ")
            stringBuffer.append(((com.a.b.f.c.a) this.f269b.get(i)).d())
        }
        return stringBuffer.toString()
    }
}
