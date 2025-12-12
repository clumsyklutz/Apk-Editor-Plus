package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.content.Context
import android.view.View
import com.gmail.heagoo.apkeditor.pro.R

final class dz implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ dx f979a

    dz(dx dxVar) {
        this.f979a = dxVar
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        String string = ((Activity) this.f979a.f976a.get()).getString(R.string.select_folder)
        cn((Context) this.f979a.f976a.get(), ea(this), null, null, string, true, false, false, null).show()
        this.f979a.dismiss()
    }
}
