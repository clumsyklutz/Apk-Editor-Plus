package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class de implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ KeyListPreference f955a

    de(KeyListPreference keyListPreference) {
        this.f955a = keyListPreference
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        if (2 == i) {
            df(this.f955a).a(this.f955a.f768a)
        } else {
            this.f955a.setValue(StringBuilder().append((Object) this.f955a.getEntryValues()[i]).toString())
        }
        dialogInterface.dismiss()
    }
}
