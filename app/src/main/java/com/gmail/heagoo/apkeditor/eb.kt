package com.gmail.heagoo.apkeditor

import android.content.Context
import android.view.View

final class eb implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ dx f1019a

    eb(dx dxVar) {
        this.f1019a = dxVar
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        if (!dx.f(this.f1019a)) {
            dt.a((Context) this.f1019a.f976a.get())
        } else {
            cn((Context) this.f1019a.f976a.get(), ec(this), ".xml", this.f1019a.f977b, null, false, false, true, null).show()
            this.f1019a.dismiss()
        }
    }
}
