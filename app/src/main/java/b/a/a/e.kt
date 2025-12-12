package b.a.a

import java.util.ArrayList
import java.util.HashMap
import java.util.Iterator
import java.util.Map

class e implements Iterable {

    /* renamed from: a, reason: collision with root package name */
    private val f91a = HashMap()

    /* renamed from: b, reason: collision with root package name */
    private val f92b = ArrayList()

    constructor(a aVar, Map map) {
        String str = (String) map.get("cookie")
        if (str != null) {
            Array<String> strArrSplit = str.split(";")
            for (String str2 : strArrSplit) {
                Array<String> strArrSplit2 = str2.trim().split("=")
                if (strArrSplit2.length == 2) {
                    this.f91a.put(strArrSplit2[0], strArrSplit2[1])
                }
            }
        }
    }

    public final Unit a(j jVar) {
        Iterator it = this.f92b.iterator()
        while (it.hasNext()) {
            jVar.a("Set-Cookie", ((d) it.next()).a())
        }
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return this.f91a.keySet().iterator()
    }
}
