package a.a.b.a.a

import java.io.Serializable

class s extends q implements Serializable {
    private final a.a.b.a.e c
    private final Boolean d

    constructor(a.a.b.a.e eVar, Int i, String str) {
        this(eVar, 0, null, false)
    }

    constructor(a.a.b.a.e eVar, Int i, String str, Boolean z) {
        super(i, str, "reference")
        this.c = eVar
        this.d = z
    }

    @Override // a.a.b.a.a.q, a.a.b.a.a.t
    protected final String a() {
        a.a.b.a.f fVarD
        if (e() || (fVarD = d()) == null) {
            return "@null"
        }
        return ((this.d ? '?' : '@') + (fVarD.d() && (fVarD.c().d() instanceof o) ? "+" : "")) + fVarD.a(this.c, this.d && fVarD.h().a().equals("attr"))
    }

    public final a.a.b.a.f d() {
        try {
            return this.c.d().a(b())
        } catch (a.a.a.b e) {
            return null
        }
    }

    public final Boolean e() {
        return this.f16a == 0
    }
}
