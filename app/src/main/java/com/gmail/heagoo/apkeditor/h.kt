package com.gmail.heagoo.apkeditor

import android.content.Intent
import android.view.View
import com.gmail.heagoo.a.c.a

final class h implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f1132a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ Int f1133b
    private /* synthetic */ g c

    h(g gVar, String str, Int i) {
        this.c = gVar
        this.f1132a = str
        this.f1133b = i
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        ApkComposeActivity apkComposeActivity = (ApkComposeActivity) this.c.f1095a.get()
        Intent intentA = a.a(apkComposeActivity, this.f1132a, apkComposeActivity.f751a)
        if (this.f1133b > 0) {
            a.a(intentA, "startLine", StringBuilder().append(this.f1133b).toString())
        }
        apkComposeActivity.startActivity(intentA)
    }
}
