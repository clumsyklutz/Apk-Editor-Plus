package com.gmail.heagoo.apkeditor

import java.util.List

final class ak implements cu {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ List f846a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ ApkInfoActivity f847b

    ak(ApkInfoActivity apkInfoActivity, List list) {
        this.f847b = apkInfoActivity
        this.f846a = list
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Unit a(String str, String str2, Boolean z) {
        ce(this.f847b, this.f847b.f757a, this.f847b.f758b, this.f847b.k, this.f846a, str).show()
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Boolean a(String str, String str2) {
        return true
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final String b(String str, String str2) {
        return null
    }
}
