package com.gmail.heagoo.apkeditor

final class hm implements fa {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ com.gmail.heagoo.common.i f1151a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ TextEditBigActivity f1152b

    hm(TextEditBigActivity textEditBigActivity, com.gmail.heagoo.common.i iVar) {
        this.f1152b = textEditBigActivity
        this.f1151a = iVar
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() {
        com.gmail.heagoo.common.h.a(this.f1152b.a(), this.f1152b.K.e())
        this.f1152b.j.a(false)
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        this.f1152b.d()
        this.f1152b.g = true
        this.f1152b.b()
        if (this.f1151a != null) {
            this.f1151a.a()
        }
    }
}
