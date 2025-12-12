package com.gmail.heagoo.appdm

import android.view.View

final class n implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ PrefOverallActivity f1395a

    n(PrefOverallActivity prefOverallActivity) {
        this.f1395a = prefOverallActivity
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        this.f1395a.a()
    }
}
