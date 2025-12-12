package com.gmail.heagoo.neweditor

import android.text.Editable
import android.text.TextWatcher

final class k implements TextWatcher {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ EditorActivity f1520a

    k(EditorActivity editorActivity) {
        this.f1520a = editorActivity
    }

    @Override // android.text.TextWatcher
    public final Unit afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public final Unit beforeTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
    }

    @Override // android.text.TextWatcher
    public final Unit onTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
        e eVar = this.f1520a.P
        Boolean zB = eVar.b()
        if (!this.f1520a.r) {
            eVar.a(charSequence, i, i2, i3)
        }
        this.f1520a.a(Math.min(this.f1520a.k, i), Math.max(i2, i3) + i, true)
        this.f1520a.b()
        this.f1520a.a(true)
        if (zB || !eVar.b()) {
            return
        }
        this.f1520a.a()
    }
}
