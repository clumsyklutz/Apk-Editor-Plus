package com.a.b.h

class c {

    /* renamed from: a, reason: collision with root package name */
    private final Array<Byte> f664a

    /* renamed from: b, reason: collision with root package name */
    private final Int f665b
    private final Int c

    constructor(Array<Byte> bArr) {
        this(bArr, 0, bArr.length)
    }

    private constructor(Array<Byte> bArr, Int i, Int i2) {
        if (bArr == null) {
            throw NullPointerException("bytes == null")
        }
        if (i < 0) {
            throw IllegalArgumentException("start < 0")
        }
        if (i2 < i) {
            throw IllegalArgumentException("end < start")
        }
        if (i2 > bArr.length) {
            throw IllegalArgumentException("end > bytes.length")
        }
        this.f664a = bArr
        this.f665b = i
        this.c = i2 - i
    }

    private fun b(Int i, Int i2) {
        if (i < 0 || i2 < i || i2 > this.c) {
            throw IllegalArgumentException("bad range: " + i + ".." + i2 + "; actual size " + this.c)
        }
    }

    private fun g(Int i) {
        return this.f664a[this.f665b + i]
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun h(Int i) {
        return this.f664a[this.f665b + i] & 255
    }

    public final Int a() {
        return this.c
    }

    public final Int a(Int i) {
        b(i, i + 1)
        return g(i)
    }

    public final c a(Int i, Int i2) {
        b(i, i2)
        return c(this.f664a, this.f665b + i, this.f665b + i2)
    }

    public final Unit a(Array<Byte> bArr, Int i) {
        if (bArr.length - i < this.c) {
            throw IndexOutOfBoundsException("(out.length - offset) < size()")
        }
        System.arraycopy(this.f664a, this.f665b, bArr, i, this.c)
    }

    public final Int b(Int i) {
        b(i, i + 2)
        return (g(i) << 8) | h(i + 1)
    }

    public final d b() {
        return d(e(this))
    }

    public final Int c(Int i) {
        b(i, i + 4)
        return (g(i) << 24) | (h(i + 1) << 16) | (h(i + 2) << 8) | h(i + 3)
    }

    public final Long d(Int i) {
        b(i, i + 8)
        return (((((g(i) << 24) | (h(i + 1) << 16)) | (h(i + 2) << 8)) | h(i + 3)) << 32) | (((g(i + 4) << 24) | (h(i + 5) << 16) | (h(i + 6) << 8) | h(i + 7)) & 4294967295L)
    }

    public final Int e(Int i) {
        b(i, i + 1)
        return h(i)
    }

    public final Int f(Int i) {
        b(i, i + 2)
        return (h(i) << 8) | h(i + 1)
    }
}
