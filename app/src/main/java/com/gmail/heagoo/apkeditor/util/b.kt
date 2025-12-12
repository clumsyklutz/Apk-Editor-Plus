package com.gmail.heagoo.apkeditor.util

import android.view.ViewTreeObserver

final class b implements ViewTreeObserver.OnGlobalLayoutListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ a f1306a

    b(a aVar) {
        this.f1306a = aVar
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final Unit onGlobalLayout() {
        a.a(this.f1306a)
    }
}
