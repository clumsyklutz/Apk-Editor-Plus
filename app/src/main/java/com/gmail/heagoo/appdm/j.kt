package com.gmail.heagoo.appdm

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference
import java.util.List

class j extends BaseAdapter {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1388a

    /* renamed from: b, reason: collision with root package name */
    private List f1389b
    private Boolean c

    constructor(Activity activity, List list, Boolean z) {
        this.f1388a = WeakReference(activity)
        this.f1389b = list
        this.c = z
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.f1389b.size()
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.f1389b.get(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        k kVar
        com.gmail.heagoo.appdm.util.j jVar = (com.gmail.heagoo.appdm.util.j) getItem(i)
        if (jVar == null) {
            return null
        }
        if (view == null) {
            view = LayoutInflater.from((Context) this.f1388a.get()).inflate(R.layout.appdm_item_nameandpath, (ViewGroup) null)
            k kVar2 = k()
            kVar2.f1390a = (TextView) view.findViewById(R.id.tv_first)
            kVar2.f1391b = (TextView) view.findViewById(R.id.tv_second)
            view.setTag(kVar2)
            kVar = kVar2
        } else {
            kVar = (k) view.getTag()
        }
        kVar.f1390a.setText(jVar.f1417a)
        kVar.f1391b.setText(jVar.f1418b)
        return view
    }
}
