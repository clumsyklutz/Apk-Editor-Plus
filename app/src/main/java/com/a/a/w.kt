package com.a.a

import jadx.core.deobf.Deobfuscator

class w implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final i f147a

    /* renamed from: b, reason: collision with root package name */
    private final Int f148b
    private final Int c
    private final Int d

    constructor(i iVar, Int i, Int i2, Int i3) {
        this.f147a = iVar
        this.f148b = i
        this.c = i2
        this.d = i3
    }

    public final Int a() {
        return this.f148b
    }

    public final Unit a(o oVar) {
        oVar.e(this.f148b)
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
        w wVar = (w) obj
        return this.f148b != wVar.f148b ? com.gmail.heagoo.a.c.a.a(this.f148b, wVar.f148b) : this.d != wVar.d ? com.gmail.heagoo.a.c.a.a(this.d, wVar.d) : com.gmail.heagoo.a.c.a.a(this.c, wVar.c)
    }

    public final String toString() {
        return this.f147a == null ? this.f148b + " " + this.c + " " + this.d : ((String) this.f147a.g().get(this.c)) + Deobfuscator.CLASS_NAME_SEPARATOR + ((String) this.f147a.e().get(this.d))
    }
}
