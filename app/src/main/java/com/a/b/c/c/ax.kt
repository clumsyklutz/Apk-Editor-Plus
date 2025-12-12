package com.a.b.c.c

class ax extends ac implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.y f376a

    /* renamed from: b, reason: collision with root package name */
    private aw f377b

    constructor(com.a.b.f.c.y yVar) {
        if (yVar == null) {
            throw NullPointerException("value == null")
        }
        this.f376a = yVar
        this.f377b = null
    }

    @Override // com.a.b.c.c.ad
    public final ae a() {
        return ae.f351b
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
        if (this.f377b == null) {
            al alVarD = rVar.d()
            this.f377b = aw(this.f376a)
            alVarD.a((ap) this.f377b)
        }
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar, com.a.b.h.r rVar2) {
        String str
        Int iF = this.f377b.f()
        if (rVar2.b()) {
            StringBuilder sbAppend = StringBuilder().append(j()).append(' ')
            String strD = this.f376a.d()
            if (strD.length() <= 98) {
                str = ""
            } else {
                strD = strD.substring(0, 95)
                str = "..."
            }
            rVar2.a(0, sbAppend.append("\"" + strD + str + '\"').toString())
            rVar2.a(4, "  string_data_off: " + com.gmail.heagoo.a.c.a.t(iF))
        }
        rVar2.c(iF)
    }

    public final com.a.b.f.c.y c() {
        return this.f376a
    }

    @Override // java.lang.Comparable
    public final Int compareTo(Object obj) {
        return this.f376a.compareTo(((ax) obj).f376a)
    }

    @Override // com.a.b.c.c.ad
    public final Int e_() {
        return 4
    }

    public final Boolean equals(Object obj) {
        if (obj is ax) {
            return this.f376a.equals(((ax) obj).f376a)
        }
        return false
    }

    public final Int hashCode() {
        return this.f376a.hashCode()
    }
}
