package com.gmail.heagoo.neweditor

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

class ObScrollView extends ScrollView {

    /* renamed from: a, reason: collision with root package name */
    private y f1492a

    constructor(Context context) {
        super(context)
        this.f1492a = null
    }

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
        this.f1492a = null
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        this.f1492a = null
    }

    public final Unit a(y yVar) {
        this.f1492a = yVar
    }

    @Override // android.view.View
    protected fun onScrollChanged(Int i, Int i2, Int i3, Int i4) {
        super.onScrollChanged(i, i2, i3, i4)
        if (this.f1492a != null) {
            this.f1492a.a()
        }
    }
}
