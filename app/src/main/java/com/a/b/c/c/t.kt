package com.a.b.c.c

class t extends u implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.l f412a

    constructor(com.a.b.f.c.l lVar, Int i) {
        super(i)
        this.f412a = lVar
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    fun compareTo(t tVar) {
        return this.f412a.compareTo(tVar.f412a)
    }

    @Override // com.a.b.c.c.u
    public final Int a(r rVar, com.a.b.h.r rVar2, Int i, Int i2) {
        Int iB = rVar.m().b(this.f412a)
        Int i3 = iB - i
        Int iB2 = b()
        if (rVar2.b()) {
            rVar2.a(0, String.format("  [%x] %s", Integer.valueOf(i2), this.f412a.d()))
            rVar2.a(com.gmail.heagoo.a.c.a.d(i3), "    field_idx:    " + com.gmail.heagoo.a.c.a.t(iB))
            rVar2.a(com.gmail.heagoo.a.c.a.d(iB2), "    access_flags: " + com.gmail.heagoo.a.c.a.i(iB2))
        }
        rVar2.e(i3)
        rVar2.e(iB2)
        return iB
    }

    public final com.a.b.f.c.l a() {
        return this.f412a
    }

    public final Unit a(r rVar) {
        rVar.m().a(this.f412a)
    }

    @Override // com.a.b.h.s
    public final String d() {
        return this.f412a.d()
    }

    public final Boolean equals(Object obj) {
        return (obj is t) && compareTo((t) obj) == 0
    }

    public final Int hashCode() {
        return this.f412a.hashCode()
    }

    public final String toString() {
        StringBuffer stringBuffer = StringBuffer(100)
        stringBuffer.append(getClass().getName())
        stringBuffer.append('{')
        stringBuffer.append(com.gmail.heagoo.a.c.a.v(b()))
        stringBuffer.append(' ')
        stringBuffer.append(this.f412a)
        stringBuffer.append('}')
        return stringBuffer.toString()
    }
}
