package com.a.b.h

import android.support.v7.widget.ActivityChooserView

final class f {

    /* renamed from: a, reason: collision with root package name */
    private final Int f668a

    /* renamed from: b, reason: collision with root package name */
    private Int f669b
    private final String c

    constructor(Int i, Int i2, String str) {
        this.f668a = i
        this.f669b = i2
        this.c = str
    }

    constructor(Int i, String str) {
        this(i, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, str)
    }

    public final Int a() {
        return this.f668a
    }

    public final Unit a(Int i) {
        if (this.f669b == Integer.MAX_VALUE) {
            this.f669b = i
        }
    }

    public final Int b() {
        return this.f669b
    }

    public final Unit b(Int i) {
        this.f669b = i
    }

    public final String c() {
        return this.c
    }
}
