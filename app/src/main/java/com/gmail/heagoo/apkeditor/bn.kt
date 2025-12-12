package com.gmail.heagoo.apkeditor

import java.util.Comparator
import java.util.Locale

final class bn implements Comparator {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Locale f899a

    bn(bl blVar, Locale locale) {
        this.f899a = locale
    }

    @Override // java.util.Comparator
    public final /* synthetic */ Int compare(Object obj, Object obj2) {
        return ((bk) obj).c.toLowerCase(this.f899a).compareTo(((bk) obj2).c.toLowerCase(this.f899a))
    }
}
