package com.gmail.heagoo.apkeditor

import android.view.View

final class ee implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ dx f1022a

    ee(dx dxVar) {
        this.f1022a = dxVar
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        this.f1022a.dismiss()
    }
}
