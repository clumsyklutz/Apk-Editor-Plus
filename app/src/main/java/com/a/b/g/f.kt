package com.a.b.g

import java.util.BitSet

final class f implements am {

    /* renamed from: a, reason: collision with root package name */
    private BitSet f625a

    constructor(BitSet bitSet) {
        this.f625a = bitSet
    }

    @Override // com.a.b.g.am
    public final Unit a(ac acVar) {
        if (e.b(acVar)) {
            return
        }
        this.f625a.set(acVar.n().g())
    }

    @Override // com.a.b.g.am
    public final Unit a(z zVar) {
        if (e.b(zVar)) {
            return
        }
        this.f625a.set(zVar.n().g())
    }

    @Override // com.a.b.g.am
    public final Unit b(z zVar) {
        com.a.b.f.b.r rVarN = zVar.n()
        if (e.b(zVar) || rVarN == null) {
            return
        }
        this.f625a.set(rVarN.g())
    }
}
