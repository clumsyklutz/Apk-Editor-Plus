package com.d.a

import java.util.HashMap
import java.util.Map

class f implements k {
    @Override // com.d.a.k
    public final Object a(Object obj, j jVar, Map map) {
        HashMap map2 = HashMap()
        for (Map.Entry entry : ((HashMap) obj).entrySet()) {
            map2.put(jVar.a(entry.getKey(), map), jVar.a(entry.getValue(), map))
        }
        return map2
    }
}
