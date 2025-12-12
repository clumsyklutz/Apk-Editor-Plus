package com.a.b.c.c

import java.util.ArrayList

class f {

    /* renamed from: a, reason: collision with root package name */
    private static final com.a.b.f.c.z f389a = com.a.b.f.c.z.b(com.a.b.f.d.c.a("Ldalvik/annotation/AnnotationDefault;"))

    /* renamed from: b, reason: collision with root package name */
    private static final com.a.b.f.c.z f390b = com.a.b.f.c.z.b(com.a.b.f.d.c.a("Ldalvik/annotation/EnclosingClass;"))
    private static final com.a.b.f.c.z c = com.a.b.f.c.z.b(com.a.b.f.d.c.a("Ldalvik/annotation/EnclosingMethod;"))
    private static final com.a.b.f.c.z d = com.a.b.f.c.z.b(com.a.b.f.d.c.a("Ldalvik/annotation/InnerClass;"))
    private static final com.a.b.f.c.z e = com.a.b.f.c.z.b(com.a.b.f.d.c.a("Ldalvik/annotation/MemberClasses;"))
    private static final com.a.b.f.c.z f = com.a.b.f.c.z.b(com.a.b.f.d.c.a("Ldalvik/annotation/Signature;"))
    private static final com.a.b.f.c.z g = com.a.b.f.c.z.b(com.a.b.f.d.c.a("Ldalvik/annotation/Throws;"))
    private static final com.a.b.f.c.y h = new com.a.b.f.c.y("accessFlags")
    private static final com.a.b.f.c.y i = new com.a.b.f.c.y("name")
    private static final com.a.b.f.c.y j = new com.a.b.f.c.y("value")

    public static com.a.b.f.a.a a(com.a.b.f.a.a aVar) {
        com.a.b.f.a.a aVar2 = new com.a.b.f.a.a(f389a, com.a.b.f.a.b.SYSTEM)
        aVar2.a(new com.a.b.f.a.e(j, new com.a.b.f.c.c(aVar)))
        aVar2.b_()
        return aVar2
    }

    public static com.a.b.f.a.a a(com.a.b.f.c.v vVar) {
        com.a.b.f.a.a aVar = new com.a.b.f.a.a(c, com.a.b.f.a.b.SYSTEM)
        aVar.a(new com.a.b.f.a.e(j, vVar))
        aVar.b_()
        return aVar
    }

    public static com.a.b.f.a.a a(com.a.b.f.c.y yVar) {
        com.a.b.f.a.a aVar = new com.a.b.f.a.a(f, com.a.b.f.a.b.SYSTEM)
        String strJ = yVar.j()
        Int length = strJ.length()
        ArrayList arrayList = ArrayList(20)
        Int i2 = 0
        while (i2 < length) {
            Int i3 = i2 + 1
            if (strJ.charAt(i2) == 'L') {
                while (true) {
                    if (i3 >= length) {
                        break
                    }
                    Char cCharAt = strJ.charAt(i3)
                    if (cCharAt == ';') {
                        i3++
                        break
                    }
                    if (cCharAt != '<') {
                        i3++
                    }
                }
            } else {
                while (i3 < length && strJ.charAt(i3) != 'L') {
                    i3++
                }
            }
            arrayList.add(strJ.substring(i2, i3))
            i2 = i3
        }
        Int size = arrayList.size()
        com.a.b.f.c.e eVar = new com.a.b.f.c.e(size)
        for (Int i4 = 0; i4 < size; i4++) {
            eVar.a(i4, (com.a.b.f.c.a) new com.a.b.f.c.y((String) arrayList.get(i4)))
        }
        eVar.b_()
        aVar.a(new com.a.b.f.a.e(j, new com.a.b.f.c.d(eVar)))
        aVar.b_()
        return aVar
    }

    public static com.a.b.f.a.a a(com.a.b.f.c.y yVar, Int i2) {
        com.a.b.f.a.a aVar = new com.a.b.f.a.a(d, com.a.b.f.a.b.SYSTEM)
        com.a.b.f.c.a aVar2 = yVar
        if (yVar == null) {
            aVar2 = com.a.b.f.c.p.f549a
        }
        aVar.a(new com.a.b.f.a.e(i, aVar2))
        aVar.a(new com.a.b.f.a.e(h, com.a.b.f.c.n.a(i2)))
        aVar.b_()
        return aVar
    }

    public static com.a.b.f.a.a a(com.a.b.f.c.z zVar) {
        com.a.b.f.a.a aVar = new com.a.b.f.a.a(f390b, com.a.b.f.a.b.SYSTEM)
        aVar.a(new com.a.b.f.a.e(j, zVar))
        aVar.b_()
        return aVar
    }

    public static com.a.b.f.a.a a(com.a.b.f.d.e eVar) {
        com.a.b.f.c.d dVarC = c(eVar)
        com.a.b.f.a.a aVar = new com.a.b.f.a.a(e, com.a.b.f.a.b.SYSTEM)
        aVar.a(new com.a.b.f.a.e(j, dVarC))
        aVar.b_()
        return aVar
    }

    public static com.a.b.f.a.a b(com.a.b.f.d.e eVar) {
        com.a.b.f.c.d dVarC = c(eVar)
        com.a.b.f.a.a aVar = new com.a.b.f.a.a(g, com.a.b.f.a.b.SYSTEM)
        aVar.a(new com.a.b.f.a.e(j, dVarC))
        aVar.b_()
        return aVar
    }

    private static com.a.b.f.c.d c(com.a.b.f.d.e eVar) {
        Int iD_ = eVar.d_()
        com.a.b.f.c.e eVar2 = new com.a.b.f.c.e(iD_)
        for (Int i2 = 0; i2 < iD_; i2++) {
            eVar2.a(i2, (com.a.b.f.c.a) com.a.b.f.c.z.b(eVar.a(i2)))
        }
        eVar2.b_()
        return new com.a.b.f.c.d(eVar2)
    }
}
