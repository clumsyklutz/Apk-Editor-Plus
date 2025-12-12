package com.gmail.heagoo.apkeditor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.List

final class cw extends BaseAdapter {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f947a

    /* renamed from: b, reason: collision with root package name */
    private List f948b
    private List c = ArrayList()

    cw(Context context, List list) {
        this.f947a = WeakReference(context)
        this.f948b = list
    }

    public final Unit a(List list) {
        this.c = list
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.f948b.size()
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.f948b.get(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        cx cxVar
        if (view == null) {
            view = LayoutInflater.from((Context) this.f947a.get()).inflate(R.layout.popup_item_big, (ViewGroup) null)
            cxVar = cx()
            view.setTag(cxVar)
            cxVar.f950b = (TextView) view.findViewById(R.id.groupItem)
            cxVar.f949a = (ImageView) view.findViewById(R.id.checkImage)
        } else {
            cxVar = (cx) view.getTag()
        }
        cxVar.f950b.setText((CharSequence) this.f948b.get(i))
        cxVar.f949a.setVisibility(this.c.contains(Integer.valueOf(i)) ? 0 : 4)
        return view
    }
}
