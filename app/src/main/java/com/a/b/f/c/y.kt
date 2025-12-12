package com.a.b.f.c

class y extends ab {

    /* renamed from: a, reason: collision with root package name */
    private final String f559a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.h.c f560b

    static {
        y("")
    }

    constructor(com.a.b.h.c cVar) {
        if (cVar == null) {
            throw NullPointerException("bytes == null")
        }
        this.f560b = cVar
        this.f559a = a(cVar).intern()
    }

    constructor(String str) {
        if (str == null) {
            throw NullPointerException("string == null")
        }
        this.f559a = str.intern()
        this.f560b = new com.a.b.h.c(a(str))
    }

    private fun a(Int i, Int i2) {
        throw IllegalArgumentException("bad utf-8 Byte " + com.gmail.heagoo.a.c.a.x(i) + " at offset " + com.gmail.heagoo.a.c.a.t(i2))
    }

    private fun a(com.a.b.h.c cVar) {
        Int i
        Char c
        Int iA = cVar.a()
        Array<Char> cArr = new Char[iA]
        Int i2 = 0
        Int i3 = 0
        while (iA > 0) {
            Int iE = cVar.e(i2)
            switch (iE >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    iA--
                    if (iE != 0) {
                        c = (Char) iE
                        i2++
                        break
                    } else {
                        return a(iE, i2)
                    }
                case 8:
                case 9:
                case 10:
                case 11:
                default:
                    return a(iE, i2)
                case 12:
                case 13:
                    iA -= 2
                    if (iA < 0) {
                        return a(iE, i2)
                    }
                    Int iE2 = cVar.e(i2 + 1)
                    if ((iE2 & 192) != 128) {
                        return a(iE2, i2 + 1)
                    }
                    Int i4 = ((iE & 31) << 6) | (iE2 & 63)
                    if (i4 != 0 && i4 < 128) {
                        return a(iE2, i2 + 1)
                    }
                    c = (Char) i4
                    i2 += 2
                    break
                case 14:
                    iA -= 3
                    if (iA < 0) {
                        return a(iE, i2)
                    }
                    Int iE3 = cVar.e(i2 + 1)
                    if ((iE3 & 192) != 128) {
                        return a(iE3, i2 + 1)
                    }
                    Int iE4 = cVar.e(i2 + 2)
                    if ((iE3 & 192) == 128 && (i = ((iE & 15) << 12) | ((iE3 & 63) << 6) | (iE4 & 63)) >= 2048) {
                        c = (Char) i
                        i2 += 3
                        break
                    }
                    return a(iE4, i2 + 2)
            }
            cArr[i3] = c
            i3++
        }
        return String(cArr, 0, i3)
    }

    private static Array<Byte> a(String str) {
        Int length = str.length()
        Array<Byte> bArr = new Byte[length * 3]
        Int i = 0
        for (Int i2 = 0; i2 < length; i2++) {
            Char cCharAt = str.charAt(i2)
            if (cCharAt != 0 && cCharAt < 128) {
                bArr[i] = (Byte) cCharAt
                i++
            } else if (cCharAt < 2048) {
                bArr[i] = (Byte) (((cCharAt >> 6) & 31) | 192)
                bArr[i + 1] = (Byte) ((cCharAt & '?') | 128)
                i += 2
            } else {
                bArr[i] = (Byte) (((cCharAt >> '\f') & 15) | 224)
                bArr[i + 1] = (Byte) (((cCharAt >> 6) & 63) | 128)
                bArr[i + 2] = (Byte) ((cCharAt & '?') | 128)
                i += 3
            }
        }
        Array<Byte> bArr2 = new Byte[i]
        System.arraycopy(bArr, 0, bArr2, 0, i)
        return bArr2
    }

    @Override // com.a.b.f.d.d
    public final com.a.b.f.d.c a() {
        return com.a.b.f.d.c.p
    }

    @Override // com.a.b.f.c.a
    protected final Int b(a aVar) {
        return this.f559a.compareTo(((y) aVar).f559a)
    }

    @Override // com.a.b.h.s
    public final String d() {
        Int length = this.f559a.length()
        StringBuilder sb = StringBuilder((length * 3) / 2)
        Int i = 0
        while (i < length) {
            Char cCharAt = this.f559a.charAt(i)
            if (cCharAt >= ' ' && cCharAt < 127) {
                if (cCharAt == '\'' || cCharAt == '\"' || cCharAt == '\\') {
                    sb.append('\\')
                }
                sb.append(cCharAt)
            } else if (cCharAt <= 127) {
                switch (cCharAt) {
                    case '\t':
                        sb.append("\\t")
                        break
                    case '\n':
                        sb.append("\\n")
                        break
                    case 11:
                    case '\f':
                    default:
                        Char cCharAt2 = i < length + (-1) ? this.f559a.charAt(i + 1) : (Char) 0
                        Boolean z = cCharAt2 >= '0' && cCharAt2 <= '7'
                        sb.append('\\')
                        for (Int i2 = 6; i2 >= 0; i2 -= 3) {
                            Char c = (Char) (((cCharAt >> i2) & 7) + 48)
                            if (c != '0' || z) {
                                sb.append(c)
                                z = true
                            }
                        }
                        if (z) {
                            break
                        } else {
                            sb.append('0')
                            break
                        }
                    case '\r':
                        sb.append("\\r")
                        break
                }
            } else {
                sb.append("\\u")
                sb.append(Character.forDigit(cCharAt >> '\f', 16))
                sb.append(Character.forDigit((cCharAt >> '\b') & 15, 16))
                sb.append(Character.forDigit((cCharAt >> 4) & 15, 16))
                sb.append(Character.forDigit(cCharAt & 15, 16))
            }
            i++
        }
        return sb.toString()
    }

    public final Boolean equals(Object obj) {
        if (obj is y) {
            return this.f559a.equals(((y) obj).f559a)
        }
        return false
    }

    @Override // com.a.b.f.c.a
    public final Boolean g() {
        return false
    }

    @Override // com.a.b.f.c.a
    public final String h() {
        return "utf8"
    }

    public final Int hashCode() {
        return this.f559a.hashCode()
    }

    public final String i() {
        return "\"" + d() + '\"'
    }

    public final String j() {
        return this.f559a
    }

    public final com.a.b.h.c k() {
        return this.f560b
    }

    public final Int l() {
        return this.f560b.a()
    }

    public final Int m() {
        return this.f559a.length()
    }

    public final String toString() {
        return "string{\"" + d() + "\"}"
    }
}
