package com.a.b.a.d

import com.a.b.a.a.u
import com.a.b.a.b.q
import com.a.b.f.c.ab
import com.a.b.f.c.w
import com.a.b.f.c.y
import com.a.b.f.c.z
import java.io.IOException

class p extends b {

    /* renamed from: a, reason: collision with root package name */
    public static val f248a = p()

    private fun a(com.a.b.h.c cVar, com.a.b.f.c.b bVar, com.a.b.a.e.j jVar, Int i, Boolean z) {
        y yVar
        y yVar2
        if (cVar.a() != i * 10) {
            c((i * 10) + 2)
        }
        com.a.b.h.d dVarB = cVar.b()
        q qVar = q(i)
        for (Int i2 = 0; i2 < i; i2++) {
            try {
                Int unsignedShort = dVarB.readUnsignedShort()
                Int unsignedShort2 = dVarB.readUnsignedShort()
                Int unsignedShort3 = dVarB.readUnsignedShort()
                Int unsignedShort4 = dVarB.readUnsignedShort()
                Int unsignedShort5 = dVarB.readUnsignedShort()
                y yVar3 = (y) bVar.a(unsignedShort3)
                y yVar4 = (y) bVar.a(unsignedShort4)
                if (z) {
                    yVar = yVar4
                    yVar2 = null
                } else {
                    yVar = null
                    yVar2 = yVar4
                }
                qVar.a(i2, unsignedShort, unsignedShort2, yVar3, yVar2, yVar, unsignedShort5)
                if (jVar != null) {
                    StringBuilder().append(com.gmail.heagoo.a.c.a.v(unsignedShort)).append("..").append(com.gmail.heagoo.a.c.a.v(unsignedShort + unsignedShort2)).append(" ").append(com.gmail.heagoo.a.c.a.v(unsignedShort5)).append(" ").append(yVar3.d()).append(" ").append(yVar4.d())
                }
            } catch (IOException e) {
                throw RuntimeException("shouldn't happen", e)
            }
        }
        qVar.b_()
        return qVar
    }

    private static com.a.b.a.e.a a() {
        throw new com.a.b.a.e.i("severely truncated attribute")
    }

    private static com.a.b.a.e.a a(Int i) {
        return i != 0 ? c(0) : new com.a.b.a.a.d()
    }

    private static com.a.b.a.e.a b() {
        throw new com.a.b.a.e.i("truncated attribute")
    }

    private static com.a.b.a.e.a b(Int i) {
        return i != 0 ? c(0) : new com.a.b.a.a.q()
    }

    private com.a.b.a.e.a b(k kVar, Int i, Int i2, com.a.b.a.e.j jVar) {
        if (i2 < 12) {
            return a()
        }
        com.a.b.h.c cVarB = kVar.b()
        com.a.b.f.c.b bVarG = kVar.g()
        Int iF = cVarB.f(i)
        Int iF2 = cVarB.f(i + 2)
        Int iC = cVarB.c(i + 4)
        if (jVar != null) {
            StringBuilder("max_stack: ").append(com.gmail.heagoo.a.c.a.v(iF))
            StringBuilder("max_locals: ").append(com.gmail.heagoo.a.c.a.v(iF2))
            StringBuilder("code_length: ").append(com.gmail.heagoo.a.c.a.t(iC))
        }
        Int i3 = i + 8
        Int i4 = i2 - 8
        if (i4 < iC + 4) {
            return b()
        }
        Int i5 = i3 + iC
        Int i6 = i4 - iC
        com.a.b.a.b.h hVar = new com.a.b.a.b.h(cVarB.a(i3, iC + i3), bVarG)
        if (jVar != null) {
            hVar.a(j(hVar.a(), jVar))
        }
        Int iF3 = cVarB.f(i5)
        com.a.b.a.b.e eVar = iF3 == 0 ? com.a.b.a.b.e.f199a : new com.a.b.a.b.e(iF3)
        if (jVar != null) {
            StringBuilder("exception_table_length: ").append(com.gmail.heagoo.a.c.a.v(iF3))
        }
        Int i7 = i5 + 2
        Int i8 = i6 - 2
        if (i8 < (iF3 << 3) + 2) {
            return b()
        }
        Int i9 = 0
        Int i10 = i8
        Int i11 = i7
        while (i9 < iF3) {
            Int iF4 = cVarB.f(i11)
            Int iF5 = cVarB.f(i11 + 2)
            Int iF6 = cVarB.f(i11 + 4)
            z zVar = (z) bVarG.b(cVarB.f(i11 + 6))
            eVar.a(i9, iF4, iF5, iF6, zVar)
            if (jVar != null) {
                StringBuilder().append(com.gmail.heagoo.a.c.a.v(iF4)).append("..").append(com.gmail.heagoo.a.c.a.v(iF5)).append(" -> ").append(com.gmail.heagoo.a.c.a.v(iF6)).append(" ").append(zVar == null ? "<any>" : zVar.d())
            }
            i9++
            i10 -= 8
            i11 += 8
        }
        eVar.b_()
        c cVar = c(kVar, 3, i11, this)
        cVar.a(jVar)
        com.a.b.a.e.k kVarB = cVar.b()
        kVarB.b_()
        Int iA = cVar.a() - i11
        return iA != i10 ? c((i11 - i) + iA) : new com.a.b.a.a.b(iF, iF2, hVar, eVar, kVarB)
    }

    private static com.a.b.a.e.a c(Int i) {
        throw new com.a.b.a.e.i("bad attribute length; expected length " + com.gmail.heagoo.a.c.a.t(i))
    }

    private static com.a.b.a.e.a c(k kVar, Int i, Int i2, com.a.b.a.e.j jVar) {
        if (i2 < 2) {
            return a()
        }
        com.a.b.h.c cVarB = kVar.b()
        com.a.b.f.c.b bVarG = kVar.g()
        Int iF = cVarB.f(i)
        if (jVar != null) {
            StringBuilder("number_of_classes: ").append(com.gmail.heagoo.a.c.a.v(iF))
        }
        Int i3 = i + 2
        if (i2 - 2 != (iF << 3)) {
            c((iF << 3) + 2)
        }
        u uVar = u(iF)
        Int i4 = 0
        Int i5 = i3
        while (i4 < iF) {
            Int iF2 = cVarB.f(i5)
            Int iF3 = cVarB.f(i5 + 2)
            Int iF4 = cVarB.f(i5 + 4)
            Int iF5 = cVarB.f(i5 + 6)
            z zVar = (z) bVarG.a(iF2)
            z zVar2 = (z) bVarG.b(iF3)
            y yVar = (y) bVarG.b(iF4)
            uVar.a(i4, zVar, zVar2, yVar, iF5)
            if (jVar != null) {
                StringBuilder("inner_class: ").append(k.a(zVar))
                StringBuilder("  outer_class: ").append(k.a(zVar2))
                StringBuilder("  name: ").append(k.a(yVar))
                StringBuilder("  access_flags: ").append(com.gmail.heagoo.a.c.a.h(iF5))
            }
            i4++
            i5 += 8
        }
        uVar.b_()
        return new com.a.b.a.a.g(uVar)
    }

    private static com.a.b.a.e.a d(k kVar, Int i, Int i2, com.a.b.a.e.j jVar) {
        if (i2 < 2) {
            return a()
        }
        com.a.b.h.c cVarB = kVar.b()
        Int iF = cVarB.f(i)
        if (jVar != null) {
            StringBuilder("line_number_table_length: ").append(com.gmail.heagoo.a.c.a.v(iF))
        }
        Int i3 = i + 2
        if (i2 - 2 != (iF << 2)) {
            c((iF << 2) + 2)
        }
        com.a.b.a.b.o oVar = new com.a.b.a.b.o(iF)
        for (Int i4 = 0; i4 < iF; i4++) {
            Int iF2 = cVarB.f(i3)
            Int iF3 = cVarB.f(i3 + 2)
            oVar.a(i4, iF2, iF3)
            if (jVar != null) {
                StringBuilder().append(com.gmail.heagoo.a.c.a.v(iF2)).append(" ").append(iF3)
            }
            i3 += 4
        }
        oVar.b_()
        return new com.a.b.a.a.h(oVar)
    }

    private static com.a.b.a.e.a e(k kVar, Int i, Int i2, com.a.b.a.e.j jVar) {
        if (i2 < 2) {
            a()
        }
        return new com.a.b.a.a.k(a(kVar, i, i2, jVar).b(com.a.b.f.a.b.BUILD), i2)
    }

    private static com.a.b.a.e.a f(k kVar, Int i, Int i2, com.a.b.a.e.j jVar) {
        if (i2 < 2) {
            a()
        }
        return new com.a.b.a.a.m(a(kVar, i, i2, jVar).b(com.a.b.f.a.b.RUNTIME), i2)
    }

    private static com.a.b.a.e.a g(k kVar, Int i, Int i2, com.a.b.a.e.j jVar) {
        if (i2 != 2) {
            c(2)
        }
        y yVar = (y) kVar.g().a(kVar.b().f(i))
        com.a.b.a.a.o oVar = new com.a.b.a.a.o(yVar)
        if (jVar != null) {
            StringBuilder("signature: ").append(yVar)
        }
        return oVar
    }

    @Override // com.a.b.a.d.b
    protected final com.a.b.a.e.a a(k kVar, Int i, String str, Int i2, Int i3, com.a.b.a.e.j jVar) {
        switch (i) {
            case 0:
                if (str == "Deprecated") {
                    return a(i3)
                }
                if (str == "EnclosingMethod") {
                    if (i3 != 4) {
                        c(4)
                    }
                    com.a.b.h.c cVarB = kVar.b()
                    com.a.b.f.c.b bVarG = kVar.g()
                    z zVar = (z) bVarG.a(cVarB.f(i2))
                    w wVar = (w) bVarG.b(cVarB.f(i2 + 2))
                    com.a.b.a.a.e eVar = new com.a.b.a.a.e(zVar, wVar)
                    if (jVar != null) {
                        StringBuilder("class: ").append(zVar)
                        StringBuilder("method: ").append(k.a(wVar))
                    }
                    return eVar
                }
                if (str == "InnerClasses") {
                    return c(kVar, i2, i3, jVar)
                }
                if (str == "RuntimeInvisibleAnnotations") {
                    return e(kVar, i2, i3, jVar)
                }
                if (str == "RuntimeVisibleAnnotations") {
                    return f(kVar, i2, i3, jVar)
                }
                if (str == "Synthetic") {
                    return b(i3)
                }
                if (str == "Signature") {
                    return g(kVar, i2, i3, jVar)
                }
                if (str == "SourceFile") {
                    if (i3 != 2) {
                        c(2)
                    }
                    y yVar = (y) kVar.g().a(kVar.b().f(i2))
                    com.a.b.a.a.p pVar = new com.a.b.a.a.p(yVar)
                    if (jVar != null) {
                        StringBuilder("source: ").append(yVar)
                    }
                    return pVar
                }
                break
            case 1:
                if (str == "ConstantValue") {
                    if (i3 != 2) {
                        return c(2)
                    }
                    ab abVar = (ab) kVar.g().a(kVar.b().f(i2))
                    com.a.b.a.a.c cVar = new com.a.b.a.a.c(abVar)
                    if (jVar != null) {
                        StringBuilder("value: ").append(abVar)
                    }
                    return cVar
                }
                if (str == "Deprecated") {
                    return a(i3)
                }
                if (str == "RuntimeInvisibleAnnotations") {
                    return e(kVar, i2, i3, jVar)
                }
                if (str == "RuntimeVisibleAnnotations") {
                    return f(kVar, i2, i3, jVar)
                }
                if (str == "Signature") {
                    return g(kVar, i2, i3, jVar)
                }
                if (str == "Synthetic") {
                    return b(i3)
                }
                break
            case 2:
                if (str == "AnnotationDefault") {
                    if (i3 < 2) {
                        a()
                    }
                    return new com.a.b.a.a.a(a(kVar, i2, i3, jVar).a(), i3)
                }
                if (str == "Code") {
                    return b(kVar, i2, i3, jVar)
                }
                if (str == "Deprecated") {
                    return a(i3)
                }
                if (str == "Exceptions") {
                    if (i3 < 2) {
                        return a()
                    }
                    Int iF = kVar.b().f(i2)
                    if (jVar != null) {
                        StringBuilder("number_of_exceptions: ").append(com.gmail.heagoo.a.c.a.v(iF))
                    }
                    Int i4 = i2 + 2
                    if (i3 - 2 != (iF << 1)) {
                        c((iF << 1) + 2)
                    }
                    return new com.a.b.a.a.f(kVar.a(i4, iF))
                }
                if (str == "RuntimeInvisibleAnnotations") {
                    return e(kVar, i2, i3, jVar)
                }
                if (str == "RuntimeVisibleAnnotations") {
                    return f(kVar, i2, i3, jVar)
                }
                if (str == "RuntimeInvisibleParameterAnnotations") {
                    if (i3 < 2) {
                        a()
                    }
                    return new com.a.b.a.a.l(a(kVar, i2, i3, jVar).a(com.a.b.f.a.b.BUILD), i3)
                }
                if (str == "RuntimeVisibleParameterAnnotations") {
                    if (i3 < 2) {
                        a()
                    }
                    return new com.a.b.a.a.n(a(kVar, i2, i3, jVar).a(com.a.b.f.a.b.RUNTIME), i3)
                }
                if (str == "Signature") {
                    return g(kVar, i2, i3, jVar)
                }
                if (str == "Synthetic") {
                    return b(i3)
                }
                break
            case 3:
                if (str == "LineNumberTable") {
                    return d(kVar, i2, i3, jVar)
                }
                if (str == "LocalVariableTable") {
                    if (i3 < 2) {
                        return a()
                    }
                    com.a.b.h.c cVarB2 = kVar.b()
                    Int iF2 = cVarB2.f(i2)
                    if (jVar != null) {
                        StringBuilder("local_variable_table_length: ").append(com.gmail.heagoo.a.c.a.v(iF2))
                    }
                    return new com.a.b.a.a.i(a(cVarB2.a(i2 + 2, i2 + i3), kVar.g(), jVar, iF2, false))
                }
                if (str == "LocalVariableTypeTable") {
                    if (i3 < 2) {
                        return a()
                    }
                    com.a.b.h.c cVarB3 = kVar.b()
                    Int iF3 = cVarB3.f(i2)
                    if (jVar != null) {
                        StringBuilder("local_variable_type_table_length: ").append(com.gmail.heagoo.a.c.a.v(iF3))
                    }
                    return new com.a.b.a.a.j(a(cVarB3.a(i2 + 2, i2 + i3), kVar.g(), jVar, iF3, true))
                }
                break
        }
        return super.a(kVar, i, str, i2, i3, jVar)
    }
}
