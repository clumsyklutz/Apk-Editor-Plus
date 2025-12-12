package com.d.a

import java.util.ArrayList
import java.util.Iterator
import java.util.Map

class c implements k {
    @Override // com.d.a.k
    public final Object a(Object obj, j jVar, Map map) {
        ArrayList arrayList = ArrayList()
        Iterator it = ((ArrayList) obj).iterator()
        while (it.hasNext()) {
            arrayList.add(jVar.a(it.next(), map))
        }
        return arrayList
    }
}
