package com.gmail.heagoo.apkeditor.se

import android.util.LruCache

final class j extends LruCache {
    j(i iVar, Int i) {
        super(32)
    }

    @Override // android.util.LruCache
    protected final /* synthetic */ Unit entryRemoved(Boolean z, Object obj, Object obj2, Object obj3) {
        o oVar = (o) obj2
        if (oVar.f1271a != null) {
            oVar.f1271a.recycle()
        }
    }
}
