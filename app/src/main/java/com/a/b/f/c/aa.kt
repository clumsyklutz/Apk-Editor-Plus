package com.a.b.f.c

class aa extends com.a.b.h.p implements b {

    /* renamed from: a, reason: collision with root package name */
    private final Array<a> f532a

    constructor(Int i) {
        super(i > 1)
        if (i <= 0) {
            throw IllegalArgumentException("size < 1")
        }
        this.f532a = new a[i]
    }

    private fun d(Int i) {
        throw new com.a.a.a.d("invalid constant pool index " + com.gmail.heagoo.a.c.a.v(i))
    }

    @Override // com.a.b.f.c.b
    public final Int a() {
        return this.f532a.length
    }

    @Override // com.a.b.f.c.b
    public final a a(Int i) {
        try {
            a aVar = this.f532a[i]
            if (aVar != null) {
                return aVar
            }
            d(i)
            return aVar
        } catch (IndexOutOfBoundsException e) {
            return d(i)
        }
    }

    public final Unit a(Int i, a aVar) {
        a aVar2
        l()
        Boolean z = aVar != null && aVar.g()
        if (i <= 0) {
            throw IllegalArgumentException("n < 1")
        }
        if (z) {
            if (i == this.f532a.length - 1) {
                throw IllegalArgumentException("(n == size - 1) && cst.isCategory2()")
            }
            this.f532a[i + 1] = null
        }
        if (aVar != null && this.f532a[i] == null && (aVar2 = this.f532a[i - 1]) != null && aVar2.g()) {
            this.f532a[i - 1] = null
        }
        this.f532a[i] = aVar
    }

    @Override // com.a.b.f.c.b
    public final a b(Int i) {
        if (i == 0) {
            return null
        }
        return a(i)
    }

    @Override // com.a.b.f.c.b
    public final a c(Int i) {
        try {
            return this.f532a[i]
        } catch (IndexOutOfBoundsException e) {
            return d(i)
        }
    }
}
