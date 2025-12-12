package com.a.b.d.a

class ax extends f {

    /* renamed from: a, reason: collision with root package name */
    private final Array<Int> f429a

    /* renamed from: b, reason: collision with root package name */
    private final Array<Int> f430b

    constructor(j jVar, Int i, Array<Int> iArr, Array<Int> iArr2) {
        super(jVar, i, 0, 0, 0, 0L)
        if (iArr.length != iArr2.length) {
            throw IllegalArgumentException("keys/targets length mismatch")
        }
        this.f429a = iArr
        this.f430b = iArr2
    }

    @Override // com.a.b.d.a.f
    public final f d(Int i) {
        throw UnsupportedOperationException("no index in instruction")
    }

    @Override // com.a.b.d.a.f
    public final Int m() {
        return 0
    }

    public final Array<Int> u() {
        return this.f429a
    }

    public final Array<Int> v() {
        return this.f430b
    }
}
