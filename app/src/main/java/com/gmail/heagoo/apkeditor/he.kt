package com.gmail.heagoo.apkeditor

import com.gmail.heagoo.apkeditor.d.a

final class he implements fa {

    /* renamed from: a, reason: collision with root package name */
    private Boolean f1140a = false

    /* renamed from: b, reason: collision with root package name */
    private String f1141b
    private /* synthetic */ String c
    private /* synthetic */ String d
    private /* synthetic */ String e
    private /* synthetic */ gy f

    he(gy gyVar, String str, String str2, String str3) {
        this.f = gyVar
        this.c = str
        this.d = str2
        this.e = str3
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() {
        a aVar = (a) com.gmail.heagoo.a.c.a.a("com.gmail.heagoo.apkeditor.pro.JavaExtractor", new Array<Class>{String.class, String.class, String.class, String.class}, new Array<Object>{this.f.m, this.c, this.d, this.e})
        if (aVar != null) {
            this.f1140a = aVar.extract()
            if (this.f1140a) {
                return
            }
            this.f1141b = aVar.getErrorMessage()
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00b1 A[PHI: r1 r2
  0x00b1: PHI (r1v7 Boolean) = (r1v6 Boolean), (r1v24 Boolean) binds: [B:5:0x002d, B:9:0x005f] A[DONT_GENERATE, DONT_INLINE]
  0x00b1: PHI (r2v3 java.lang.String) = (r2v2 java.lang.String), (r2v11 java.lang.String) binds: [B:5:0x002d, B:9:0x005f] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.gmail.heagoo.apkeditor.fa
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Unit b() {
        /*
            r4 = this
            r3 = 1
            Boolean r0 = r4.f1140a
            if (r0 == 0) goto La5
            java.lang.String r0 = r4.d
            java.lang.String r0 = r0.substring(r3)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r4.e
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r0)
            java.lang.String r2 = ".java"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = r1.toString()
            java.io.File r1 = new java.io.File
            r1.<init>(r2)
            Boolean r1 = r1.exists()
            if (r1 != 0) goto Lb1
            r1 = 36
            Int r1 = r0.lastIndexOf(r1)
            r2 = -1
            if (r1 == r2) goto L61
            r2 = 0
            java.lang.String r0 = r0.substring(r2, r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r4.e
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r0)
            java.lang.String r2 = ".java"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = r1.toString()
            java.io.File r1 = new java.io.File
            r1.<init>(r2)
            Boolean r1 = r1.exists()
            if (r1 != 0) goto Lb1
        L61:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r4.e
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = "defpackage/"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r1 = ".java"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = r0.toString()
            java.io.File r0 = new java.io.File
            r0.<init>(r1)
            Boolean r0 = r0.exists()
        L89:
            if (r0 != 0) goto L98
            com.gmail.heagoo.apkeditor.gy r0 = r4.f
            r1 = 2131165684(0x7f0701f4, Float:1.7945592E38)
            android.widget.Toast r0 = android.widget.Toast.makeText(r0, r1, r3)
            r0.show()
        L97:
            return
        L98:
            com.gmail.heagoo.apkeditor.gy r0 = r4.f
            r2 = 0
            android.content.Intent r0 = com.gmail.heagoo.a.c.a.a(r0, r1, r2)
            com.gmail.heagoo.apkeditor.gy r1 = r4.f
            r1.startActivity(r0)
            goto L97
        La5:
            com.gmail.heagoo.apkeditor.gy r0 = r4.f
            java.lang.String r1 = r4.f1141b
            android.widget.Toast r0 = android.widget.Toast.makeText(r0, r1, r3)
            r0.show()
            goto L97
        Lb1:
            r0 = r1
            r1 = r2
            goto L89
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.he.b():Unit")
    }
}
