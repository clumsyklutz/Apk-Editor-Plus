package com.gmail.heagoo.apkeditor.b

class d {

    /* renamed from: a, reason: collision with root package name */
    private Array<Byte> f871a

    /* renamed from: b, reason: collision with root package name */
    private Int f872b

    constructor(Array<Byte> bArr, Int i) {
        this.f871a = bArr
        this.f872b = i
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0076 A[PHI: r0 r2
  0x0076: PHI (r0v6 Int) = (r0v5 Int), (r0v8 Int) binds: [B:5:0x001f, B:9:0x003d] A[DONT_GENERATE, DONT_INLINE]
  0x0076: PHI (r2v2 Int) = (r2v1 Int), (r2v5 Int) binds: [B:5:0x001f, B:9:0x003d] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Int a() throws com.gmail.heagoo.apkeditor.b.i {
        /*
            r7 = this
            r6 = 1
            r5 = 0
            r4 = 127(0x7f, Float:1.78E-43)
            Int r0 = r7.f872b
            Array<Byte> r2 = r7.f871a
            Int r1 = r0 + 1
            r0 = r2[r0]
            r0 = r0 & 255(0xff, Float:3.57E-43)
            if (r0 <= r4) goto L73
            Array<Byte> r3 = r7.f871a
            Int r2 = r1 + 1
            r1 = r3[r1]
            r1 = r1 & 255(0xff, Float:3.57E-43)
            r0 = r0 & 127(0x7f, Float:1.78E-43)
            r3 = r1 & 127(0x7f, Float:1.78E-43)
            Int r3 = r3 << 7
            r0 = r0 | r3
            if (r1 <= r4) goto L76
            Array<Byte> r3 = r7.f871a
            Int r1 = r2 + 1
            r2 = r3[r2]
            r2 = r2 & 255(0xff, Float:3.57E-43)
            r3 = r2 & 127(0x7f, Float:1.78E-43)
            Int r3 = r3 << 14
            r0 = r0 | r3
            if (r2 <= r4) goto L73
            Array<Byte> r3 = r7.f871a
            Int r2 = r1 + 1
            r1 = r3[r1]
            r1 = r1 & 255(0xff, Float:3.57E-43)
            r3 = r1 & 127(0x7f, Float:1.78E-43)
            Int r3 = r3 << 21
            r0 = r0 | r3
            if (r1 <= r4) goto L76
            Array<Byte> r3 = r7.f871a
            Int r1 = r2 + 1
            r2 = r3[r2]
            if (r2 >= 0) goto L59
            com.gmail.heagoo.apkeditor.b.i r0 = new com.gmail.heagoo.apkeditor.b.i
            java.lang.String r1 = "Invalid uleb128 at offset 0x%x"
            java.lang.Array<Object> r2 = new java.lang.Object[r6]
            Int r3 = r7.f872b
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r2[r5] = r3
            r0.<init>(r1, r2)
            throw r0
        L59:
            r3 = r2 & 15
            r4 = 7
            if (r3 <= r4) goto L70
            com.gmail.heagoo.apkeditor.b.i r0 = new com.gmail.heagoo.apkeditor.b.i
            java.lang.String r1 = "uleb128 is out of range at offset 0x%x"
            java.lang.Array<Object> r2 = new java.lang.Object[r6]
            Int r3 = r7.f872b
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r2[r5] = r3
            r0.<init>(r1, r2)
            throw r0
        L70:
            Int r2 = r2 << 28
            r0 = r0 | r2
        L73:
            r7.f872b = r1
            return r0
        L76:
            r1 = r2
            goto L73
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.b.d.a():Int")
    }

    public final String a(Int i) {
        Array<Int> iArr = new Int[1]
        String strA = j.a(this.f871a, this.f872b, i, iArr)
        this.f872b = iArr[0] + this.f872b
        return strA
    }

    public final Int b() {
        return this.f872b
    }

    public final Unit b(Int i) {
        this.f872b = i
    }
}
