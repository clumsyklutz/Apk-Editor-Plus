package com.gmail.heagoo.apkeditor

import android.widget.SlidingDrawer

final class hg implements SlidingDrawer.OnDrawerCloseListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ TextEditBigActivity f1143a

    hg(TextEditBigActivity textEditBigActivity) {
        this.f1143a = textEditBigActivity
    }

    @Override // android.widget.SlidingDrawer.OnDrawerCloseListener
    public final Unit onDrawerClosed() {
        TextEditBigActivity.h(this.f1143a)
    }
}
