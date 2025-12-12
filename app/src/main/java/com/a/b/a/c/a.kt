package com.a.b.a.c

import com.a.b.a.e.i
import com.a.b.a.e.j
import com.a.b.f.c.aa
import com.a.b.f.c.l
import com.a.b.f.c.m
import com.a.b.f.c.n
import com.a.b.f.c.o
import com.a.b.f.c.t
import com.a.b.f.c.v
import com.a.b.f.c.w
import com.a.b.f.c.y
import com.a.b.f.c.z
import com.a.b.h.c
import java.util.BitSet

class a {

    /* renamed from: a, reason: collision with root package name */
    private final c f230a

    /* renamed from: b, reason: collision with root package name */
    private final aa f231b
    private final Array<Int> c
    private Int d
    private j e

    constructor(c cVar) {
        Int iF = cVar.f(8)
        this.f230a = cVar
        this.f231b = aa(iF)
        this.c = new Int[iF]
        this.d = -1
    }

    private com.a.b.f.c.a a(Int i, BitSet bitSet) {
        com.a.b.f.c.a aVarC = this.f231b.c(i)
        if (aVarC == null) {
            Int i2 = this.c[i]
            try {
                Int iE = this.f230a.e(i2)
                switch (iE) {
                    case 1:
                        aVarC = a(i2)
                        bitSet.set(i)
                        break
                    case 2:
                    case 13:
                    case 14:
                    case 17:
                    default:
                        throw i("unknown tag Byte: " + com.gmail.heagoo.a.c.a.x(iE))
                    case 3:
                        aVarC = n.a(this.f230a.c(i2 + 1))
                        break
                    case 4:
                        aVarC = m.a(this.f230a.c(i2 + 1))
                        break
                    case 5:
                        aVarC = t.a(this.f230a.d(i2 + 1))
                        break
                    case 6:
                        aVarC = com.a.b.f.c.j.a(this.f230a.d(i2 + 1))
                        break
                    case 7:
                        aVarC = z(com.a.b.f.d.c.c(((y) a(this.f230a.f(i2 + 1), bitSet)).j()))
                        break
                    case 8:
                        aVarC = a(this.f230a.f(i2 + 1), bitSet)
                        break
                    case 9:
                        aVarC = l((z) a(this.f230a.f(i2 + 1), bitSet), (w) a(this.f230a.f(i2 + 3), bitSet))
                        break
                    case 10:
                        aVarC = v((z) a(this.f230a.f(i2 + 1), bitSet), (w) a(this.f230a.f(i2 + 3), bitSet))
                        break
                    case 11:
                        aVarC = o((z) a(this.f230a.f(i2 + 1), bitSet), (w) a(this.f230a.f(i2 + 3), bitSet))
                        break
                    case 12:
                        aVarC = w((y) a(this.f230a.f(i2 + 1), bitSet), (y) a(this.f230a.f(i2 + 3), bitSet))
                        break
                    case 15:
                        throw i("MethodHandle not supported")
                    case 16:
                        throw i("MethodType not supported")
                    case 18:
                        throw i("InvokeDynamic not supported")
                }
                this.f231b.a(i, aVarC)
            } catch (i e) {
                e.a("...while parsing cst " + com.gmail.heagoo.a.c.a.v(i) + " at offset " + com.gmail.heagoo.a.c.a.t(i2))
                throw e
            } catch (RuntimeException e2) {
                i iVar = i(e2)
                iVar.a("...while parsing cst " + com.gmail.heagoo.a.c.a.v(i) + " at offset " + com.gmail.heagoo.a.c.a.t(i2))
                throw iVar
            }
        }
        return aVarC
    }

    private fun a(Int i) {
        Int i2 = i + 3
        try {
            return y(this.f230a.a(i2, this.f230a.f(i + 1) + i2))
        } catch (IllegalArgumentException e) {
            throw i(e)
        }
    }

    private fun c() {
        if (this.d < 0) {
            d()
            if (this.e != null) {
                StringBuilder("constant_pool_count: ").append(com.gmail.heagoo.a.c.a.v(this.c.length))
            }
            BitSet bitSet = BitSet(this.c.length)
            for (Int i = 1; i < this.c.length; i++) {
                if (this.c[i] != 0 && this.f231b.c(i) == null) {
                    a(i, bitSet)
                }
            }
            if (this.e != null) {
                for (Int i2 = 1; i2 < this.c.length; i2++) {
                    com.a.b.f.c.a aVarC = this.f231b.c(i2)
                    if (aVarC != null) {
                        if (bitSet.get(i2)) {
                            StringBuilder().append(com.gmail.heagoo.a.c.a.v(i2)).append(": utf8{\"").append(aVarC.d()).append("\"}")
                        } else {
                            StringBuilder().append(com.gmail.heagoo.a.c.a.v(i2)).append(": ").append(aVarC.toString())
                        }
                    }
                }
            }
        }
    }

    private fun d() {
        Int i
        Int iF = 10
        Int i2 = 1
        while (i2 < this.c.length) {
            this.c[i2] = iF
            Int iE = this.f230a.e(iF)
            switch (iE) {
                case 1:
                    try {
                        iF += this.f230a.f(iF + 1) + 3
                        i = 1
                        break
                    } catch (i e) {
                        e.a("...while preparsing cst " + com.gmail.heagoo.a.c.a.v(i2) + " at offset " + com.gmail.heagoo.a.c.a.t(iF))
                        throw e
                    }
                case 2:
                case 13:
                case 14:
                case 17:
                default:
                    throw i("unknown tag Byte: " + com.gmail.heagoo.a.c.a.x(iE))
                case 3:
                case 4:
                case 9:
                case 10:
                case 11:
                case 12:
                    iF += 5
                    i = 1
                    break
                case 5:
                case 6:
                    i = 2
                    iF += 9
                    break
                case 7:
                case 8:
                    iF += 3
                    i = 1
                    break
                case 15:
                    throw i("MethodHandle not supported")
                case 16:
                    throw i("MethodType not supported")
                case 18:
                    throw i("InvokeDynamic not supported")
            }
            i2 += i
        }
        this.d = iF
    }

    public final Int a() {
        c()
        return this.d
    }

    public final Unit a(j jVar) {
        this.e = jVar
    }

    public final aa b() {
        c()
        return this.f231b
    }
}
