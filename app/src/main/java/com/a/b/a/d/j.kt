package com.a.b.a.d

import com.a.b.a.b.ak
import com.a.b.f.c.t
import com.a.b.f.c.z
import java.util.ArrayList

class j implements com.a.b.a.b.k {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.h.c f238a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.a.e.j f239b

    constructor(com.a.b.h.c cVar, com.a.b.a.e.j jVar) {
        if (cVar == null) {
            throw NullPointerException("bytes == null")
        }
        if (jVar == null) {
            throw NullPointerException("observer == null")
        }
        this.f238a = cVar
        this.f239b = jVar
    }

    private fun b(Int i) {
        Int iE = this.f238a.e(i)
        String strA = com.a.b.a.b.g.a(iE)
        if (iE == 196) {
            strA = strA + " " + com.a.b.a.b.g.a(this.f238a.e(i + 1))
        }
        return com.gmail.heagoo.a.c.a.v(i) + ": " + strA
    }

    @Override // com.a.b.a.b.k
    public final Int a() {
        return -1
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i) {
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3) {
        b(i2)
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3, Int i4) {
        StringBuilder().append(b(i2)).append(" ").append(i3 <= 3 ? com.gmail.heagoo.a.c.a.v(i4) : com.gmail.heagoo.a.c.a.t(i4))
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3, Int i4, com.a.b.f.d.c cVar, Int i5) {
        String strX = i3 <= 3 ? com.gmail.heagoo.a.c.a.x(i4) : com.gmail.heagoo.a.c.a.v(i4)
        Boolean z = i3 == 1
        String str = ""
        if (i == 132) {
            str = ", #" + (i3 <= 3 ? com.gmail.heagoo.a.c.a.B(i5) : com.gmail.heagoo.a.c.a.A(i5))
        }
        String str2 = ""
        if (cVar.k()) {
            str2 = (z ? "," : " //") + " category-2"
        }
        StringBuilder().append(b(i2)).append(z ? " // " : " ").append(strX).append(str).append(str2)
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3, ak akVar, Int i4) {
        Int iA = akVar.a()
        StringBuffer stringBuffer = StringBuffer((iA * 20) + 100)
        stringBuffer.append(b(i2))
        if (i4 != 0) {
            stringBuffer.append(" // padding: " + com.gmail.heagoo.a.c.a.t(i4))
        }
        stringBuffer.append('\n')
        for (Int i5 = 0; i5 < iA; i5++) {
            stringBuffer.append("  ")
            stringBuffer.append(com.gmail.heagoo.a.c.a.z(akVar.a(i5)))
            stringBuffer.append(": ")
            stringBuffer.append(com.gmail.heagoo.a.c.a.v(akVar.b(i5)))
            stringBuffer.append('\n')
        }
        stringBuffer.append("  default: ")
        stringBuffer.append(com.gmail.heagoo.a.c.a.v(akVar.b()))
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3, com.a.b.f.c.a aVar, Int i4) {
        if (aVar is com.a.b.f.c.p) {
            b(i2)
            return
        }
        if (aVar is com.a.b.f.c.n) {
            String str = i3 == 1 ? " // " : " "
            Int iE = this.f238a.e(i2)
            StringBuilder().append(b(i2)).append(str).append((i3 == 1 || iE == 16) ? "#" + com.gmail.heagoo.a.c.a.B(i4) : iE == 17 ? "#" + com.gmail.heagoo.a.c.a.A(i4) : "#" + com.gmail.heagoo.a.c.a.z(i4))
        } else if (aVar is t) {
            Long jK = ((t) aVar).k()
            StringBuilder().append(b(i2)).append(i3 == 1 ? " // " : " #").append(i3 == 1 ? com.gmail.heagoo.a.c.a.B((Int) jK) : com.gmail.heagoo.a.c.a.b(jK))
        } else if (aVar is com.a.b.f.c.m) {
            Int iJ = ((com.a.b.f.c.m) aVar).j()
            StringBuilder().append(b(i2)).append(i3 != 1 ? " #" + com.gmail.heagoo.a.c.a.t(iJ) : "").append(" // ").append(Float.intBitsToFloat(iJ))
        } else if (!(aVar is com.a.b.f.c.j)) {
            StringBuilder().append(b(i2)).append(" ").append(aVar).append(i4 != 0 ? i == 197 ? ", " + com.gmail.heagoo.a.c.a.x(i4) : ", " + com.gmail.heagoo.a.c.a.v(i4) : "")
        } else {
            Long jK2 = ((com.a.b.f.c.j) aVar).k()
            StringBuilder().append(b(i2)).append(i3 != 1 ? " #" + com.gmail.heagoo.a.c.a.a(jK2) : "").append(" // ").append(Double.longBitsToDouble(jK2))
        }
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, Int i3, com.a.b.f.d.c cVar) {
        b(i2)
    }

    @Override // com.a.b.a.b.k
    public final Unit a(Int i, Int i2, z zVar, ArrayList arrayList) {
        StringBuilder().append(b(i)).append(i2 == 1 ? " // " : " ").append(zVar.i().s().d())
    }
}
