package com.gmail.heagoo.apkeditor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.util.List

class gm extends BaseAdapter {

    /* renamed from: a, reason: collision with root package name */
    private Context f1110a

    /* renamed from: b, reason: collision with root package name */
    private List f1111b

    constructor(Context context, List list) {
        this.f1110a = context
        this.f1111b = list
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.f1111b.size()
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.f1111b.get(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        gn gnVar
        if (view == null) {
            view = LayoutInflater.from(this.f1110a).inflate(R.layout.popup_item_small, (ViewGroup) null)
            gn gnVar2 = gn()
            gnVar2.f1112a = (TextView) view.findViewById(R.id.groupItem)
            view.setTag(gnVar2)
            gnVar = gnVar2
        } else {
            gnVar = (gn) view.getTag()
        }
        gnVar.f1112a.setText(((go) this.f1111b.get(i)).f1113a)
        return view
    }
}
