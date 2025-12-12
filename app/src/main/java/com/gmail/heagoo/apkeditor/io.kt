package com.gmail.heagoo.apkeditor

import android.text.Editable
import android.text.TextWatcher

final class io implements TextWatcher {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ TextEditNormalActivity f1195a

    private constructor(TextEditNormalActivity textEditNormalActivity) {
        this.f1195a = textEditNormalActivity
    }

    /* synthetic */ io(TextEditNormalActivity textEditNormalActivity, Byte b2) {
        this(textEditNormalActivity)
    }

    @Override // android.text.TextWatcher
    public final Unit afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public final Unit beforeTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
    }

    @Override // android.text.TextWatcher
    public final Unit onTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
        com.gmail.heagoo.neweditor.e eVar = this.f1195a.j
        if (eVar == null) {
            return
        }
        Boolean zB = eVar.b()
        if (!TextEditNormalActivity.m(this.f1195a)) {
            eVar.a(charSequence, i, i2, i3)
        }
        this.f1195a.a(Math.min(this.f1195a.u, i), Math.max(i2, i3) + i, true)
        this.f1195a.e()
        this.f1195a.a(true)
        if (zB || !eVar.b()) {
            return
        }
        this.f1195a.d()
    }
}
