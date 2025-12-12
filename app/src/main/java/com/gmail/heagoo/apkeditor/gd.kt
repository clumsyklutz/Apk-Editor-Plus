package com.gmail.heagoo.apkeditor

final class gd implements gu {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Int f1099a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ fv f1100b

    gd(fv fvVar, Int i) {
        this.f1100b = fvVar
        this.f1099a = i
    }

    @Override // com.gmail.heagoo.apkeditor.gu
    public final Unit a() {
        this.f1100b.c.collapseGroup(this.f1099a)
        this.f1100b.d.b(this.f1099a)
    }
}
