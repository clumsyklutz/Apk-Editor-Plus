package b.a.a

import java.io.File
import java.util.ArrayList
import java.util.Iterator
import java.util.List
import java.util.logging.Level

class r {

    /* renamed from: a, reason: collision with root package name */
    private val f106a = File(System.getProperty("java.io.tmpdir"))

    /* renamed from: b, reason: collision with root package name */
    private final List f107b

    constructor() {
        if (!this.f106a.exists()) {
            this.f106a.mkdirs()
        }
        this.f107b = ArrayList()
    }

    fun a() {
        Iterator it = this.f107b.iterator()
        while (it.hasNext()) {
            try {
                ((q) it.next()).a()
            } catch (Exception e) {
                a.g.log(Level.WARNING, "could not delete file ", (Throwable) e)
            }
        }
        this.f107b.clear()
    }

    fun b() {
        q qVar = q(this.f106a)
        this.f107b.add(qVar)
        return qVar
    }
}
