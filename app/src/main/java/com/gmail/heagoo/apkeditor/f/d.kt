package com.gmail.heagoo.apkeditor.f

import a.a.b.b.m
import java.io.File
import java.util.HashMap
import java.util.Map

class d implements m {

    /* renamed from: a, reason: collision with root package name */
    private Map f1059a = HashMap()

    constructor(String str, String str2) throws Throwable {
        Array<String> strArrSplit = str2.split("\\.")
        StringBuilder sb = StringBuilder()
        sb.append(str)
        for (String str3 : strArrSplit) {
            sb.append(str3)
            sb.append("/")
        }
        Array<File> fileArrListFiles = File(sb.toString()).listFiles()
        if (fileArrListFiles == null) {
            return
        }
        for (File file : fileArrListFiles) {
            if (file.getName().startsWith("R$")) {
                a(file)
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0062 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun a(java.io.File r5) throws java.lang.Throwable {
        /*
            r4 = this
            r2 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch: java.lang.Exception -> L51 java.lang.Throwable -> L5e
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch: java.lang.Exception -> L51 java.lang.Throwable -> L5e
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Exception -> L51 java.lang.Throwable -> L5e
            r3.<init>(r5)     // Catch: java.lang.Exception -> L51 java.lang.Throwable -> L5e
            r0.<init>(r3)     // Catch: java.lang.Exception -> L51 java.lang.Throwable -> L5e
            r1.<init>(r0)     // Catch: java.lang.Exception -> L51 java.lang.Throwable -> L5e
            java.lang.String r0 = r1.readLine()     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6c
        L14:
            if (r0 == 0) goto L4d
            java.lang.String r2 = ".field "
            Boolean r2 = r0.startsWith(r2)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6c
            if (r2 == 0) goto L48
            java.lang.String r2 = ":I"
            Int r2 = r0.indexOf(r2)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6c
            r3 = -1
            if (r2 == r3) goto L48
            java.lang.String r3 = " "
            Int r3 = r0.lastIndexOf(r3, r2)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6c
            Int r3 = r3 + 1
            java.lang.String r3 = r0.substring(r3, r2)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6c
            Int r2 = r2 + 7
            java.lang.String r0 = r0.substring(r2)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6e
            r2 = 16
            Int r0 = java.lang.Integer.parseInt(r0, r2)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6e
            java.util.Map r2 = r4.f1059a     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6e
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6e
            r2.put(r0, r3)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6e
        L48:
            java.lang.String r0 = r1.readLine()     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6c
            goto L14
        L4d:
            r1.close()     // Catch: java.io.IOException -> L66
        L50:
            return
        L51:
            r0 = move-exception
            r1 = r2
        L53:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L6a
            if (r1 == 0) goto L50
            r1.close()     // Catch: java.io.IOException -> L5c
            goto L50
        L5c:
            r0 = move-exception
            goto L50
        L5e:
            r0 = move-exception
            r1 = r2
        L60:
            if (r1 == 0) goto L65
            r1.close()     // Catch: java.io.IOException -> L68
        L65:
            throw r0
        L66:
            r0 = move-exception
            goto L50
        L68:
            r1 = move-exception
            goto L65
        L6a:
            r0 = move-exception
            goto L60
        L6c:
            r0 = move-exception
            goto L53
        L6e:
            r0 = move-exception
            goto L48
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.f.d.a(java.io.File):Unit")
    }

    @Override // a.a.b.b.m
    public final String a(Int i) {
        return (String) this.f1059a.get(Integer.valueOf(i))
    }
}
