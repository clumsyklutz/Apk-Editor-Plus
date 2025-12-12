package com.a.b.a.b

class h {

    /* renamed from: a, reason: collision with root package name */
    private static k f204a = i()

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.h.c f205b
    private final com.a.b.f.c.b c

    constructor(com.a.b.h.c cVar, com.a.b.f.c.b bVar) {
        if (cVar == null) {
            throw NullPointerException("bytes == null")
        }
        if (bVar == null) {
            throw NullPointerException("pool == null")
        }
        this.f205b = cVar
        this.c = bVar
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0776  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Int a(Int r14, com.a.b.a.b.k r15) {
        /*
            Method dump skipped, instructions count: 2414
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.a.b.a.b.h.a(Int, com.a.b.a.b.k):Int")
    }

    public final com.a.b.h.c a() {
        return this.f205b
    }

    public final Unit a(k kVar) {
        Int iA = this.f205b.a()
        Int iA2 = 0
        while (iA2 < iA) {
            iA2 += a(iA2, kVar)
        }
    }

    public final Int b() {
        return this.f205b.a()
    }

    public final Int c() {
        return this.f205b.a() + 4
    }
}
