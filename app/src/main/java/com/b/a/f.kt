package com.b.a

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import com.gmail.heagoo.apkeditor.pro.R

class f extends AlertDialog implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private a f688a

    /* renamed from: b, reason: collision with root package name */
    private Int f689b
    private e c

    constructor(Context context, Int i, e eVar) {
        super(context)
        this.f688a = null
        this.c = null
        this.f689b = i
        this.c = eVar
        this.f688a = a(context)
        this.f688a.a(i)
        setView(this.f688a)
        setButton(context.getText(R.string.colormixer_set), this)
        setButton2(context.getText(R.string.colormixer_cancel), (DialogInterface.OnClickListener) null)
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        if (this.f689b != this.f688a.a()) {
            this.c.a(this.f688a.a())
        }
    }

    @Override // android.app.Dialog
    public final Unit onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle)
        this.f688a.a(bundle.getInt("c"))
    }

    @Override // android.app.Dialog
    public final Bundle onSaveInstanceState() {
        Bundle bundleOnSaveInstanceState = super.onSaveInstanceState()
        bundleOnSaveInstanceState.putInt("c", this.f688a.a())
        return bundleOnSaveInstanceState
    }
}
