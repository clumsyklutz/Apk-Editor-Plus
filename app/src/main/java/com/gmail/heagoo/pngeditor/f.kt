package com.gmail.heagoo.pngeditor

import android.view.View

final class f implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ PngEditActivity f1551a

    f(PngEditActivity pngEditActivity) {
        this.f1551a = pngEditActivity
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        this.f1551a.w.dismiss()
    }
}
