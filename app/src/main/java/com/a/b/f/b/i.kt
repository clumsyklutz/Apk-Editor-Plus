package com.a.b.f.b

abstract class i implements com.a.b.h.s {

    /* renamed from: a, reason: collision with root package name */
    private final w f507a

    /* renamed from: b, reason: collision with root package name */
    private final z f508b
    private final r c
    private final t d

    constructor(w wVar, z zVar, r rVar, t tVar) {
        if (wVar == null) {
            throw NullPointerException("opcode == null")
        }
        if (zVar == null) {
            throw NullPointerException("position == null")
        }
        if (tVar == null) {
            throw NullPointerException("sources == null")
        }
        this.f507a = wVar
        this.f508b = zVar
        this.c = rVar
        this.d = tVar
    }

    private fun a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2))
    }

    public abstract i a(r rVar, t tVar)

    public abstract i a(com.a.b.f.d.c cVar)

    fun a() {
        return null
    }

    public abstract Unit a(k kVar)

    fun a(i iVar) {
        return this.f507a == iVar.f507a && this.f508b.equals(iVar.f508b) && getClass() == iVar.getClass() && a(this.c, iVar.c) && a(this.d, iVar.d) && com.a.b.f.d.b.a(b(), iVar.b())
    }

    public abstract com.a.b.f.d.e b()

    @Override // com.a.b.h.s
    public final String d() {
        String strA = a()
        StringBuffer stringBuffer = StringBuffer(80)
        stringBuffer.append(this.f508b)
        stringBuffer.append(": ")
        stringBuffer.append(this.f507a.g())
        if (strA != null) {
            stringBuffer.append("(")
            stringBuffer.append(strA)
            stringBuffer.append(")")
        }
        if (this.c == null) {
            stringBuffer.append(" .")
        } else {
            stringBuffer.append(" ")
            stringBuffer.append(this.c.d())
        }
        stringBuffer.append(" <-")
        Int iD_ = this.d.d_()
        if (iD_ == 0) {
            stringBuffer.append(" .")
        } else {
            for (Int i = 0; i < iD_; i++) {
                stringBuffer.append(" ")
                stringBuffer.append(this.d.b(i).d())
            }
        }
        return stringBuffer.toString()
    }

    public final Boolean equals(Object obj) {
        return this == obj
    }

    public final w f() {
        return this.f507a
    }

    public final z g() {
        return this.f508b
    }

    public final r h() {
        return this.c
    }

    public final Int hashCode() {
        return System.identityHashCode(this)
    }

    public final r i() {
        r rVarB = this.f507a.a() == 54 ? this.d.b(0) : this.c
        if (rVarB == null || rVarB.i() == null) {
            return null
        }
        return rVarB
    }

    public final t j() {
        return this.d
    }

    public final Boolean k() {
        return this.f507a.h()
    }

    fun l() {
        return this
    }

    fun toString() {
        String strA = a()
        StringBuffer stringBuffer = StringBuffer(80)
        stringBuffer.append("Insn{")
        stringBuffer.append(this.f508b)
        stringBuffer.append(' ')
        stringBuffer.append(this.f507a)
        if (strA != null) {
            stringBuffer.append(' ')
            stringBuffer.append(strA)
        }
        stringBuffer.append(" :: ")
        if (this.c != null) {
            stringBuffer.append(this.c)
            stringBuffer.append(" <- ")
        }
        stringBuffer.append(this.d)
        stringBuffer.append('}')
        return stringBuffer.toString()
    }
}
