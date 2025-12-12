package com.gmail.heagoo.apkeditor

import android.text.Layout
import android.view.MotionEvent
import android.view.View
import android.widget.EditText

final class ig implements View.OnTouchListener {

    /* renamed from: a, reason: collision with root package name */
    private Boolean f1181a = false

    /* renamed from: b, reason: collision with root package name */
    private Float f1182b
    private Float c
    private Long d
    private /* synthetic */ TextEditNormalActivity e

    ig(TextEditNormalActivity textEditNormalActivity) {
        this.e = textEditNormalActivity
    }

    @Override // android.view.View.OnTouchListener
    public final Boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.f1181a = false
            Int selectionStart = this.e.l.getSelectionStart()
            Int selectionEnd = this.e.l.getSelectionEnd()
            if (selectionStart != selectionEnd) {
                Layout layout = ((EditText) view).getLayout()
                Int offsetForHorizontal = layout.getOffsetForHorizontal(layout.getLineForVertical((Int) (motionEvent.getY() + this.e.l.getScrollY())), motionEvent.getX() + this.e.l.getScrollX())
                if (offsetForHorizontal >= selectionStart && offsetForHorizontal < selectionEnd && !this.e.c()) {
                    this.f1181a = true
                    this.f1182b = motionEvent.getX()
                    this.c = motionEvent.getY()
                    this.d = System.currentTimeMillis()
                    return true
                }
            }
        } else if (motionEvent.getAction() != 2 && motionEvent.getAction() == 1) {
            if (this.f1181a) {
                Float x = motionEvent.getX()
                Float y = motionEvent.getY()
                Long jCurrentTimeMillis = System.currentTimeMillis()
                if (Math.abs(x - this.f1182b) < 32.0f && Math.abs(y - this.c) < 32.0f && jCurrentTimeMillis - this.d < 500) {
                    TextEditNormalActivity.r(this.e)
                    return true
                }
            }
            this.f1181a = false
        }
        return false
    }
}
