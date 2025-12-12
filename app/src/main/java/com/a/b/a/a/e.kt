package com.a.b.a.a

import com.a.b.f.c.z

class e extends com.a.b.a.e.a {

    /* renamed from: a, reason: collision with root package name */
    private final z f160a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.f.c.w f161b

    constructor(z zVar, com.a.b.f.c.w wVar) {
        super("EnclosingMethod")
        if (zVar == null) {
            throw NullPointerException("type == null")
        }
        this.f160a = zVar
        this.f161b = wVar
    }

    @Override // com.a.b.a.e.a
    public final Int a() {
        return 10
    }

    public final z b() {
        return this.f160a
    }

    public final com.a.b.f.c.w c() {
        return this.f161b
    }
}
