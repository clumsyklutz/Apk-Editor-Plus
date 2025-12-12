package com.gmail.heagoo.apkeditor

import android.view.View
import android.widget.AdapterView

final class di implements AdapterView.OnItemSelectedListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ dh f961a

    di(dh dhVar) {
        this.f961a = dhVar
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final Unit onItemSelected(AdapterView adapterView, View view, Int i, Long j) {
        this.f961a.a(i)
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final Unit onNothingSelected(AdapterView adapterView) {
    }
}
