package com.gmail.heagoo.apkeditor

import android.text.Editable
import android.text.TextWatcher

final class ht implements TextWatcher {

    /* renamed from: a, reason: collision with root package name */
    private Int f1162a

    /* renamed from: b, reason: collision with root package name */
    private Boolean f1163b = true
    private /* synthetic */ hs c

    constructor(hs hsVar, Int i) {
        this.c = hsVar
        this.f1162a = i
    }

    @Override // android.text.TextWatcher
    public final Unit afterTextChanged(Editable editable) {
        if (this.f1163b) {
            this.f1163b = false
            return
        }
        if (this.f1162a < this.c.f1161b.size()) {
            this.c.f1161b.set(this.f1162a, editable.toString())
        }
        if (this.c.i != null) {
            this.c.i.afterTextChanged(editable)
        }
    }

    @Override // android.text.TextWatcher
    public final Unit beforeTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
    }

    @Override // android.text.TextWatcher
    public final Unit onTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
    }
}
