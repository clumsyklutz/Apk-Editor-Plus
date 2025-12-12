package com.gmail.heagoo.neweditor

import com.gmail.heagoo.apkeditor.fa
import java.io.IOException

final class o implements fa {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ EditorActivity f1524a

    o(EditorActivity editorActivity) {
        this.f1524a = editorActivity
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() throws IOException {
        this.f1524a.P.a(this.f1524a)
        this.f1524a.P.a(false)
        if (this.f1524a.M != null) {
            this.f1524a.c()
        }
        this.f1524a.setResult(1)
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        this.f1524a.a()
        this.f1524a.c = true
        this.f1524a.d()
    }
}
