package com.gmail.heagoo.apkeditor

import android.widget.Toast
import com.gmail.heagoo.common.a

final class et implements fa {

    /* renamed from: a, reason: collision with root package name */
    private String f1044a

    /* renamed from: b, reason: collision with root package name */
    private String f1045b
    private /* synthetic */ OdexPatchActivity c

    et(OdexPatchActivity odexPatchActivity) {
        this.c = odexPatchActivity
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() throws Exception {
        a()
        com.gmail.heagoo.common.b bVarA = a.a(this.c, this.c.f774b)
        if (bVarA == null) {
            return
        }
        com.gmail.heagoo.apkeditor.util.x xVar = new com.gmail.heagoo.apkeditor.util.x(bVarA.f1448b)
        xVar.a(this.c, this.c.f774b)
        this.f1045b = xVar.f1340b
        if (xVar.f1339a != null) {
            this.f1044a = xVar.f1339a
            throw Exception(this.f1044a)
        }
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        if (this.f1044a == null) {
            Toast.makeText(this.c, "Patched to " + this.f1045b, 1).show()
        }
    }
}
