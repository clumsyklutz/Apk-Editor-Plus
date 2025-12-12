package com.a.b.e

import java.util.Comparator

final class v implements Comparator {
    v() {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ Int compare(Object obj, Object obj2) {
        u uVar = (u) obj
        u uVar2 = (u) obj2
        if (uVar == uVar2) {
            return 0
        }
        if (uVar2 == null) {
            return -1
        }
        if (uVar == null) {
            return 1
        }
        return uVar.d != uVar2.d ? uVar.d - uVar2.d : uVar.c() - uVar2.c()
    }
}
