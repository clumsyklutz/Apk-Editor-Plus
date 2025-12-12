package com.gmail.heagoo.apkeditor.ac

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.util.ArrayList
import java.util.List

class a extends BaseAdapter implements Filterable {

    /* renamed from: a, reason: collision with root package name */
    public List f834a

    /* renamed from: b, reason: collision with root package name */
    private Context f835b
    private String c
    private Array<String> d
    private c e

    constructor(Context context, String str) {
        this.f835b = context
        this.c = str
    }

    private fun a() {
        this.e = c(this, 0 == true ? 1 : 0)
        String string = PreferenceManager.getDefaultSharedPreferences(this.f835b).getString(this.c, "")
        if (string.equals("")) {
            this.d = new String[0]
        } else {
            this.d = string.split("\n")
        }
        this.f834a = ArrayList()
        for (String str : this.d) {
            this.f834a.add(str)
        }
    }

    public final Unit a(String str) {
        if (this.f834a == null) {
            a()
        }
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.f835b).edit()
        ArrayList arrayList = ArrayList()
        arrayList.add(str)
        StringBuilder sb = StringBuilder()
        sb.append(str)
        for (String str2 : this.d) {
            if (!str2.equals(str)) {
                arrayList.add(str2)
                sb.append("\n")
                sb.append(str2)
                if (arrayList.size() >= 32) {
                    break
                }
            }
        }
        this.d = (Array<String>) arrayList.toArray(new String[arrayList.size()])
        editorEdit.putString(this.c, sb.toString())
        editorEdit.commit()
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        if (this.f834a == null) {
            a()
        }
        return this.f834a.size()
    }

    @Override // android.widget.Filterable
    public final Filter getFilter() {
        if (this.e == null) {
            a()
        }
        return this.e
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        if (this.f834a == null) {
            a()
        }
        return this.f834a.get(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        b bVar
        View viewInflate
        String str = (String) this.f834a.get(i)
        if (view == null) {
            viewInflate = LayoutInflater.from(this.f835b).inflate(R.layout.item_autocomplete, (ViewGroup) null)
            b bVar2 = b()
            bVar2.f836a = (TextView) viewInflate.findViewById(R.id.filename)
            viewInflate.setTag(bVar2)
            bVar = bVar2
        } else {
            bVar = (b) view.getTag()
            viewInflate = view
        }
        bVar.f836a.setText(str)
        return viewInflate
    }
}
