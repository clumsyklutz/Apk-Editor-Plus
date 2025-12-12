package com.gmail.heagoo.appdm

import android.graphics.drawable.Drawable
import android.view.View

final class x implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ PrefOverallActivity f1421a

    x(PrefOverallActivity prefOverallActivity) {
        this.f1421a = prefOverallActivity
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        this.f1421a.f1356b = 2
        this.f1421a.e()
        this.f1421a.s.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1421a.w, (Drawable) null, (Drawable) null)
        this.f1421a.t.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1421a.y, (Drawable) null, (Drawable) null)
        this.f1421a.u.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1421a.B, (Drawable) null, (Drawable) null)
        this.f1421a.v.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1421a.C, (Drawable) null, (Drawable) null)
    }
}
