package com.gmail.heagoo.apkeditor.a

import androidx.core.app.NotificationCompat
import com.gmail.heagoo.apkeditor.a.a.m
import com.gmail.heagoo.apkeditor.a.a.s
import java.io.IOException

final class c {

    /* renamed from: a, reason: collision with root package name */
    public static Int f820a = 1048833

    /* renamed from: b, reason: collision with root package name */
    private static Int f821b = 1048834
    private static Int c = 1048835
    private static Int d = 1048832
    private static Int e = 1048836
    private Array<Byte> f
    private Int g
    private s h

    constructor(s sVar) {
        this.g = sVar.c()
        this.h = sVar
        for (Int i = 0; i < this.g; i++) {
            "android".equals(sVar.b(i))
        }
    }

    fun a(Int i) {
        if (i == 3) {
            return true
        }
        if (i != 2 && i != 1 && i != 4 && i != 17 && i != 18 && i != 5 && i != 6) {
            if (i < 28 || i > 31) {
                return (i < 16 || i > 31) ? false : false
            }
            return false
        }
        return false
    }

    public final Int a(m mVar, e eVar) throws IOException {
        Int iA = mVar.a()
        Int iA2 = mVar.a()
        this.f = new Byte[iA2]
        i.a(this.f, 0, iA)
        i.a(this.f, 4, iA2)
        if (iA2 > 8) {
            mVar.a(this.f, 8, iA2 - 8)
        }
        if (iA == f821b) {
            Int iA3 = i.a(this.f, 20)
            Int iA4 = i.a(this.f, 28)
            String strB = this.h.b(iA3)
            if ("uses-permission".equals(strB) || "manifest".equals(strB) || "application".equals(strB) || "activity".equals(strB) || NotificationCompat.CATEGORY_SERVICE.equals(strB) || "receiver".equals(strB) || "provider".equals(strB) || "activity-alias".equals(strB) || "category".equals(strB) || "permission".equals(strB) || "uses-sdk".equals(strB)) {
                for (Int i = 0; i < iA4; i++) {
                    Int iA5 = i.a(this.f, (i * 20) + 36 + 4)
                    Int iA6 = i.a(this.f, (i * 20) + 36 + 8)
                    Int iA7 = i.a(this.f, ((i * 20) + 36) + 12) >> 16
                    Int i2 = ((iA7 >> 8) & 255) | ((iA7 & 255) << 8)
                    Int iA8 = i.a(this.f, (i * 20) + 36 + 16)
                    Array<Object> objArr = {this.h.b(iA5), this.h.b(iA6), Integer.valueOf(i2), Integer.valueOf(iA6), Integer.valueOf(iA8)}
                    eVar.a(this.h.b(iA3), iA5, iA6, i2, iA8)
                }
            }
        }
        return iA
    }
}
