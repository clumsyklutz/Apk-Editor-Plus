package com.gmail.heagoo.apkeditor

import android.support.v4.view.PointerIconCompat
import com.gmail.heagoo.a.c.a

final class al implements cu {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ gu f848a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ String f849b
    private /* synthetic */ ApkInfoActivity c

    al(ApkInfoActivity apkInfoActivity, gu guVar, String str) {
        this.c = apkInfoActivity
        this.f848a = guVar
        this.f849b = str
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Unit a(String str, String str2, Boolean z) {
        if (z) {
            this.c.a(str, str2, this.f848a)
            a.a(this.c, str, PointerIconCompat.TYPE_CONTEXT_MENU)
        } else {
            this.c.e.d(str2, str)
            if (this.f848a != null) {
                this.f848a.a()
            }
        }
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Boolean a(String str, String str2) {
        if (this.f849b != null) {
            return str.endsWith(this.f849b)
        }
        return true
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final String b(String str, String str2) {
        return null
    }
}
