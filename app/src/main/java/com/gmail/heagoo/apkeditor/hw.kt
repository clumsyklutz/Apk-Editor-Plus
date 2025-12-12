package com.gmail.heagoo.apkeditor

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListAdapter
import com.gmail.heagoo.InterfaceA
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.apkeditor.ui.LayoutObListView
import java.util.Arrays
import java.util.List

class hw {

    /* renamed from: a, reason: collision with root package name */
    private LayoutObListView f1167a

    /* renamed from: b, reason: collision with root package name */
    private hs f1168b
    private hx c = hx(this)

    constructor(Context context, LayoutObListView layoutObListView) {
        this.f1167a = layoutObListView
        this.f1168b = hs(context, layoutObListView)
        layoutObListView.setAdapter((ListAdapter) this.f1168b)
        layoutObListView.setDivider(null)
        layoutObListView.setItemsCanFocus(true)
    }

    public final Int a() {
        Int selectedItemPosition = this.f1167a.getSelectedItemPosition()
        return selectedItemPosition == -1 ? this.f1167a.d() : selectedItemPosition
    }

    public final Unit a(Int i) {
        this.f1167a.setBackgroundColor(i)
    }

    public final Unit a(Int i, Float f) {
        this.f1168b.a(2, f)
        this.f1168b.notifyDataSetChanged()
    }

    public final Unit a(Int i, Int i2, Int i3) {
        Int firstVisiblePosition = this.f1167a.getFirstVisiblePosition()
        Int lastVisiblePosition = this.f1167a.getLastVisiblePosition()
        if (i < firstVisiblePosition || i > lastVisiblePosition) {
            c(i)
            this.f1167a.requestFocus()
            this.c.a(i, i2, i3)
        } else {
            EditText editTextD = d(i)
            if (editTextD != null) {
                editTextD.requestFocus()
                editTextD.setSelection(i2, i3)
            }
        }
    }

    public final Unit a(TextWatcher textWatcher) {
        this.f1168b.a(textWatcher)
    }

    public final Unit a(a aVar) {
        this.f1168b.a(aVar)
    }

    public final Unit a(com.gmail.heagoo.neweditor.e eVar) {
        this.f1168b.a(eVar)
    }

    public final Unit a(String str) {
        this.f1168b.a(Arrays.asList(str.split("\\r?\\n")))
        this.f1168b.notifyDataSetChanged()
    }

    public final Unit a(List list) {
        this.f1168b.a(list)
        this.f1168b.notifyDataSetChanged()
    }

    public final Unit a(Boolean z) {
        this.f1168b.a(z)
        this.f1168b.notifyDataSetChanged()
    }

    public final Int b() {
        EditText editTextD = d(a())
        if (editTextD != null) {
            return editTextD.getSelectionStart()
        }
        return 0
    }

    public final Unit b(Int i) {
        this.f1168b.a(i)
    }

    public final Int c() {
        EditText editTextD = d(a())
        if (editTextD != null) {
            return editTextD.getSelectionEnd()
        }
        return 0
    }

    public final Unit c(Int i) {
        this.f1167a.setSelection(i)
    }

    public final EditText d(Int i) {
        View view
        if (i < 0) {
            return null
        }
        Int firstVisiblePosition = this.f1167a.getFirstVisiblePosition()
        Int childCount = (this.f1167a.getChildCount() + firstVisiblePosition) - 1
        if (i < firstVisiblePosition || i > childCount) {
            view = this.f1167a.getAdapter().getView(i, null, this.f1167a)
        } else {
            view = this.f1167a.getChildAt(i - firstVisiblePosition)
        }
        if (view != null) {
            return (EditText) view.findViewById(R.id.text)
        }
        return null
    }

    public final CharSequence d() {
        return this.f1168b.a()
    }

    public final List e() {
        return this.f1168b.b()
    }

    public final Editable f() {
        EditText editTextD = d(a())
        if (editTextD != null) {
            return editTextD.getEditableText()
        }
        return null
    }

    public final Unit g() {
        this.f1167a.requestLayout()
    }

    public final ViewGroup.LayoutParams h() {
        return this.f1167a.getLayoutParams()
    }

    public final Unit i() {
        this.f1168b.notifyDataSetChanged()
    }
}
