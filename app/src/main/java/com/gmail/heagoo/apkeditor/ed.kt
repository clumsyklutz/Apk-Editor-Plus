package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.content.Context
import android.view.View
import com.gmail.heagoo.a.c.a

final class ed implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ dx f1021a

    ed(dx dxVar) {
        this.f1021a = dxVar
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        if (!dx.f(this.f1021a)) {
            dt.a((Context) this.f1021a.f976a.get())
            return
        }
        ((Activity) this.f1021a.f976a.get()).startActivityForResult(a.a(((Activity) this.f1021a.f976a.get()).getApplicationContext(), this.f1021a.f977b, (String) null), 2)
        this.f1021a.dismiss()
    }
}
