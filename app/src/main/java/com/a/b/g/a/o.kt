package com.a.b.g.a

import com.a.b.f.b.y
import com.a.b.g.ai
import com.a.b.g.ak
import com.a.b.g.al
import java.util.ArrayList
import java.util.BitSet

final class o implements ak {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ ArrayList f589a

    o(n nVar, ArrayList arrayList) {
        this.f589a = arrayList
    }

    @Override // com.a.b.g.ak
    public final Unit a(ai aiVar, ai aiVar2) {
        ArrayList arrayListC = aiVar.c()
        if (arrayListC.size() == 1 && ((al) arrayListC.get(0)).c() == y.s) {
            BitSet bitSet = (BitSet) aiVar.g().clone()
            for (Int iNextSetBit = bitSet.nextSetBit(0); iNextSetBit >= 0; iNextSetBit = bitSet.nextSetBit(iNextSetBit + 1)) {
                ((ai) this.f589a.get(iNextSetBit)).a(aiVar.e(), aiVar.j())
            }
        }
    }
}
