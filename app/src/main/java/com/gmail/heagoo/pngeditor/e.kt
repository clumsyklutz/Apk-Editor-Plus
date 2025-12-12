package com.gmail.heagoo.pngeditor

import android.view.View
import com.gmail.heagoo.apkeditor.pro.R

final class e implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ PngEditActivity f1550a

    e(PngEditActivity pngEditActivity) {
        this.f1550a = pngEditActivity
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Float fA = 1.0f
        Int id = view.getId()
        if (id == R.id.btn_scale_fit) {
            fA = this.f1550a.g.a()
        } else if (id != R.id.btn_scale_100) {
            if (id == R.id.btn_scale_200) {
                fA = 2.0f
            } else if (id == R.id.btn_scale_400) {
                fA = 4.0f
            }
        }
        this.f1550a.g.a(fA)
        this.f1550a.g.postInvalidate()
        this.f1550a.a(fA)
        this.f1550a.v.dismiss()
    }
}
