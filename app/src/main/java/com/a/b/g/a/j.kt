package com.a.b.g.a

import com.a.b.f.b.r
import com.a.b.g.ac
import com.a.b.g.ai
import com.a.b.g.al
import com.a.b.g.an
import java.util.BitSet
import java.util.Iterator
import java.util.List

class j {

    /* renamed from: a, reason: collision with root package name */
    private final BitSet f580a

    /* renamed from: b, reason: collision with root package name */
    private final BitSet f581b
    private final Int c
    private final an d
    private final i e
    private ai f
    private Int g
    private Int h

    private constructor(an anVar, Int i, i iVar) {
        Int size = anVar.j().size()
        this.d = anVar
        this.c = i
        this.f580a = BitSet(size)
        this.f581b = BitSet(size)
        this.e = iVar
    }

    fun a(an anVar) {
        Int iG = anVar.g()
        i iVar = i(iG)
        for (Int i = 0; i < iG; i++) {
            j jVar = j(anVar, i, iVar)
            for (al alVar : jVar.d.d(jVar.c)) {
                jVar.h = l.d
                if (alVar is ac) {
                    Iterator it = ((ac) alVar).a(jVar.c, jVar.d).iterator()
                    while (it.hasNext()) {
                        jVar.f = (ai) it.next()
                        jVar.h = l.c
                        jVar.a()
                    }
                } else {
                    jVar.f = alVar.o()
                    jVar.g = jVar.f.c().indexOf(alVar)
                    if (jVar.g < 0) {
                        throw RuntimeException("insn not found in it's own block")
                    }
                    jVar.h = l.f583a
                    jVar.a()
                }
            }
            while (true) {
                Int iNextSetBit = jVar.f581b.nextSetBit(0)
                if (iNextSetBit >= 0) {
                    jVar.f = (ai) jVar.d.j().get(iNextSetBit)
                    jVar.f581b.clear(iNextSetBit)
                    jVar.h = l.c
                    jVar.a()
                }
            }
        }
        a(anVar, iVar)
        return iVar
    }

    private fun a() {
        while (this.h != l.d) {
            switch (k.f582a[this.h - 1]) {
                case 1:
                    this.h = l.d
                    if (this.g != 0) {
                        this.g--
                        this.h = l.f584b
                        break
                    } else {
                        this.f.d(this.c)
                        this.f581b.or(this.f.g())
                        break
                    }
                case 2:
                    this.h = l.d
                    al alVar = (al) this.f.c().get(this.g)
                    r rVarN = alVar.n()
                    if (!alVar.b(this.c)) {
                        if (rVarN != null) {
                            this.e.a(this.c, rVarN.g())
                        }
                        this.h = l.f583a
                        break
                    } else {
                        break
                    }
                case 3:
                    this.h = l.d
                    if (!this.f580a.get(this.f.e())) {
                        this.f580a.set(this.f.e())
                        this.f.c(this.c)
                        this.g = this.f.c().size() - 1
                        this.h = l.f584b
                        break
                    } else {
                        break
                    }
            }
        }
    }

    private fun a(an anVar, i iVar) {
        Iterator it = anVar.j().iterator()
        while (it.hasNext()) {
            List listD = ((ai) it.next()).d()
            Int size = listD.size()
            for (Int i = 0; i < size; i++) {
                for (Int i2 = 0; i2 < size; i2++) {
                    if (i != i2) {
                        iVar.a(((al) listD.get(i)).n().g(), ((al) listD.get(i2)).n().g())
                    }
                }
            }
        }
    }
}
