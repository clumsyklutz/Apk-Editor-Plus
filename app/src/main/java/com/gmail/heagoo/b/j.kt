package com.gmail.heagoo.b

import android.content.DialogInterface
import android.widget.EditText
import java.util.Iterator

final class j implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ EditText f1437a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ String f1438b
    private /* synthetic */ String c
    private /* synthetic */ e d

    j(e eVar, EditText editText, String str, String str2) {
        this.d = eVar
        this.f1437a = editText
        this.f1438b = str
        this.c = str2
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        String string = this.f1437a.getText().toString()
        if (this.d.a(this.f1438b, this.c, string)) {
            c cVar = this.d.e
            String str = this.c
            synchronized (cVar.f1426a) {
                Iterator it = cVar.f1426a.iterator()
                while (true) {
                    if (!it.hasNext()) {
                        break
                    }
                    a aVar = (a) it.next()
                    if (aVar.f1424a.equals(str)) {
                        aVar.f1424a = string
                        break
                    }
                }
            }
            cVar.notifyDataSetChanged()
        }
    }
}
