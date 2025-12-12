package com.gmail.heagoo.apkeditor

import android.view.View
import android.widget.AdapterView

final class iu implements AdapterView.OnItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ UserAppActivity f1204a

    iu(UserAppActivity userAppActivity) {
        this.f1204a = userAppActivity
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        this.f1204a.c.setVisibility(4)
        this.f1204a.l.setVisibility(0)
        switch (i) {
            case 0:
                UserAppActivity.a(this.f1204a, 0, this.f1204a.f776b)
                break
            case 1:
                UserAppActivity.a(this.f1204a, 1, this.f1204a.f776b)
                break
            case 2:
                UserAppActivity.a(this.f1204a, this.f1204a.f775a, 0)
                break
            case 3:
                UserAppActivity.a(this.f1204a, this.f1204a.f775a, 1)
                break
        }
        if (this.f1204a.g != null) {
            this.f1204a.g.dismiss()
        }
    }
}
