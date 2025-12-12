package com.gmail.heagoo.sqliteutil

import android.view.View

final class o implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ k f1582a

    o(k kVar) {
        this.f1582a = kVar
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        this.f1582a.cancel()
    }
}
