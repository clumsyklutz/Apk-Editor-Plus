package com.gmail.heagoo.appdm

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference
import java.util.List

class e extends BaseAdapter {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1378a

    /* renamed from: b, reason: collision with root package name */
    private List f1379b
    private Boolean c

    constructor(Activity activity, List list, Boolean z) {
        this.f1378a = WeakReference(activity)
        this.f1379b = list
        this.c = z
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.f1379b.size()
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.f1379b.get(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        f fVar
        g gVar = (g) getItem(i)
        if (gVar == null) {
            return null
        }
        if (view == null) {
            view = LayoutInflater.from((Context) this.f1378a.get()).inflate(R.layout.appdm_item_basicinfo, (ViewGroup) null)
            f fVar2 = f()
            fVar2.f1380a = (TextView) view.findViewById(R.id.tv_title)
            fVar2.f1381b = (TextView) view.findViewById(R.id.tv_value)
            fVar2.c = (Button) view.findViewById(R.id.btn_operation)
            view.setTag(fVar2)
            fVar = fVar2
        } else {
            fVar = (f) view.getTag()
        }
        fVar.f1380a.setText(gVar.f1382a)
        fVar.f1381b.setText(gVar.f1383b)
        if (gVar.d == null) {
            fVar.c.setVisibility(8)
            return view
        }
        fVar.c.setVisibility(0)
        fVar.c.setText(gVar.c)
        fVar.c.setOnClickListener(gVar.d)
        return view
    }
}
