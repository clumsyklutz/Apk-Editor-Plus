package com.gmail.heagoo.apkeditor

import android.util.LruCache

final class fg extends LruCache {
    fg(ff ffVar, Int i) {
        super(64)
    }

    @Override // android.util.LruCache
    protected final /* synthetic */ Unit entryRemoved(Boolean z, Object obj, Object obj2, Object obj3) {
        fi fiVar = (fi) obj2
        if (fiVar.f1068a != null) {
            fiVar.f1068a.recycle()
        }
    }
}
