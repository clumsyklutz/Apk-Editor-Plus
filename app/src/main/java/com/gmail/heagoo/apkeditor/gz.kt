package com.gmail.heagoo.apkeditor

import android.view.View

final class gz implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ gy f1131a

    gz(gy gyVar) {
        this.f1131a = gyVar
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        if (this.f1131a.x.getVisibility() == 0) {
            this.f1131a.w.setVisibility(0)
            this.f1131a.x.setVisibility(4)
        } else {
            this.f1131a.x.setVisibility(0)
            this.f1131a.w.setVisibility(4)
        }
    }
}
