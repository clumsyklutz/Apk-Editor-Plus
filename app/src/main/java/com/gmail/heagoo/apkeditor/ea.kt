package com.gmail.heagoo.apkeditor

import android.content.Context
import java.util.Map

final class ea implements cu {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ dz f1018a

    ea(dz dzVar) {
        this.f1018a = dzVar
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Unit a(String str, String str2, Boolean z) {
        ce((Context) this.f1018a.f979a.f976a.get(), this.f1018a.f979a.f977b, str, (String) null, (String) null, (Map) null).show()
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
