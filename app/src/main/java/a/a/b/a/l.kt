package a.a.b.a

import android.content.Context
import android.content.DialogInterface

class l implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ k f41a

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Int f42b
    final /* synthetic */ Context c
    final /* synthetic */ m d

    l(k kVar, Int i, Context context, m mVar) {
        this.f41a = kVar
        this.f42b = i
        this.c = context
        this.d = mVar
    }

    @Override // android.content.DialogInterface.OnClickListener
    fun onClick(DialogInterface dialogInterface, Int i) {
        if (this.f42b != i) {
            k.e(this.c, i)
            this.d.a()
        }
        dialogInterface.dismiss()
    }
}
