package com.a.a

class v {

    /* renamed from: a, reason: collision with root package name */
    private com.a.a.a.b f145a

    /* renamed from: b, reason: collision with root package name */
    private Int f146b
    private Int c
    private Int d

    constructor(com.a.a.a.b bVar, Int i) {
        this.f146b = -1
        this.f145a = bVar
        this.f146b = i
    }

    constructor(u uVar, Int i) {
        this(uVar.a(), i)
    }

    private fun a(Int i) {
        if (a() != i) {
            throw IllegalStateException(String.format("Expected %x but was %x", Integer.valueOf(i), Integer.valueOf(a())))
        }
    }

    public final Int a() {
        if (this.f146b == -1) {
            Int iD = this.f145a.d() & 255
            this.f146b = iD & 31
            this.d = (iD & 224) >> 5
        }
        return this.f146b
    }

    public final Int b() {
        a(28)
        this.f146b = -1
        return com.gmail.heagoo.a.c.a.b(this.f145a)
    }

    public final Int c() {
        a(29)
        this.f146b = -1
        this.c = com.gmail.heagoo.a.c.a.b(this.f145a)
        return com.gmail.heagoo.a.c.a.b(this.f145a)
    }

    public final Int d() {
        return this.c
    }

    public final Int e() {
        return com.gmail.heagoo.a.c.a.b(this.f145a)
    }

    public final Byte f() {
        a(0)
        this.f146b = -1
        return (Byte) com.gmail.heagoo.a.c.a.a(this.f145a, this.d)
    }

    public final Short g() {
        a(2)
        this.f146b = -1
        return (Short) com.gmail.heagoo.a.c.a.a(this.f145a, this.d)
    }

    public final Char h() {
        a(3)
        this.f146b = -1
        return (Char) com.gmail.heagoo.a.c.a.a(this.f145a, this.d, false)
    }

    public final Int i() {
        a(4)
        this.f146b = -1
        return com.gmail.heagoo.a.c.a.a(this.f145a, this.d)
    }

    public final Long j() {
        a(6)
        this.f146b = -1
        return com.gmail.heagoo.a.c.a.b(this.f145a, this.d)
    }

    public final Float k() {
        a(16)
        this.f146b = -1
        return Float.intBitsToFloat(com.gmail.heagoo.a.c.a.a(this.f145a, this.d, true))
    }

    public final Double l() {
        a(17)
        this.f146b = -1
        return Double.longBitsToDouble(com.gmail.heagoo.a.c.a.b(this.f145a, this.d, true))
    }

    public final Int m() {
        a(23)
        this.f146b = -1
        return com.gmail.heagoo.a.c.a.a(this.f145a, this.d, false)
    }

    public final Int n() {
        a(24)
        this.f146b = -1
        return com.gmail.heagoo.a.c.a.a(this.f145a, this.d, false)
    }

    public final Int o() {
        a(25)
        this.f146b = -1
        return com.gmail.heagoo.a.c.a.a(this.f145a, this.d, false)
    }

    public final Int p() {
        a(27)
        this.f146b = -1
        return com.gmail.heagoo.a.c.a.a(this.f145a, this.d, false)
    }

    public final Int q() {
        a(26)
        this.f146b = -1
        return com.gmail.heagoo.a.c.a.a(this.f145a, this.d, false)
    }

    public final Unit r() {
        a(30)
        this.f146b = -1
    }

    public final Boolean s() {
        a(31)
        this.f146b = -1
        return this.d != 0
    }

    public final Unit t() {
        Int i = 0
        switch (a()) {
            case 0:
                f()
                return
            case 1:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            default:
                throw s("Unexpected type: " + Integer.toHexString(this.f146b))
            case 2:
                g()
                return
            case 3:
                h()
                return
            case 4:
                i()
                return
            case 6:
                j()
                return
            case 16:
                k()
                return
            case 17:
                l()
                return
            case 23:
                m()
                return
            case 24:
                n()
                return
            case 25:
                o()
                return
            case 26:
                q()
                return
            case 27:
                p()
                return
            case 28:
                Int iB = b()
                while (i < iB) {
                    t()
                    i++
                }
                return
            case 29:
                Int iC = c()
                while (i < iC) {
                    com.gmail.heagoo.a.c.a.b(this.f145a)
                    t()
                    i++
                }
                return
            case 30:
                r()
                return
            case 31:
                s()
                return
        }
    }
}
