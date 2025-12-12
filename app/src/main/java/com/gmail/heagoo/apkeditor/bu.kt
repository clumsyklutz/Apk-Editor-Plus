package com.gmail.heagoo.apkeditor

import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File

final class bu implements fa {

    /* renamed from: a, reason: collision with root package name */
    private String f908a

    /* renamed from: b, reason: collision with root package name */
    private String f909b
    private String c
    private String d
    private String e
    private String f
    private Boolean g = false
    private /* synthetic */ AxmlEditActivity h

    constructor(AxmlEditActivity axmlEditActivity, String str, String str2) {
        this.h = axmlEditActivity
        this.f908a = str
        this.f909b = str + ".bin"
        this.d = str2
        this.c = str + com.gmail.heagoo.common.s.a(6)
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() {
        String strC = AxmlEditActivity.c(this.h)
        com.gmail.heagoo.common.c cVar = new com.gmail.heagoo.common.c()
        cVar.a((Object) ((strC + "aaptz") + " z -I " + (strC + "android.jar") + " " + this.f908a + " " + this.c + " " + this.h.a()), (Array<String>) null, (Integer) 5000, false)
        this.e = cVar.a()
        this.f = cVar.b()
        File file = File(this.c)
        if (file.exists()) {
            this.g = true
            if (file.renameTo(File(this.f909b))) {
                return
            }
            file.delete()
        }
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        if (this.g && File(this.f909b).exists()) {
            Toast.makeText(this.h, String.format(this.h.getString(R.string.entry_modified), this.d), 0).show()
            this.h.e.c(this.d, this.f909b)
            this.h.i.setVisibility(0)
        } else {
            String str = this.e
            if (this.f != null && !this.f.equals("")) {
                str = this.f
            }
            new AlertDialog.Builder(this.h).setTitle(R.string.error).setMessage(str).setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null).show()
        }
    }
}
