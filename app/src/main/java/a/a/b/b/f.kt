package a.a.b.b

final class f {

    /* renamed from: a, reason: collision with root package name */
    private Array<Int> f52a = new Int[32]

    /* renamed from: b, reason: collision with root package name */
    private Int f53b
    private Int c
    private Int d

    private final Int a(Int i, Boolean z) {
        if (this.f53b == 0 || i < 0) {
            return -1
        }
        Int i2 = 0
        for (Int i3 = this.d; i3 != 0; i3--) {
            Int i4 = this.f52a[i2]
            if (i < i4) {
                Int i5 = (i << 1) + 1 + i2
                if (!z) {
                    i5++
                }
                return this.f52a[i5]
            }
            i -= i4
            i2 += (i4 << 1) + 2
        }
        return -1
    }

    private fun e(Int i) {
        Int length = this.f52a.length - this.f53b
        if (length > 2) {
            return
        }
        Array<Int> iArr = new Int[(length + this.f52a.length) << 1]
        System.arraycopy(this.f52a, 0, iArr, 0, this.f53b)
        this.f52a = iArr
    }

    public final Int a(Int i) {
        Int i2 = 0
        if (this.f53b != 0 && i >= 0) {
            if (i > this.d) {
                i = this.d
            }
            Int i3 = 0
            while (i != 0) {
                Int i4 = this.f52a[i3]
                i--
                i3 = (i4 << 1) + 2 + i3
                i2 += i4
            }
        }
        return i2
    }

    public final Unit a() {
        this.f53b = 0
        this.c = 0
        this.d = 0
    }

    public final Unit a(Int i, Int i2) {
        if (this.d == 0) {
            e()
        }
        e(2)
        Int i3 = this.f53b - 1
        Int i4 = this.f52a[i3]
        this.f52a[(i3 - 1) - (i4 << 1)] = i4 + 1
        this.f52a[i3] = i
        this.f52a[i3 + 1] = i2
        this.f52a[i3 + 2] = i4 + 1
        this.f53b += 2
        this.c++
    }

    public final Int b() {
        if (this.f53b == 0) {
            return 0
        }
        return this.f52a[this.f53b - 1]
    }

    public final Int b(Int i) {
        return a(i, true)
    }

    public final Int c(Int i) {
        return a(i, false)
    }

    public final Boolean c() {
        Int i
        Int i2
        if (this.f53b == 0 || (i2 = this.f52a[this.f53b - 1]) == 0) {
            return false
        }
        Int i3 = i2 - 1
        Int i4 = i - 2
        this.f52a[i4] = i3
        this.f52a[i4 - ((i3 << 1) + 1)] = i3
        this.f53b -= 2
        this.c--
        return true
    }

    public final Int d() {
        return this.d
    }

    public final Int d(Int i) {
        if (this.f53b != 0) {
            Int i2 = this.f53b - 1
            for (Int i3 = this.d; i3 != 0; i3--) {
                i2 -= 2
                for (Int i4 = this.f52a[i2]; i4 != 0; i4--) {
                    if (this.f52a[i2 + 1] == i) {
                        return this.f52a[i2]
                    }
                    i2 -= 2
                }
            }
        }
        return -1
    }

    public final Unit e() {
        e(2)
        Int i = this.f53b
        this.f52a[i] = 0
        this.f52a[i + 1] = 0
        this.f53b += 2
        this.d++
    }

    public final Unit f() {
        if (this.f53b == 0) {
            return
        }
        Int i = this.f53b - 1
        Int i2 = this.f52a[i]
        if ((i - 1) - (i2 << 1) != 0) {
            this.f53b -= (i2 << 1) + 2
            this.c -= i2
            this.d--
        }
    }
}
