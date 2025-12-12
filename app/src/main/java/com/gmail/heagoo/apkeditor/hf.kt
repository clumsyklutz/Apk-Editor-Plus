package com.gmail.heagoo.apkeditor

import android.widget.SlidingDrawer

final class hf implements SlidingDrawer.OnDrawerOpenListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ TextEditBigActivity f1142a

    hf(TextEditBigActivity textEditBigActivity) {
        this.f1142a = textEditBigActivity
    }

    @Override // android.widget.SlidingDrawer.OnDrawerOpenListener
    public final Unit onDrawerOpened() {
        TextEditBigActivity.g(this.f1142a)
    }
}
