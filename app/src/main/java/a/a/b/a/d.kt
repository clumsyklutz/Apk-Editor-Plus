package a.a.b.a

class d {

    /* renamed from: a, reason: collision with root package name */
    public final Int f27a

    /* renamed from: b, reason: collision with root package name */
    public final Int f28b

    constructor(Int i) {
        this(i >>> 24, (i >> 16) & 255, 65535 & i, i)
    }

    private constructor(Int i, Int i2, Int i3, Int i4) {
        this.f27a = i == 0 ? 2 : i
        this.f28b = i4
    }

    public final Boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && this.f28b == ((d) obj).f28b
    }

    public final Int hashCode() {
        return this.f28b + 527
    }

    public final String toString() {
        return String.format("0x%08x", Integer.valueOf(this.f28b))
    }
}
