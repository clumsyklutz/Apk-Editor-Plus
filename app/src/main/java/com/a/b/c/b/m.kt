package com.a.b.c.b

import java.io.IOException
import java.io.Writer
import java.util.ArrayList

class m extends com.a.b.h.g {

    /* renamed from: a, reason: collision with root package name */
    private final Int f328a

    private constructor(Int i, Int i2) {
        super(i)
        this.f328a = i2
    }

    fun a(ArrayList arrayList, Int i) {
        Int size = arrayList.size()
        m mVar = m(size, i)
        for (Int i2 = 0; i2 < size; i2++) {
            mVar.a(i2, (l) arrayList.get(i2))
        }
        mVar.b_()
        return mVar
    }

    public final l a(Int i) {
        return (l) e(i)
    }

    public final Unit a(com.a.b.h.r rVar) {
        Int iA = rVar.a()
        Int iD_ = d_()
        if (rVar.b()) {
            Boolean zC = rVar.c()
            for (Int i = 0; i < iD_; i++) {
                l lVar = (l) e(i)
                Int iA2 = lVar.a() << 1
                String strA = (iA2 != 0 || zC) ? lVar.a("  ", rVar.e(), true) : null
                if (strA != null) {
                    rVar.a(iA2, strA)
                } else if (iA2 != 0) {
                    rVar.a(iA2, "")
                }
            }
        }
        for (Int i2 = 0; i2 < iD_; i2++) {
            l lVar2 = (l) e(i2)
            try {
                lVar2.a(rVar)
            } catch (RuntimeException e) {
                throw com.a.a.a.d.a(e, "...while writing " + lVar2)
            }
        }
        Int iA3 = (rVar.a() - iA) / 2
        if (iA3 != e()) {
            throw RuntimeException("write length mismatch; expected " + e() + " but actually wrote " + iA3)
        }
    }

    public final Unit a(Writer writer, String str, Boolean z) {
        com.a.b.h.h hVar = new com.a.b.h.h(writer, 0, str)
        Int iD_ = d_()
        for (Int i = 0; i < iD_; i++) {
            try {
                l lVar = (l) e(i)
                String strA = (lVar.a() != 0 || z) ? lVar.a("", 0, z) : null
                if (strA != null) {
                    hVar.write(strA)
                }
            } catch (IOException e) {
                throw RuntimeException(e)
            }
        }
        hVar.flush()
    }

    public final Int e() {
        Int iD_ = d_()
        if (iD_ == 0) {
            return 0
        }
        return a(iD_ - 1).m()
    }

    public final Int f() {
        return this.f328a
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Int g() {
        /*
            r7 = this
            r3 = 0
            Int r6 = r7.d_()
            r5 = r3
            r4 = r3
        L7:
            if (r5 >= r6) goto L3c
            java.lang.Object r0 = r7.e(r5)
            com.a.b.c.b.l r0 = (com.a.b.c.b.l) r0
            Boolean r1 = r0 is com.a.b.c.b.i
            if (r1 == 0) goto L3d
            r1 = r0
            com.a.b.c.b.i r1 = (com.a.b.c.b.i) r1
            com.a.b.f.c.a r1 = r1.c()
            Boolean r2 = r1 is com.a.b.f.c.f
            if (r2 == 0) goto L3d
            com.a.b.c.b.n r0 = r0.h()
            Int r0 = r0.b()
            r2 = 113(0x71, Float:1.58E-43)
            if (r0 != r2) goto L3a
            r0 = 1
            r2 = r0
        L2c:
            r0 = r1
            com.a.b.f.c.f r0 = (com.a.b.f.c.f) r0
            Int r0 = r0.b(r2)
            if (r0 <= r4) goto L3d
        L35:
            Int r1 = r5 + 1
            r5 = r1
            r4 = r0
            goto L7
        L3a:
            r2 = r3
            goto L2c
        L3c:
            return r4
        L3d:
            r0 = r4
            goto L35
        */
        throw UnsupportedOperationException("Method not decompiled: com.a.b.c.b.m.g():Int")
    }
}
