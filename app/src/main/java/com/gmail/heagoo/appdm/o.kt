package com.gmail.heagoo.appdm

import android.view.View
import android.widget.AdapterView
import java.util.ArrayList

final class o implements AdapterView.OnItemLongClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ PrefOverallActivity f1396a

    o(PrefOverallActivity prefOverallActivity) {
        this.f1396a = prefOverallActivity
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public final Boolean onItemLongClick(AdapterView adapterView, View view, Int i, Long j) {
        ArrayList arrayList = ArrayList()
        String strA = this.f1396a.p.a(arrayList)
        com.gmail.heagoo.appdm.util.e eVar = (com.gmail.heagoo.appdm.util.e) arrayList.get(i)
        if (eVar.c) {
            return true
        }
        adapterView.setOnCreateContextMenuListener(p(this, strA, eVar))
        return false
    }
}
