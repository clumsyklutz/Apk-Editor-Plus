package com.a.b.e

import java.util.Comparator

final class u {

    /* renamed from: a, reason: collision with root package name */
    public static val f482a = v()

    /* renamed from: b, reason: collision with root package name */
    private final com.a.a.i f483b
    private com.a.a.e c
    private Int d = -1

    constructor(com.a.a.i iVar, com.a.a.e eVar) {
        this.f483b = iVar
        this.c = eVar
    }

    public final com.a.a.i a() {
        return this.f483b
    }

    public final Boolean a(Array<u> uVarArr) {
        Int iMax
        if (this.c.c() == -1) {
            iMax = 0
        } else {
            u uVar = uVarArr[this.c.c()]
            if (uVar == null) {
                iMax = 1
            } else {
                if (uVar.d == -1) {
                    return false
                }
                iMax = uVar.d
            }
        }
        for (Short s : this.c.e()) {
            u uVar2 = uVarArr[s]
            if (uVar2 == null) {
                iMax = Math.max(iMax, 1)
            } else {
                if (uVar2.d == -1) {
                    return false
                }
                iMax = Math.max(iMax, uVar2.d)
            }
        }
        this.d = iMax + 1
        return true
    }

    public final com.a.a.e b() {
        return this.c
    }

    public final Int c() {
        return this.c.b()
    }

    public final Boolean d() {
        return this.d != -1
    }
}
