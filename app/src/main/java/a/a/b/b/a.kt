package a.a.b.b

import a.a.b.a.a.p
import a.a.b.a.a.t
import a.a.b.a.a.u
import a.a.b.a.a.w
import a.a.b.a.a.x
import androidx.core.internal.view.SupportMenu
import androidx.core.view.InputDeviceCompat
import androidx.core.view.ViewCompat
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.math.BigInteger
import java.util.ArrayList
import java.util.Arrays
import java.util.HashMap
import java.util.List
import java.util.logging.Logger

class a {
    private static val u = Logger.getLogger(a.class.getName())

    /* renamed from: a, reason: collision with root package name */
    private m f45a

    /* renamed from: b, reason: collision with root package name */
    private Array<Boolean> f46b
    private Boolean c
    private Boolean d
    private final a.d.f f
    private final com.gmail.heagoo.a.c.a g
    private final a.a.b.a.FilterInputStreamA h
    private final List i
    private final Boolean j
    private d k
    private n l
    private n m
    private n n
    private a.a.b.a.e o
    private a.a.b.a.i p
    private a.a.b.a.h q
    private Int r
    private Array<Boolean> s
    private Array<Byte> e = new Byte[12]
    private HashMap t = HashMap()

    private constructor(InputStream inputStream, com.gmail.heagoo.a.c.a aVar, m mVar, Boolean z, Boolean z2, Boolean z3) {
        this.f45a = mVar
        this.d = z3
        a.a.b.a.FilterInputStreamA aVar2 = new a.a.b.a.FilterInputStreamA(inputStream)
        this.h = aVar2
        if (z) {
            this.i = ArrayList()
        } else {
            this.i = null
        }
        this.f = new a.d.f(new a.a.b.a.b(aVar2))
        this.g = aVar
        this.j = z2
    }

    private fun a(Byte b2, Int i) {
        Boolean zB
        String str
        String str2
        if ((this.r & ViewCompat.MEASURED_STATE_MASK) != 16777216) {
            String strA = this.l.a(i)
            String strB = this.l.b(i, strA)
            zB = this.l.b()
            str = strA
            str2 = strB
        } else {
            zB = false
            str = ""
            str2 = ""
        }
        if (b2 != 3) {
            return this.o.g().a(b2, i, (String) null)
        }
        this.o.g()
        return (str2 == null || str2.equals("")) ? u(str2, i) : (this.c || str2.startsWith("res/") || str2.startsWith("r/") || str2.startsWith("R/")) ? new a.a.b.a.a.i(str, i) : u(str2, i, str, zB)
    }

    fun a(InputStream inputStream, Boolean z, Boolean z2, com.gmail.heagoo.a.c.a aVar, m mVar, Boolean z3) throws a.a.ExceptionA {
        try {
            a aVar2 = a(inputStream, aVar, mVar, false, z2, z3)
            aVar2.f()
            aVar2.b(2)
            Int i = aVar2.f.readInt()
            aVar2.l = n.a(aVar2.f)
            a.a.b.a.Array<e> eVarArr = new a.a.b.a.e[i]
            aVar2.f()
            for (Int i2 = 0; i2 < i; i2++) {
                aVar2.b(512)
                Byte b2 = (Byte) aVar2.f.readInt()
                Byte b3 = b2 == 0 ? (Byte) 2 : b2
                String strA = aVar2.f.a(128, true)
                aVar2.f.skipBytes(4)
                aVar2.f.skipBytes(4)
                aVar2.f.skipBytes(4)
                aVar2.f.skipBytes(4)
                aVar2.m = n.a(aVar2.f)
                aVar2.n = n.a(aVar2.f)
                if (aVar2.d) {
                    StringBuilder sb = StringBuilder()
                    sb.append('_')
                    sb.append('r')
                    sb.append('r')
                    String string = sb.toString()
                    Int i3 = 0
                    for (Int i4 = 0; i4 < aVar2.n.a(); i4++) {
                        String strA2 = aVar2.n.a(i4)
                        if (strA2 != null && !strA2.equals("") && !strA2.matches("[a-zA-Z0-9_\\.]+")) {
                            aVar2.n.a(i4, string + i3)
                            i3++
                        } else if ("do".equals(strA2)) {
                            aVar2.n.a(i4, "do" + string + i3)
                            i3++
                        } else if ("if".equals(strA2)) {
                            aVar2.n.a(i4, "if" + string + i3)
                            i3++
                        } else if ("for".equals(strA2)) {
                            aVar2.n.a(i4, "for" + string + i3)
                            i3++
                        }
                    }
                }
                aVar2.f46b = new Boolean[aVar2.m.a() + 32]
                Arrays.fill(aVar2.f46b, false)
                for (Int i5 = 0; i5 < aVar2.m.a(); i5++) {
                    String strA3 = aVar2.m.a(i5)
                    if (strA3.equals("ani") || strA3.equals("animator") || strA3.equals("drawable") || strA3.equals("interpolator") || strA3.equals("layout") || strA3.equals("menu") || strA3.equals("mipmap") || strA3.equals("raw") || strA3.equals("transition") || strA3.equals("xml")) {
                        aVar2.f46b[i5] = true
                    }
                }
                aVar2.r = b3 << 24
                aVar2.o = new a.a.b.a.e(aVar2.g, b3, strA)
                aVar2.f()
                Boolean z4 = true
                while (z4) {
                    switch (aVar2.k.f48a) {
                        case 514:
                            aVar2.b()
                            break
                        case 515:
                            aVar2.a()
                            break
                        default:
                            z4 = false
                            break
                    }
                }
                eVarArr[i2] = aVar2.o
            }
            return b(eVarArr, aVar2.i == null ? null : (Array<c>) aVar2.i.toArray(new c[0]), aVar)
        } catch (IOException e) {
            throw new a.a.ExceptionA("Could not decode arsc file", e)
        }
    }

    private fun a(Int i) {
        Int i2
        Short s
        StringBuilder sb = StringBuilder(16)
        while (true) {
            i2 = i - 1
            if (i == 0 || this.f.readByte() == 0) {
                break
            }
            sb.append((Char) s)
            i = i2
        }
        this.f.skipBytes(i2)
        return sb.toString()
    }

    private fun a(Int i, Int i2) {
        String strA
        return (this.f45a == null || (strA = this.f45a.a(i)) == null) ? this.n.a(i2) : strA
    }

    private fun a() throws a.a.ExceptionA, IOException {
        b(515)
        Int i = this.f.readInt()
        for (Int i2 = 0; i2 < i; i2++) {
            u.info(String.format("Decoding (%s), pkgId: %d", this.f.a(128, true), Integer.valueOf(this.f.readInt())))
        }
        while (f().f48a == 513) {
            b()
        }
    }

    private fun a(a.a.b.a.i iVar) {
        this.t.put(Integer.valueOf(iVar.b()), iVar)
    }

    private static Array<Char> a(Byte b2, Byte b3, Char c) {
        return ((b2 >> 7) & 1) == 1 ? new Array<Char>{(Char) ((b3 & 31) + c), (Char) (((b2 & 3) << 3) + ((b3 & 224) >> 5) + c), (Char) (((b2 & 124) >> 2) + c)} : new Array<Char>{(Char) b2, (Char) b3}
    }

    private a.a.b.a.i b() throws a.a.ExceptionA, IOException {
        this.p = c()
        a(this.p)
        Short s = f().f48a
        while (s == 514) {
            a(c())
            s = f().f48a
        }
        while (s == 513) {
            b(InputDeviceCompat.SOURCE_DPAD)
            this.f.readFully(this.e)
            Int i = this.e[0] & 255
            if (this.t.containsKey(Integer.valueOf(i))) {
                this.r = (((a.a.b.a.i) this.t.get(Integer.valueOf(i))).b() << 16) | (this.r & ViewCompat.MEASURED_STATE_MASK)
                this.p = (a.a.b.a.i) this.t.get(Integer.valueOf(i))
            }
            if (i < this.f46b.length) {
                this.c = this.f46b[i - 1]
            } else {
                this.c = false
            }
            Int i2 = ((this.e[7] & 255) << 24) | ((this.e[6] & 255) << 16) | ((this.e[5] & 255) << 8) | (this.e[4] & 255)
            Int i3 = ((this.e[11] & 255) << 24) | ((this.e[10] & 255) << 16) | ((this.e[9] & 255) << 8) | (this.e[8] & 255)
            this.s = new Boolean[i2]
            Arrays.fill(this.s, true)
            Int i4 = this.f.readInt()
            Int i5 = 28
            if (i4 < 28) {
                throw new a.a.ExceptionA("Config size < 28")
            }
            Boolean z = false
            Array<Byte> bArr = new Byte[24]
            this.f.readFully(bArr)
            Short s2 = (Short) (((bArr[1] & 255) << 8) | (bArr[0] & 255))
            Short s3 = (Short) (((bArr[3] & 255) << 8) | (bArr[2] & 255))
            Array<Char> cArrA = a(bArr[4], bArr[5], 'a')
            Array<Char> cArrA2 = a(bArr[6], bArr[7], '0')
            Byte b2 = bArr[8]
            Byte b3 = bArr[9]
            Int i6 = ((bArr[11] & 255) << 8) | (bArr[10] & 255)
            Byte b4 = bArr[12]
            Byte b5 = bArr[13]
            Byte b6 = bArr[14]
            Short s4 = (Short) (((bArr[17] & 255) << 8) | (bArr[16] & 255))
            Short s5 = (Short) (((bArr[19] & 255) << 8) | (bArr[18] & 255))
            Short s6 = (Short) ((bArr[20] & 255) | ((bArr[21] & 255) << 8))
            Byte b7 = 0
            Byte b8 = 0
            Short s7 = 0
            if (i4 >= 32) {
                Array<Byte> bArr2 = new Byte[4]
                this.f.readFully(bArr2)
                b7 = bArr2[0]
                b8 = bArr2[1]
                s7 = (Short) ((bArr2[2] & 255) | ((bArr2[3] & 255) << 8))
                i5 = 32
            }
            Short s8 = 0
            Short s9 = 0
            if (i4 >= 36) {
                Array<Byte> bArr3 = new Byte[4]
                this.f.readFully(bArr3)
                s8 = (Short) (((bArr3[1] & 255) << 8) | (bArr3[0] & 255))
                s9 = (Short) ((bArr3[2] & 255) | ((bArr3[3] & 255) << 8))
                i5 = 36
            }
            Array<Char> charArray = null
            Array<Char> charArray2 = null
            if (i4 >= 48) {
                charArray = a(4).toCharArray()
                charArray2 = a(8).toCharArray()
                i5 = 48
            }
            Byte b9 = 0
            Byte b10 = 0
            if (i4 >= 52) {
                b9 = this.f.readByte()
                b10 = this.f.readByte()
                this.f.skipBytes(2)
                i5 = 52
            }
            if (i4 >= 56) {
                this.f.skipBytes(4)
                i5 = 56
            }
            Int i7 = i4 - 56
            if (i7 > 0) {
                Array<Byte> bArr4 = new Byte[i7]
                i5 += i7
                this.f.readFully(bArr4)
                if (!BigInteger(1, bArr4).equals(BigInteger.ZERO)) {
                    z = true
                }
            }
            Int i8 = i4 - i5
            if (i8 > 0) {
                this.f.skipBytes(i8)
            }
            a.a.b.a.c cVar = new a.a.b.a.c(s2, s3, cArrA, cArrA2, b2, b3, i6, b4, b5, b6, s4, s5, s6, b7, b8, s7, s8, s9, charArray, charArray2, b9, b10, z, i4)
            Int i9 = (this.k.f49b + i3) - (i2 << 2)
            if (i9 != this.h.a()) {
                this.f.skipBytes(i9 - this.h.a())
            }
            Array<Int> iArrA = this.f.a(i2)
            if (cVar.f25a) {
                String str = this.p.a() + cVar.a()
                if (this.j) {
                    u.warning("Invalid config flags detected: " + str)
                } else {
                    u.warning("Invalid config flags detected. Dropping resources: " + str)
                }
            }
            this.q = (!cVar.f25a || this.j) ? this.o.a(cVar) : null
            for (Int i10 = 0; i10 < iArrA.length; i10++) {
                if (iArrA[i10] != -1) {
                    this.s[i10] = false
                    this.r = (this.r & SupportMenu.CATEGORY_MASK) | i10
                    d()
                }
            }
            a.a.b.a.h hVar = this.q
            if (this.h.a() < this.k.c) {
                this.h.skip(this.k.c - this.h.a())
            }
            s = f().f48a
            e()
        }
        return this.p
    }

    private fun b(Int i) throws a.a.ExceptionA {
        if (this.k.f48a != i) {
            throw new a.a.ExceptionA(String.format("Invalid chunk type: expected=0x%08x, got=0x%08x", Integer.valueOf(i), Short.valueOf(this.k.f48a)))
        }
    }

    private a.a.b.a.i c() throws a.a.ExceptionA {
        b(514)
        Int unsignedByte = this.f.readUnsignedByte()
        this.f.skipBytes(3)
        Int i = this.f.readInt()
        if (this.i != null) {
            this.i.add(c(this.h.a(), i))
        }
        this.f.skipBytes(i << 2)
        this.p = new a.a.b.a.i(this.m.a(unsignedByte - 1), this.g, this.o, unsignedByte, i)
        this.o.a(this.p)
        return this.p
    }

    private fun d() throws a.a.ExceptionA {
        w wVarA
        a.a.b.a.f fVar
        Array<Byte> bArr = new Byte[8]
        this.f.readFully(bArr)
        Short s = (Short) (((bArr[3] & 255) << 8) | (bArr[2] & 255))
        Int i = (bArr[7] << 24) | ((bArr[6] & 255) << 16) | ((bArr[5] & 255) << 8) | (bArr[4] & 255)
        if (this.h.a() == this.k.c) {
            return
        }
        if ((s & 1) == 0) {
            Array<Byte> bArr2 = new Byte[8]
            this.f.readFully(bArr2)
            wVarA = a(bArr2[3], (bArr2[4] & 255) | ((bArr2[7] & 255) << 24) | ((bArr2[6] & 255) << 16) | ((bArr2[5] & 255) << 8))
        } else {
            Array<Int> iArrA = this.f.a(2)
            Int i2 = iArrA[0]
            Int i3 = iArrA[1]
            x xVarG = this.o.g()
            a.d.Array<e> eVarArr = new a.d.e[i3]
            Array<Byte> bArr3 = new Byte[i3 * 12]
            this.f.readFully(bArr3)
            for (Int i4 = 0; i4 < i3; i4++) {
                Int i5 = i4 * 12
                Int i6 = (bArr3[i5] & 255) | ((bArr3[i5 + 3] & 255) << 24) | ((bArr3[i5 + 2] & 255) << 16) | ((bArr3[i5 + 1] & 255) << 8)
                Byte b2 = bArr3[i5 + 7]
                Int i7 = ((bArr3[i5 + 11] & 255) << 24) | ((bArr3[i5 + 10] & 255) << 16) | ((bArr3[i5 + 9] & 255) << 8) | (bArr3[i5 + 8] & 255)
                if (((bArr3[i5 + 4] & 255) | ((bArr3[i5 + 5] & 255) << 8)) != 8) {
                    Log.d("DEBUG", "error")
                }
                p pVarA = a(b2, i7)
                if (pVarA is t) {
                    eVarArr[i4] = new a.d.e(Integer.valueOf(i6), (t) pVarA)
                } else {
                    eVarArr[i4] = new a.d.e(Integer.valueOf(i6), u(pVarA.toString(), pVarA.c()))
                }
            }
            wVarA = xVarG.a(i2, eVarArr, this.p)
        }
        w uVar = (this.p.c() && (wVarA is a.a.b.a.FilterInputStreamA.i)) ? u(wVarA.toString(), ((a.a.b.a.FilterInputStreamA.i) wVarA).c()) : wVarA
        if (this.q != null) {
            a.a.b.a.d dVar = new a.a.b.a.d(this.r)
            if (this.o.a(dVar)) {
                fVar = this.o.b(dVar)
                if (fVar.i()) {
                    if (this.o.a(fVar.e())) {
                        this.o.a(fVar)
                        this.p.a(fVar)
                    }
                    fVar = new a.a.b.a.f(dVar, a(dVar.f28b, i), this.o, this.p, this.d)
                    this.o.b(fVar)
                    this.p.b(fVar)
                }
            } else {
                fVar = new a.a.b.a.f(dVar, a(dVar.f28b, i), this.o, this.p, this.d)
                this.o.b(fVar)
                this.p.b(fVar)
            }
            a.a.b.a.g gVar = new a.a.b.a.g(this.q, fVar, uVar)
            try {
                this.q.a(gVar)
                fVar.a(gVar)
            } catch (a.a.a e) {
                if (!this.j) {
                    throw e
                }
                this.q.a(gVar, true)
                fVar.a(gVar, true)
                u.warning(String.format("Duplicate res igonred: %s", gVar.toString()))
            }
        }
    }

    private fun e() throws a.a.ExceptionA {
        Int i = this.r & SupportMenu.CATEGORY_MASK
        for (Int i2 = 0; i2 < this.s.length; i2++) {
            if (this.s[i2]) {
                a.a.b.a.f fVar = new a.a.b.a.f(new a.a.b.a.d(i | i2), "carlos_ae_" + Integer.toHexString(i2), this.o, this.p, false)
                if (!this.o.a(new a.a.b.a.d(i | i2))) {
                    this.o.b(fVar)
                    this.p.b(fVar)
                    if (this.q == null) {
                        this.q = this.o.a(new a.a.b.a.c())
                    }
                    a.a.b.a.g gVar = new a.a.b.a.g(this.q, fVar, new a.a.b.a.a.d(false, 0, null))
                    this.q.a(gVar)
                    fVar.a(gVar)
                }
            }
        }
    }

    private fun f() {
        d dVarA = d.a(this.f, this.h)
        this.k = dVarA
        return dVarA
    }
}
