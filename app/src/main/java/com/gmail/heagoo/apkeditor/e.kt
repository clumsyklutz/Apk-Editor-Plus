package com.gmail.heagoo.apkeditor

import android.content.Context
import android.content.DialogInterface
import android.widget.CheckBox
import com.gmail.heagoo.a.c.a

final class e implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ CheckBox f980a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ ApkComposeActivity f981b

    e(ApkComposeActivity apkComposeActivity, CheckBox checkBox) {
        this.f981b = apkComposeActivity
        this.f980a = checkBox
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        if (this.f981b.A != null && this.f981b.A.b()) {
            this.f981b.finish()
        }
        if (this.f980a.isChecked()) {
            a.b((Context) this.f981b, "donot_show_compose_tip", true)
        }
    }
}
