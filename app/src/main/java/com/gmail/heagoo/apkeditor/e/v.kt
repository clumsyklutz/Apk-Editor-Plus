package com.gmail.heagoo.apkeditor.e

import com.gmail.heagoo.apkeditor.ApkInfoActivity
import com.gmail.heagoo.apkeditor.pro.R
import java.util.ArrayList
import java.util.List
import java.util.zip.ZipFile

final class v extends g {

    /* renamed from: b, reason: collision with root package name */
    private List f1012b = ArrayList()

    v() {
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:66:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String d(java.lang.String r8) throws java.lang.Throwable {
        /*
            r7 = this
            r1 = 0
            java.util.zip.ZipFile r4 = new java.util.zip.ZipFile     // Catch: java.io.IOException -> L65 java.lang.Throwable -> L77
            r4.<init>(r8)     // Catch: java.io.IOException -> L65 java.lang.Throwable -> L77
            java.util.Enumeration r2 = r4.entries()     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L92
        La:
            Boolean r0 = r2.hasMoreElements()     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L92
            if (r0 == 0) goto La0
            java.lang.Object r0 = r2.nextElement()     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L92
            java.util.zip.ZipEntry r0 = (java.util.zip.ZipEntry) r0     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L92
            Boolean r3 = r0.isDirectory()     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L92
            if (r3 != 0) goto La
            java.lang.String r3 = r0.getName()     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L92
            java.lang.String r5 = ".RSA"
            Boolean r5 = r3.endsWith(r5)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L92
            if (r5 != 0) goto L40
            java.lang.String r5 = ".rsa"
            Boolean r5 = r3.endsWith(r5)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L92
            if (r5 != 0) goto L40
            java.lang.String r5 = ".DSA"
            Boolean r5 = r3.endsWith(r5)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L92
            if (r5 != 0) goto L40
            java.lang.String r5 = ".dsa"
            Boolean r3 = r3.endsWith(r5)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L92
            if (r3 == 0) goto La
        L40:
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L92
            java.io.InputStream r0 = r4.getInputStream(r0)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L92
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L92
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L96
            r0.<init>()     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L96
            com.gmail.heagoo.a.c.a.b(r2, r0)     // Catch: java.lang.Throwable -> L89 java.io.IOException -> L9a
        L51:
            a(r2)
            a(r0)
            a(r4)
        L5a:
            if (r0 == 0) goto L64
            Array<Byte> r0 = r0.toByteArray()
            java.lang.String r1 = com.gmail.heagoo.a.c.a.c(r0)
        L64:
            return r1
        L65:
            r0 = move-exception
            r2 = r1
            r3 = r1
            r4 = r1
        L69:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L8e
            a(r3)
            a(r2)
            a(r4)
            r0 = r2
            goto L5a
        L77:
            r0 = move-exception
            r2 = r1
            r4 = r1
        L7a:
            a(r2)
            a(r1)
            a(r4)
            throw r0
        L84:
            r0 = move-exception
            r2 = r1
            goto L7a
        L87:
            r0 = move-exception
            goto L7a
        L89:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
            goto L7a
        L8e:
            r0 = move-exception
            r1 = r2
            r2 = r3
            goto L7a
        L92:
            r0 = move-exception
            r2 = r1
            r3 = r1
            goto L69
        L96:
            r0 = move-exception
            r3 = r2
            r2 = r1
            goto L69
        L9a:
            r3 = move-exception
            r6 = r3
            r3 = r2
            r2 = r0
            r0 = r6
            goto L69
        La0:
            r0 = r1
            r2 = r1
            goto L51
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.e.v.d(java.lang.String):java.lang.String")
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final String a(ApkInfoActivity apkInfoActivity, ZipFile zipFile, b bVar) throws Throwable {
        String strD = d(apkInfoActivity.l())
        String str = apkInfoActivity.m().f1448b
        String str2 = bVar.b() + "/" + ((String) this.f1012b.get(0))
        try {
            com.gmail.heagoo.a.c.a.b(str2, a(str2).replace("%PACKAGE_NAME%", str).replace("%RSA_DATA%", strD))
            return null
        } catch (Exception e) {
            bVar.a(R.string.patch_error_write_to, str2)
            return null
        }
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Unit a(c cVar, b bVar) {
        this.f995a = cVar.a()
        String line = cVar.readLine()
        while (line != null) {
            String strTrim = line.trim()
            if ("[/SIGNATURE_REVISE]".equals(strTrim)) {
                return
            }
            if (super.a(strTrim, cVar)) {
                line = cVar.readLine()
            } else {
                if ("TARGET:".equals(strTrim)) {
                    this.f1012b.add(cVar.readLine().trim())
                } else {
                    bVar.a(R.string.patch_error_cannot_parse, Integer.valueOf(cVar.a()), strTrim)
                }
                line = cVar.readLine()
            }
        }
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a() {
        return true
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a(b bVar) {
        return !this.f1012b.isEmpty()
    }
}
