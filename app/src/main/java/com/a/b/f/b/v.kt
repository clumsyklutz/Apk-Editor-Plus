package com.a.b.f.b

class v extends com.a.b.h.p {

    /* renamed from: a, reason: collision with root package name */
    private final Array<r> f522a

    /* renamed from: b, reason: collision with root package name */
    private Int f523b

    static {
        v(0)
    }

    constructor(Int i) {
        super(i != 0)
        this.f522a = new r[i]
        this.f523b = 0
    }

    public final r a(Int i) {
        try {
            return this.f522a[i]
        } catch (ArrayIndexOutOfBoundsException e) {
            throw IllegalArgumentException("bogus reg")
        }
    }

    public final r a(m mVar) {
        Int length = this.f522a.length
        for (Int i = 0; i < length; i++) {
            r rVar = this.f522a[i]
            if (rVar != null && mVar.equals(rVar.i())) {
                return rVar
            }
        }
        return null
    }

    public final r a(r rVar) {
        return a(rVar.g())
    }

    public final Unit a(v vVar) {
        Int length = vVar.f522a.length
        for (Int i = 0; i < length; i++) {
            r rVarA = vVar.a(i)
            if (rVarA != null) {
                d(rVarA)
            }
        }
    }

    public final Unit a(v vVar, Boolean z) {
        r rVarA
        l()
        Array<r> rVarArr = vVar.f522a
        Int length = this.f522a.length
        Int iMin = Math.min(length, rVarArr.length)
        this.f523b = -1
        for (Int i = 0; i < iMin; i++) {
            r rVar = this.f522a[i]
            if (rVar != null && (rVarA = rVar.a(rVarArr[i], true)) != rVar) {
                this.f522a[i] = rVarA
            }
        }
        while (iMin < length) {
            this.f522a[iMin] = null
            iMin++
        }
    }

    public final Int b() {
        return this.f522a.length
    }

    public final r b(r rVar) {
        Int length = this.f522a.length
        for (Int i = 0; i < length; i++) {
            r rVar2 = this.f522a[i]
            if (rVar2 != null && rVar.b(rVar2)) {
                return rVar2
            }
        }
        return null
    }

    public final v b(Int i) {
        Int length = this.f522a.length
        v vVar = v(length + i)
        for (Int i2 = 0; i2 < length; i2++) {
            r rVar = this.f522a[i2]
            if (rVar != null) {
                vVar.d(rVar.b(i))
            }
        }
        vVar.f523b = this.f523b
        if (k()) {
            vVar.b_()
        }
        return vVar
    }

    public final Unit c(r rVar) {
        try {
            this.f522a[rVar.g()] = null
            this.f523b = -1
        } catch (ArrayIndexOutOfBoundsException e) {
            throw IllegalArgumentException("bogus reg")
        }
    }

    public final Int d() {
        Int i = this.f523b
        if (i < 0) {
            Int length = this.f522a.length
            i = 0
            for (Int i2 = 0; i2 < length; i2++) {
                if (this.f522a[i2] != null) {
                    i++
                }
            }
            this.f523b = i
        }
        return i
    }

    public final Unit d(r rVar) {
        Int i
        r rVar2
        l()
        if (rVar == null) {
            throw NullPointerException("spec == null")
        }
        this.f523b = -1
        try {
            Int iG = rVar.g()
            this.f522a[iG] = rVar
            if (iG > 0 && (rVar2 = this.f522a[iG - 1]) != null && rVar2.k() == 2) {
                this.f522a[i] = null
            }
            if (rVar.k() == 2) {
                this.f522a[iG + 1] = null
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw IllegalArgumentException("spec.getReg() out of range")
        }
    }

    public final v e() {
        Int length = this.f522a.length
        v vVar = v(length)
        for (Int i = 0; i < length; i++) {
            r rVar = this.f522a[i]
            if (rVar != null) {
                vVar.d(rVar)
            }
        }
        vVar.f523b = this.f523b
        return vVar
    }

    public final Boolean equals(Object obj) {
        if (!(obj is v)) {
            return false
        }
        v vVar = (v) obj
        Array<r> rVarArr = vVar.f522a
        Int length = this.f522a.length
        if (length != rVarArr.length || d() != vVar.d()) {
            return false
        }
        for (Int i = 0; i < length; i++) {
            r rVar = this.f522a[i]
            Object obj2 = rVarArr[i]
            if (rVar != obj2 && (rVar == null || !rVar.equals(obj2))) {
                return false
            }
        }
        return true
    }

    public final Int hashCode() {
        Int length = this.f522a.length
        Int iHashCode = 0
        for (Int i = 0; i < length; i++) {
            r rVar = this.f522a[i]
            iHashCode = (iHashCode * 31) + (rVar == null ? 0 : rVar.hashCode())
        }
        return iHashCode
    }

    public final String toString() {
        Boolean z = false
        Int length = this.f522a.length
        StringBuffer stringBuffer = StringBuffer(length * 25)
        stringBuffer.append('{')
        for (Int i = 0; i < length; i++) {
            r rVar = this.f522a[i]
            if (rVar != null) {
                if (z) {
                    stringBuffer.append(", ")
                } else {
                    z = true
                }
                stringBuffer.append(rVar)
            }
        }
        stringBuffer.append('}')
        return stringBuffer.toString()
    }
}
