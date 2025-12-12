package com.a.b.g.a

import com.a.b.f.b.r
import com.a.b.g.aa
import com.a.b.g.ac
import com.a.b.g.al
import com.a.b.g.am
import com.a.b.g.z
import java.util.ArrayList

final class b implements am {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ a f572a

    b(a aVar) {
        this.f572a = aVar
    }

    private fun a(al alVar) {
        r rVarF = alVar.f()
        if (rVarF != null) {
            com.a.b.f.b.m mVarI = rVarF.i()
            ArrayList arrayList = (ArrayList) this.f572a.f571b.get(mVarI)
            if (arrayList == null) {
                arrayList = ArrayList()
                this.f572a.f571b.put(mVarI, arrayList)
            }
            arrayList.add(rVarF)
        }
        if (!(alVar is z)) {
            if (alVar is ac) {
                this.f572a.e.add((ac) alVar)
            }
        } else if (alVar.c().a() == 56) {
            this.f572a.c.add((z) alVar)
        } else if (aa.b().a(alVar.e().f(), alVar.a())) {
            this.f572a.d.add((z) alVar)
        }
    }

    @Override // com.a.b.g.am
    public final Unit a(ac acVar) {
        a((al) acVar)
    }

    @Override // com.a.b.g.am
    public final Unit a(z zVar) {
        a((al) zVar)
    }

    @Override // com.a.b.g.am
    public final Unit b(z zVar) {
        a((al) zVar)
    }
}
