package com.a.b.c.c

import java.io.Writer
import java.security.DigestException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Iterator
import java.util.zip.Adler32

class r {

    /* renamed from: a, reason: collision with root package name */
    private com.a.b.c.a f408a
    private val n = aa(this)
    private val c = al(null, this, 4, ao.f361a)

    /* renamed from: b, reason: collision with root package name */
    private val f409b = al("word_data", this, 4, ao.f362b)
    private val e = al("string_data", this, 1, ao.c)
    private val l = al(null, this, 1, ao.f361a)
    private val m = al("byte_data", this, 1, ao.f362b)
    private val f = ay(this)
    private val g = ba(this)
    private val h = as(this)
    private val i = y(this)
    private val j = ak(this)
    private val k = k(this)
    private val d = al("map", this, 4, ao.f361a)
    private final Array<at> o = {this.n, this.f, this.g, this.h, this.i, this.j, this.k, this.f409b, this.c, this.e, this.m, this.l, this.d}
    private Int p = -1
    private Int q = 79

    constructor(com.a.b.c.a aVar) {
        this.f408a = aVar
    }

    private com.a.b.h.r a(Boolean z, Boolean z2) throws NoSuchAlgorithmException, DigestException {
        this.k.h()
        this.l.h()
        this.f409b.h()
        this.m.h()
        this.j.h()
        this.i.h()
        this.h.h()
        this.c.h()
        this.g.h()
        this.f.h()
        this.e.h()
        this.n.h()
        Int i = 0
        Int iF_ = 0
        while (i < 13) {
            at atVar = this.o[i]
            Int iB = atVar.b(iF_)
            if (iB < iF_) {
                throw RuntimeException("bogus placement for section " + i)
            }
            try {
                if (atVar == this.d) {
                    af.a(this.o, this.d)
                    this.d.h()
                }
                if (atVar is al) {
                    ((al) atVar).d()
                }
                i++
                iF_ = iB + atVar.f_()
            } catch (RuntimeException e) {
                throw com.a.a.a.d.a(e, "...while writing section " + i)
            }
        }
        this.p = iF_
        Array<Byte> bArr = new Byte[this.p]
        com.a.b.h.r rVar = new com.a.b.h.r(bArr)
        if (z) {
            rVar.a(this.q, z2)
        }
        for (Int i2 = 0; i2 < 13; i2++) {
            try {
                at atVar2 = this.o[i2]
                Int iG = atVar2.g() - rVar.a()
                if (iG < 0) {
                    throw new com.a.a.a.d("excess write of " + (-iG))
                }
                rVar.f(atVar2.g() - rVar.a())
                atVar2.c(rVar)
            } catch (RuntimeException e2) {
                com.a.a.a.d dVar = e2 is com.a.a.a.d ? (com.a.a.a.d) e2 : new com.a.a.a.d(e2)
                dVar.a("...while writing section " + i2)
                throw dVar
            }
        }
        if (rVar.a() != this.p) {
            throw RuntimeException("foreshortened write")
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1")
            messageDigest.update(bArr, 32, bArr.length - 32)
            try {
                Int iDigest = messageDigest.digest(bArr, 12, 20)
                if (iDigest != 20) {
                    throw RuntimeException("unexpected digest write: " + iDigest + " bytes")
                }
                Adler32 adler32 = Adler32()
                adler32.update(bArr, 12, bArr.length - 12)
                Int value = (Int) adler32.getValue()
                bArr[8] = (Byte) value
                bArr[9] = (Byte) (value >> 8)
                bArr[10] = (Byte) (value >> 16)
                bArr[11] = value >> 24
                if (z) {
                    this.f409b.a(rVar, ae.m, "\nmethod code index:\n\n")
                    r().a(rVar)
                    rVar.h()
                }
                return rVar
            } catch (DigestException e3) {
                throw RuntimeException(e3)
            }
        } catch (NoSuchAlgorithmException e4) {
            throw RuntimeException(e4)
        }
    }

    public final j a(String str) {
        try {
            return (j) this.k.a(new com.a.b.f.c.z(com.a.b.f.d.c.c(str)))
        } catch (IllegalArgumentException e) {
            return null
        }
    }

    public final Unit a(Int i) {
        if (i < 40) {
            throw IllegalArgumentException("dumpWidth < 40")
        }
        this.q = i
    }

    public final Unit a(j jVar) {
        this.k.a(jVar)
    }

    final Unit a(com.a.b.f.c.a aVar) {
        if (aVar is com.a.b.f.c.y) {
            this.f.a((com.a.b.f.c.y) aVar)
            return
        }
        if (aVar is com.a.b.f.c.z) {
            this.g.a((com.a.b.f.c.z) aVar)
            return
        }
        if (aVar is com.a.b.f.c.f) {
            this.j.a((com.a.b.f.c.f) aVar)
            return
        }
        if (aVar is com.a.b.f.c.l) {
            this.i.a((com.a.b.f.c.l) aVar)
        } else if (aVar is com.a.b.f.c.k) {
            this.i.a(((com.a.b.f.c.k) aVar).i())
        } else if (aVar == null) {
            throw NullPointerException("cst == null")
        }
    }

    public final Boolean a() {
        return this.k.a().isEmpty()
    }

    public final Array<Byte> a(Writer writer, Boolean z) throws NoSuchAlgorithmException, DigestException {
        Boolean z2 = writer != null
        com.a.b.h.r rVarA = a(z2, z)
        if (z2) {
            rVarA.a(writer)
        }
        return rVarA.f()
    }

    public final com.a.b.c.a b() {
        return this.f408a
    }

    final ac b(com.a.b.f.c.a aVar) {
        if (aVar is com.a.b.f.c.y) {
            return this.f.a(aVar)
        }
        if (aVar is com.a.b.f.c.z) {
            return this.g.a(aVar)
        }
        if (aVar is com.a.b.f.c.f) {
            return this.j.a(aVar)
        }
        if (aVar is com.a.b.f.c.l) {
            return this.i.a(aVar)
        }
        return null
    }

    public final Int c() {
        if (this.p < 0) {
            throw RuntimeException("file size not yet known")
        }
        return this.p
    }

    final al d() {
        return this.e
    }

    final al e() {
        return this.f409b
    }

    final al f() {
        return this.c
    }

    final al g() {
        return this.d
    }

    final ay h() {
        return this.f
    }

    public final k i() {
        return this.k
    }

    final al j() {
        return this.l
    }

    public final ba k() {
        return this.g
    }

    final as l() {
        return this.h
    }

    public final y m() {
        return this.i
    }

    public final ak n() {
        return this.j
    }

    final al o() {
        return this.m
    }

    final at p() {
        return this.f409b
    }

    final at q() {
        return this.d
    }

    public final au r() {
        au auVar = au()
        Array<at> atVarArr = this.o
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= 13) {
                return auVar
            }
            Iterator it = atVarArr[i2].a().iterator()
            while (it.hasNext()) {
                auVar.a((ad) it.next())
            }
            i = i2 + 1
        }
    }
}
