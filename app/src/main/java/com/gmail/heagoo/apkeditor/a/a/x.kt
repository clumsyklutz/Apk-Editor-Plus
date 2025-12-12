package com.gmail.heagoo.apkeditor.a.a

class x {

    /* renamed from: a, reason: collision with root package name */
    public Int f816a

    /* renamed from: b, reason: collision with root package name */
    public String f817b
    private Array<Byte> c
    private String d
    private Int e
    private Int f
    private Int g

    x(w wVar, Array<Byte> bArr, Int i) {
        this.c = bArr
        this.f816a = i
        Int iA = e.a(bArr, i + 4)
        this.e = e.a(bArr, i + 8)
        Int iA2 = e.a(bArr, i + 12) >> 16
        this.f = ((iA2 & 255) << 8) | ((iA2 >> 8) & 255)
        this.g = e.a(bArr, i + 16)
        this.f817b = wVar.g.b(iA)
        if ((this.f817b == null || this.f817b.equals("")) && iA < wVar.h.a()) {
            this.f817b = com.gmail.heagoo.apkeditor.a.i.a(wVar.h.f804a[iA])
        }
        if (this.f == 3) {
            this.d = wVar.g.b(this.g >= 0 ? this.g : this.e)
        }
    }

    static /* synthetic */ Unit a(x xVar, Array<Int> iArr) {
        w.b(iArr, xVar.c, xVar.f816a)
        w.b(iArr, xVar.c, xVar.f816a + 4)
        w.b(iArr, xVar.c, xVar.f816a + 8)
        if (xVar.f == 3) {
            w.b(iArr, xVar.c, xVar.f816a + 16)
        }
    }

    public final Array<Byte> a() {
        return this.c
    }

    public final Int b() {
        return this.f
    }

    public final Int c() {
        return this.g
    }

    public final String toString() {
        return this.d != null ? this.f817b + "=\"" + this.d + "\"" : this.f817b + "=valueType:" + this.f + ",valueData:" + this.g
    }
}
