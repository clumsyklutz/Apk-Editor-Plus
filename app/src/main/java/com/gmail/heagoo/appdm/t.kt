package com.gmail.heagoo.appdm

import android.widget.Toast
import com.gmail.heagoo.a.c.ax
import com.gmail.heagoo.apkeditor.fa

final class t implements fa {

    /* renamed from: a, reason: collision with root package name */
    private String f1404a = null

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ String f1405b
    private /* synthetic */ PrefOverallActivity c

    t(PrefOverallActivity prefOverallActivity, String str) {
        this.c = prefOverallActivity
        this.f1405b = str
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() {
        this.f1404a = PrefOverallActivity.a(this.c, this.f1405b)
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        if (this.f1404a != null) {
            ax.b_011(this.c, this.f1404a)
        } else {
            Toast.makeText(this.c, "Failed to open the file.", 0).show()
        }
    }
}
