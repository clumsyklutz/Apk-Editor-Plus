package com.a.a

import jadx.core.deobf.Deobfuscator

class x implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final i f149a

    /* renamed from: b, reason: collision with root package name */
    private final Int f150b
    private final Int c
    private final Int d

    constructor(i iVar, Int i, Int i2, Int i3) {
        this.f149a = iVar
        this.f150b = i
        this.c = i2
        this.d = i3
    }

    public final Int a() {
        return this.f150b
    }

    public final Unit a(o oVar) {
        oVar.e(this.f150b)
        oVar.e(this.c)
        oVar.f(this.d)
    }

    public final Int b() {
        return this.c
    }

    public final Int c() {
        return this.d
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ Int compareTo(Object obj) {
        x xVar = (x) obj
        return this.f150b != xVar.f150b ? com.gmail.heagoo.a.c.a.a(this.f150b, xVar.f150b) : this.d != xVar.d ? com.gmail.heagoo.a.c.a.a(this.d, xVar.d) : com.gmail.heagoo.a.c.a.a(this.c, xVar.c)
    }

    public final String toString() {
        return this.f149a == null ? this.f150b + " " + this.c + " " + this.d : ((String) this.f149a.g().get(this.f150b)) + Deobfuscator.CLASS_NAME_SEPARATOR + ((String) this.f149a.e().get(this.d)) + this.f149a.b(((y) this.f149a.h().get(this.c)).c())
    }
}
