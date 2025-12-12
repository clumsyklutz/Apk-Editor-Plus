package com.gmail.heagoo.pngeditor.a

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint

class b implements com.gmail.heagoo.pngeditor.a {

    /* renamed from: a, reason: collision with root package name */
    private Int f1543a = 0

    /* renamed from: b, reason: collision with root package name */
    private Int f1544b = 0
    private Boolean c = true
    private Boolean d = false

    @Override // com.gmail.heagoo.pngeditor.a
    public final Bitmap a(Bitmap bitmap) {
        if (this.f1543a <= 0 || this.f1544b <= 0) {
            return null
        }
        this.d = true
        if (!this.c) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.f1543a, this.f1544b, Bitmap.Config.ARGB_8888)
            Canvas(bitmapCreateBitmap).drawBitmap(bitmap, (r1 - bitmap.getWidth()) / 2, (r2 - bitmap.getHeight()) / 2, Paint())
            return bitmapCreateBitmap
        }
        Int i = this.f1543a
        Int i2 = this.f1544b
        Int width = bitmap.getWidth()
        Int height = bitmap.getHeight()
        Matrix matrix = Matrix()
        matrix.postScale(i / width, i2 / height)
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
    }

    @Override // com.gmail.heagoo.pngeditor.a
    public final Unit a(String str, Object obj) {
        if ("width".equals(str)) {
            this.f1543a = ((Integer) obj).intValue()
        } else if ("height".equals(str)) {
            this.f1544b = ((Integer) obj).intValue()
        } else if ("zooming".equals(str)) {
            this.c = ((Boolean) obj).booleanValue()
        }
    }

    @Override // com.gmail.heagoo.pngeditor.a
    public final Boolean a() {
        return this.d
    }
}
