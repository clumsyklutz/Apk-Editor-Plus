package com.gmail.heagoo.appdm

import android.view.View
import android.widget.AdapterView

final class ab implements AdapterView.OnItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ PrefOverallActivity f1360a

    ab(PrefOverallActivity prefOverallActivity) {
        this.f1360a = prefOverallActivity
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        this.f1360a.a(i)
    }
}
