package com.gmail.heagoo.apkeditor

import android.content.Context
import android.view.View
import android.widget.Toast

final class dy implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ dx f978a

    dy(dx dxVar) {
        this.f978a = dxVar
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        String strA = this.f978a.d.a(this.f978a.c)
        if (strA == null) {
            this.f978a.dismiss()
        } else {
            if (strA.equals("")) {
                return
            }
            Toast.makeText((Context) this.f978a.f976a.get(), strA, 0).show()
        }
    }
}
