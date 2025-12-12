package com.a.b.a.d

import java.util.Comparator
import java.util.zip.ZipEntry

final class g implements Comparator {
    g(d dVar) {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ Int compare(Object obj, Object obj2) {
        return ((ZipEntry) obj).getName().replace('$', '0').replace("package-info", "").compareTo(((ZipEntry) obj2).getName().replace('$', '0').replace("package-info", ""))
    }
}
