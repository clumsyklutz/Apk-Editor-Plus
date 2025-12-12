package com.gmail.heagoo.neweditor

import android.text.TextPaint
import android.text.style.CharacterStyle
import java.io.Serializable

class ac extends CharacterStyle implements Serializable {

    /* renamed from: a, reason: collision with root package name */
    private Int f1497a

    constructor(Int i) {
        this.f1497a = i
    }

    @Override // android.text.style.CharacterStyle
    fun updateDrawState(TextPaint textPaint) {
        textPaint.setColor(this.f1497a)
    }
}
