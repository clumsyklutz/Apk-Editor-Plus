package com.gmail.heagoo.imageviewlib

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.ViewGroup
import android.widget.LinearLayout
import com.c.a.f
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R

class ViewZipImageActivity extends Activity {

    /* renamed from: a, reason: collision with root package name */
    private f f1486a

    /* renamed from: b, reason: collision with root package name */
    private String f1487b
    private String c
    private String d
    private Int e
    private Int f

    /* JADX WARN: Removed duplicated region for block: B:47:0x0049 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0044 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.graphics.Bitmap a() throws java.lang.Throwable {
        /*
            r5 = this
            r0 = 0
            java.util.zip.ZipFile r3 = new java.util.zip.ZipFile     // Catch: java.lang.Exception -> L1f java.lang.Throwable -> L3e
            java.lang.String r1 = r5.c     // Catch: java.lang.Exception -> L1f java.lang.Throwable -> L3e
            r3.<init>(r1)     // Catch: java.lang.Exception -> L1f java.lang.Throwable -> L3e
            java.lang.String r1 = r5.d     // Catch: java.lang.Throwable -> L57 java.lang.Exception -> L5d
            java.util.zip.ZipEntry r1 = r3.getEntry(r1)     // Catch: java.lang.Throwable -> L57 java.lang.Exception -> L5d
            java.io.InputStream r2 = r3.getInputStream(r1)     // Catch: java.lang.Throwable -> L57 java.lang.Exception -> L5d
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeStream(r2)     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L60
            if (r2 == 0) goto L1b
            r2.close()     // Catch: java.io.IOException -> L4d
        L1b:
            r3.close()     // Catch: java.io.IOException -> L4f
        L1e:
            return r0
        L1f:
            r1 = move-exception
            r2 = r0
            r3 = r0
        L22:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L5b
            java.lang.String r1 = r1.getMessage()     // Catch: java.lang.Throwable -> L5b
            r4 = 0
            android.widget.Toast r1 = android.widget.Toast.makeText(r5, r1, r4)     // Catch: java.lang.Throwable -> L5b
            r1.show()     // Catch: java.lang.Throwable -> L5b
            if (r2 == 0) goto L36
            r2.close()     // Catch: java.io.IOException -> L51
        L36:
            if (r3 == 0) goto L1e
            r3.close()     // Catch: java.io.IOException -> L3c
            goto L1e
        L3c:
            r1 = move-exception
            goto L1e
        L3e:
            r1 = move-exception
            r2 = r0
            r3 = r0
            r0 = r1
        L42:
            if (r2 == 0) goto L47
            r2.close()     // Catch: java.io.IOException -> L53
        L47:
            if (r3 == 0) goto L4c
            r3.close()     // Catch: java.io.IOException -> L55
        L4c:
            throw r0
        L4d:
            r1 = move-exception
            goto L1b
        L4f:
            r1 = move-exception
            goto L1e
        L51:
            r1 = move-exception
            goto L36
        L53:
            r1 = move-exception
            goto L47
        L55:
            r1 = move-exception
            goto L4c
        L57:
            r1 = move-exception
            r2 = r0
            r0 = r1
            goto L42
        L5b:
            r0 = move-exception
            goto L42
        L5d:
            r1 = move-exception
            r2 = r0
            goto L22
        L60:
            r1 = move-exception
            goto L22
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.imageviewlib.ViewZipImageActivity.a():android.graphics.Bitmap")
    }

    @Override // android.app.Activity
    fun onCreate(Bundle bundle) {
        super.onCreate(bundle)
        Intent intent = getIntent()
        if (intent == null) {
            finish()
            return
        }
        if (intent.getBooleanExtra("fullScreen", false)) {
            getWindow().setFlags(1024, 1024)
        }
        setContentView(R.layout.imageviewlib_activity_empty)
        this.c = a.a(intent, "zipFilePath")
        this.d = a.a(intent, "entryName")
        this.f1487b = a.a(intent, "imageFilePath")
        Bitmap bitmapDecodeFile = this.f1487b != null ? BitmapFactory.decodeFile(this.f1487b) : a()
        if (bitmapDecodeFile == null) {
            finish()
            return
        }
        Display defaultDisplay = getWindowManager().getDefaultDisplay()
        Point point = Point()
        try {
            defaultDisplay.getSize(point)
        } catch (NoSuchMethodError e) {
            point.x = defaultDisplay.getWidth()
            point.y = defaultDisplay.getHeight()
        }
        this.e = point.x
        this.f = point.y
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1)
        this.f1486a = f(this, bitmapDecodeFile)
        this.f1486a.setLayoutParams(layoutParams)
        if (bitmapDecodeFile.getWidth() <= this.e && bitmapDecodeFile.getHeight() <= this.f) {
            this.f1486a.b(1.0f)
            this.f1486a.b(this.e / 2.0f, this.f / 2.0f)
        }
        ((ViewGroup) findViewById(R.id.layout)).addView(this.f1486a)
    }
}
