package com.gmail.heagoo.appdm

import android.graphics.drawable.Drawable
import android.view.View

final class v implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ PrefOverallActivity f1419a

    v(PrefOverallActivity prefOverallActivity) {
        this.f1419a = prefOverallActivity
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        this.f1419a.f1356b = 0
        this.f1419a.c()
        this.f1419a.s.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1419a.x, (Drawable) null, (Drawable) null)
        this.f1419a.t.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1419a.y, (Drawable) null, (Drawable) null)
        this.f1419a.u.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1419a.A, (Drawable) null, (Drawable) null)
        this.f1419a.v.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, this.f1419a.C, (Drawable) null, (Drawable) null)
    }
}
