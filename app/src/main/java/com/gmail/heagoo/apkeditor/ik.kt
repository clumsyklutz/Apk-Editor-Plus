package com.gmail.heagoo.apkeditor

import java.io.IOException

final class ik implements fa {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ com.gmail.heagoo.common.i f1188a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ TextEditNormalActivity f1189b

    ik(TextEditNormalActivity textEditNormalActivity, com.gmail.heagoo.common.i iVar) {
        this.f1189b = textEditNormalActivity
        this.f1188a = iVar
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() throws IOException {
        this.f1189b.j.a(this.f1189b)
        this.f1189b.j.a(false)
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        this.f1189b.d()
        this.f1189b.g = true
        this.f1189b.b()
        if (this.f1188a != null) {
            this.f1188a.a()
        }
    }
}
