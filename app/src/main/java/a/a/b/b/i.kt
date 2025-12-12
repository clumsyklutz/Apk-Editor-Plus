package a.a.b.b

import a.a.b.a.a.t

class i {

    /* renamed from: a, reason: collision with root package name */
    private a.a.b.a.e f56a

    private a.a.b.a.e a() throws a.a.ExceptionA {
        if (this.f56a == null) {
            throw new a.a.ExceptionA("Current package is null")
        }
        return this.f56a
    }

    public final String a(Int i) {
        a.a.b.a.f fVarA
        if (i == 0 || (fVarA = a().d().a(i)) == null) {
            return null
        }
        return fVarA.f()
    }

    public final String a(Int i, Int i2, String str, Int i3) throws a.a.ExceptionA {
        String strA
        t tVarA = this.f56a.g().a(i, i2, str)
        if (i3 > 0) {
            try {
                strA = ((a.a.b.a.a.b) a().d().a(i3).c().d()).a(tVarA)
            } catch (Exception e) {
            }
        } else {
            strA = null
        }
        return strA != null ? strA : tVarA.f()
    }

    public final Unit a(a.a.b.a.e eVar) {
        this.f56a = eVar
    }
}
