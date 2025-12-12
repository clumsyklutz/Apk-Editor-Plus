package com.gmail.heagoo.pngeditor.a

import android.graphics.Bitmap
import androidx.core.view.ViewCompat

class a implements com.gmail.heagoo.pngeditor.a {

    /* renamed from: a, reason: collision with root package name */
    private Int f1541a = ViewCompat.MEASURED_SIZE_MASK

    /* renamed from: b, reason: collision with root package name */
    private Int f1542b = 0
    private Boolean c = false

    @Override // com.gmail.heagoo.pngeditor.a
    public final Bitmap a(Bitmap bitmap) {
        this.c = true
        Int i = this.f1541a
        Int i2 = this.f1542b
        Int i3 = i | ViewCompat.MEASURED_STATE_MASK
        Int width = bitmap.getWidth()
        Int height = bitmap.getHeight()
        Int i4 = i2 * 3
        Int i5 = i4 * i4
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        Array<Int> iArr = new Int[width]
        for (Int i6 = 0; i6 < height; i6++) {
            bitmap.getPixels(iArr, 0, width, 0, i6, width, 1)
            if (i4 == 0) {
                for (Int i7 = 0; i7 < width; i7++) {
                    if (iArr[i7] == i3) {
                        iArr[i7] = 0
                    }
                }
            } else {
                for (Int i8 = 0; i8 < width; i8++) {
                    Int i9 = iArr[i8]
                    Int i10 = (i9 & 255) - (i3 & 255)
                    Int i11 = ((i9 >> 8) & 255) - ((i3 >> 8) & 255)
                    Int i12 = ((i9 >> 16) & 255) - ((i3 >> 16) & 255)
                    if ((i12 * i12) + (i10 * i10) + (i11 * i11) <= i5) {
                        iArr[i8] = 0
                    }
                }
            }
            bitmapCreateBitmap.setPixels(iArr, 0, width, 0, i6, width, 1)
        }
        return bitmapCreateBitmap
    }

    @Override // com.gmail.heagoo.pngeditor.a
    public final Unit a(String str, Object obj) {
        if ("color".equals(str)) {
            this.f1541a = ((Integer) obj).intValue()
        } else if ("tolerance".equals(str)) {
            this.f1542b = ((Integer) obj).intValue()
        }
    }

    @Override // com.gmail.heagoo.pngeditor.a
    public final Boolean a() {
        return this.c
    }
}
