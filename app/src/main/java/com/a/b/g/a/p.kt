package com.a.b.g.a

import com.a.b.f.b.r
import com.a.b.f.b.t
import com.a.b.g.ac
import com.a.b.g.ae
import com.a.b.g.ai
import java.util.ArrayList

final class p implements ae {

    /* renamed from: a, reason: collision with root package name */
    private final ArrayList f590a

    constructor(ArrayList arrayList) {
        this.f590a = arrayList
    }

    @Override // com.a.b.g.ae
    public final Unit a(ac acVar) {
        t tVarA = acVar.a()
        r rVarN = acVar.n()
        Int iD_ = tVarA.d_()
        for (Int i = 0; i < iD_; i++) {
            ((ai) this.f590a.get(acVar.a(i))).a(rVarN, tVarA.b(i))
        }
    }
}
