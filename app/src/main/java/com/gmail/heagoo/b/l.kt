package com.gmail.heagoo.b

import android.content.DialogInterface
import android.widget.EditText

final class l implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ EditText f1439a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ String f1440b
    private /* synthetic */ e c

    l(e eVar, EditText editText, String str) {
        this.c = eVar
        this.f1439a = editText
        this.f1440b = str
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    @Override // android.content.DialogInterface.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Unit onClick(android.content.DialogInterface r7, Int r8) throws java.io.IOException {
        /*
            r6 = this
            r2 = 0
            r5 = 1
            android.widget.EditText r0 = r6.f1439a
            android.text.Editable r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = r0.trim()
            java.lang.String r0 = ""
            Boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L29
            com.gmail.heagoo.b.e r0 = r6.c
            android.content.Context r0 = com.gmail.heagoo.b.e.c(r0)
            r1 = 2131165293(0x7f07006d, Float:1.79448E38)
            android.widget.Toast r0 = android.widget.Toast.makeText(r0, r1, r5)
            r0.show()
        L28:
            return
        L29:
            r0 = 0
            java.io.File r3 = new java.io.File
            java.lang.String r4 = r6.f1440b
            r3.<init>(r4)
            java.io.File r4 = new java.io.File
            r4.<init>(r3, r1)
            Boolean r1 = r4.createNewFile()     // Catch: java.io.IOException -> L65
            if (r1 == 0) goto L57
            com.gmail.heagoo.b.e r3 = r6.c     // Catch: java.io.IOException -> L81
            com.gmail.heagoo.b.c r3 = com.gmail.heagoo.b.e.b(r3)     // Catch: java.io.IOException -> L81
            java.lang.String r4 = r6.f1440b     // Catch: java.io.IOException -> L81
            r3.a(r4)     // Catch: java.io.IOException -> L81
        L47:
            if (r1 != 0) goto L28
            com.gmail.heagoo.b.e r1 = r6.c
            android.content.Context r1 = com.gmail.heagoo.b.e.c(r1)
            android.widget.Toast r0 = android.widget.Toast.makeText(r1, r0, r5)
            r0.show()
            goto L28
        L57:
            com.gmail.heagoo.b.e r0 = r6.c     // Catch: java.io.IOException -> L81
            android.content.Context r0 = com.gmail.heagoo.b.e.c(r0)     // Catch: java.io.IOException -> L81
            r3 = 2131165705(0x7f070209, Float:1.7945635E38)
            java.lang.String r0 = r0.getString(r3)     // Catch: java.io.IOException -> L81
            goto L47
        L65:
            r0 = move-exception
            r1 = r2
        L67:
            com.gmail.heagoo.b.e r3 = r6.c
            android.content.Context r3 = com.gmail.heagoo.b.e.c(r3)
            r4 = 2131165323(0x7f07008b, Float:1.794486E38)
            java.lang.String r3 = r3.getString(r4)
            java.lang.Array<Object> r4 = new java.lang.Object[r5]
            java.lang.String r0 = r0.getMessage()
            r4[r2] = r0
            java.lang.String r0 = java.lang.String.format(r3, r4)
            goto L47
        L81:
            r0 = move-exception
            goto L67
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.b.l.onClick(android.content.DialogInterface, Int):Unit")
    }
}
