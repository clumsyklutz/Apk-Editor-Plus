package com.gmail.heagoo.apkeditor

import android.view.View
import android.widget.AdapterView

final class ab implements AdapterView.OnItemSelectedListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ ApkInfoActivity f833a

    ab(ApkInfoActivity apkInfoActivity) {
        this.f833a = apkInfoActivity
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final Unit onItemSelected(AdapterView adapterView, View view, Int i, Long j) {
        this.f833a.D = (String) this.f833a.E.get(i)
        this.f833a.u()
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final Unit onNothingSelected(AdapterView adapterView) {
    }
}
