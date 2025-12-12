package com.gmail.heagoo.apkeditor.ui

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference

class EditTextRememberCursor extends AppCompatEditText {

    /* renamed from: a, reason: collision with root package name */
    private Int f1291a

    /* renamed from: b, reason: collision with root package name */
    private Int f1292b
    private WeakReference c
    private WeakReference d
    private WeakReference e

    constructor(Context context) {
        super(context)
    }

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
    }

    public final Unit a() {
        setOnTouchListener(c(this))
    }

    public final Unit a(Context context, WeakReference weakReference) {
        this.c = WeakReference(context)
        this.d = weakReference
    }

    public final Unit a(WeakReference weakReference) {
        this.e = weakReference
    }

    @Override // android.widget.TextView, android.view.View
    fun onKeyPreIme(Int i, KeyEvent keyEvent) {
        LayoutObListView layoutObListView
        if (i != 4 || (layoutObListView = (LayoutObListView) this.d.get()) == null || !layoutObListView.b()) {
            return false
        }
        ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(getWindowToken(), 0)
        return true
    }

    @Override // android.widget.TextView
    protected fun onSelectionChanged(Int i, Int i2) {
        com.gmail.heagoo.a aVar
        if (i != i2 || this.d == null || this.d.get() == null || !((LayoutObListView) this.d.get()).a()) {
            this.f1291a = i
            this.f1292b = i2
            Integer num = (Integer) getTag(R.id.text)
            if (num != null) {
                if (this.e != null && (aVar = (com.gmail.heagoo.a) this.e.get()) != null) {
                    String strSubstring = ""
                    if (i < i2) {
                        try {
                            strSubstring = getText().toString().substring(i, i2)
                        } catch (Exception e) {
                            e.printStackTrace()
                        }
                    }
                    num.intValue()
                    aVar.a(i, i2, strSubstring)
                }
                LayoutObListView layoutObListView = (LayoutObListView) this.d.get()
                if (layoutObListView != null) {
                    layoutObListView.a(num.intValue(), i, i2)
                }
            }
        }
    }
}
