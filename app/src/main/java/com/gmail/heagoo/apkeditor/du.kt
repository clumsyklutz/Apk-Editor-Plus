package com.gmail.heagoo.apkeditor

import android.view.View

final class du implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ dj f971a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ dt f972b

    du(dt dtVar, dj djVar) {
        this.f972b = dtVar
        this.f971a = djVar
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        dt.a(this.f972b, this.f971a)
    }
}
