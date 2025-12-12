package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.LruCache
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.List
import java.util.Locale

class bl extends BaseAdapter {

    /* renamed from: b, reason: collision with root package name */
    private Context f898b
    private PackageManager c
    private Int e

    /* renamed from: a, reason: collision with root package name */
    private List f897a = ArrayList()
    private LruCache f = bm(this, 32)

    constructor(Context context) {
        this.f898b = context
        this.c = context.getPackageManager()
    }

    public final Unit a(List list, String str) throws Resources.NotFoundException {
        Array<String> stringArray = this.f898b.getResources().getStringArray(R.array.order_value)
        if (str.equals(stringArray[0]) || !str.equals(stringArray[1])) {
            this.e = bq.f901a
        } else {
            this.e = bq.f902b
        }
        Locale locale = Locale.getDefault()
        Comparator boVar = null
        switch (bp.f900a[this.e - 1]) {
            case 1:
                boVar = bn(this, locale)
                break
            case 2:
                boVar = bo(this)
                break
        }
        Collections.sort(list, boVar)
        synchronized (this.f897a) {
            this.f897a.clear()
            this.f897a.addAll(list)
        }
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        Int size
        synchronized (this.f897a) {
            size = this.f897a.size()
        }
        return size
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        Object obj
        synchronized (this.f897a) {
            obj = this.f897a.get(i)
        }
        return obj
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    @SuppressLint({"InflateParams"})
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        br brVar
        View view2
        bk bkVar = (bk) getItem(i)
        if (bkVar == null) {
            return null
        }
        if (view == null) {
            View viewInflate = LayoutInflater.from(this.f898b).inflate(R.layout.item_applist, (ViewGroup) null)
            br brVar2 = br()
            brVar2.f903a = (ImageView) viewInflate.findViewById(R.id.app_icon)
            brVar2.c = (TextView) viewInflate.findViewById(R.id.app_name)
            brVar2.f904b = (TextView) viewInflate.findViewById(R.id.app_desc1)
            viewInflate.findViewById(R.id.app_desc2)
            viewInflate.setTag(brVar2)
            brVar = brVar2
            view2 = viewInflate
        } else {
            brVar = (br) view.getTag()
            view2 = view
        }
        try {
            brVar.c.setText(bkVar.c)
            brVar.f904b.setText(bkVar.f896b)
            Drawable drawableLoadIcon = (Drawable) this.f.get(bkVar.f896b)
            if (drawableLoadIcon == null) {
                drawableLoadIcon = bkVar.f895a.loadIcon(this.c)
                this.f.put(bkVar.f896b, drawableLoadIcon)
            }
            brVar.f903a.setImageDrawable(drawableLoadIcon)
        } catch (Throwable th) {
            th.printStackTrace()
        }
        return view2
    }
}
