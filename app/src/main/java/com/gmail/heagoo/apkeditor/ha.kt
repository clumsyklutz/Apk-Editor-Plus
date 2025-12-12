package com.gmail.heagoo.apkeditor

import android.view.View

final class ha implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ gy f1134a

    ha(gy gyVar) {
        this.f1134a = gyVar
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        this.f1134a.c(((Integer) view.getTag()).intValue())
    }
}
