package com.a.b.b.a

import java.util.ArrayList
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map

final class e implements com.a.b.a.d.i {

    /* renamed from: a, reason: collision with root package name */
    private Map f259a = HashMap()

    constructor() {
        Iterator it = a.l.iterator()
        while (it.hasNext()) {
            String strF = a.f((String) it.next())
            String strB = b(strF)
            List arrayList = (List) this.f259a.get(strB)
            if (arrayList == null) {
                arrayList = ArrayList(1)
                this.f259a.put(strB, arrayList)
            }
            arrayList.add(strF)
        }
    }

    private fun b(String str) {
        Int iLastIndexOf = str.lastIndexOf(47)
        return iLastIndexOf >= 0 ? str.substring(iLastIndexOf + 1) : str
    }

    @Override // com.a.b.a.d.i
    public final Boolean a(String str) {
        if (!str.endsWith(".class")) {
            return true
        }
        String strF = a.f(str)
        List list = (List) this.f259a.get(b(strF))
        if (list != null) {
            Iterator it = list.iterator()
            while (it.hasNext()) {
                if (strF.endsWith((String) it.next())) {
                    return true
                }
            }
        }
        return false
    }
}
