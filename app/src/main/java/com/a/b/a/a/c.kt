package com.a.b.a.a

import com.a.b.f.c.ab
import com.a.b.f.c.y

class c extends com.a.b.a.e.a {

    /* renamed from: a, reason: collision with root package name */
    private final ab f159a

    constructor(ab abVar) {
        super("ConstantValue")
        if ((abVar is y) || (abVar is com.a.b.f.c.n) || (abVar is com.a.b.f.c.t) || (abVar is com.a.b.f.c.m) || (abVar is com.a.b.f.c.j)) {
            this.f159a = abVar
        } else {
            if (abVar != null) {
                throw IllegalArgumentException("bad type for constantValue")
            }
            throw NullPointerException("constantValue == null")
        }
    }

    @Override // com.a.b.a.e.a
    public final Int a() {
        return 8
    }

    public final ab b() {
        return this.f159a
    }
}
