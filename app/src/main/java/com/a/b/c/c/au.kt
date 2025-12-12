package com.a.b.c.c

import java.util.HashMap
import java.util.Iterator
import java.util.TreeMap

class au {

    /* renamed from: a, reason: collision with root package name */
    private val f372a = HashMap(50)

    public final String a() {
        StringBuilder sb = StringBuilder()
        sb.append("Statistics:\n")
        TreeMap treeMap = TreeMap()
        for (av avVar : this.f372a.values()) {
            treeMap.put(avVar.f373a, avVar)
        }
        Iterator it = treeMap.values().iterator()
        while (it.hasNext()) {
            sb.append(((av) it.next()).a())
        }
        return sb.toString()
    }

    public final Unit a(ad adVar) {
        String strD = adVar.a().d()
        av avVar = (av) this.f372a.get(strD)
        if (avVar == null) {
            this.f372a.put(strD, av(adVar, strD))
        } else {
            avVar.a(adVar)
        }
    }

    public final Unit a(com.a.b.h.r rVar) {
        if (this.f372a.size() == 0) {
            return
        }
        rVar.a(0, "\nstatistics:\n")
        TreeMap treeMap = TreeMap()
        for (av avVar : this.f372a.values()) {
            treeMap.put(avVar.f373a, avVar)
        }
        Iterator it = treeMap.values().iterator()
        while (it.hasNext()) {
            rVar.a(((av) it.next()).a())
        }
    }
}
