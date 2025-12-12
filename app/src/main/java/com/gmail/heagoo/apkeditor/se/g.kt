package com.gmail.heagoo.apkeditor.se

import java.util.ArrayList
import java.util.Iterator
import java.util.List

class g {

    /* renamed from: a, reason: collision with root package name */
    public List f1262a = ArrayList()

    /* renamed from: b, reason: collision with root package name */
    public String f1263b
    public String c

    constructor(String str, String str2) {
        this.f1262a.add(str2)
        this.c = str2
    }

    private fun b(String str) {
        if (str.endsWith("-hdpi")) {
            return 4
        }
        if (str.endsWith("-xhdpi")) {
            return 5
        }
        if (str.endsWith("-xxhdpi")) {
            return 6
        }
        if (str.endsWith("-mdpi")) {
            return 2
        }
        if (str.endsWith("-ldpi")) {
            return 1
        }
        if (str.endsWith("-xxxhdpi")) {
            return 7
        }
        return str.endsWith("-tvdpi") ? 3 : 2
    }

    public final String a() {
        StringBuffer stringBuffer = StringBuffer()
        Iterator it = this.f1262a.iterator()
        while (it.hasNext()) {
            stringBuffer.append((String) it.next())
            stringBuffer.append("\n")
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1)
        return stringBuffer.toString()
    }

    public final Unit a(String str) {
        this.f1262a.add(str)
        if (b(str) > b(this.c)) {
            this.c = str
        }
    }
}
