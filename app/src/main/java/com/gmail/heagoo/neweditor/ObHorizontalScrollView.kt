package com.gmail.heagoo.neweditor

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView

class ObHorizontalScrollView extends HorizontalScrollView {
    constructor(Context context) {
        super(context)
    }

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    protected fun measureChildWithMargins(View view, Int i, Int i2, Int i3, Int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams()
        ((ObEditText) view).b(getChildMeasureSpec(i, marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width), getChildMeasureSpec(i3, marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height))
    }
}
