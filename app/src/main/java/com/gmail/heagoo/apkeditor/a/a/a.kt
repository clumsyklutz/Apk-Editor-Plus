package com.gmail.heagoo.apkeditor.a.a

import java.io.IOException
import java.util.ArrayList
import java.util.Iterator
import java.util.List
import java.util.Stack

class a {

    /* renamed from: a, reason: collision with root package name */
    private Array<Byte> f779a

    /* renamed from: b, reason: collision with root package name */
    private s f780b
    private r c
    private List d = ArrayList()

    constructor(s sVar, r rVar) {
        this.f780b = sVar
        this.c = rVar
    }

    private fun b(m mVar) throws IOException {
        Int iA = mVar.a()
        Int iA2 = mVar.a()
        this.f779a = new Byte[iA2]
        e.a(this.f779a, 0, iA)
        e.a(this.f779a, 4, iA2)
        if (iA2 > 8) {
            mVar.a(this.f779a, 8, iA2 - 8)
        }
        return iA
    }

    public final Unit a() {
        List listA = this.f780b.a()
        if (listA == null || listA.isEmpty()) {
            return
        }
        Array<Int> iArrB = this.f780b.b()
        Iterator it = this.d.iterator()
        while (it.hasNext()) {
            ((w) it.next()).a(iArrB)
        }
    }

    public final Unit a(l lVar) throws IOException {
        Iterator it = this.d.iterator()
        while (it.hasNext()) {
            ((w) it.next()).a(lVar)
        }
    }

    public final Unit a(m mVar) {
        Int iB
        Stack stack = Stack()
        do {
            iB = b(mVar)
            Array<Byte> bArr = this.f779a
            w wVar = null
            if (iB == 1048834) {
                wVar = w(iB, bArr, this.f780b, this.c, stack.size())
                if (!stack.isEmpty()) {
                    wVar.a((w) stack.peek())
                }
                stack.push(wVar)
            } else if (iB != 1048835) {
                wVar = w(iB, bArr, this.f780b, this.c, stack.size())
            } else if (!stack.isEmpty()) {
                stack.pop()
                wVar = w(iB, bArr, this.f780b, this.c, stack.size())
            }
            this.d.add(wVar)
        } while (iB != b.f781a);
    }

    public final Unit a(String str, Int i, Int i2, Int i3, Int i4) {
        for (w wVar : this.d) {
            if (wVar.a() == 1048834 && str.equals(wVar.b())) {
                wVar.a(0, -1, 268435464, i4)
            }
        }
    }

    public final Unit a(String str, String str2, d dVar) {
        List<x> listC
        for (w wVar : this.d) {
            if (wVar.a() == 1048834 && str.equals(wVar.b()) && (listC = wVar.c()) != null) {
                for (x xVar : listC) {
                    if (str2.equals(xVar.f817b)) {
                        dVar.a(xVar)
                    }
                }
            }
        }
    }
}
