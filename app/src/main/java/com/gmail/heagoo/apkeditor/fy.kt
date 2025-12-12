package com.gmail.heagoo.apkeditor

import android.content.Context
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.util.Iterator

final class fy implements fa {

    /* renamed from: a, reason: collision with root package name */
    private Int f1091a = 0

    /* renamed from: b, reason: collision with root package name */
    private String f1092b = ""
    private /* synthetic */ String c
    private /* synthetic */ fv d

    fy(fv fvVar, String str) {
        this.d = fvVar
        this.c = str
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() throws Throwable {
        Iterator it = this.d.k.iterator()
        while (it.hasNext()) {
            String str = (String) it.next()
            try {
                this.d.d.a(str, this.c)
                this.d.b(str)
            } catch (Exception e) {
                this.f1092b += "\n" + String.format(((ApkInfoActivity) this.d.f.get()).getString(R.string.failed_to_modify), str)
                this.f1091a++
            }
        }
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        for (Int i = 0; i < this.d.d.getGroupCount(); i++) {
            this.d.c.collapseGroup(i)
            this.d.d.b(i)
        }
        String str = String.format(((ApkInfoActivity) this.d.f.get()).getString(R.string.str_num_modified_file), Integer.valueOf(this.d.k.size() - this.f1091a))
        if (this.f1091a > 0) {
            Toast.makeText((Context) this.d.f.get(), str + this.f1092b, 1).show()
        } else {
            Toast.makeText((Context) this.d.f.get(), str, 0).show()
        }
    }
}
