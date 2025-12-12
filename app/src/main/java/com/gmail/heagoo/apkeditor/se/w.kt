package com.gmail.heagoo.apkeditor.se

import java.util.Comparator

final class w implements Comparator {
    w(u uVar) {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ Int compare(Object obj, Object obj2) {
        y yVar = (y) obj
        y yVar2 = (y) obj2
        if (yVar.f1285b) {
            if (!yVar2.f1285b) {
                return -1
            }
        } else if (yVar2.f1285b) {
            return 1
        }
        return yVar.f1284a.compareTo(yVar2.f1284a)
    }
}
