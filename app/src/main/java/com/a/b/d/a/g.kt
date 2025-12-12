package com.a.b.d.a

class g extends f {

    /* renamed from: a, reason: collision with root package name */
    private final Object f439a

    /* renamed from: b, reason: collision with root package name */
    private final Int f440b
    private final Int c

    private constructor(j jVar, Int i, Object obj, Int i2, Int i3) {
        super(jVar, i, 0, 0, 0, 0L)
        this.f439a = obj
        this.f440b = i2
        this.c = i3
    }

    constructor(j jVar, Int i, Array<Byte> bArr) {
        this(jVar, i, bArr, bArr.length, 1)
    }

    constructor(j jVar, Int i, Array<Int> iArr) {
        this(jVar, i, iArr, iArr.length, 4)
    }

    constructor(j jVar, Int i, Array<Long> jArr) {
        this(jVar, i, jArr, jArr.length, 8)
    }

    constructor(j jVar, Int i, Array<Short> sArr) {
        this(jVar, i, sArr, sArr.length, 2)
    }

    @Override // com.a.b.d.a.f
    public final f d(Int i) {
        throw UnsupportedOperationException("no index in instruction")
    }

    @Override // com.a.b.d.a.f
    public final Int m() {
        return 0
    }

    public final Short u() {
        return (Short) this.c
    }

    public final Int v() {
        return this.f440b
    }

    public final Object w() {
        return this.f439a
    }
}
