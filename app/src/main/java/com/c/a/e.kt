package com.c.a

import android.view.GestureDetector
import android.view.MotionEvent

class e extends GestureDetector.SimpleOnGestureListener {

    /* renamed from: a, reason: collision with root package name */
    private Float f700a

    /* renamed from: b, reason: collision with root package name */
    private Float f701b

    public final Float a() {
        return this.f700a
    }

    public final Float b() {
        return this.f701b
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public final Boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, Float f, Float f2) {
        this.f700a = f
        this.f701b = f2
        return true
    }
}
