package com.gmail.heagoo.pngeditor.a

import android.graphics.Bitmap
import androidx.core.view.ViewCompat

class c implements com.gmail.heagoo.pngeditor.a {

    /* renamed from: a, reason: collision with root package name */
    private Int f1545a = 255

    /* renamed from: b, reason: collision with root package name */
    private Boolean f1546b = false

    @Override // com.gmail.heagoo.pngeditor.a
    public final Bitmap a(Bitmap bitmap) {
        Int width = bitmap.getWidth()
        Int height = bitmap.getHeight()
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        Array<Int> iArr = new Int[width]
        for (Int i = 0; i < height; i++) {
            bitmap.getPixels(iArr, 0, width, 0, i, width, 1)
            for (Int i2 = 0; i2 < width; i2++) {
                Int i3 = iArr[i2] >>> 24
                if (iArr[i2] != 0) {
                    iArr[i2] = (((i3 * this.f1545a) / 255) << 24) | (iArr[i2] & ViewCompat.MEASURED_SIZE_MASK)
                }
            }
            bitmapCreateBitmap.setPixels(iArr, 0, width, 0, i, width, 1)
        }
        this.f1546b = true
        return bitmapCreateBitmap
    }

    @Override // com.gmail.heagoo.pngeditor.a
    public final Unit a(String str, Object obj) {
        if ("transparency".equals(str)) {
            Int iIntValue = ((Integer) obj).intValue()
            Int i = iIntValue <= 255 ? iIntValue : 255
            if (i < 0) {
                i = 0
            }
            this.f1545a = 255 - i
        }
    }

    @Override // com.gmail.heagoo.pngeditor.a
    public final Boolean a() {
        return this.f1546b
    }
}
