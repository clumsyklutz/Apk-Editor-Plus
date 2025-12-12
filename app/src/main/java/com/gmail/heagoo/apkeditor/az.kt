package com.gmail.heagoo.apkeditor

import android.view.View
import android.widget.EditText
import com.gmail.heagoo.b.a

final class az implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ a f865a

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f866b
    final /* synthetic */ String c
    final /* synthetic */ Int d
    final /* synthetic */ av e
    private /* synthetic */ EditText f

    az(av avVar, EditText editText, a aVar, String str, String str2, Int i) {
        this.e = avVar
        this.f = editText
        this.f865a = aVar
        this.f866b = str
        this.c = str2
        this.d = i
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x008e  */
    @Override // android.view.View.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Unit onClick(android.view.View r8) {
        /*
            r7 = this
            r6 = 46
            r0 = 1
            r5 = -1
            r1 = 0
            android.widget.EditText r2 = r7.f
            android.text.Editable r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            java.lang.String r4 = r2.trim()
            java.lang.String r2 = ""
            Boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L2a
            com.gmail.heagoo.apkeditor.av r0 = r7.e
            com.gmail.heagoo.apkeditor.ApkInfoExActivity r0 = r0.f860a
            r2 = 2131165293(0x7f07006d, Float:1.79448E38)
            android.widget.Toast r0 = android.widget.Toast.makeText(r0, r2, r1)
            r0.show()
        L29:
            return
        L2a:
            com.gmail.heagoo.b.a r2 = r7.f865a
            java.lang.String r2 = r2.f1424a
            Boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L43
            com.gmail.heagoo.apkeditor.av r0 = r7.e
            com.gmail.heagoo.apkeditor.ApkInfoExActivity r0 = r0.f860a
            r2 = 2131165365(0x7f0700b5, Float:1.7944945E38)
            android.widget.Toast r0 = android.widget.Toast.makeText(r0, r2, r1)
            r0.show()
            goto L29
        L43:
            com.gmail.heagoo.b.a r2 = r7.f865a
            Boolean r2 = r2.f1425b
            if (r2 != 0) goto L90
            com.gmail.heagoo.b.a r2 = r7.f865a
            java.lang.String r2 = r2.f1424a
            Int r3 = r2.lastIndexOf(r6)
            if (r3 == r5) goto L8e
            java.lang.String r2 = r2.substring(r3)
            Int r3 = r4.lastIndexOf(r6)
            if (r3 != r5) goto L84
        L5d:
            if (r0 == 0) goto L90
            android.app.AlertDialog$Builder r0 = new android.app.AlertDialog$Builder
            com.gmail.heagoo.apkeditor.av r1 = r7.e
            com.gmail.heagoo.apkeditor.ApkInfoExActivity r1 = r1.f860a
            r0.<init>(r1)
            r1 = 2131165581(0x7f07018d, Float:1.7945383E38)
            r0.setMessage(r1)
            r1 = 2131165499(0x7f07013b, Float:1.7945217E38)
            com.gmail.heagoo.apkeditor.ba r2 = new com.gmail.heagoo.apkeditor.ba
            r2.<init>(r7, r4)
            r0.setPositiveButton(r1, r2)
            r1 = 2131165364(0x7f0700b4, Float:1.7944943E38)
            r2 = 0
            r0.setNegativeButton(r1, r2)
            r0.show()
            goto L29
        L84:
            java.lang.String r3 = r4.substring(r3)
            Boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L5d
        L8e:
            r0 = r1
            goto L5d
        L90:
            com.gmail.heagoo.apkeditor.av r0 = r7.e
            java.lang.String r1 = r7.f866b
            com.gmail.heagoo.b.a r2 = r7.f865a
            java.lang.String r3 = r7.c
            Int r5 = r7.d
            r0.a(r1, r2, r3, r4, r5)
            goto L29
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.az.onClick(android.view.View):Unit")
    }
}
