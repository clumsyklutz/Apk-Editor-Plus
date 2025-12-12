package com.a.b.g

import java.util.Comparator

class aj implements Comparator {
    @Override // java.util.Comparator
    public final /* synthetic */ Int compare(Object obj, Object obj2) {
        Int i = ((ai) obj).f
        Int i2 = ((ai) obj2).f
        if (i < i2) {
            return -1
        }
        return i > i2 ? 1 : 0
    }
}
