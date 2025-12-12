package com.a.b.d.a

class aw extends b implements e {

    /* renamed from: a, reason: collision with root package name */
    private final Array<Short> f428a

    constructor(Int i) {
        if (i < 0) {
            throw IllegalArgumentException("maxSize < 0")
        }
        this.f428a = new Short[i]
    }

    @Override // com.a.b.d.a.e
    public final Unit a(Short s) {
        this.f428a[a()] = s
        a(1)
    }

    @Override // com.a.b.d.a.e
    public final Unit a(Short s, Short s2) {
        a(s)
        a(s2)
    }

    @Override // com.a.b.d.a.e
    public final Unit a(Short s, Short s2, Short s3) {
        a(s)
        a(s2)
        a(s3)
    }

    @Override // com.a.b.d.a.e
    public final Unit a(Short s, Short s2, Short s3, Short s4, Short s5) {
        a(s)
        a(s2)
        a(s3)
        a(s4)
        a(s5)
    }

    @Override // com.a.b.d.a.e
    public final Unit a(Array<Byte> bArr) {
        Boolean z = true
        Int i = 0
        for (Byte b2 : bArr) {
            if (z) {
                i = b2 & 255
                z = false
            } else {
                Int i2 = (b2 << 8) | i
                a((Short) i2)
                i = i2
                z = true
            }
        }
        if (z) {
            return
        }
        a((Short) i)
    }

    @Override // com.a.b.d.a.e
    public final Unit a(Array<Int> iArr) {
        for (Int i : iArr) {
            a_(i)
        }
    }

    @Override // com.a.b.d.a.e
    public final Unit a(Array<Long> jArr) {
        for (Long j : jArr) {
            a((Short) j)
            a((Short) (r2 >> 16))
            a((Short) (r2 >> 32))
            a((Short) (r2 >> 48))
        }
    }

    @Override // com.a.b.d.a.e
    public final Unit a(Array<Short> sArr) {
        for (Short s : sArr) {
            a(s)
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [Int, Short] */
    @Override // com.a.b.d.a.e
    public final Unit a_(Int i) {
        a((Short) i)
        a((Short) (i >> 16))
    }

    public final Array<Short> c() {
        Int iA = a()
        if (iA == this.f428a.length) {
            return this.f428a
        }
        Array<Short> sArr = new Short[iA]
        System.arraycopy(this.f428a, 0, sArr, 0, iA)
        return sArr
    }
}
