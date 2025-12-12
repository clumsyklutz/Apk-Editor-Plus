package a.a.b.a

import android.content.Context
import android.content.DialogInterface

class o implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ n f43a

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Int f44b
    final /* synthetic */ Context c
    final /* synthetic */ p d

    o(n nVar, Int i, Context context, p pVar) {
        this.f43a = nVar
        this.f44b = i
        this.c = context
        this.d = pVar
    }

    @Override // android.content.DialogInterface.OnClickListener
    fun onClick(DialogInterface dialogInterface, Int i) {
        if (this.f44b != i) {
            n.e(this.c, i)
            this.d.a()
        }
        dialogInterface.dismiss()
    }
}
