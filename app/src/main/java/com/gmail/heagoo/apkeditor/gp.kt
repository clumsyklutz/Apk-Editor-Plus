package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference
import java.util.List

class gp {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1115a

    /* renamed from: b, reason: collision with root package name */
    private PopupWindow f1116b
    private String c

    constructor(gr grVar) {
        this.f1115a = WeakReference(grVar)
    }

    static Unit a(gp gpVar, Activity activity, String str, List list) {
        gpVar.c = str
        View viewInflate = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate(R.layout.popup_list, (ViewGroup) null)
        ListView listView = (ListView) viewInflate.findViewById(R.id.lvGroup)
        listView.setAdapter((ListAdapter) gm(activity, list))
        gpVar.f1116b = PopupWindow(viewInflate, com.gmail.heagoo.common.f.a(activity), -2)
        listView.setOnItemClickListener(gq(gpVar, list))
    }

    public final String a() {
        return this.c
    }

    public final Unit a(Activity activity, String str, String str2, View view) {
        gs(this, activity, str, str2, view).execute(new Void[0])
    }

    public final Unit a(View view) {
        if (this.f1116b != null) {
            this.f1116b.setFocusable(true)
            this.f1116b.setOutsideTouchable(true)
            this.f1116b.setBackgroundDrawable(BitmapDrawable())
            this.f1116b.showAsDropDown(view, 0, 0)
        }
    }
}
