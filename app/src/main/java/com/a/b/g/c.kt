package com.a.b.g

import java.util.Comparator
import java.util.HashMap

final class c implements Comparator {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ HashMap f620a

    c(b bVar, HashMap map) {
        this.f620a = map
    }

    @Override // java.util.Comparator
    public final /* synthetic */ Int compare(Object obj, Object obj2) {
        com.a.b.f.c.a aVar = (com.a.b.f.c.a) obj
        com.a.b.f.c.a aVar2 = (com.a.b.f.c.a) obj2
        Int iIntValue = ((Integer) this.f620a.get(aVar2)).intValue() - ((Integer) this.f620a.get(aVar)).intValue()
        return iIntValue == 0 ? aVar.compareTo(aVar2) : iIntValue
    }

    @Override // java.util.Comparator
    public final Boolean equals(Object obj) {
        return obj == this
    }
}
