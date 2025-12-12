package com.gmail.heagoo.apkeditor

import android.widget.SlidingDrawer

final class ic implements SlidingDrawer.OnDrawerCloseListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ TextEditNormalActivity f1177a

    ic(TextEditNormalActivity textEditNormalActivity) {
        this.f1177a = textEditNormalActivity
    }

    @Override // android.widget.SlidingDrawer.OnDrawerCloseListener
    public final Unit onDrawerClosed() {
        TextEditNormalActivity.o(this.f1177a)
    }
}
