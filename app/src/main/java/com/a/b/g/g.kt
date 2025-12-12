package com.a.b.g

import java.util.ArrayList
import java.util.BitSet

class g {

    /* renamed from: a, reason: collision with root package name */
    private static Boolean f626a = false

    /* renamed from: b, reason: collision with root package name */
    private final an f627b
    private final ArrayList c
    private final Array<h> d

    constructor(an anVar) {
        this.f627b = anVar
        this.c = anVar.j()
        Int size = this.c.size()
        this.d = new h[size]
        for (Int i = 0; i < size; i++) {
            this.d[i] = h()
        }
    }

    public final Array<h> a() {
        Int size = this.c.size()
        i.a(this.f627b, this.d, false)
        Int size2 = this.c.size()
        for (Int i = 0; i < size2; i++) {
            h hVar = this.d[i]
            if (hVar.f629b != -1) {
                ((ai) this.c.get(hVar.f629b)).a((ai) this.c.get(i))
            }
        }
        for (Int i2 = 0; i2 < size; i2++) {
            this.d[i2].f628a = size <= 3072 ? new com.a.b.h.a(size) : new com.a.b.h.n()
        }
        Int size3 = this.c.size()
        for (Int i3 = 0; i3 < size3; i3++) {
            ai aiVar = (ai) this.c.get(i3)
            h hVar2 = this.d[i3]
            BitSet bitSetG = aiVar.g()
            if (bitSetG.cardinality() > 1) {
                for (Int iNextSetBit = bitSetG.nextSetBit(0); iNextSetBit >= 0; iNextSetBit = bitSetG.nextSetBit(iNextSetBit + 1)) {
                    Int i4 = iNextSetBit
                    while (i4 != hVar2.f629b && i4 != -1) {
                        h hVar3 = this.d[i4]
                        if (!hVar3.f628a.b(i3)) {
                            hVar3.f628a.a(i3)
                            i4 = hVar3.f629b
                        }
                    }
                }
            }
        }
        return this.d
    }
}
