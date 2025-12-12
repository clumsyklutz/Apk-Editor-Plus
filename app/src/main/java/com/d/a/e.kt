package com.d.a

import java.util.Map
import java.util.concurrent.ConcurrentHashMap

class e implements k {
    @Override // com.d.a.k
    public final Object a(Object obj, j jVar, Map map) {
        ConcurrentHashMap concurrentHashMap = ConcurrentHashMap()
        for (Map.Entry entry : ((ConcurrentHashMap) obj).entrySet()) {
            concurrentHashMap.put(jVar.a(entry.getKey(), map), jVar.a(entry.getValue(), map))
        }
        return concurrentHashMap
    }
}
