package com.gmail.heagoo.appdm

import android.graphics.drawable.Drawable
import android.view.View

final class w implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ PrefOverallActivity f1420a

    w(PrefOverallActivity prefOverallActivity) {
        this.f1420a = prefOverallActivity
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        this.f1420a.f1356b = 1
        this.f1420a.d()
        this.f1420a.s.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1420a.w, (Drawable) null, (Drawable) null)
        this.f1420a.t.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1420a.z, (Drawable) null, (Drawable) null)
        this.f1420a.u.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1420a.A, (Drawable) null, (Drawable) null)
        this.f1420a.v.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1420a.C, (Drawable) null, (Drawable) null)
    }
}
