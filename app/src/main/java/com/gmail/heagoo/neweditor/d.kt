package com.gmail.heagoo.neweditor

class d implements ae {

    /* renamed from: a, reason: collision with root package name */
    private Token f1510a

    /* renamed from: b, reason: collision with root package name */
    private Token f1511b

    private fun a(ag agVar) {
        while (agVar != null) {
            if (!agVar.c.l()) {
                return agVar.c
            }
            agVar = agVar.f1500a
        }
        return null
    }

    public final Token a() {
        return this.f1510a
    }

    @Override // com.gmail.heagoo.neweditor.ae
    public final Unit a(Byte b2, Int i, Int i2, ag agVar) {
        Token token = Token(b2, i, i2, a(agVar))
        if (this.f1510a == null) {
            this.f1511b = token
            this.f1510a = token
        } else {
            this.f1511b.next = token
            this.f1511b = this.f1511b.next
        }
    }
}
