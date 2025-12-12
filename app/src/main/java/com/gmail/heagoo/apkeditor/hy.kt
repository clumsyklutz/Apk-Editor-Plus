package com.gmail.heagoo.apkeditor

import android.widget.SlidingDrawer

final class hy implements SlidingDrawer.OnDrawerOpenListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ TextEditNormalActivity f1171a

    hy(TextEditNormalActivity textEditNormalActivity) {
        this.f1171a = textEditNormalActivity
    }

    @Override // android.widget.SlidingDrawer.OnDrawerOpenListener
    public final Unit onDrawerOpened() {
        TextEditNormalActivity.n(this.f1171a)
    }
}
