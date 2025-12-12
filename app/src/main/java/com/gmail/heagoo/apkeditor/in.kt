package com.gmail.heagoo.apkeditor

import android.graphics.Rect
import android.view.ViewTreeObserver

final class in implements ViewTreeObserver.OnGlobalLayoutListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Rect f1193a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ com.gmail.heagoo.neweditor.e f1194b
    private /* synthetic */ im c

    in(im imVar, Rect rect, com.gmail.heagoo.neweditor.e eVar) {
        this.c = imVar
        this.f1193a = rect
        this.f1194b = eVar
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final Unit onGlobalLayout() {
        this.c.f1192a.l.getViewTreeObserver().removeGlobalOnLayoutListener(this)
        if (this.c.f1192a.l.getLocalVisibleRect(this.f1193a)) {
            this.c.a(this.f1194b, this.f1193a)
        }
    }
}
