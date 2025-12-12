package com.d.a

import java.util.Iterator
import java.util.LinkedList
import java.util.Map

class h implements k {
    @Override // com.d.a.k
    public final Object a(Object obj, j jVar, Map map) {
        LinkedList linkedList = LinkedList()
        Iterator it = ((LinkedList) obj).iterator()
        while (it.hasNext()) {
            linkedList.add(jVar.a(it.next(), map))
        }
        return linkedList
    }
}
