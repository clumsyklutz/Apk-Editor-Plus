package com.a.b.f.b

class w {

    /* renamed from: a, reason: collision with root package name */
    private final Int f524a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.f.d.c f525b
    private final com.a.b.f.d.e c
    private final com.a.b.f.d.e d
    private final Int e
    private final Boolean f
    private final String g

    constructor(Int i, com.a.b.f.d.c cVar, com.a.b.f.d.e eVar, Int i2, String str) {
        this(i, cVar, eVar, com.a.b.f.d.b.f565a, i2, false, str)
    }

    private constructor(Int i, com.a.b.f.d.c cVar, com.a.b.f.d.e eVar, com.a.b.f.d.e eVar2, Int i2, Boolean z, String str) {
        if (cVar == null) {
            throw NullPointerException("result == null")
        }
        if (eVar == null) {
            throw NullPointerException("sources == null")
        }
        if (eVar2 == null) {
            throw NullPointerException("exceptions == null")
        }
        if (i2 <= 0 || i2 > 6) {
            throw IllegalArgumentException("bogus branchingness")
        }
        if (eVar2.d_() != 0 && i2 != 6) {
            throw IllegalArgumentException("exceptions / branchingness mismatch")
        }
        this.f524a = i
        this.f525b = cVar
        this.c = eVar
        this.d = eVar2
        this.e = i2
        this.f = z
        this.g = str
    }

    constructor(Int i, com.a.b.f.d.c cVar, com.a.b.f.d.e eVar, com.a.b.f.d.e eVar2, String str) {
        this(i, cVar, eVar, eVar2, 6, false, str)
    }

    constructor(Int i, com.a.b.f.d.c cVar, com.a.b.f.d.e eVar, String str) {
        this(i, cVar, eVar, com.a.b.f.d.b.f565a, 1, false, str)
    }

    constructor(Int i, com.a.b.f.d.e eVar, com.a.b.f.d.e eVar2) {
        this(i, com.a.b.f.d.c.i, eVar, eVar2, 6, true, null)
    }

    public final Int a() {
        return this.f524a
    }

    public final com.a.b.f.d.c b() {
        return this.f525b
    }

    public final com.a.b.f.d.e c() {
        return this.c
    }

    public final Int d() {
        return this.e
    }

    public final Boolean e() {
        return this.f
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (!(obj is w)) {
            return false
        }
        w wVar = (w) obj
        return this.f524a == wVar.f524a && this.e == wVar.e && this.f525b == wVar.f525b && this.c.equals(wVar.c) && this.d.equals(wVar.d)
    }

    public final Boolean f() {
        switch (this.f524a) {
            case 14:
            case 16:
            case 20:
            case 21:
            case 22:
                return true
            case 15:
            case 17:
            case 18:
            case 19:
            default:
                return false
        }
    }

    public final String g() {
        return this.g != null ? this.g : toString()
    }

    public final Boolean h() {
        return this.d.d_() != 0
    }

    public final Int hashCode() {
        return (((((((this.f524a * 31) + this.e) * 31) + this.f525b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode()
    }

    public final String toString() {
        StringBuffer stringBuffer = StringBuffer(40)
        stringBuffer.append("Rop{")
        stringBuffer.append(com.gmail.heagoo.a.c.a.q(this.f524a))
        if (this.f525b != com.a.b.f.d.c.i) {
            stringBuffer.append(" ")
            stringBuffer.append(this.f525b)
        } else {
            stringBuffer.append(" .")
        }
        stringBuffer.append(" <-")
        Int iD_ = this.c.d_()
        if (iD_ == 0) {
            stringBuffer.append(" .")
        } else {
            for (Int i = 0; i < iD_; i++) {
                stringBuffer.append(' ')
                stringBuffer.append(this.c.a(i))
            }
        }
        if (this.f) {
            stringBuffer.append(" call")
        }
        Int iD_2 = this.d.d_()
        if (iD_2 == 0) {
            switch (this.e) {
                case 1:
                    stringBuffer.append(" flows")
                    break
                case 2:
                    stringBuffer.append(" returns")
                    break
                case 3:
                    stringBuffer.append(" gotos")
                    break
                case 4:
                    stringBuffer.append(" ifs")
                    break
                case 5:
                    stringBuffer.append(" switches")
                    break
                default:
                    stringBuffer.append(" " + com.gmail.heagoo.a.c.a.x(this.e))
                    break
            }
        } else {
            stringBuffer.append(" throws")
            for (Int i2 = 0; i2 < iD_2; i2++) {
                stringBuffer.append(' ')
                if (this.d.a(i2) == com.a.b.f.d.c.q) {
                    stringBuffer.append("<any>")
                } else {
                    stringBuffer.append(this.d.a(i2))
                }
            }
        }
        stringBuffer.append('}')
        return stringBuffer.toString()
    }
}
