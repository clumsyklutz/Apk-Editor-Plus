package com.gmail.heagoo.apkeditor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.List

class fl implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1074a

    /* renamed from: b, reason: collision with root package name */
    private String f1075b
    private LinearLayout c
    private HorizontalScrollView d
    private List e = ArrayList()

    constructor(ApkInfoActivity apkInfoActivity, String str, LinearLayout linearLayout, HorizontalScrollView horizontalScrollView) {
        this.f1074a = WeakReference(apkInfoActivity)
        this.f1075b = str
        this.c = linearLayout
        this.d = horizontalScrollView
    }

    public final Unit a(String str) {
        if (str.endsWith("/")) {
            str = str.substring(0, str.length() - 1)
        }
        if (str.equals(this.f1075b)) {
            return
        }
        if (str.startsWith(this.f1075b + "/")) {
            for (String str2 : str.substring(this.f1075b.length() + 1).split("/")) {
                String str3 = this.f1075b + "/" + str2
                View viewInflate = LayoutInflater.from((Context) this.f1074a.get()).inflate(R.layout.item_navigation_dir, (ViewGroup) null)
                View viewFindViewById = viewInflate.findViewById(R.id.menu_dirtab)
                viewFindViewById.setTag(str3)
                viewFindViewById.setOnClickListener(this)
                ((TextView) viewInflate.findViewById(R.id.dirname)).setText(str3.substring(str3.lastIndexOf(47) + 1))
                this.c.addView(viewInflate)
                this.e.add(viewInflate)
                this.f1075b = str3
            }
        } else if (this.f1075b.startsWith(str + "/")) {
            for (Int length = this.f1075b.substring(str.length() + 1).split("/").length - 1; length >= 0; length--) {
                Int size = this.e.size() - 1
                View view = (View) this.e.get(size)
                this.e.remove(size)
                this.c.removeView(view)
            }
            this.f1075b = str
        }
        this.d.postDelayed(fm(this), 100L)
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        String str = (String) view.getTag()
        if (str == null) {
            return
        }
        String str2 = this.f1075b
        a(str)
        if (this.f1074a.get() != null) {
            ((ApkInfoActivity) this.f1074a.get()).a(str, str2.startsWith(StringBuilder().append(str).append("/").toString()) ? str2.substring(str.length() + 1).split("/").length : 0)
        }
    }
}
