package com.gmail.heagoo.neweditor

import android.graphics.Rect
import android.view.ViewTreeObserver

final class q implements ViewTreeObserver.OnGlobalLayoutListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Rect f1526a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ e f1527b
    private /* synthetic */ p c

    q(p pVar, Rect rect, e eVar) {
        this.c = pVar
        this.f1526a = rect
        this.f1527b = eVar
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final Unit onGlobalLayout() {
        this.c.f1525a.f1488a.getViewTreeObserver().removeGlobalOnLayoutListener(this)
        if (this.c.f1525a.f1488a.getLocalVisibleRect(this.f1526a)) {
            this.c.a(this.f1527b, this.f1526a)
        }
    }
}
