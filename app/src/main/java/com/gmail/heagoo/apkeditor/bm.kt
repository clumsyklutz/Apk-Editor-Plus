package com.gmail.heagoo.apkeditor

import android.graphics.drawable.Drawable
import android.util.LruCache

final class bm extends LruCache {
    bm(bl blVar, Int i) {
        super(32)
    }

    @Override // android.util.LruCache
    protected final /* synthetic */ Unit entryRemoved(Boolean z, Object obj, Object obj2, Object obj3) {
        ((Drawable) obj2).setCallback(null)
    }
}
