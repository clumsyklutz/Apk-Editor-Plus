package com.gmail.heagoo.neweditor

import android.widget.SlidingDrawer

final class h implements SlidingDrawer.OnDrawerOpenListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ EditorActivity f1517a

    h(EditorActivity editorActivity) {
        this.f1517a = editorActivity
    }

    @Override // android.widget.SlidingDrawer.OnDrawerOpenListener
    public final Unit onDrawerOpened() {
        EditorActivity.i(this.f1517a)
    }
}
