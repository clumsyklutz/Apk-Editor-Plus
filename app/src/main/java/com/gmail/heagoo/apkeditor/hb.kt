package com.gmail.heagoo.apkeditor

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver

final class hb implements ViewTreeObserver.OnGlobalLayoutListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ View f1135a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ com.gmail.heagoo.neweditor.s f1136b
    private /* synthetic */ gy c

    hb(gy gyVar, View view, com.gmail.heagoo.neweditor.s sVar) {
        this.c = gyVar
        this.f1135a = view
        this.f1136b = sVar
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final Unit onGlobalLayout() {
        Rect rect = Rect()
        this.f1135a.getWindowVisibleDisplayFrame(rect)
        Int height = this.f1135a.getRootView().getHeight()
        Int i = height - rect.bottom
        this.c.s = ((Double) i) > ((Double) height) * 0.15d
        this.f1136b.a(this.c.s)
        gy.b(this.c, this.c.s)
    }
}
