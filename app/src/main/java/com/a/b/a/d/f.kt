package com.a.b.a.d

import java.io.File
import java.util.Comparator

final class f implements Comparator {
    f(d dVar) {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ Int compare(Object obj, Object obj2) {
        return ((File) obj).getName().replace('$', '0').replace("package-info", "").compareTo(((File) obj2).getName().replace('$', '0').replace("package-info", ""))
    }
}
