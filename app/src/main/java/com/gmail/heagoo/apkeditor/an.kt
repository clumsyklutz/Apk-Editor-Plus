package com.gmail.heagoo.apkeditor

final class an implements cu {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ ApkInfoActivity f851a

    an(ApkInfoActivity apkInfoActivity) {
        this.f851a = apkInfoActivity
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Unit a(String str, String str2, Boolean z) throws Throwable {
        this.f851a.e.a(str2 + "/" + str.substring(str.lastIndexOf("/") + 1), str)
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
