package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.content.Context
import android.util.LruCache
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.common.a
import java.util.ArrayList
import java.util.List

class bc extends BaseAdapter {

    /* renamed from: b, reason: collision with root package name */
    private Context f885b

    /* renamed from: a, reason: collision with root package name */
    List f884a = ArrayList()
    private LruCache d = bd(this, 64)

    constructor(Context context) {
        this.f885b = context
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.f884a.size()
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.f884a.get(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    @SuppressLint({"InflateParams"})
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        be beVar
        String str = (String) this.f884a.get(i)
        if (view == null) {
            view = LayoutInflater.from(this.f885b).inflate(R.layout.item_file_founded, (ViewGroup) null)
            beVar = be((Byte) 0)
            beVar.f886a = (ImageView) view.findViewById(R.id.file_icon)
            beVar.f887b = (TextView) view.findViewById(R.id.filename)
            beVar.c = (TextView) view.findViewById(R.id.detail1)
            view.setTag(beVar)
        } else {
            beVar = (be) view.getTag()
        }
        com.gmail.heagoo.common.b bVar = (com.gmail.heagoo.common.b) this.d.get(str)
        if (bVar == null) {
            try {
                a()
                bVar = a.a(this.f885b, str)
            } catch (Throwable th) {
            }
            if (bVar == null) {
                bVar = new com.gmail.heagoo.common.b()
                bVar.c = this.f885b.getResources().getDrawable(R.drawable.apk_icon)
            }
        }
        this.d.put(str, bVar)
        beVar.f886a.setImageDrawable(bVar.c)
        if (bVar.f1447a != null) {
            beVar.f887b.setText(bVar.f1447a)
            beVar.c.setText(str)
            beVar.c.setVisibility(0)
        } else {
            beVar.f887b.setText(str)
            beVar.c.setVisibility(8)
        }
        return view
    }
}
