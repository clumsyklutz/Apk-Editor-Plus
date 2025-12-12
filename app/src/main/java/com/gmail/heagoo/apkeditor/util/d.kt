package com.gmail.heagoo.apkeditor.util

import android.app.Activity
import java.io.BufferedWriter
import java.io.IOException
import java.util.HashMap
import java.util.Map

class d {

    /* renamed from: b, reason: collision with root package name */
    private String f1310b
    private String c
    private f d
    private Int e = -1
    private Map f = HashMap()

    /* renamed from: a, reason: collision with root package name */
    Map f1309a = HashMap()

    constructor(String str) {
        this.f1310b = str
    }

    protected static Unit a(BufferedWriter bufferedWriter) {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close()
            } catch (IOException e) {
            }
        }
    }

    fun b(String str) {
        StringBuilder sb = StringBuilder()
        for (Int i = 0; i < str.length(); i++) {
            Char cCharAt = str.charAt(i)
            if (Character.isDigit(cCharAt) || ((cCharAt >= 'a' && cCharAt <= 'z') || ((cCharAt >= 'A' && cCharAt <= 'Z') || cCharAt == '_' || cCharAt == '.'))) {
                sb.append(cCharAt)
            } else {
                sb.append(com.gmail.heagoo.common.s.a(4))
            }
        }
        return sb.toString()
    }

    public final Unit a(Activity activity) {
        if (this.d != null) {
            new com.gmail.heagoo.common.p(activity, e(this, activity), -1).show()
        }
    }

    public final Unit a(String str) {
        this.c = str
    }

    public final Boolean a() {
        if (this.c == null) {
            return false
        }
        this.d = m(this.f1310b, this.c)
        if (this.d.a()) {
            this.e = 0
            return true
        }
        this.d = r(this.f1310b, this.c)
        if (this.d.a()) {
            this.e = 1
            return true
        }
        this.d = h(this.f1310b, this.c)
        if (this.d.a()) {
            this.e = 2
            return true
        }
        this.d = o(this.f1310b, this.c)
        if (this.d.a()) {
            this.e = 3
            return true
        }
        this.d = l(this.f1310b, this.c)
        if (!this.d.a()) {
            return false
        }
        this.e = 4
        return true
    }

    public final Int b() {
        return this.e
    }

    public final Map c() {
        return this.f1309a
    }
}
