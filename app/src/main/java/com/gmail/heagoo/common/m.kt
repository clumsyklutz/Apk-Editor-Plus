package com.gmail.heagoo.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils

class m {

    /* renamed from: a, reason: collision with root package name */
    private Int f1459a

    /* renamed from: b, reason: collision with root package name */
    private Int f1460b

    public final Int a() {
        return this.f1459a
    }

    public final Bitmap a(String str, Int i, Int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(str, options)
        options.inJustDecodeBounds = false
        this.f1460b = options.outHeight
        this.f1459a = options.outWidth
        Int i3 = this.f1459a / 32
        Int i4 = this.f1460b / 32
        if (i3 >= i4) {
            i3 = i4
        }
        options.inSampleSize = i3 > 0 ? i3 : 1
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(str, options), 32, 32, 2)
    }

    public final Int b() {
        return this.f1460b
    }
}
