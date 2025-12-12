package com.c.a

import android.view.MotionEvent
import android.view.View

final class g implements View.OnTouchListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ f f704a

    g(f fVar) {
        this.f704a = fVar
    }

    @Override // android.view.View.OnTouchListener
    public final Boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.f704a.F != null) {
            this.f704a.F.onTouch(view, motionEvent)
        }
        return this.f704a.E.onTouch(view, motionEvent)
    }
}
