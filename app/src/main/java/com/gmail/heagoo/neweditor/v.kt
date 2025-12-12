package com.gmail.heagoo.neweditor

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.BaseInputConnection

final class v extends BaseInputConnection {
    v(ObEditText obEditText, View view, Boolean z) {
        super(view, false)
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public final Boolean deleteSurroundingText(Int i, Int i2) {
        return (i == 1 && i2 == 0) ? super.sendKeyEvent(KeyEvent(0, 67)) && super.sendKeyEvent(KeyEvent(1, 67)) : super.deleteSurroundingText(i, i2)
    }
}
