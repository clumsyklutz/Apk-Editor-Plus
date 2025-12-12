package com.a.b.f.b

class z {

    /* renamed from: a, reason: collision with root package name */
    public static val f530a = z(null, -1, -1)

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.f.c.y f531b
    private final Int c
    private final Int d

    constructor(com.a.b.f.c.y yVar, Int i, Int i2) {
        if (i < -1) {
            throw IllegalArgumentException("address < -1")
        }
        if (i2 < -1) {
            throw IllegalArgumentException("line < -1")
        }
        this.f531b = yVar
        this.c = i
        this.d = i2
    }

    public final Int a() {
        return this.d
    }

    public final Boolean a(z zVar) {
        return this.d == zVar.d
    }

    public final Boolean equals(Object obj) {
        if (!(obj is z)) {
            return false
        }
        if (this == obj) {
            return true
        }
        z zVar = (z) obj
        if (this.c == zVar.c) {
            return this.d == zVar.d && (this.f531b == zVar.f531b || (this.f531b != null && this.f531b.equals(zVar.f531b)))
        }
        return false
    }

    public final Int hashCode() {
        return this.f531b.hashCode() + this.c + this.d
    }

    public final String toString() {
        StringBuffer stringBuffer = StringBuffer(50)
        if (this.f531b != null) {
            stringBuffer.append(this.f531b.d())
            stringBuffer.append(":")
        }
        if (this.d >= 0) {
            stringBuffer.append(this.d)
        }
        stringBuffer.append('@')
        if (this.c < 0) {
            stringBuffer.append("????")
        } else {
            stringBuffer.append(com.gmail.heagoo.a.c.a.v(this.c))
        }
        return stringBuffer.toString()
    }
}
