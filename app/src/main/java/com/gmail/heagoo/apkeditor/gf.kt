package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.os.AsyncTask
import java.io.File
import java.util.Iterator
import java.util.List

final class gf extends AsyncTask {

    /* renamed from: a, reason: collision with root package name */
    private String f1103a

    /* renamed from: b, reason: collision with root package name */
    private List f1104b
    private String c
    private String d
    private Boolean e
    private /* synthetic */ fv f

    @SuppressLint({"DefaultLocale"})
    constructor(fv fvVar, String str, List list, String str2, Boolean z) {
        this.f = fvVar
        this.f1103a = str
        this.f1104b = list
        this.c = str2
        this.d = str2.toLowerCase()
        this.e = z
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|2|45|3|44|4|(1:(1:(2:7|(2:47|9)(1:12))(0)))(2:(1:(2:15|(2:48|17)(1:13))(0))|11)|42|10|11|(1:(0))) */
    @android.annotation.SuppressLint({"DefaultLocale"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun a(java.io.File r7) throws java.lang.Throwable {
        /*
            r6 = this
            r3 = 1
            r0 = 0
            r2 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L4c
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L4c
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L4c
            r5.<init>(r7)     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L4c
            r4.<init>(r5)     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L4c
            r1.<init>(r4)     // Catch: java.lang.Exception -> L42 java.lang.Throwable -> L4c
            java.lang.String r2 = r1.readLine()     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5a
            Boolean r4 = r6.e     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5a
            if (r4 == 0) goto L32
        L1a:
            if (r2 == 0) goto L25
            java.lang.String r4 = r6.c     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5a
            Boolean r2 = r2.contains(r4)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5a
            if (r2 == 0) goto L29
            r0 = r3
        L25:
            r1.close()     // Catch: java.io.IOException -> L54
        L28:
            return r0
        L29:
            java.lang.String r2 = r1.readLine()     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5a
            goto L1a
        L2e:
            java.lang.String r2 = r1.readLine()     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5a
        L32:
            if (r2 == 0) goto L25
            java.lang.String r2 = r2.toLowerCase()     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5a
            java.lang.String r4 = r6.d     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5a
            Boolean r2 = r2.contains(r4)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5a
            if (r2 == 0) goto L2e
            r0 = r3
            goto L25
        L42:
            r1 = move-exception
            r1 = r2
        L44:
            if (r1 == 0) goto L28
            r1.close()     // Catch: java.io.IOException -> L4a
            goto L28
        L4a:
            r1 = move-exception
            goto L28
        L4c:
            r0 = move-exception
            r1 = r2
        L4e:
            if (r1 == 0) goto L53
            r1.close()     // Catch: java.io.IOException -> L56
        L53:
            throw r0
        L54:
            r1 = move-exception
            goto L28
        L56:
            r1 = move-exception
            goto L53
        L58:
            r0 = move-exception
            goto L4e
        L5a:
            r2 = move-exception
            goto L44
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.gf.a(java.io.File):Boolean")
    }

    private fun b(File file) {
        Array<File> fileArrListFiles = file.listFiles()
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    b(file2)
                } else if (c(file2) && a(file2)) {
                    this.f.k.add(file2.getPath())
                }
            }
        }
    }

    private fun c(File file) {
        String name = file.getName()
        return name.endsWith(".xml") || name.endsWith(".smali") || name.endsWith(".txt")
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ Object doInBackground(Array<Object> objArr) {
        File file = File(this.f1103a)
        Iterator it = this.f1104b.iterator()
        while (it.hasNext()) {
            File file2 = File(file, (String) it.next())
            if (file2.exists()) {
                if (file2.isDirectory()) {
                    b(file2)
                } else if (c(file2) && a(file2)) {
                    this.f.k.add(file2.getPath())
                }
            }
        }
        return this.f.k
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ Unit onPostExecute(Object obj) {
        fv.b(this.f)
    }
}
