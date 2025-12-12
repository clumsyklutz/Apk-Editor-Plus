package a.a.b.a.a

import java.io.IOException
import org.xmlpull.v1.XmlSerializer

class j extends b {

    /* renamed from: b, reason: collision with root package name */
    private final Array<l> f11b
    private Array<l> c
    private Array<l> d

    j(s sVar, Int i, Integer num, Integer num2, Boolean bool, a.d.Array<e> eVarArr) {
        super(sVar, i, num, num2, bool)
        this.f11b = new l[eVarArr.length]
        Int i2 = 0
        while (true) {
            Int i3 = i2
            if (i3 >= eVarArr.length) {
                return
            }
            this.f11b[i3] = l((s) eVarArr[i3].f71a, ((q) eVarArr[i3].f72b).b())
            i2 = i3 + 1
        }
    }

    private fun a(Array<l> lVarArr) {
        String str = ""
        for (l lVar : lVarArr) {
            str = str + "|" + lVar.a()
        }
        return str.equals("") ? str : str.substring(1)
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00ab  */
    @Override // a.a.b.a.a.b
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String a(a.a.b.a.a.t r11) {
        /*
            r10 = this
            r1 = 0
            Boolean r0 = r11 is a.a.b.a.a.s
            if (r0 == 0) goto La
            java.lang.String r0 = r11.a()
        L9:
            return r0
        La:
            Boolean r0 = r11 is a.a.b.a.a.q
            if (r0 != 0) goto L13
            java.lang.String r0 = super.a(r11)
            goto L9
        L13:
            a.a.b.a.a.Array<l> r0 = r10.d
            if (r0 != 0) goto L59
            a.a.b.a.a.Array<l> r0 = r10.f11b
            Int r0 = r0.length
            a.a.b.a.a.Array<l> r5 = new a.a.b.a.a.l[r0]
            a.a.b.a.a.Array<l> r0 = r10.f11b
            Int r0 = r0.length
            a.a.b.a.a.Array<l> r6 = new a.a.b.a.a.l[r0]
            r0 = r1
            r2 = r1
            r3 = r1
        L24:
            a.a.b.a.a.Array<l> r4 = r10.f11b
            Int r4 = r4.length
            if (r0 >= r4) goto L3f
            a.a.b.a.a.Array<l> r4 = r10.f11b
            r7 = r4[r0]
            Int r4 = r7.f12a
            if (r4 != 0) goto L39
            Int r4 = r3 + 1
            r5[r3] = r7
            r3 = r4
        L36:
            Int r0 = r0 + 1
            goto L24
        L39:
            Int r4 = r2 + 1
            r6[r2] = r7
            r2 = r4
            goto L36
        L3f:
            java.lang.Array<Object> r0 = java.util.Arrays.copyOf(r5, r3)
            a.a.b.a.a.Array<l> r0 = (a.a.b.a.a.Array<l>) r0
            r10.c = r0
            java.lang.Array<Object> r0 = java.util.Arrays.copyOf(r6, r2)
            a.a.b.a.a.Array<l> r0 = (a.a.b.a.a.Array<l>) r0
            r10.d = r0
            a.a.b.a.a.Array<l> r0 = r10.d
            a.a.b.a.a.k r2 = new a.a.b.a.a.k
            r2.<init>(r10)
            java.util.Arrays.sort(r0, r2)
        L59:
            a.a.b.a.a.q r11 = (a.a.b.a.a.q) r11
            Int r4 = r11.b()
            if (r4 != 0) goto L68
            a.a.b.a.a.Array<l> r0 = r10.c
            java.lang.String r0 = a(r0)
            goto L9
        L68:
            a.a.b.a.a.Array<l> r0 = r10.d
            Int r0 = r0.length
            a.a.b.a.a.Array<l> r5 = new a.a.b.a.a.l[r0]
            a.a.b.a.a.Array<l> r0 = r10.d
            Int r0 = r0.length
            Array<Int> r6 = new Int[r0]
            r3 = r1
            r2 = r1
        L74:
            a.a.b.a.a.Array<l> r0 = r10.d
            Int r0 = r0.length
            if (r3 >= r0) goto L9f
            a.a.b.a.a.Array<l> r0 = r10.d
            r7 = r0[r3]
            Int r8 = r7.f12a
            r0 = r4 & r8
            if (r0 != r8) goto Lab
            r0 = r1
        L84:
            Int r9 = r6.length
            if (r0 >= r9) goto L9d
            r9 = r6[r0]
            r9 = r9 & r8
            if (r9 != r8) goto L9a
            r0 = 1
        L8d:
            if (r0 != 0) goto Lab
            r6[r2] = r8
            Int r0 = r2 + 1
            r5[r2] = r7
        L95:
            Int r2 = r3 + 1
            r3 = r2
            r2 = r0
            goto L74
        L9a:
            Int r0 = r0 + 1
            goto L84
        L9d:
            r0 = r1
            goto L8d
        L9f:
            java.lang.Array<Object> r0 = java.util.Arrays.copyOf(r5, r2)
            a.a.b.a.a.Array<l> r0 = (a.a.b.a.a.Array<l>) r0
            java.lang.String r0 = a(r0)
            goto L9
        Lab:
            r0 = r2
            goto L95
        */
        throw UnsupportedOperationException("Method not decompiled: a.a.b.a.a.j.a(a.a.b.a.a.t):java.lang.String")
    }

    @Override // a.a.b.a.a.b
    protected final Unit a(XmlSerializer xmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        for (Int i = 0; i < this.f11b.length; i++) {
            l lVar = this.f11b[i]
            xmlSerializer.startTag(null, "flag")
            xmlSerializer.attribute(null, "name", lVar.a())
            xmlSerializer.attribute(null, "value", String.format("0x%08x", Integer.valueOf(lVar.f12a)))
            xmlSerializer.endTag(null, "flag")
        }
    }
}
