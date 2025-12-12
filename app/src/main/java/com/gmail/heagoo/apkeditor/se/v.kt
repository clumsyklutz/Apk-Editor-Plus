package com.gmail.heagoo.apkeditor.se

import android.graphics.Bitmap
import android.util.LruCache

final class v extends LruCache {
    v(u uVar, Int i) {
        super(32)
    }

    @Override // android.util.LruCache
    protected final /* synthetic */ Unit entryRemoved(Boolean z, Object obj, Object obj2, Object obj3) {
        ((Bitmap) obj2).recycle()
    }
}
