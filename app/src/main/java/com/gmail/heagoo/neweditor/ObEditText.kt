package com.gmail.heagoo.neweditor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.preference.PreferenceManager
import androidx.core.view.InputDeviceCompat
import android.text.DynamicLayout
import android.text.style.BackgroundColorSpan
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.lang.ref.WeakReference

class ObEditText extends EditText implements s {

    /* renamed from: a, reason: collision with root package name */
    private Boolean f1490a

    /* renamed from: b, reason: collision with root package name */
    private Int f1491b
    private Boolean c
    private Int d
    private Boolean e
    private Boolean f
    private WeakReference g
    private Boolean h

    constructor(Context context) {
        super(context)
        BackgroundColorSpan(InputDeviceCompat.SOURCE_ANY)
        this.f1490a = true
        Rect()
        BackgroundColorSpan(InputDeviceCompat.SOURCE_ANY)
        this.f = false
        this.g = null
    }

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
        BackgroundColorSpan(InputDeviceCompat.SOURCE_ANY)
        this.f1490a = true
        Rect()
        BackgroundColorSpan(InputDeviceCompat.SOURCE_ANY)
        this.f = false
        this.g = null
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        BackgroundColorSpan(InputDeviceCompat.SOURCE_ANY)
        this.f1490a = true
        Rect()
        BackgroundColorSpan(InputDeviceCompat.SOURCE_ANY)
        this.f = false
        this.g = null
    }

    public final Unit a(Int i) {
        BackgroundColorSpan(i)
        BackgroundColorSpan(i)
    }

    public final Unit a(Int i, Int i2) {
        Boolean z = this.f
        this.f = false
        try {
            setSelection(i, i2)
        } catch (Exception e) {
        }
        this.f = z
    }

    public final Unit a(ad adVar) {
        this.g = WeakReference(adVar)
    }

    @Override // com.gmail.heagoo.neweditor.s
    public final Unit a(Boolean z) {
        this.h = z
    }

    public final Int b(Int i) {
        if (getLayout() != null) {
            return ((DynamicLayout) getLayout()).getLineForOffset(i)
        }
        return 0
    }

    @SuppressLint({"WrongCall"})
    public final Unit b(Int i, Int i2) {
        onMeasure(i, i2)
    }

    public final Unit b(Boolean z) {
        this.e = z
    }

    @Override // android.widget.TextView, android.view.View
    fun onCheckIsTextEditor() {
        return this.f1490a
    }

    @Override // android.widget.TextView, android.view.View
    fun onCreateInputConnection(EditorInfo editorInfo) {
        if (!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("rawKeys", false)) {
            return super.onCreateInputConnection(editorInfo)
        }
        v vVar = v(this, this, false)
        editorInfo.actionLabel = null
        editorInfo.inputType = 0
        editorInfo.imeOptions = 1342177280
        return vVar
    }

    @Override // android.widget.TextView, android.view.View
    fun onKeyPreIme(Int i, KeyEvent keyEvent) {
        if (i != 4 || !this.h) {
            return false
        }
        ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(getWindowToken(), 0)
        return true
    }

    @Override // android.widget.TextView, android.view.View
    protected fun onMeasure(Int i, Int i2) {
        if (this.e) {
            super.onMeasure(i, i2)
            return
        }
        Float fMeasureText = getPaint().measureText("X")
        Int lineHeight = getLineHeight()
        Array<String> strArrSplit = getText().toString().split("\\n")
        Int i3 = this.f1491b
        this.f1491b = 0
        this.d = strArrSplit.length
        for (String str : strArrSplit) {
            this.f1491b = Math.max(this.f1491b, str.length())
        }
        if (this.d == 0) {
            this.d = 1
        }
        this.c = i3 != this.f1491b
        setMeasuredDimension(Math.max(((Int) ((String.valueOf(this.d).length() + this.f1491b) * fMeasureText)) + 10, View.MeasureSpec.getSize(i)), Math.max((this.d * lineHeight) + 10, View.MeasureSpec.getSize(i2)))
        if (this.c) {
            setPaintFlags(getPaintFlags() + 1)
            setPaintFlags(getPaintFlags() - 1)
        }
    }

    @Override // android.widget.TextView
    protected fun onSelectionChanged(Int i, Int i2) {
        ad adVar
        super.onSelectionChanged(i, i2)
        if (this.g == null || (adVar = (ad) this.g.get()) == null) {
            return
        }
        adVar.b(i, i2)
    }

    @Override // android.widget.TextView
    protected fun onTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
        super.onTextChanged(charSequence, i, i2, i3)
        if (getSelectionStart() == getSelectionEnd()) {
            getSelectionStart()
        }
    }

    @Override // android.widget.TextView
    fun setWidth(Int i) {
        if (i != 0) {
            super.setWidth(i)
        }
    }
}
