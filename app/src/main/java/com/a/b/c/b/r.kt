package com.a.b.c.b

import java.util.BitSet

abstract class r {
    protected static String a(com.a.b.f.b.t tVar) {
        Int iD_ = tVar.d_()
        StringBuffer stringBuffer = StringBuffer((iD_ * 5) + 2)
        stringBuffer.append('{')
        for (Int i = 0; i < iD_; i++) {
            if (i != 0) {
                stringBuffer.append(", ")
            }
            stringBuffer.append(tVar.b(i).m())
        }
        stringBuffer.append('}')
        return stringBuffer.toString()
    }

    protected static String a(com.a.b.f.c.s sVar) {
        StringBuffer stringBuffer = StringBuffer(100)
        stringBuffer.append('#')
        if (sVar is com.a.b.f.c.p) {
            stringBuffer.append("null")
        } else {
            stringBuffer.append(sVar.h())
            stringBuffer.append(' ')
            stringBuffer.append(sVar.d())
        }
        return stringBuffer.toString()
    }

    protected static String a(com.a.b.f.c.s sVar, Int i) {
        StringBuffer stringBuffer = StringBuffer(20)
        stringBuffer.append("#")
        Long jK = sVar is com.a.b.f.c.r ? ((com.a.b.f.c.r) sVar).k() : sVar.j()
        switch (i) {
            case 4:
                stringBuffer.append(com.gmail.heagoo.a.c.a.y((Int) jK))
                break
            case 8:
                stringBuffer.append(com.gmail.heagoo.a.c.a.x((Int) jK))
                break
            case 16:
                stringBuffer.append(com.gmail.heagoo.a.c.a.v((Int) jK))
                break
            case 32:
                stringBuffer.append(com.gmail.heagoo.a.c.a.t((Int) jK))
                break
            case 64:
                stringBuffer.append(com.gmail.heagoo.a.c.a.a(jK))
                break
            default:
                throw RuntimeException("shouldn't happen")
        }
        return stringBuffer.toString()
    }

    protected static Short a(Int i, Int i2) {
        if ((i & 255) != i) {
            throw IllegalArgumentException("low out of range 0..255")
        }
        if ((i2 & 255) != i2) {
            throw IllegalArgumentException("high out of range 0..255")
        }
        return (Short) ((i2 << 8) | i)
    }

    protected static Short a(l lVar, Int i) {
        if ((i & 255) != i) {
            throw IllegalArgumentException("arg out of range 0..255")
        }
        Int iA = lVar.h().a()
        if ((iA & 255) != iA) {
            throw IllegalArgumentException("opcode out of range 0..255")
        }
        return (Short) (iA | (i << 8))
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [Int, Short] */
    protected static Unit a(com.a.b.h.r rVar, Short s, Int i) {
        a(rVar, s, (Short) i, i >> 16)
    }

    protected static Unit a(com.a.b.h.r rVar, Short s, Long j) {
        rVar.b(s)
        rVar.b((Short) j)
        rVar.b((Short) (j >> 16))
        rVar.b((Short) (j >> 32))
        rVar.b((Short) (j >> 48))
    }

    protected static Unit a(com.a.b.h.r rVar, Short s, Short s2) {
        rVar.b(s)
        rVar.b(s2)
    }

    protected static Unit a(com.a.b.h.r rVar, Short s, Short s2, Short s3) {
        rVar.b(s)
        rVar.b(s2)
        rVar.b(s3)
    }

    protected static Boolean a(Int i) {
        return i == (i & 15)
    }

    protected static Int b(Int i, Int i2) {
        if ((i & 15) != i) {
            throw IllegalArgumentException("low out of range 0..15")
        }
        if ((i2 & 15) != i2) {
            throw IllegalArgumentException("high out of range 0..15")
        }
        return (i2 << 4) | i
    }

    protected static Boolean b(Int i) {
        return ((Byte) i) == i
    }

    protected static Boolean c(Int i) {
        return i == (i & 255)
    }

    protected static String d(l lVar) {
        Int iD = ((al) lVar).d()
        return iD == ((Char) iD) ? com.gmail.heagoo.a.c.a.v(iD) : com.gmail.heagoo.a.c.a.t(iD)
    }

    protected static Boolean d(Int i) {
        return ((Short) i) == i
    }

    protected static String e(l lVar) {
        Int iE = ((al) lVar).e()
        return iE == ((Short) iE) ? com.gmail.heagoo.a.c.a.A(iE) : com.gmail.heagoo.a.c.a.z(iE)
    }

    protected static Boolean e(Int i) {
        return i == (65535 & i)
    }

    protected static String f(l lVar) {
        com.a.b.f.c.a aVarC = ((i) lVar).c()
        return aVarC is com.a.b.f.c.y ? ((com.a.b.f.c.y) aVarC).i() : aVarC.d()
    }

    protected static String g(l lVar) {
        i iVar = (i) lVar
        if (!iVar.e()) {
            return ""
        }
        StringBuilder sb = StringBuilder(20)
        Int iD = iVar.d()
        sb.append(iVar.c().h())
        sb.append('@')
        if (iD < 65536) {
            sb.append(com.gmail.heagoo.a.c.a.v(iD))
        } else {
            sb.append(com.gmail.heagoo.a.c.a.t(iD))
        }
        return sb.toString()
    }

    public abstract Int a()

    public abstract String a(l lVar)

    public abstract String a(l lVar, Boolean z)

    public abstract Unit a(com.a.b.h.r rVar, l lVar)

    fun a(al alVar) {
        return false
    }

    public abstract Boolean b(l lVar)

    fun c(l lVar) {
        return BitSet()
    }
}
