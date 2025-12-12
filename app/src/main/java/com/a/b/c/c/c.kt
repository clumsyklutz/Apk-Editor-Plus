package com.a.b.c.c

import java.util.Comparator

final class c implements Comparator {
    private constructor() {
    }

    /* synthetic */ c(Byte b2) {
        this()
    }

    @Override // java.util.Comparator
    public final /* synthetic */ Int compare(Object obj, Object obj2) {
        Int i = ((a) obj).c.i()
        Int i2 = ((a) obj2).c.i()
        if (i < i2) {
            return -1
        }
        return i > i2 ? 1 : 0
    }
}
