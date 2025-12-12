package com.gmail.heagoo.apkeditor.f

import android.content.Context
import android.os.AsyncTask
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.ArrayList
import java.util.Iterator
import java.util.List
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class a extends AsyncTask {

    /* renamed from: a, reason: collision with root package name */
    private Context f1057a

    /* renamed from: b, reason: collision with root package name */
    private b f1058b
    private String c
    private String d
    private List e = ArrayList()
    private String f
    private String g

    constructor(Context context, String str, String str2, b bVar) {
        this.f1057a = context
        this.f1058b = bVar
        this.d = str
        this.c = str2
    }

    private fun a(ZipFile zipFile, ZipEntry zipEntry, String str) throws Throwable {
        FileOutputStream fileOutputStream
        InputStream inputStream = null
        try {
            InputStream inputStream2 = zipFile.getInputStream(zipEntry)
            try {
                fileOutputStream = FileOutputStream(str)
                try {
                    com.gmail.heagoo.a.c.a.b(inputStream2, fileOutputStream)
                    this.e.add(str)
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close()
                        } catch (Exception e) {
                        }
                    }
                    try {
                        fileOutputStream.close()
                    } catch (Exception e2) {
                    }
                } catch (Throwable th) {
                    th = th
                    inputStream = inputStream2
                    if (inputStream != null) {
                        try {
                            inputStream.close()
                        } catch (Exception e3) {
                        }
                    }
                    if (fileOutputStream == null) {
                        throw th
                    }
                    try {
                        fileOutputStream.close()
                        throw th
                    } catch (Exception e4) {
                        throw th
                    }
                }
            } catch (Throwable th2) {
                th = th2
                fileOutputStream = null
                inputStream = inputStream2
            }
        } catch (Throwable th3) {
            th = th3
            fileOutputStream = null
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x005d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun b() throws java.lang.Throwable {
        /*
            r6 = this
            android.content.Context r0 = r6.f1057a
            java.lang.String r1 = "tmp"
            java.lang.String r3 = com.gmail.heagoo.a.c.a.d(r0, r1)
            r2 = 0
            java.util.zip.ZipFile r1 = new java.util.zip.ZipFile     // Catch: java.lang.Throwable -> L59 java.io.IOException -> L67
            java.lang.String r0 = r6.d     // Catch: java.lang.Throwable -> L59 java.io.IOException -> L67
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L59 java.io.IOException -> L67
            java.util.Enumeration r2 = r1.entries()     // Catch: java.io.IOException -> L49 java.lang.Throwable -> L65
        L14:
            Boolean r0 = r2.hasMoreElements()     // Catch: java.io.IOException -> L49 java.lang.Throwable -> L65
            if (r0 == 0) goto L53
            java.lang.Object r0 = r2.nextElement()     // Catch: java.io.IOException -> L49 java.lang.Throwable -> L65
            java.util.zip.ZipEntry r0 = (java.util.zip.ZipEntry) r0     // Catch: java.io.IOException -> L49 java.lang.Throwable -> L65
            java.lang.String r4 = r0.getName()     // Catch: java.io.IOException -> L49 java.lang.Throwable -> L65
            java.lang.String r5 = ".dex"
            Boolean r5 = r4.endsWith(r5)     // Catch: java.io.IOException -> L49 java.lang.Throwable -> L65
            if (r5 == 0) goto L14
            java.lang.String r5 = "/"
            Boolean r5 = r4.contains(r5)     // Catch: java.io.IOException -> L49 java.lang.Throwable -> L65
            if (r5 != 0) goto L14
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.io.IOException -> L49 java.lang.Throwable -> L65
            r5.<init>()     // Catch: java.io.IOException -> L49 java.lang.Throwable -> L65
            java.lang.StringBuilder r5 = r5.append(r3)     // Catch: java.io.IOException -> L49 java.lang.Throwable -> L65
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch: java.io.IOException -> L49 java.lang.Throwable -> L65
            java.lang.String r4 = r4.toString()     // Catch: java.io.IOException -> L49 java.lang.Throwable -> L65
            r6.a(r1, r0, r4)     // Catch: java.io.IOException -> L49 java.lang.Throwable -> L65
            goto L14
        L49:
            r0 = move-exception
        L4a:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L65
            if (r1 == 0) goto L52
            r1.close()     // Catch: java.lang.Exception -> L61
        L52:
            return
        L53:
            r1.close()     // Catch: java.lang.Exception -> L57
            goto L52
        L57:
            r0 = move-exception
            goto L52
        L59:
            r0 = move-exception
            r1 = r2
        L5b:
            if (r1 == 0) goto L60
            r1.close()     // Catch: java.lang.Exception -> L63
        L60:
            throw r0
        L61:
            r0 = move-exception
            goto L52
        L63:
            r1 = move-exception
            goto L60
        L65:
            r0 = move-exception
            goto L5b
        L67:
            r0 = move-exception
            r1 = r2
            goto L4a
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.f.a.b():Unit")
    }

    private fun c() throws Throwable {
        try {
            b()
            e()
            d()
            return true
        } catch (Exception e) {
            this.f = e.getMessage()
            return false
        }
    }

    private fun d() {
        Iterator it = this.e.iterator()
        while (it.hasNext()) {
            File((String) it.next()).delete()
        }
    }

    private fun e() {
        for (String str : this.e) {
            Object objA = com.gmail.heagoo.a.c.a.a("com.gmail.heagoo.apkeditor.pro.DexDecoder", new Array<Class>{String.class}, new Array<Object>{str})
            String str2 = !str.endsWith("/classes.dex") ? this.c + "/smali_" + str.substring(str.lastIndexOf("/") + 1, str.length() - 4) : this.c + "/smali"
            File file = File(str2)
            if (file.exists()) {
                file.mkdir()
            }
            com.gmail.heagoo.a.c.a.a("com.gmail.heagoo.apkeditor.pro.DexDecoder", "dex2smali", objA, new Array<Class>{String.class}, new Array<Object>{str2})
            if (this.g == null) {
                this.g = (String) com.gmail.heagoo.a.c.a.a("com.gmail.heagoo.apkeditor.pro.DexDecoder", "getWarning", objA, null, null)
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0045 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Unit a() throws java.lang.Throwable {
        /*
            r5 = this
            android.content.Context r0 = r5.f1057a
            java.lang.String r1 = "tmp"
            java.lang.String r0 = com.gmail.heagoo.a.c.a.d(r0, r1)
            r2 = 0
            java.lang.String r3 = "classes.dex"
            java.util.zip.ZipFile r1 = new java.util.zip.ZipFile     // Catch: java.io.IOException -> L34 java.lang.Throwable -> L41
            java.lang.String r4 = r5.d     // Catch: java.io.IOException -> L34 java.lang.Throwable -> L41
            r1.<init>(r4)     // Catch: java.io.IOException -> L34 java.lang.Throwable -> L41
            java.util.zip.ZipEntry r2 = r1.getEntry(r3)     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L4f
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L4f
            r4.<init>()     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L4f
            java.lang.StringBuilder r0 = r4.append(r0)     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L4f
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L4f
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L4f
            r5.a(r1, r2, r0)     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L4f
            r1.close()     // Catch: java.lang.Exception -> L49
        L2d:
            r5.e()
            r5.d()
            return
        L34:
            r0 = move-exception
            r1 = r2
        L36:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L4d
            if (r1 == 0) goto L2d
            r1.close()     // Catch: java.lang.Exception -> L3f
            goto L2d
        L3f:
            r0 = move-exception
            goto L2d
        L41:
            r0 = move-exception
            r1 = r2
        L43:
            if (r1 == 0) goto L48
            r1.close()     // Catch: java.lang.Exception -> L4b
        L48:
            throw r0
        L49:
            r0 = move-exception
            goto L2d
        L4b:
            r1 = move-exception
            goto L48
        L4d:
            r0 = move-exception
            goto L43
        L4f:
            r0 = move-exception
            goto L36
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.f.a.a():Unit")
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ Object doInBackground(Array<Object> objArr) {
        return c()
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ Unit onPostExecute(Object obj) {
        Boolean bool = (Boolean) obj
        if (this.f1058b != null) {
            if (bool.booleanValue()) {
                this.f1058b.a(true, null, this.g)
            } else {
                this.f1058b.a(false, this.f, null)
            }
        }
    }

    @Override // android.os.AsyncTask
    protected final Unit onPreExecute() {
        if (this.f1058b != null) {
            this.f1058b.h()
        }
    }
}
