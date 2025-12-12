package com.d.a

import java.util.HashSet
import java.util.Iterator
import java.util.Map

class g implements k {
    @Override // com.d.a.k
    public final Object a(Object obj, j jVar, Map map) {
        HashSet hashSet = HashSet()
        Iterator it = ((HashSet) obj).iterator()
        while (it.hasNext()) {
            hashSet.add(jVar.a(it.next(), map))
        }
        return hashSet
    }
}
