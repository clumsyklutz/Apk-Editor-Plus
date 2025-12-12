package com.gmail.heagoo.apkeditor

import android.content.Context

final class it implements cb {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Context f1202a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ String f1203b

    it(Context context, String str) {
        this.f1202a = context
        this.f1203b = str
    }

    @Override // com.gmail.heagoo.apkeditor.cb
    public final Unit a(Int i) {
        UserAppActivity.b(this.f1202a, this.f1203b, StringBuilder().append(i).toString())
    }
}
