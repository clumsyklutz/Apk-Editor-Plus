package com.a.a

import java.io.UTFDataFormatException
import java.nio.ByteBuffer

class o implements com.a.a.a.b, com.a.a.a.c {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ i f139a

    /* renamed from: b, reason: collision with root package name */
    private final String f140b
    private final ByteBuffer c
    private final Int d

    private constructor(i iVar, String str, ByteBuffer byteBuffer) {
        this.f139a = iVar
        this.f140b = str
        this.c = byteBuffer
        this.d = byteBuffer.position()
    }

    /* synthetic */ o(i iVar, String str, ByteBuffer byteBuffer, Byte b2) {
        this(iVar, str, byteBuffer)
    }

    static /* synthetic */ b a(o oVar) {
        return b(oVar.j(com.gmail.heagoo.a.c.a.b(oVar)), oVar.j(com.gmail.heagoo.a.c.a.b(oVar)), oVar.k(com.gmail.heagoo.a.c.a.b(oVar)), oVar.k(com.gmail.heagoo.a.c.a.b(oVar)))
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x00cf, code lost:
    
        r8[r10] = new com.a.a.h(r13, r14, r2)
        r10 = r10 + 1
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static /* synthetic */ com.a.a.f b(com.a.a.o r20) {
        /*
            Method dump skipped, instructions count: 239
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.a.a.o.b(com.a.a.o):com.a.a.f")
    }

    private Array<Short> i(Int i) {
        if (i == 0) {
            return i.f131a
        }
        Array<Short> sArr = new Short[i]
        for (Int i2 = 0; i2 < i; i2++) {
            sArr[i2] = this.c.getShort()
        }
        return sArr
    }

    private Array<c> j(Int i) {
        Array<c> cVarArr = new c[i]
        Int iB = 0
        for (Int i2 = 0; i2 < i; i2++) {
            iB += com.gmail.heagoo.a.c.a.b(this)
            cVarArr[i2] = c(iB, com.gmail.heagoo.a.c.a.b(this))
        }
        return cVarArr
    }

    private Array<d> k(Int i) {
        Array<d> dVarArr = new d[i]
        Int iB = 0
        for (Int i2 = 0; i2 < i; i2++) {
            iB += com.gmail.heagoo.a.c.a.b(this)
            dVarArr[i2] = d(iB, com.gmail.heagoo.a.c.a.b(this), com.gmail.heagoo.a.c.a.b(this))
        }
        return dVarArr
    }

    private Array<Byte> l(Int i) {
        Array<Byte> bArr = new Byte[this.c.position() - i]
        this.c.position(i)
        this.c.get(bArr)
        return bArr
    }

    private fun o() {
        return this.c.getShort() & 65535
    }

    public final Int a() {
        return this.c.position()
    }

    public final Unit a(ab abVar) {
        Array<Short> sArrA = abVar.a()
        f(sArrA.length)
        for (Short s : sArrA) {
            a(s)
        }
        l()
    }

    public final Unit a(String str) {
        try {
            g(str.length())
            a(com.gmail.heagoo.a.c.a.f(str))
            d(0)
        } catch (UTFDataFormatException e) {
            throw AssertionError()
        }
    }

    public final Unit a(Short s) {
        this.c.putShort(s)
    }

    public final Unit a(Array<Byte> bArr) {
        this.c.put(bArr)
    }

    public final Unit a(Array<Short> sArr) {
        for (Short s : sArr) {
            a(s)
        }
    }

    public final Array<Byte> a(Int i) {
        Array<Byte> bArr = new Byte[i]
        this.c.get(bArr)
        return bArr
    }

    public final Int b() {
        return this.c.getInt()
    }

    public final Unit b(Int i) {
        g(i + 1)
    }

    public final Short c() {
        return this.c.getShort()
    }

    public final Unit c(Int i) {
        if (i < 0) {
            throw IllegalArgumentException()
        }
        this.c.position(this.c.position() + i)
    }

    @Override // com.a.a.a.b
    public final Byte d() {
        return this.c.get()
    }

    @Override // com.a.a.a.c
    public final Unit d(Int i) {
        this.c.put((Byte) i)
    }

    public final ab e() {
        Array<Short> sArrI = i(this.c.getInt())
        this.c.position((this.c.position() + 3) & (-4))
        return ab(this.f139a, sArrI)
    }

    public final Unit e(Int i) {
        Short s = (Short) i
        if (i != (65535 & s)) {
            throw IllegalArgumentException("Expected an unsigned Short: " + i)
        }
        a(s)
    }

    public final String f() {
        Int i = this.c.getInt()
        Int iPosition = this.c.position()
        Int iLimit = this.c.limit()
        this.c.position(i)
        this.c.limit(this.c.capacity())
        try {
            try {
                Int iB = com.gmail.heagoo.a.c.a.b(this)
                String strA = com.gmail.heagoo.a.c.a.a(this, new Char[iB])
                if (strA.length() != iB) {
                    throw s("Declared length " + iB + " doesn't match decoded length of " + strA.length())
                }
                return strA
            } catch (UTFDataFormatException e) {
                throw s(e)
            }
        } finally {
            this.c.position(iPosition)
            this.c.limit(iLimit)
        }
    }

    public final Unit f(Int i) {
        this.c.putInt(i)
    }

    public final w g() {
        return w(this.f139a, o(), o(), this.c.getInt())
    }

    public final Unit g(Int i) {
        try {
            com.gmail.heagoo.a.c.a.a((com.a.a.a.c) this, i)
        } catch (ArrayIndexOutOfBoundsException e) {
            throw s("Section limit " + this.c.limit() + " exceeded by " + this.f140b)
        }
    }

    public final x h() {
        return x(this.f139a, o(), o(), this.c.getInt())
    }

    public final Unit h(Int i) {
        try {
            com.gmail.heagoo.a.c.a.b((com.a.a.a.c) this, i)
        } catch (ArrayIndexOutOfBoundsException e) {
            throw s("Section limit " + this.c.limit() + " exceeded by " + this.f140b)
        }
    }

    public final y i() {
        return y(this.f139a, this.c.getInt(), this.c.getInt(), this.c.getInt())
    }

    public final a j() {
        Byte b2 = this.c.get()
        Int iPosition = this.c.position()
        v(this, 29).t()
        return a(this.f139a, b2, u(l(iPosition)))
    }

    public final u k() {
        Int iPosition = this.c.position()
        v(this, 28).t()
        return u(l(iPosition))
    }

    public final Unit l() {
        while ((this.c.position() & 3) != 0) {
            this.c.put((Byte) 0)
        }
    }

    public final Unit m() {
        if ((this.c.position() & 3) != 0) {
            throw IllegalStateException("Not four Byte aligned!")
        }
    }

    public final Int n() {
        return this.c.position() - this.d
    }
}
