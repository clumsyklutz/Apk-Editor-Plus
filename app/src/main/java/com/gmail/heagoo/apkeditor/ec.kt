package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.support.v4.view.PointerIconCompat
import com.gmail.heagoo.a.c.a

final class ec implements cu {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ eb f1020a

    ec(eb ebVar) {
        this.f1020a = ebVar
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Unit a(String str, String str2, Boolean z) throws Throwable {
        if (z) {
            ((ApkInfoActivity) this.f1020a.f1019a.f976a.get()).a(str, str2, (gu) null)
            a.a((Activity) this.f1020a.f1019a.f976a.get(), str, PointerIconCompat.TYPE_CONTEXT_MENU)
        } else {
            ((ApkInfoActivity) this.f1020a.f1019a.f976a.get()).b(str2, str)
            ((ApkInfoActivity) this.f1020a.f1019a.f976a.get()).a(true)
        }
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Boolean a(String str, String str2) {
        return str.endsWith(".xml")
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final String b(String str, String str2) {
        return null
    }
}
