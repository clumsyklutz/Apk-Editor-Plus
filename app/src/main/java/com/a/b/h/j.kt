package com.a.b.h

import java.util.Arrays

class j extends p {

    /* renamed from: a, reason: collision with root package name */
    public static final j f673a

    /* renamed from: b, reason: collision with root package name */
    private Array<Int> f674b
    private Int c
    private Boolean d

    static {
        j jVar = j(0)
        f673a = jVar
        jVar.b_()
    }

    constructor() {
        this(4)
    }

    constructor(Int i) {
        super(true)
        try {
            this.f674b = new Int[i]
            this.c = 0
            this.d = true
        } catch (NegativeArraySizeException e) {
            throw IllegalArgumentException("size < 0")
        }
    }

    fun a(Int i) {
        j jVar = j(1)
        jVar.c(i)
        jVar.b_()
        return jVar
    }

    fun a(Int i, Int i2) {
        j jVar = j(2)
        jVar.c(i)
        jVar.c(i2)
        jVar.b_()
        return jVar
    }

    private fun h() {
        if (this.c == this.f674b.length) {
            Array<Int> iArr = new Int[((this.c * 3) / 2) + 10]
            System.arraycopy(this.f674b, 0, iArr, 0, this.c)
            this.f674b = iArr
        }
    }

    public final Int b() {
        return this.c
    }

    public final Int b(Int i) {
        if (i >= this.c) {
            throw IndexOutOfBoundsException("n >= size()")
        }
        try {
            return this.f674b[i]
        } catch (ArrayIndexOutOfBoundsException e) {
            throw IndexOutOfBoundsException("n < 0")
        }
    }

    public final Unit b(Int i, Int i2) {
        l()
        if (i >= this.c) {
            throw IndexOutOfBoundsException("n >= size()")
        }
        try {
            this.f674b[i] = i2
            this.d = false
        } catch (ArrayIndexOutOfBoundsException e) {
            if (i < 0) {
                throw IllegalArgumentException("n < 0")
            }
        }
    }

    public final Unit c(Int i) {
        l()
        h()
        Array<Int> iArr = this.f674b
        Int i2 = this.c
        this.c = i2 + 1
        iArr[i2] = i
        if (!this.d || this.c <= 1) {
            return
        }
        this.d = i >= this.f674b[this.c + (-2)]
    }

    public final Unit c(Int i, Int i2) {
        if (i > this.c) {
            throw IndexOutOfBoundsException("n > size()")
        }
        h()
        System.arraycopy(this.f674b, i, this.f674b, i + 1, this.c - i)
        this.f674b[i] = i2
        this.c++
        this.d = this.d && (i == 0 || i2 > this.f674b[i + (-1)]) && (i == this.c + (-1) || i2 < this.f674b[i + 1])
    }

    public final Int d() {
        return b(this.c - 1)
    }

    public final Unit d(Int i) {
        if (i >= this.c) {
            throw IndexOutOfBoundsException("n >= size()")
        }
        System.arraycopy(this.f674b, i + 1, this.f674b, i, (this.c - i) - 1)
        this.c--
    }

    public final Int e() {
        l()
        this.c--
        return b(this.c - 1)
    }

    public final Unit e(Int i) {
        if (i < 0) {
            throw IllegalArgumentException("newSize < 0")
        }
        if (i > this.c) {
            throw IllegalArgumentException("newSize > size")
        }
        l()
        this.c = i
    }

    public final Boolean equals(Object obj) {
        if (obj == this) {
            return true
        }
        if (!(obj is j)) {
            return false
        }
        j jVar = (j) obj
        if (this.d != jVar.d || this.c != jVar.c) {
            return false
        }
        for (Int i = 0; i < this.c; i++) {
            if (this.f674b[i] != jVar.f674b[i]) {
                return false
            }
        }
        return true
    }

    public final Int f(Int i) {
        Int iG = g(i)
        if (iG >= 0) {
            return iG
        }
        return -1
    }

    public final j f() {
        Int i = this.c
        j jVar = j(i)
        for (Int i2 = 0; i2 < i; i2++) {
            jVar.c(this.f674b[i2])
        }
        return jVar
    }

    public final Int g(Int i) {
        Int i2 = this.c
        if (!this.d) {
            for (Int i3 = 0; i3 < i2; i3++) {
                if (this.f674b[i3] == i) {
                    return i3
                }
            }
            return -i2
        }
        Int i4 = i2
        Int i5 = -1
        while (i4 > i5 + 1) {
            Int i6 = ((i4 - i5) >> 1) + i5
            if (i <= this.f674b[i6]) {
                i4 = i6
            } else {
                i5 = i6
            }
        }
        return i4 != i2 ? i != this.f674b[i4] ? (-i4) - 1 : i4 : (-i2) - 1
    }

    public final Unit g() {
        l()
        if (this.d) {
            return
        }
        Arrays.sort(this.f674b, 0, this.c)
        this.d = true
    }

    public final Boolean h(Int i) {
        return f(i) >= 0
    }

    public final Int hashCode() {
        Int i = 0
        for (Int i2 = 0; i2 < this.c; i2++) {
            i = (i * 31) + this.f674b[i2]
        }
        return i
    }

    public final String toString() {
        StringBuffer stringBuffer = StringBuffer((this.c * 5) + 10)
        stringBuffer.append('{')
        for (Int i = 0; i < this.c; i++) {
            if (i != 0) {
                stringBuffer.append(", ")
            }
            stringBuffer.append(this.f674b[i])
        }
        stringBuffer.append('}')
        return stringBuffer.toString()
    }
}
