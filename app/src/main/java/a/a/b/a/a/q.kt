package a.a.b.a.a

import android.util.TypedValue

class q extends t {

    /* renamed from: a, reason: collision with root package name */
    protected final Int f16a
    private Int c

    constructor(Int i, String str, Int i2) {
        this(i, str, "integer")
        this.c = i2
    }

    constructor(Int i, String str, String str2) {
        super(str2, i, str)
        this.f16a = i
    }

    @Override // a.a.b.a.a.t
    protected fun a() {
        return TypedValue.coerceToString(this.c, this.f16a)
    }

    public final Int b() {
        return this.f16a
    }
}
