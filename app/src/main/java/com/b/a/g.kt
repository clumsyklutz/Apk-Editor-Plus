package com.b.a

import android.annotation.SuppressLint
import android.content.Context
import java.util.List

class g {

    /* renamed from: a, reason: collision with root package name */
    public String f690a

    /* renamed from: b, reason: collision with root package name */
    public String f691b
    public Int c
    public Boolean d

    constructor(String str, String str2) {
        this.f690a = str
        this.f691b = str2
        if (str2.startsWith("#")) {
            try {
                this.c = (Int) Long.parseLong(str2.substring(1), 16)
                this.d = true
            } catch (Exception e) {
            }
        }
    }

    @SuppressLint({"NewApi"})
    public final Unit a(Context context, List list) {
        if (this.d) {
            return
        }
        if (!this.f691b.startsWith("@color/")) {
            if (this.f691b.startsWith("@android:color/")) {
                try {
                    Object objE = com.gmail.heagoo.a.c.a.e("android.R$color", this.f691b.substring(15))
                    if (objE != null) {
                        this.c = context.getColor(((Integer) objE).intValue())
                        this.d = true
                        return
                    }
                    return
                } catch (Throwable th) {
                    return
                }
            }
            return
        }
        String strSubstring = this.f691b.substring(7)
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= list.size()) {
                return
            }
            g gVar = (g) list.get(i2)
            if (gVar.d && strSubstring.equals(gVar.f690a)) {
                this.c = gVar.c
                this.d = true
                return
            }
            i = i2 + 1
        }
    }

    public final String toString() {
        return "    <color name=\"" + this.f690a + "\">" + this.f691b + "</color>"
    }
}
