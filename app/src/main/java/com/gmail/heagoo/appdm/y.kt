package com.gmail.heagoo.appdm

import android.graphics.drawable.Drawable
import android.view.View

final class y implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ PrefOverallActivity f1422a

    y(PrefOverallActivity prefOverallActivity) {
        this.f1422a = prefOverallActivity
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        this.f1422a.f1356b = 3
        this.f1422a.f()
        this.f1422a.s.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1422a.w, (Drawable) null, (Drawable) null)
        this.f1422a.t.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1422a.y, (Drawable) null, (Drawable) null)
        this.f1422a.u.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1422a.A, (Drawable) null, (Drawable) null)
        this.f1422a.v.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1422a.D, (Drawable) null, (Drawable) null)
    }
}
