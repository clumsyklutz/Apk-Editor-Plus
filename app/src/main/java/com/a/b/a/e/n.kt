package com.a.b.a.e

import com.a.b.f.c.w
import com.a.b.f.c.y
import com.a.b.f.c.z

abstract class n implements f {

    /* renamed from: a, reason: collision with root package name */
    private final z f250a

    /* renamed from: b, reason: collision with root package name */
    private final Int f251b
    private final w c
    private final b d

    constructor(z zVar, Int i, w wVar, b bVar) {
        if (zVar == null) {
            throw NullPointerException("definingClass == null")
        }
        if (wVar == null) {
            throw NullPointerException("nat == null")
        }
        if (bVar == null) {
            throw NullPointerException("attributes == null")
        }
        this.f250a = zVar
        this.f251b = i
        this.c = wVar
        this.d = bVar
    }

    @Override // com.a.b.a.e.f
    public final w a() {
        return this.c
    }

    @Override // com.a.b.a.e.f
    public final y b() {
        return this.c.a()
    }

    @Override // com.a.b.a.e.f
    public final y c() {
        return this.c.b()
    }

    @Override // com.a.b.a.e.f
    public final Int d() {
        return this.f251b
    }

    @Override // com.a.b.a.e.f
    public final b e() {
        return this.d
    }

    @Override // com.a.b.a.e.f
    public final z f() {
        return this.f250a
    }

    fun toString() {
        StringBuffer stringBuffer = StringBuffer(100)
        stringBuffer.append(getClass().getName())
        stringBuffer.append('{')
        stringBuffer.append(this.c.d())
        stringBuffer.append('}')
        return stringBuffer.toString()
    }
}
