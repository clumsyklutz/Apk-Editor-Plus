package com.a.b.d.a

class at extends f {

    /* renamed from: a, reason: collision with root package name */
    private final Int f423a

    /* renamed from: b, reason: collision with root package name */
    private final Array<Int> f424b

    constructor(j jVar, Int i, Int i2, Array<Int> iArr) {
        super(jVar, i, 0, 0, 0, 0L)
        this.f423a = i2
        this.f424b = iArr
    }

    @Override // com.a.b.d.a.f
    public final f d(Int i) {
        throw UnsupportedOperationException("no index in instruction")
    }

    @Override // com.a.b.d.a.f
    public final Int m() {
        return 0
    }

    public final Int u() {
        return this.f423a
    }

    public final Array<Int> v() {
        return this.f424b
    }
}
