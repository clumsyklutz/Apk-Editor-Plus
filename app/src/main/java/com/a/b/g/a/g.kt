package com.a.b.g.a

final class g {

    /* renamed from: a, reason: collision with root package name */
    private final Array<Int> f575a

    /* renamed from: b, reason: collision with root package name */
    private final Array<Int> f576b
    private Int c = 0

    constructor(Int i) {
        this.f575a = new Int[i]
        this.f576b = new Int[i]
    }

    public final Int a() {
        Int i = 0
        Int i2 = -1
        Int i3 = -1
        for (Int i4 = 0; i4 < this.c; i4++) {
            if (i < this.f576b[i4]) {
                i2 = this.f575a[i4]
                i = this.f576b[i4]
                i3 = i4
            }
        }
        this.f576b[i3] = 0
        return i2
    }

    public final Unit a(Int i) {
        for (Int i2 = 0; i2 < this.c; i2++) {
            if (this.f575a[i2] == i) {
                Array<Int> iArr = this.f576b
                iArr[i2] = iArr[i2] + 1
                return
            }
        }
        this.f575a[this.c] = i
        this.f576b[this.c] = 1
        this.c++
    }

    public final Int b() {
        return this.c
    }
}
