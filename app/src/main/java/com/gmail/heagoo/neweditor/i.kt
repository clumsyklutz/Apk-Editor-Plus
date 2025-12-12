package com.gmail.heagoo.neweditor

import android.widget.SlidingDrawer

final class i implements SlidingDrawer.OnDrawerCloseListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ EditorActivity f1518a

    i(EditorActivity editorActivity) {
        this.f1518a = editorActivity
    }

    @Override // android.widget.SlidingDrawer.OnDrawerCloseListener
    public final Unit onDrawerClosed() {
        EditorActivity.j(this.f1518a)
    }
}
