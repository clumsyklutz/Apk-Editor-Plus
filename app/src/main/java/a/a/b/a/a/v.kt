package a.a.b.a.a

class v extends c implements a.a.b.d.a {

    /* renamed from: b, reason: collision with root package name */
    private final a.d.Array<e> f21b

    v(s sVar, a.d.Array<e> eVarArr, x xVar) {
        super(sVar)
        this.f21b = new a.d.e[eVarArr.length]
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= eVarArr.length) {
                return
            }
            this.f21b[i2] = new a.d.e(xVar.a(((Integer) eVarArr[i2].f71a).intValue(), (String) null), eVarArr[i2].f72b)
            i = i2 + 1
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0099  */
    @Override // a.a.b.a.a.c, a.a.b.d.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Unit a(org.xmlpull.v1.XmlSerializer r8, a.a.b.a.g r9) {
        /*
            r7 = this
            r5 = 1
            r3 = 0
            r4 = 0
            java.lang.String r0 = "style"
            r8.startTag(r4, r0)
            java.lang.String r0 = "name"
            a.a.b.a.f r1 = r9.c()
            java.lang.String r1 = r1.f()
            r8.attribute(r4, r0, r1)
            a.a.b.a.a.s r0 = r7.f7a
            Boolean r0 = r0.e()
            if (r0 != 0) goto L99
            a.a.b.a.a.s r0 = r7.f7a
            a.a.b.a.f r0 = r0.d()
            if (r0 != 0) goto L97
            r0 = r5
        L26:
            if (r0 != 0) goto L99
            java.lang.String r0 = "parent"
            a.a.b.a.a.s r1 = r7.f7a
            java.lang.String r1 = r1.f()
            r8.attribute(r4, r0, r1)
        L33:
            r2 = r3
        L34:
            a.d.Array<e> r0 = r7.f21b
            Int r0 = r0.length
            if (r2 >= r0) goto Ld0
            a.d.Array<e> r0 = r7.f21b
            r0 = r0[r2]
            java.lang.Object r0 = r0.f71a
            a.a.b.a.a.s r0 = (a.a.b.a.a.s) r0
            a.a.b.a.f r6 = r0.d()
            if (r6 == 0) goto L93
            a.a.b.a.g r0 = r6.c()
            a.a.b.a.a.w r0 = r0.d()
            Boolean r1 = r0 is a.a.b.a.a.s
            if (r1 != 0) goto L93
            Boolean r1 = r0 is a.a.b.a.a.b
            if (r1 == 0) goto Lb2
            a.a.b.a.a.b r0 = (a.a.b.a.a.b) r0
            a.d.Array<e> r1 = r7.f21b
            r1 = r1[r2]
            java.lang.Object r1 = r1.f72b
            a.a.b.a.a.t r1 = (a.a.b.a.a.t) r1
            java.lang.String r0 = r0.a(r1)
            a.a.b.a.f r1 = r9.c()
            a.a.b.a.e r1 = r1.g()
            java.lang.String r1 = r6.a(r1, r5)
        L71:
            if (r0 != 0) goto L7f
            a.d.Array<e> r0 = r7.f21b
            r0 = r0[r2]
            java.lang.Object r0 = r0.f72b
            a.a.b.a.a.t r0 = (a.a.b.a.a.t) r0
            java.lang.String r0 = r0.h()
        L7f:
            if (r0 == 0) goto L93
            java.lang.String r6 = "item"
            r8.startTag(r4, r6)
            java.lang.String r6 = "name"
            r8.attribute(r4, r6, r1)
            r8.text(r0)
            java.lang.String r0 = "item"
            r8.endTag(r4, r0)
        L93:
            Int r0 = r2 + 1
            r2 = r0
            goto L34
        L97:
            r0 = r3
            goto L26
        L99:
            a.a.b.a.f r0 = r9.c()
            java.lang.String r0 = r0.f()
            r1 = 46
            Int r0 = r0.indexOf(r1)
            r1 = -1
            if (r0 == r1) goto L33
            java.lang.String r0 = "parent"
            java.lang.String r1 = ""
            r8.attribute(r4, r0, r1)
            goto L33
        Lb2:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "@"
            r0.<init>(r1)
            a.a.b.a.f r1 = r9.c()
            a.a.b.a.e r1 = r1.g()
            java.lang.String r1 = r6.a(r1, r3)
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r1 = r0
            r0 = r4
            goto L71
        Ld0:
            java.lang.String r0 = "style"
            r8.endTag(r4, r0)
            return
        */
        throw UnsupportedOperationException("Method not decompiled: a.a.b.a.a.v.a(org.xmlpull.v1.XmlSerializer, a.a.b.a.g):Unit")
    }
}
