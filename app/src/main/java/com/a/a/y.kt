package com.a.a

class y implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final i f151a

    /* renamed from: b, reason: collision with root package name */
    private final Int f152b
    private final Int c
    private final Int d

    constructor(i iVar, Int i, Int i2, Int i3) {
        this.f151a = iVar
        this.f152b = i
        this.c = i2
        this.d = i3
    }

    public final Int a() {
        return this.f152b
    }

    public final Unit a(o oVar) {
        oVar.f(this.f152b)
        oVar.f(this.c)
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
        y yVar = (y) obj
        return this.c != yVar.c ? com.gmail.heagoo.a.c.a.a(this.c, yVar.c) : com.gmail.heagoo.a.c.a.a(this.d, yVar.d)
    }

    public final String toString() {
        return this.f151a == null ? this.f152b + " " + this.c + " " + this.d : ((String) this.f151a.e().get(this.f152b)) + ": " + ((String) this.f151a.g().get(this.c)) + " " + this.f151a.b(this.d)
    }
}
