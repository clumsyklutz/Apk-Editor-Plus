package com.gmail.heagoo.apkeditor

import android.view.View
import android.widget.AdapterView

final class gl implements AdapterView.OnItemSelectedListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ gk f1109a

    gl(gk gkVar) {
        this.f1109a = gkVar
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final Unit onItemSelected(AdapterView adapterView, View view, Int i, Long j) {
        this.f1109a.a(i)
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final Unit onNothingSelected(AdapterView adapterView) {
    }
}
