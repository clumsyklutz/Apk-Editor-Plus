package com.gmail.heagoo.apkeditor.a

import a.a.b.a.a.u
import a.a.b.a.a.w
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.gmail.heagoo.common.l
import java.io.IOException
import java.io.InputStream
import java.io.Serializable
import java.util.Iterator
import java.util.Map
import java.util.zip.ZipFile

class j implements d, Serializable {

    /* renamed from: a, reason: collision with root package name */
    private Int f828a

    /* renamed from: b, reason: collision with root package name */
    private String f829b

    constructor(Int i, String str) {
        this.f828a = i
        this.f829b = str
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0038 A[EXC_TOP_SPLITTER, SYNTHETIC] */
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
    private com.gmail.heagoo.apkeditor.a.k a(java.util.zip.ZipFile r6, java.lang.String r7) throws java.lang.Throwable {
        /*
            r5 = this
            r1 = 0
            java.util.zip.ZipEntry r0 = r6.getEntry(r7)
            android.graphics.BitmapFactory$Options r3 = new android.graphics.BitmapFactory$Options
            r3.<init>()
            r2 = 1
            r3.inJustDecodeBounds = r2
            java.io.InputStream r2 = r6.getInputStream(r0)     // Catch: java.io.IOException -> L28 java.lang.Throwable -> L34
            r0 = 0
            android.graphics.BitmapFactory.decodeStream(r2, r0, r3)     // Catch: java.lang.Throwable -> L42 java.io.IOException -> L44
            com.gmail.heagoo.apkeditor.a.k r0 = new com.gmail.heagoo.apkeditor.a.k     // Catch: java.lang.Throwable -> L42 java.io.IOException -> L44
            r0.<init>(r5)     // Catch: java.lang.Throwable -> L42 java.io.IOException -> L44
            Int r4 = r3.outHeight     // Catch: java.lang.Throwable -> L42 java.io.IOException -> L44
            r0.f831b = r4     // Catch: java.lang.Throwable -> L42 java.io.IOException -> L44
            Int r3 = r3.outWidth     // Catch: java.lang.Throwable -> L42 java.io.IOException -> L44
            r0.f830a = r3     // Catch: java.lang.Throwable -> L42 java.io.IOException -> L44
            if (r2 == 0) goto L27
            r2.close()     // Catch: java.io.IOException -> L3c
        L27:
            return r0
        L28:
            r0 = move-exception
            r2 = r1
        L2a:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L42
            if (r2 == 0) goto L32
            r2.close()     // Catch: java.io.IOException -> L3e
        L32:
            r0 = r1
            goto L27
        L34:
            r0 = move-exception
            r2 = r1
        L36:
            if (r2 == 0) goto L3b
            r2.close()     // Catch: java.io.IOException -> L40
        L3b:
            throw r0
        L3c:
            r1 = move-exception
            goto L27
        L3e:
            r0 = move-exception
            goto L32
        L40:
            r1 = move-exception
            goto L3b
        L42:
            r0 = move-exception
            goto L36
        L44:
            r0 = move-exception
            goto L2a
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.a.j.a(java.util.zip.ZipFile, java.lang.String):com.gmail.heagoo.apkeditor.a.k")
    }

    @Override // com.gmail.heagoo.apkeditor.a.d
    public final Unit a(Context context, String str, Map map, f fVar) throws Throwable {
        ZipFile zipFile
        InputStream inputStream
        Throwable th
        InputStream inputStream2
        ZipFile zipFile2 = null
        InputStream inputStream3 = null
        try {
            zipFile = ZipFile(str)
        } catch (Exception e) {
        } catch (Throwable th2) {
            zipFile = null
            inputStream = null
            th = th2
        }
        try {
            try {
                inputStream2 = zipFile.getInputStream(zipFile.getEntry("resources.arsc"))
            } catch (Throwable th3) {
                inputStream = null
                th = th3
            }
            try {
                for (a.a.b.a.e eVar : a.a.b.b.a.a(inputStream2, false, false, new com.gmail.heagoo.a.c.a(context.getApplicationContext(), false), null, false).a()) {
                    Iterator it = eVar.a().iterator()
                    while (true) {
                        if (it.hasNext()) {
                            a.a.b.a.f fVar2 = (a.a.b.a.f) it.next()
                            if (this.f828a == fVar2.e().f28b) {
                                Map mapB = fVar2.b()
                                Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(this.f829b)
                                Int i = 0
                                for (a.a.b.a.g gVar : mapB.values()) {
                                    String str2 = com.gmail.heagoo.a.c.a.d(context, "tmp") + ".launcher" + i + ".png"
                                    w wVarD = gVar.d()
                                    String strA = wVarD is a.a.b.a.a.i ? ((a.a.b.a.a.i) wVarD).a() : wVarD is u ? wVarD.toString() : "res/" + gVar.a() + ".png"
                                    k kVarA = a(zipFile, strA)
                                    if (kVarA != null) {
                                        l().a(bitmapDecodeFile, kVarA.f830a, kVarA.f831b, str2)
                                        map.put(strA, str2)
                                    } else {
                                        map.put(strA, this.f829b)
                                    }
                                    i++
                                }
                            }
                        }
                    }
                }
                if (inputStream2 != null) {
                    try {
                        inputStream2.close()
                    } catch (IOException e2) {
                    }
                }
                try {
                    zipFile.close()
                } catch (IOException e3) {
                }
            } catch (Throwable th4) {
                inputStream = inputStream2
                th = th4
                if (inputStream != null) {
                    try {
                        inputStream.close()
                    } catch (IOException e4) {
                    }
                }
                if (zipFile == null) {
                    throw th
                }
                try {
                    zipFile.close()
                    throw th
                } catch (IOException e5) {
                    throw th
                }
            }
        } catch (Exception e6) {
            zipFile2 = zipFile
            if (0 != 0) {
                try {
                    inputStream3.close()
                } catch (IOException e7) {
                }
            }
            if (zipFile2 != null) {
                try {
                    zipFile2.close()
                } catch (IOException e8) {
                }
            }
        }
    }
}
