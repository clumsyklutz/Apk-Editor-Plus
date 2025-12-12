package com.gmail.heagoo.apkeditor.se

import java.io.Closeable
import java.io.IOException
import java.util.zip.ZipFile

class aa {

    /* renamed from: a, reason: collision with root package name */
    private ZipFile f1252a

    /* renamed from: b, reason: collision with root package name */
    private Int f1253b
    private Int c

    constructor(ZipFile zipFile) {
        this.f1252a = zipFile
    }

    private fun a(Closeable closeable) throws IOException {
        try {
            closeable.close()
        } catch (IOException e) {
        }
    }

    public final Int a() {
        return this.f1253b
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.graphics.Bitmap a(java.lang.String r7, Int r8, Int r9) throws java.lang.Throwable {
        /*
            r6 = this
            r1 = 0
            r4 = 1
            java.util.zip.ZipFile r0 = r6.f1252a     // Catch: java.lang.Exception -> L38 java.lang.Throwable -> L44
            java.util.zip.ZipEntry r0 = r0.getEntry(r7)     // Catch: java.lang.Exception -> L38 java.lang.Throwable -> L44
            java.util.zip.ZipFile r2 = r6.f1252a     // Catch: java.lang.Exception -> L38 java.lang.Throwable -> L44
            java.io.InputStream r2 = r2.getInputStream(r0)     // Catch: java.lang.Exception -> L38 java.lang.Throwable -> L44
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeStream(r2)     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            Int r3 = r0.getWidth()     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            r6.f1253b = r3     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            Int r3 = r0.getHeight()     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            r6.c = r3     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            Int r3 = r6.f1253b     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            Int r3 = r3 / r8
            Int r5 = r6.c     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            Int r5 = r5 / r9
            if (r3 >= r5) goto L36
        L26:
            if (r3 > 0) goto L29
            r3 = r4
        L29:
            if (r3 <= r4) goto L30
            r3 = 2
            android.graphics.Bitmap r0 = android.media.ThumbnailUtils.extractThumbnail(r0, r8, r9, r3)     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
        L30:
            if (r2 == 0) goto L35
            a(r2)
        L35:
            return r0
        L36:
            r3 = r5
            goto L26
        L38:
            r0 = move-exception
            r2 = r1
        L3a:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L4c
            if (r2 == 0) goto L42
            a(r2)
        L42:
            r0 = r1
            goto L35
        L44:
            r0 = move-exception
            r2 = r1
        L46:
            if (r2 == 0) goto L4b
            a(r2)
        L4b:
            throw r0
        L4c:
            r0 = move-exception
            goto L46
        L4e:
            r0 = move-exception
            goto L3a
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.se.aa.a(java.lang.String, Int, Int):android.graphics.Bitmap")
    }

    public final Int b() {
        return this.c
    }
}
