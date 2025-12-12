package com.gmail.heagoo.apkeditor

import android.content.Context
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.gmail.heagoo.InterfaceA
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.apkeditor.ui.EditTextRememberCursor
import com.gmail.heagoo.apkeditor.ui.LayoutObListView
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.Iterator
import java.util.List

class hs extends BaseAdapter {

    /* renamed from: a, reason: collision with root package name */
    private Context f1160a
    private WeakReference c
    private Int d
    private Float e
    private com.gmail.heagoo.neweditor.e h
    private TextWatcher i
    private WeakReference j
    private Int f = -1
    private Boolean g = true
    private hu k = hu(this)

    /* renamed from: b, reason: collision with root package name */
    private List f1161b = ArrayList()

    constructor(Context context, LayoutObListView layoutObListView) {
        this.f1160a = context
        this.c = WeakReference(layoutObListView)
    }

    private fun b(Int i) {
        Int i2 = 1
        while (i >= 10) {
            i /= 10
            i2++
        }
        return i2
    }

    public final CharSequence a() {
        StringBuilder sb = StringBuilder()
        if (this.f1161b.size() > 0) {
            sb.append((String) this.f1161b.get(0))
            Int i = 1
            while (true) {
                Int i2 = i
                if (i2 >= this.f1161b.size()) {
                    break
                }
                sb.append("\n")
                sb.append((String) this.f1161b.get(i2))
                i = i2 + 1
            }
        }
        return sb.toString()
    }

    public final Unit a(Int i) {
        this.f = i
    }

    public final Unit a(Int i, Float f) {
        this.d = i
        this.e = f
    }

    public final Unit a(TextWatcher textWatcher) {
        this.i = textWatcher
    }

    public final Unit a(a aVar) {
        this.j = WeakReference(aVar)
    }

    public final Unit a(com.gmail.heagoo.neweditor.e eVar) {
        this.h = eVar
    }

    public final Unit a(List list) {
        this.f1161b.clear()
        Iterator it = list.iterator()
        while (it.hasNext()) {
            this.f1161b.add((String) it.next())
        }
        ((LayoutObListView) this.c.get()).c()
    }

    public final Unit a(Boolean z) {
        this.g = z
    }

    public final List b() {
        return this.f1161b
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.f1161b.size()
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.f1161b.get(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        hv hvVar
        String string
        String str
        if (view == null) {
            view = LayoutInflater.from(this.f1160a).inflate(R.layout.item_lined_text, (ViewGroup) null)
            hvVar = hv()
            hvVar.f1165a = (TextView) view.findViewById(R.id.line_num)
            hvVar.f1166b = (EditTextRememberCursor) view.findViewById(R.id.text)
            view.setTag(hvVar)
        } else {
            hvVar = (hv) view.getTag()
        }
        Object tag = hvVar.f1166b.getTag()
        if (tag != null) {
            hvVar.f1166b.removeTextChangedListener((ht) tag)
        }
        ht htVar = ht(this, i)
        hvVar.f1166b.addTextChangedListener(htVar)
        hvVar.f1166b.setTag(htVar)
        hvVar.f1166b.a(this.j)
        Int i2 = i + 1
        Int iB = b(this.f1161b.size())
        Int iB2 = b(i2)
        if (iB2 < iB) {
            StringBuilder sb = StringBuilder()
            switch (iB - iB2) {
                case 1:
                    str = "0"
                    break
                case 2:
                    str = "00"
                    break
                case 3:
                    str = "000"
                    break
                case 4:
                    str = "0000"
                    break
                case 5:
                    str = "00000"
                    break
                case 6:
                    str = "000000"
                    break
                default:
                    str = "0000000"
                    break
            }
            string = sb.append(str).append(i2).toString()
        } else {
            string = StringBuilder().append(i2).toString()
        }
        if (this.g) {
            hvVar.f1165a.setVisibility(0)
            hvVar.f1165a.setText(string)
            hvVar.f1165a.setTextColor(this.f)
        } else {
            hvVar.f1165a.setVisibility(8)
        }
        hvVar.f1166b.setTextColor(this.f)
        hvVar.f1166b.setText((CharSequence) this.f1161b.get(i))
        if (this.e > 0.0f) {
            hvVar.f1165a.setTextSize(this.d, this.e)
            hvVar.f1166b.setTextSize(this.d, this.e)
        }
        if (this.h != null) {
            this.h.a(hvVar.f1166b)
        }
        hvVar.f1166b.setTag(R.id.text, Integer.valueOf(i))
        hvVar.f1166b.a(this.f1160a, this.c)
        hvVar.f1166b.a()
        LayoutObListView layoutObListView = (LayoutObListView) this.c.get()
        if (layoutObListView != null && layoutObListView.d() == i) {
            hvVar.f1166b.setSelection(layoutObListView.e(), layoutObListView.f())
            hvVar.f1166b.requestFocus()
            this.k.f1164a = hvVar.f1166b
            this.k.removeMessages(0)
            this.k.sendEmptyMessageDelayed(0, 100L)
        }
        return view
    }
}
