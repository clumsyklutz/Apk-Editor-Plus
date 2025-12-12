package b.a.a

import java.util.ArrayList
import java.util.Collections
import java.util.Iterator
import java.util.List

class b {

    /* renamed from: a, reason: collision with root package name */
    private Long f85a

    /* renamed from: b, reason: collision with root package name */
    private val f86b = Collections.synchronizedList(ArrayList())

    fun a() {
        Iterator it = ArrayList(this.f86b).iterator()
        while (it.hasNext()) {
            ((c) it.next()).a()
        }
    }

    fun a(c cVar) {
        this.f86b.remove(cVar)
    }

    fun b(c cVar) {
        this.f85a++
        Thread thread = Thread(cVar)
        thread.setDaemon(true)
        thread.setName("NanoHttpd Request Processor (#" + this.f85a + ")")
        this.f86b.add(cVar)
        thread.start()
    }
}
