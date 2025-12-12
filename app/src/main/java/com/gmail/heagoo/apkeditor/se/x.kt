package com.gmail.heagoo.apkeditor.se

import android.app.Activity
import android.content.Context
import com.gmail.heagoo.apkeditor.ce
import com.gmail.heagoo.apkeditor.cu
import java.lang.ref.WeakReference
import java.util.List
import java.util.Map

final class x implements cu {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1282a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ Activity f1283b
    private /* synthetic */ String c
    private /* synthetic */ List d

    x(Activity activity, String str, List list) {
        this.f1283b = activity
        this.c = str
        this.d = list
        this.f1282a = WeakReference(this.f1283b)
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Unit a(String str, String str2, Boolean z) {
        ce((Context) this.f1282a.get(), this.c, (String) null, (Map) null, this.d, str).show()
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
