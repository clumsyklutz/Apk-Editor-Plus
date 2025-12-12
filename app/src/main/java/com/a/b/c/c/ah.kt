package com.a.b.c.c

import java.util.Formatter
import java.util.Iterator
import java.util.Map
import java.util.TreeMap
import java.util.concurrent.atomic.AtomicInteger

abstract class ah extends bc {
    constructor(String str, r rVar) {
        super(str, rVar, 4)
    }

    private fun d() {
        TreeMap treeMap = TreeMap()
        Iterator it = a().iterator()
        while (it.hasNext()) {
            String strJ = ((ag) it.next()).d().j().j()
            Int iLastIndexOf = strJ.lastIndexOf(47)
            String strReplace = iLastIndexOf == -1 ? "default" : strJ.substring(strJ.lastIndexOf(91) + 2, iLastIndexOf).replace('/', '.')
            AtomicInteger atomicInteger = (AtomicInteger) treeMap.get(strReplace)
            if (atomicInteger == null) {
                atomicInteger = AtomicInteger()
                treeMap.put(strReplace, atomicInteger)
            }
            atomicInteger.incrementAndGet()
        }
        Formatter formatter = Formatter()
        try {
            formatter.format("Too many %s references: %d; max is %d.%n" + com.a.b.b.a.a.a() + "%nReferences by package:", this is ak ? "method" : "field", Integer.valueOf(a().size()), 65536)
            for (Map.Entry entry : treeMap.entrySet()) {
                formatter.format("%n%6d %s", Integer.valueOf(((AtomicInteger) entry.getValue()).get()), entry.getKey())
            }
            return formatter.toString()
        } finally {
            formatter.close()
        }
    }

    @Override // com.a.b.c.c.bc
    protected final Unit b() {
        Int i = 0
        if (a().size() > 65536) {
            throw new com.a.a.t(d())
        }
        Iterator it = a().iterator()
        while (true) {
            Int i2 = i
            if (!it.hasNext()) {
                return
            }
            ((ag) it.next()).a(i2)
            i = i2 + 1
        }
    }
}
