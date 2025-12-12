package com.a.b.e

import com.a.a.ab
import com.a.a.w
import com.a.a.x
import com.a.a.y
import com.a.a.z
import java.util.HashMap

class m {

    /* renamed from: a, reason: collision with root package name */
    public final Array<Int> f471a

    /* renamed from: b, reason: collision with root package name */
    public final Array<Short> f472b
    public final Array<Short> c
    public final Array<Short> d
    public final Array<Short> e
    private final com.a.a.i f
    private val g = HashMap()
    private val h = HashMap()
    private val i = HashMap()
    private val j = HashMap()
    private val k = HashMap()
    private val l = HashMap()

    constructor(com.a.a.i iVar, z zVar) {
        this.f = iVar
        this.f471a = new Int[zVar.f154b.f114b]
        this.f472b = new Short[zVar.c.f114b]
        this.c = new Short[zVar.d.f114b]
        this.d = new Short[zVar.e.f114b]
        this.e = new Short[zVar.f.f114b]
        this.g.put(0, 0)
        this.i.put(0, 0)
        this.k.put(0, 0)
        this.l.put(0, 0)
    }

    private fun j(Int i) {
        return ((Integer) this.g.get(Integer.valueOf(i))).intValue()
    }

    public final Int a(Int i) {
        if (i == -1) {
            return -1
        }
        return this.f471a[i]
    }

    public final com.a.a.a a(com.a.a.a aVar) {
        com.a.b.h.r rVar = new com.a.b.h.r(32)
        n(this, rVar).b(aVar.b())
        return new com.a.a.a(this.f, aVar.a(), new com.a.a.u(rVar.g()))
    }

    public final ab a(ab abVar) {
        if (abVar == ab.f115a) {
            return abVar
        }
        Array<Short> sArr = (Array<Short>) abVar.a().clone()
        for (Int i = 0; i < sArr.length; i++) {
            sArr[i] = (Short) b(sArr[i])
        }
        return ab(this.f, sArr)
    }

    public final com.a.a.e a(com.a.a.e eVar) {
        return new com.a.a.e(this.f, eVar.a(), b(eVar.b()), eVar.f(), b(eVar.c()), j(eVar.d()), eVar.g(), eVar.h(), eVar.i(), eVar.j())
    }

    public final w a(w wVar) {
        return w(this.f, b(wVar.a()), b(wVar.b()), a(wVar.c()))
    }

    public final x a(x xVar) {
        return x(this.f, b(xVar.a()), this.c[xVar.b()] & 65535, a(xVar.c()))
    }

    public final y a(y yVar) {
        return y(this.f, a(yVar.a()), b(yVar.b()), j(yVar.c()))
    }

    public final Unit a(Int i, Int i2) {
        if (i <= 0 || i2 <= 0) {
            throw IllegalArgumentException()
        }
        this.g.put(Integer.valueOf(i), Integer.valueOf(i2))
    }

    public final Int b(Int i) {
        if (i == -1) {
            return -1
        }
        return this.f472b[i] & 65535
    }

    public final Unit b(Int i, Int i2) {
        if (i <= 0 || i2 <= 0) {
            throw IllegalArgumentException()
        }
        this.h.put(Integer.valueOf(i), Integer.valueOf(i2))
    }

    public final Int c(Int i) {
        return this.d[i] & 65535
    }

    public final Unit c(Int i, Int i2) {
        if (i <= 0 || i2 <= 0) {
            throw IllegalArgumentException()
        }
        this.i.put(Integer.valueOf(i), Integer.valueOf(i2))
    }

    public final Int d(Int i) {
        return this.e[i] & 65535
    }

    public final Unit d(Int i, Int i2) {
        if (i <= 0 || i2 <= 0) {
            throw IllegalArgumentException()
        }
        this.j.put(Integer.valueOf(i), Integer.valueOf(i2))
    }

    public final Int e(Int i) {
        return ((Integer) this.h.get(Integer.valueOf(i))).intValue()
    }

    public final Unit e(Int i, Int i2) {
        if (i <= 0 || i2 <= 0) {
            throw IllegalArgumentException()
        }
        this.k.put(Integer.valueOf(i), Integer.valueOf(i2))
    }

    public final Int f(Int i) {
        return ((Integer) this.i.get(Integer.valueOf(i))).intValue()
    }

    public final Unit f(Int i, Int i2) {
        if (i <= 0 || i2 <= 0) {
            throw IllegalArgumentException()
        }
        this.l.put(Integer.valueOf(i), Integer.valueOf(i2))
    }

    public final Int g(Int i) {
        return ((Integer) this.j.get(Integer.valueOf(i))).intValue()
    }

    public final Int h(Int i) {
        return ((Integer) this.k.get(Integer.valueOf(i))).intValue()
    }

    public final Int i(Int i) {
        return ((Integer) this.l.get(Integer.valueOf(i))).intValue()
    }
}
