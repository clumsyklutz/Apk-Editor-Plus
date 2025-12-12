package com.b.a

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.List

class h extends BaseAdapter {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f692a

    /* renamed from: b, reason: collision with root package name */
    private List f693b = ArrayList()

    constructor(Activity activity, List list) {
        this.f692a = WeakReference(activity)
        this.f693b.addAll(list)
    }

    public final Unit a(ArrayList arrayList) {
        this.f693b.clear()
        this.f693b.addAll(arrayList)
        notifyDataSetChanged()
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.f693b.size()
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.f693b.get(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    @SuppressLint({"InflateParams"})
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        i iVar
        g gVar = (g) this.f693b.get(i)
        if (view == null) {
            view = LayoutInflater.from((Context) this.f692a.get()).inflate(R.layout.item_color_value, (ViewGroup) null)
            i iVar2 = i((Byte) 0)
            iVar2.f694a = view.findViewById(R.id.color_view)
            iVar2.f695b = (TextView) view.findViewById(R.id.tv_name)
            iVar2.c = (TextView) view.findViewById(R.id.tv_value)
            view.setTag(iVar2)
            iVar = iVar2
        } else {
            iVar = (i) view.getTag()
        }
        try {
            iVar.f695b.setText(gVar.f690a)
            iVar.c.setText(gVar.f691b)
            if (gVar.d) {
                iVar.f694a.setBackgroundColor(gVar.c)
            } else {
                iVar.f694a.setBackgroundColor(-1)
            }
        } catch (Throwable th) {
            th.printStackTrace()
        }
        return view
    }
}
