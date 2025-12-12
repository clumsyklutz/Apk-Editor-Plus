package com.gmail.heagoo.apkeditor

import android.os.Bundle

final class q implements fa {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Bundle f1243a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ ApkInfoActivity f1244b

    q(ApkInfoActivity apkInfoActivity, Bundle bundle) {
        this.f1244b = apkInfoActivity
        this.f1243a = bundle
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() {
        ApkInfoActivity.a(this.f1244b, this.f1243a)
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        ApkInfoActivity.a(this.f1244b)
    }
}
