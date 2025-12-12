package com.a.b.a.d

import com.a.b.f.c.aa
import com.a.b.f.c.y
import com.a.b.f.c.z
import jadx.core.deobf.Deobfuscator

class k implements com.a.b.a.e.c {

    /* renamed from: a, reason: collision with root package name */
    private final String f240a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.h.c f241b
    private final Boolean c
    private aa d
    private Int e
    private z f
    private z g
    private com.a.b.f.d.e h
    private com.a.b.a.e.e i
    private com.a.b.a.e.h j
    private com.a.b.a.e.k k
    private b l

    private constructor(com.a.b.h.c cVar, String str, Boolean z) {
        if (str == null) {
            throw NullPointerException("filePath == null")
        }
        this.f240a = str
        this.f241b = cVar
        this.c = z
        this.e = -1
    }

    constructor(Array<Byte> bArr, String str, Boolean z) {
        this(new com.a.b.h.c(bArr), str, z)
    }

    fun a(Object obj) {
        return obj == null ? "(none)" : obj.toString()
    }

    private fun m() {
        return this.f241b.c(0)
    }

    private fun n() {
        return this.f241b.f(4)
    }

    private fun o() {
        return this.f241b.f(6)
    }

    private fun p() {
        if (this.e == -1) {
            r()
        }
    }

    private fun q() {
        if (this.k == null) {
            r()
        }
    }

    private fun r() {
        Boolean z = true
        try {
            if (this.f241b.a() < 10) {
                throw new com.a.b.a.e.i("severely truncated class file")
            }
            if (this.c) {
                Int iM = m()
                Int iN = n()
                Int iO = o()
                if (iM != -889275714 || iN < 0 || (iO != 51 ? iO >= 51 || iO < 45 : iN > 0)) {
                    z = false
                }
                if (!z) {
                    throw new com.a.b.a.e.i("bad class file magic (" + com.gmail.heagoo.a.c.a.t(m()) + ") or version (" + com.gmail.heagoo.a.c.a.v(o()) + Deobfuscator.CLASS_NAME_SEPARATOR + com.gmail.heagoo.a.c.a.v(n()) + ")")
                }
            }
            com.a.b.a.c.a aVar = new com.a.b.a.c.a(this.f241b)
            aVar.a((com.a.b.a.e.j) null)
            this.d = aVar.b()
            this.d.b_()
            Int iA = aVar.a()
            Int iF = this.f241b.f(iA)
            this.f = (z) this.d.a(this.f241b.f(iA + 2))
            this.g = (z) this.d.b(this.f241b.f(iA + 4))
            Int iF2 = this.f241b.f(iA + 6)
            Int i = iA + 8
            this.h = a(i, iF2)
            Int i2 = (iF2 << 1) + i
            if (this.c) {
                String strH = this.f.i().h()
                if (!this.f240a.endsWith(".class") || !this.f240a.startsWith(strH) || this.f240a.length() != strH.length() + 6) {
                    throw new com.a.b.a.e.i("class name (" + strH + ") does not match path (" + this.f240a + ")")
                }
            }
            this.e = iF
            m mVar = m(this, this.f, i2, this.l)
            mVar.a((com.a.b.a.e.j) null)
            this.i = mVar.a()
            o oVar = o(this, this.f, mVar.d(), this.l)
            oVar.a((com.a.b.a.e.j) null)
            this.j = oVar.a()
            c cVar = c(this, 0, oVar.d(), this.l)
            cVar.a(null)
            this.k = cVar.b()
            this.k.b_()
            Int iA2 = cVar.a()
            if (iA2 != this.f241b.a()) {
                throw new com.a.b.a.e.i("extra bytes at end of class file, at offset " + com.gmail.heagoo.a.c.a.t(iA2))
            }
        } catch (com.a.b.a.e.i e) {
            e.a("...while parsing " + this.f240a)
            throw e
        } catch (RuntimeException e2) {
            com.a.b.a.e.i iVar = new com.a.b.a.e.i(e2)
            iVar.a("...while parsing " + this.f240a)
            throw iVar
        }
    }

    public final com.a.b.f.d.e a(Int i, Int i2) {
        if (i2 == 0) {
            return com.a.b.f.d.b.f565a
        }
        if (this.d == null) {
            throw IllegalStateException("pool not yet initialized")
        }
        return l(this.f241b, i, i2, this.d, null)
    }

    public final String a() {
        return this.f240a
    }

    public final Unit a(b bVar) {
        if (bVar == null) {
            throw NullPointerException("attributeFactory == null")
        }
        this.l = bVar
    }

    public final com.a.b.h.c b() {
        return this.f241b
    }

    public final Int c() {
        p()
        return m()
    }

    @Override // com.a.b.a.e.c
    public final Int d() {
        p()
        return this.e
    }

    public final z e() {
        p()
        return this.f
    }

    public final z f() {
        p()
        return this.g
    }

    public final com.a.b.f.c.b g() {
        p()
        return this.d
    }

    public final com.a.b.f.d.e h() {
        p()
        return this.h
    }

    public final com.a.b.a.e.e i() {
        q()
        return this.i
    }

    public final com.a.b.a.e.h j() {
        q()
        return this.j
    }

    public final com.a.b.a.e.b k() {
        q()
        return this.k
    }

    @Override // com.a.b.a.e.c
    public final y l() {
        com.a.b.a.e.a aVarA = k().a("SourceFile")
        if (aVarA is com.a.b.a.a.p) {
            return ((com.a.b.a.a.p) aVarA).b()
        }
        return null
    }
}
