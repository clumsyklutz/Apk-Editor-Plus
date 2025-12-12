package com.gmail.heagoo.apkeditor

import jadx.core.codegen.CodeWriter

final class dj {

    /* renamed from: a, reason: collision with root package name */
    Int f962a

    /* renamed from: b, reason: collision with root package name */
    String f963b
    Int c
    Int d
    Int e
    Boolean f = false
    Boolean g = false
    private String h

    dj(Int i, String str, String str2) {
        this.f962a = i
        this.f963b = str
        while (this.f963b.startsWith(str2)) {
            this.c++
            this.f963b = this.f963b.substring(str2.length())
        }
        while (this.f963b.startsWith(CodeWriter.INDENT)) {
            this.c++
            this.f963b = this.f963b.substring(4)
        }
        this.f963b = this.f963b.trim()
        this.d = -1
        this.e = -1
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x005c A[PHI: r0
  0x005c: PHI (r0v5 Int) = (r0v3 Int), (r0v21 Int) binds: [B:14:0x0025, B:9:0x0016] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final java.lang.String a() {
        /*
            r5 = this
            r4 = -1
            java.lang.String r0 = r5.h
            if (r0 == 0) goto L8
            java.lang.String r0 = r5.h
        L7:
            return r0
        L8:
            java.lang.String r2 = r5.f963b
            Int r0 = r5.f962a
            Int r1 = r5.d
            if (r0 == r1) goto L1f
            java.lang.String r0 = "</"
            Int r0 = r2.indexOf(r0)
            if (r0 == r4) goto L5c
            Int r0 = r0 + 2
            r1 = r0
        L1b:
            if (r1 != r4) goto L2b
            r0 = 0
            goto L7
        L1f:
            java.lang.String r0 = "<"
            Int r0 = r2.indexOf(r0)
            if (r0 == r4) goto L5c
            Int r0 = r0 + 1
            r1 = r0
            goto L1b
        L2b:
            java.lang.String r0 = " "
            Int r0 = r2.indexOf(r0)
            if (r0 != r4) goto L43
            Int r0 = r5.d
            Int r3 = r5.e
            if (r0 != r3) goto L50
            java.lang.String r0 = "/>"
            Int r0 = r2.indexOf(r0)
            if (r0 == r4) goto L43
            Int r0 = r0 + (-1)
        L43:
            if (r0 == r4) goto L57
            java.lang.String r0 = r2.substring(r1, r0)
        L49:
            java.lang.String r0 = r0.trim()
            r5.h = r0
            goto L7
        L50:
            java.lang.String r0 = ">"
            Int r0 = r2.indexOf(r0)
            goto L43
        L57:
            java.lang.String r0 = r2.substring(r1)
            goto L49
        L5c:
            r1 = r0
            goto L1b
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.dj.a():java.lang.String")
    }
}
