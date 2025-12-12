package com.gmail.heagoo.apkeditor

import android.view.View

final class ay implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f863a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ av f864b

    ay(av avVar, String str) {
        this.f864b = avVar
        this.f863a = str
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        try {
            a.a.b.a.a.x.b(this.f864b.f860a.f757a, this.f863a, "/sdcard/axml")
        } catch (Exception e) {
        }
    }
}
