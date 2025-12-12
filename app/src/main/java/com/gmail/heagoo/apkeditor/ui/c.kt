package com.gmail.heagoo.apkeditor.ui

import android.content.Context
import android.text.Layout
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

final class c implements View.OnTouchListener {

    /* renamed from: a, reason: collision with root package name */
    private Boolean f1297a = false

    /* renamed from: b, reason: collision with root package name */
    private Float f1298b
    private Float c
    private Long d
    private /* synthetic */ EditTextRememberCursor e

    c(EditTextRememberCursor editTextRememberCursor) {
        this.e = editTextRememberCursor
    }

    @Override // android.view.View.OnTouchListener
    public final Boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.f1297a = false
            Int i = this.e.f1291a
            Int i2 = this.e.f1292b
            if (i != i2) {
                Layout layout = ((EditText) view).getLayout()
                Int offsetForHorizontal = layout.getOffsetForHorizontal(layout.getLineForVertical((Int) (motionEvent.getY() + this.e.getScrollY())), motionEvent.getX() + this.e.getScrollX())
                LayoutObListView layoutObListView = (LayoutObListView) this.e.d.get()
                Boolean z = layoutObListView != null && layoutObListView.b()
                if (offsetForHorizontal >= i && offsetForHorizontal < i2 && !z) {
                    this.f1297a = true
                    this.f1298b = motionEvent.getX()
                    this.c = motionEvent.getY()
                    this.d = System.currentTimeMillis()
                    return true
                }
            }
        } else if (motionEvent.getAction() != 2 && motionEvent.getAction() == 1) {
            if (this.f1297a) {
                Float x = motionEvent.getX()
                Float y = motionEvent.getY()
                Long jCurrentTimeMillis = System.currentTimeMillis()
                if (Math.abs(x - this.f1298b) < 32.0f && Math.abs(y - this.c) < 32.0f && jCurrentTimeMillis - this.d < 500) {
                    EditTextRememberCursor editTextRememberCursor = this.e
                    ((InputMethodManager) ((Context) editTextRememberCursor.c.get()).getSystemService("input_method")).showSoftInput(editTextRememberCursor, 0)
                    return true
                }
            }
            this.f1297a = false
        }
        return false
    }
}
