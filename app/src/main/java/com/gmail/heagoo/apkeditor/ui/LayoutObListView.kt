package com.gmail.heagoo.apkeditor.ui

import android.content.Context
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.widget.ListView
import com.gmail.heagoo.neweditor.s

class LayoutObListView extends ListView implements s {

    /* renamed from: a, reason: collision with root package name */
    private Boolean f1293a

    /* renamed from: b, reason: collision with root package name */
    private Boolean f1294b
    private Int c
    private Int d
    private Int e

    constructor(Context context) {
        super(context)
        this.f1293a = false
        this.c = -1
    }

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
        this.f1293a = false
        this.c = -1
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        this.f1293a = false
        this.c = -1
    }

    @RequiresApi(21)
    constructor(Context context, AttributeSet attributeSet, Int i, Int i2) {
        super(context, attributeSet, i, i2)
        this.f1293a = false
        this.c = -1
    }

    public final Unit a(Int i, Int i2, Int i3) {
        if (i >= getFirstVisiblePosition() && i <= getLastVisiblePosition()) {
            this.c = i
            this.d = i2
            this.e = i3
        }
    }

    @Override // com.gmail.heagoo.neweditor.s
    public final Unit a(Boolean z) {
        this.f1294b = z
    }

    public final Boolean a() {
        return this.f1293a
    }

    public final Boolean b() {
        return this.f1294b
    }

    public final Unit c() {
        this.c = 0
        this.d = 0
        this.e = 0
    }

    public final Int d() {
        return this.c
    }

    public final Int e() {
        return this.d
    }

    public final Int f() {
        return this.e
    }

    @Override // android.widget.ListView, android.widget.AbsListView
    protected fun layoutChildren() {
        this.f1293a = true
        super.layoutChildren()
        this.f1293a = false
    }
}
