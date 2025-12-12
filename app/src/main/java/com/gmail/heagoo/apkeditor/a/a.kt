package com.gmail.heagoo.apkeditor.a

import a.a.b.a.a.u
import a.a.b.a.a.w
import android.content.Context
import android.graphics.Bitmap
import com.gmail.heagoo.apkeditor.ApkInfoActivity
import com.gmail.heagoo.apkeditor.se.aa
import java.io.IOException
import java.io.InputStream
import java.util.Map
import java.util.zip.ZipFile

class a {

    /* renamed from: a, reason: collision with root package name */
    private Context f777a

    /* renamed from: b, reason: collision with root package name */
    private String f778b
    private String c
    private Bitmap d

    constructor(Context context, String str) {
        this.f777a = context
        this.f778b = str
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0039 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.io.InputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.gmail.heagoo.apkeditor.a.b a(java.util.zip.ZipFile r5, java.lang.String r6) throws java.lang.Throwable {
        /*
            r1 = 0
            java.util.zip.ZipEntry r0 = r5.getEntry(r6)
            android.graphics.BitmapFactory$Options r3 = new android.graphics.BitmapFactory$Options
            r3.<init>()
            r2 = 1
            r3.inJustDecodeBounds = r2
            java.io.InputStream r2 = r5.getInputStream(r0)     // Catch: java.io.IOException -> L29 java.lang.Throwable -> L35
            r0 = 0
            android.graphics.BitmapFactory.decodeStream(r2, r0, r3)     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            com.gmail.heagoo.apkeditor.a.b r0 = new com.gmail.heagoo.apkeditor.a.b     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            r4 = 0
            r0.<init>(r4)     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            Int r4 = r3.outHeight     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            r0.f819b = r4     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            Int r3 = r3.outWidth     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            r0.f818a = r3     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            if (r2 == 0) goto L28
            r2.close()     // Catch: java.io.IOException -> L3d
        L28:
            return r0
        L29:
            r0 = move-exception
            r2 = r1
        L2b:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L43
            if (r2 == 0) goto L33
            r2.close()     // Catch: java.io.IOException -> L3f
        L33:
            r0 = r1
            goto L28
        L35:
            r0 = move-exception
            r2 = r1
        L37:
            if (r2 == 0) goto L3c
            r2.close()     // Catch: java.io.IOException -> L41
        L3c:
            throw r0
        L3d:
            r1 = move-exception
            goto L28
        L3f:
            r0 = move-exception
            goto L33
        L41:
            r1 = move-exception
            goto L3c
        L43:
            r0 = move-exception
            goto L37
        L45:
            r0 = move-exception
            goto L2b
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.a.a.a(java.util.zip.ZipFile, java.lang.String):com.gmail.heagoo.apkeditor.a.b")
    }

    private fun a(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            try {
                inputStream.close()
            } catch (IOException e) {
            }
        }
    }

    private fun a(ZipFile zipFile) throws IOException {
        if (zipFile != null) {
            try {
                zipFile.close()
            } catch (IOException e) {
            }
        }
    }

    private fun a(ZipFile zipFile, a.a.b.a.e eVar, Int i) throws Throwable {
        try {
            Map mapB = eVar.b(new a.a.b.a.d(i)).b()
            aa aaVar = aa(zipFile)
            for (a.a.b.a.g gVar : mapB.values()) {
                w wVarD = gVar.d()
                String strA = wVarD is a.a.b.a.a.i ? ((a.a.b.a.a.i) wVarD).a() : wVarD is u ? wVarD.toString() : "res/" + gVar.a() + ".png"
                b bVarA = a(zipFile, strA)
                if (bVarA.f818a > 0) {
                    this.d = aaVar.a(strA, bVarA.f818a, bVarA.f819b)
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    public final String a() {
        return this.c
    }

    public final Unit a(Int i, Int i2) throws Throwable {
        ZipFile zipFile
        Throwable th
        InputStream inputStream
        ZipFile zipFile2
        try {
            zipFile = ZipFile(this.f778b)
            try {
                try {
                    InputStream inputStream2 = zipFile.getInputStream(zipFile.getEntry("resources.arsc"))
                    try {
                        for (a.a.b.a.e eVar : a.a.b.b.a.a(inputStream2, false, false, new com.gmail.heagoo.a.c.a(this.f777a.getApplicationContext(), false), null, false).a()) {
                            if (i != -1) {
                                try {
                                    a.a.b.a.f fVarB = eVar.b(new a.a.b.a.d(i))
                                    w wVarD = fVarB.a(ApkInfoActivity.a(fVarB.b().keySet())).d()
                                    if (wVarD is u) {
                                        this.c = wVarD.toString()
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace()
                                }
                            }
                            if (i2 != -1) {
                                a(zipFile, eVar, i2)
                            }
                        }
                        a(inputStream2)
                        a(zipFile)
                    } catch (Throwable th2) {
                        inputStream = inputStream2
                        th = th2
                        a(inputStream)
                        a(zipFile)
                        throw th
                    }
                } catch (Exception e2) {
                    zipFile2 = zipFile
                    a((InputStream) null)
                    a(zipFile2)
                }
            } catch (Throwable th3) {
                inputStream = null
                th = th3
            }
        } catch (Exception e3) {
            zipFile2 = null
        } catch (Throwable th4) {
            zipFile = null
            th = th4
            inputStream = null
        }
    }

    public final Bitmap b() {
        return this.d
    }
}
