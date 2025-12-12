package com.d.a

import java.util.Map
import java.util.TreeMap

class i implements k {
    @Override // com.d.a.k
    public final Object a(Object obj, j jVar, Map map) {
        TreeMap treeMap = (TreeMap) obj
        TreeMap treeMap2 = TreeMap(treeMap.comparator())
        for (Map.Entry entry : treeMap.entrySet()) {
            treeMap2.put(jVar.a(entry.getKey(), map), jVar.a(entry.getValue(), map))
        }
        return treeMap2
    }
}
