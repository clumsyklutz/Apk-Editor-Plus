package com.gmail.heagoo.apkeditor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.util.ArrayList
import java.util.List

final class er extends BaseAdapter {

    /* renamed from: a, reason: collision with root package name */
    private Context f1040a

    /* renamed from: b, reason: collision with root package name */
    private List f1041b = ArrayList()
    private List c = ArrayList()

    er(Context context, String str) {
        this.f1040a = context
        if (gy.c(str) || gy.e(str)) {
            this.c.add(new com.gmail.heagoo.common.o(Integer.valueOf(R.drawable.ic_www), Integer.valueOf(R.string.html)))
            this.f1041b.add(0)
        }
        this.c.add(new com.gmail.heagoo.common.o(Integer.valueOf(R.drawable.ic_colorize), Integer.valueOf(R.string.colorpad)))
        this.f1041b.add(1)
        if (gy.c(str) || gy.e(str)) {
            this.c.add(new com.gmail.heagoo.common.o(Integer.valueOf(R.drawable.ic_btn_4), Integer.valueOf(R.string.templates)))
            this.f1041b.add(6)
        }
        this.c.add(new com.gmail.heagoo.common.o(Integer.valueOf(R.drawable.ic_delete_lines), Integer.valueOf(R.string.delete_lines)))
        this.f1041b.add(2)
        if (gy.c(str)) {
            this.c.add(new com.gmail.heagoo.common.o(Integer.valueOf(R.drawable.ic_eye), Integer.valueOf(R.string.comment_lines)))
            this.f1041b.add(5)
            this.c.add(new com.gmail.heagoo.common.o(Integer.valueOf(R.drawable.ic_java), Integer.valueOf(R.string.java_code)))
            this.f1041b.add(7)
        }
        this.c.add(new com.gmail.heagoo.common.o(Integer.valueOf(R.drawable.ic_btn_9), Integer.valueOf(R.string.settings)))
        this.f1041b.add(3)
        this.c.add(new com.gmail.heagoo.common.o(Integer.valueOf(R.drawable.ic_help), Integer.valueOf(R.string.help)))
        this.f1041b.add(4)
    }

    public final Int a() {
        return this.c.size()
    }

    public final Int a(Int i) {
        if (i < this.f1041b.size()) {
            return ((Integer) this.f1041b.get(i)).intValue()
        }
        return -1
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.c.size()
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.c.get(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        es esVar
        if (view == null) {
            view = LayoutInflater.from(this.f1040a).inflate(R.layout.item_more_option, (ViewGroup) null)
            es esVar2 = es((Byte) 0)
            esVar2.f1042a = (ImageView) view.findViewById(R.id.menu_icon)
            esVar2.f1043b = (TextView) view.findViewById(R.id.menu_title)
            view.setTag(esVar2)
            esVar = esVar2
        } else {
            esVar = (es) view.getTag()
        }
        com.gmail.heagoo.common.o oVar = (com.gmail.heagoo.common.o) this.c.get(i)
        if (((Integer) oVar.f1461a).intValue() > 0) {
            esVar.f1042a.setImageResource(((Integer) oVar.f1461a).intValue())
        } else {
            esVar.f1042a.setImageBitmap(null)
        }
        esVar.f1043b.setText(((Integer) oVar.f1462b).intValue())
        return view
    }
}
