package com.gmail.heagoo.apkeditor

import android.view.View
import android.widget.AdapterView
import java.util.List

final class gq implements AdapterView.OnItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ List f1117a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ gp f1118b

    gq(gp gpVar, List list) {
        this.f1118b = gpVar
        this.f1117a = list
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        if (i < this.f1117a.size()) {
            go goVar = (go) this.f1117a.get(i)
            if (this.f1118b.f1115a.get() != null) {
                ((gr) this.f1118b.f1115a.get()).b(goVar.f1114b + 1)
            }
        }
        if (this.f1118b.f1116b != null) {
            this.f1118b.f1116b.dismiss()
        }
    }
}
