package com.a.b.f.b

import java.util.BitSet

final class u {

    /* renamed from: a, reason: collision with root package name */
    private BitSet f520a

    /* renamed from: b, reason: collision with root package name */
    private t f521b
    private Int c
    private t d
    private Boolean e

    private constructor(t tVar, BitSet bitSet, Int i, Boolean z) {
        this.f521b = tVar
        this.f520a = bitSet
        this.c = i
        this.d = t(tVar.d_())
        this.e = z
    }

    /* synthetic */ u(t tVar, BitSet bitSet, Int i, Boolean z, Byte b2) {
        this(tVar, bitSet, i, z)
    }

    static /* synthetic */ t a(u uVar) {
        if (uVar.f521b.k()) {
            uVar.d.b_()
        }
        return uVar.d
    }

    static /* synthetic */ Unit a(u uVar, Int i) {
        Boolean z = true
        r rVarA = (r) uVar.f521b.e(i)
        if (uVar.f520a != null && uVar.f520a.get(i)) {
            z = false
        }
        if (z) {
            rVarA = rVarA.a(uVar.c)
            if (!uVar.e) {
                uVar.c += rVarA.k()
            }
            uVar.e = false
        }
        uVar.d.a(i, (Object) rVarA)
    }
}
