package com.a.b.h

import java.io.Writer
import java.util.ArrayList

class r implements com.a.a.a.c {

    /* renamed from: a, reason: collision with root package name */
    private final Boolean f680a

    /* renamed from: b, reason: collision with root package name */
    private Array<Byte> f681b
    private Int c
    private Boolean d
    private ArrayList e
    private Int f
    private Int g

    constructor() {
        this(1000)
    }

    constructor(Int i) {
        this(new Byte[i], true)
    }

    constructor(Array<Byte> bArr) {
        this(bArr, false)
    }

    private constructor(Array<Byte> bArr, Boolean z) {
        if (bArr == null) {
            throw NullPointerException("data == null")
        }
        this.f680a = z
        this.f681b = bArr
        this.c = 0
        this.d = false
        this.e = null
        this.f = 0
        this.g = 0
    }

    private fun i() {
        throw IndexOutOfBoundsException("attempt to write past the end")
    }

    private fun i(Int i) {
        if (this.f681b.length < i) {
            Array<Byte> bArr = new Byte[(i << 1) + 1000]
            System.arraycopy(this.f681b, 0, bArr, 0, this.c)
            this.f681b = bArr
        }
    }

    fun a() {
        return this.c
    }

    fun a(Int i) {
        if (this.c != i) {
            throw new com.a.a.a.d("expected cursor " + i + "; actual value: " + this.c)
        }
    }

    fun a(Int i, String str) {
        if (this.e == null) {
            return
        }
        d()
        Int size = this.e.size()
        Int iB = size == 0 ? 0 : ((f) this.e.get(size - 1)).b()
        if (iB <= this.c) {
            iB = this.c
        }
        this.e.add(f(iB, iB + i, str))
    }

    fun a(Int i, Boolean z) {
        if (this.e != null || this.c != 0) {
            throw RuntimeException("cannot enable annotations")
        }
        if (i < 40) {
            throw IllegalArgumentException("annotationWidth < 40")
        }
        Int i2 = (((i - 7) / 15) + 1) & (-2)
        Int i3 = i2 >= 6 ? i2 > 10 ? 10 : i2 : 6
        this.e = ArrayList(1000)
        this.f = i
        this.g = i3
        this.d = z
    }

    fun a(Long j) {
        Int i = this.c
        Int i2 = i + 8
        if (this.f680a) {
            i(i2)
        } else if (i2 > this.f681b.length) {
            i()
            return
        }
        Int i3 = (Int) j
        this.f681b[i] = (Byte) i3
        this.f681b[i + 1] = (Byte) (i3 >> 8)
        this.f681b[i + 2] = (Byte) (i3 >> 16)
        this.f681b[i + 3] = i3 >> 24
        Int i4 = (Int) (j >> 32)
        this.f681b[i + 4] = (Byte) i4
        this.f681b[i + 5] = (Byte) (i4 >> 8)
        this.f681b[i + 6] = (Byte) (i4 >> 16)
        this.f681b[i + 7] = i4 >> 24
        this.c = i2
    }

    fun a(c cVar) {
        Int iA = cVar.a()
        Int i = this.c
        Int i2 = iA + i
        if (this.f680a) {
            i(i2)
        } else if (i2 > this.f681b.length) {
            i()
            return
        }
        cVar.a(this.f681b, i)
        this.c = i2
    }

    fun a(Writer writer) {
        String strC
        t tVar = t(writer, (this.f - r1) - 1, e(), "|")
        Writer writerA = tVar.a()
        Writer writerB = tVar.b()
        Int size = this.e.size()
        Int i = 0
        Int i2 = 0
        while (i2 < this.c && i < size) {
            f fVar = (f) this.e.get(i)
            Int iA = fVar.a()
            if (i2 < iA) {
                strC = ""
            } else {
                Int iB = fVar.b()
                i++
                strC = fVar.c()
                iA = iB
                i2 = iA
            }
            writerA.write(com.gmail.heagoo.a.c.a.a(this.f681b, i2, iA - i2, i2, this.g, 6))
            writerB.write(strC)
            tVar.c()
            i2 = iA
        }
        if (i2 < this.c) {
            writerA.write(com.gmail.heagoo.a.c.a.a(this.f681b, i2, this.c - i2, i2, this.g, 6))
        }
        while (i < size) {
            writerB.write(((f) this.e.get(i)).c())
            i++
        }
        tVar.c()
    }

    fun a(String str) {
        if (this.e == null) {
            return
        }
        d()
        this.e.add(f(this.c, str))
    }

    fun a(Array<Byte> bArr) {
        a(bArr, 0, bArr.length)
    }

    fun a(Array<Byte> bArr, Int i, Int i2) {
        Int i3 = this.c
        Int i4 = i3 + i2
        Int i5 = i2 + 0
        if ((i2 | 0 | i4) < 0 || i5 > bArr.length) {
            throw IndexOutOfBoundsException("bytes.length " + bArr.length + "; 0..!" + i4)
        }
        if (this.f680a) {
            i(i4)
        } else if (i4 > this.f681b.length) {
            i()
            return
        }
        System.arraycopy(bArr, 0, this.f681b, i3, i2)
        this.c = i4
    }

    fun b(Int i) {
        Int i2 = this.c
        Int i3 = i2 + 2
        if (this.f680a) {
            i(i3)
        } else if (i3 > this.f681b.length) {
            i()
            return
        }
        this.f681b[i2] = (Byte) i
        this.f681b[i2 + 1] = (Byte) (i >> 8)
        this.c = i3
    }

    fun b() {
        return this.e != null
    }

    fun c(Int i) {
        Int i2 = this.c
        Int i3 = i2 + 4
        if (this.f680a) {
            i(i3)
        } else if (i3 > this.f681b.length) {
            i()
            return
        }
        this.f681b[i2] = (Byte) i
        this.f681b[i2 + 1] = (Byte) (i >> 8)
        this.f681b[i2 + 2] = (Byte) (i >> 16)
        this.f681b[i2 + 3] = i >> 24
        this.c = i3
    }

    fun c() {
        return this.d
    }

    fun d() {
        Int size
        if (this.e == null || (size = this.e.size()) == 0) {
            return
        }
        ((f) this.e.get(size - 1)).a(this.c)
    }

    @Override // com.a.a.a.c
    fun d(Int i) {
        Int i2 = this.c
        Int i3 = i2 + 1
        if (this.f680a) {
            i(i3)
        } else if (i3 > this.f681b.length) {
            i()
            return
        }
        this.f681b[i2] = (Byte) i
        this.c = i3
    }

    fun e() {
        return this.f - (((this.g << 1) + 8) + (this.g / 2))
    }

    fun e(Int i) {
        if (this.f680a) {
            i(this.c + 5)
        }
        Int i2 = this.c
        com.gmail.heagoo.a.c.a.a(this, i)
        return this.c - i2
    }

    fun f(Int i) {
        if (i < 0) {
            throw IllegalArgumentException("count < 0")
        }
        Int i2 = this.c + i
        if (this.f680a) {
            i(i2)
        } else if (i2 > this.f681b.length) {
            i()
            return
        }
        this.c = i2
    }

    public Array<Byte> f() {
        return this.f681b
    }

    fun g(Int i) {
        Int i2 = i - 1
        if (i < 0 || (i2 & i) != 0) {
            throw IllegalArgumentException("bogus alignment")
        }
        Int i3 = (i2 ^ (-1)) & (this.c + i2)
        if (this.f680a) {
            i(i3)
        } else if (i3 > this.f681b.length) {
            i()
            return
        }
        this.c = i3
    }

    public Array<Byte> g() {
        Array<Byte> bArr = new Byte[this.c]
        System.arraycopy(this.f681b, 0, bArr, 0, this.c)
        return bArr
    }

    fun h(Int i) {
        if (this.f680a) {
            i(this.c + 5)
        }
        Int i2 = this.c
        com.gmail.heagoo.a.c.a.b(this, i)
        return this.c - i2
    }

    fun h() {
        d()
        if (this.e != null) {
            for (Int size = this.e.size(); size > 0; size--) {
                f fVar = (f) this.e.get(size - 1)
                if (fVar.a() <= this.c) {
                    if (fVar.b() > this.c) {
                        fVar.b(this.c)
                        return
                    }
                    return
                }
                this.e.remove(size - 1)
            }
        }
    }
}
