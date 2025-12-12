package org.c

import com.d.a.m
import java.util.concurrent.ConcurrentHashMap

class a implements m {

    /* renamed from: a, reason: collision with root package name */
    private org.c.b.a f1585a

    /* renamed from: b, reason: collision with root package name */
    private ConcurrentHashMap f1586b

    constructor(org.c.b.a aVar) {
        this(aVar, true)
    }

    private constructor(org.c.b.a aVar, Boolean z) {
        if (aVar == null) {
            throw IllegalArgumentException("A strategy can't be null")
        }
        this.f1585a = aVar
        this.f1586b = ConcurrentHashMap()
    }

    @Override // com.d.a.m
    public final Object a(Class cls) {
        org.c.a.a aVarA
        if (this.f1586b == null) {
            aVarA = this.f1585a.a(cls)
        } else {
            aVarA = (org.c.a.a) this.f1586b.get(cls.getName())
            if (aVarA == null) {
                org.c.a.a aVarA2 = this.f1585a.a(cls)
                aVarA = (org.c.a.a) this.f1586b.putIfAbsent(cls.getName(), aVarA2)
                if (aVarA == null) {
                    aVarA = aVarA2
                }
            }
        }
        return aVarA.a()
    }

    fun toString() {
        return getClass().getName() + " using " + this.f1585a.getClass().getName() + (this.f1586b == null ? " without" : " with") + " caching"
    }
}
