package com.gmail.heagoo.apkeditor.ac

import android.content.Context
import android.util.AttributeSet
import android.widget.AutoCompleteTextView

class EditTextWithTip extends AutoCompleteTextView {
    constructor(Context context) {
        super(context)
    }

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
    }

    @Override // android.widget.AutoCompleteTextView
    fun enoughToFilter() {
        return true
    }
}
