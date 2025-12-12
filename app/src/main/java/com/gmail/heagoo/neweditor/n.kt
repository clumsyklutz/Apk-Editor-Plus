package com.gmail.heagoo.neweditor

import android.view.KeyEvent
import android.view.View

final class n implements View.OnKeyListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ EditorActivity f1523a

    n(EditorActivity editorActivity) {
        this.f1523a = editorActivity
    }

    @Override // android.view.View.OnKeyListener
    public final Boolean onKey(View view, Int i, KeyEvent keyEvent) {
        if (this.f1523a.y.getVisibility() == 0 && keyEvent.getAction() == 1 && i == 4) {
            this.f1523a.y.close()
            return true
        }
        EditorActivity.l(this.f1523a)
        return false
    }
}
