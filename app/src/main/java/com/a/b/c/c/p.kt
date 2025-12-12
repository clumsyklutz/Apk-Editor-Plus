package com.a.b.c.c

import java.util.Comparator

final class p implements Comparator {
    p(n nVar) {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ Int compare(Object obj, Object obj2) {
        return ((com.a.b.c.b.u) obj).g() - ((com.a.b.c.b.u) obj2).g()
    }

    @Override // java.util.Comparator
    public final Boolean equals(Object obj) {
        return obj == this
    }
}
