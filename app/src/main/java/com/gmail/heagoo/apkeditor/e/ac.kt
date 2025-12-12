package com.gmail.heagoo.apkeditor.e

import com.gmail.heagoo.apkeditor.pro.R
import java.util.ArrayList
import java.util.Iterator
import java.util.List

final class ac {

    /* renamed from: a, reason: collision with root package name */
    private List f986a

    ac(b bVar, String str, Int i) {
        x xVar
        this.f986a = ArrayList()
        String strA = g.a(bVar, str)
        str = strA != null ? strA : str
        if (!str.startsWith("[") || !str.endsWith("]")) {
            if (str.contains("*")) {
                this.f986a.add(ab(bVar, str))
                return
            } else {
                this.f986a.add(aa(str))
                return
            }
        }
        for (String str2 : a(str)) {
            if ("APPLICATION".equals(str2)) {
                xVar = x(bVar, z.f1016a)
            } else if ("ACTIVITIES".equals(str2)) {
                xVar = x(bVar, z.f1017b)
            } else if ("LAUNCHER_ACTIVITIES".equals(str2)) {
                xVar = x(bVar, z.c)
            } else {
                bVar.a(R.string.patch_error_invalid_target, Integer.valueOf(i))
                xVar = null
            }
            if (xVar == null) {
                this.f986a = null
                return
            }
            this.f986a.add(xVar)
        }
    }

    private fun a(String str) {
        ArrayList arrayList = ArrayList()
        Int iIndexOf = 1
        Int iIndexOf2 = str.indexOf(93)
        while (iIndexOf > 0 && iIndexOf2 > iIndexOf) {
            arrayList.add(str.substring(iIndexOf, iIndexOf2))
            iIndexOf = str.indexOf(91, iIndexOf2) + 1
            if (iIndexOf > 0) {
                iIndexOf2 = str.indexOf(93, iIndexOf)
            }
        }
        return arrayList
    }

    final Boolean a() {
        if (this.f986a == null) {
            return false
        }
        for (Int i = 0; i < this.f986a.size(); i++) {
            if (((w) this.f986a.get(i)).b()) {
                return true
            }
        }
        return false
    }

    final String b() {
        Boolean z
        if (this.f986a == null) {
            return null
        }
        String strA = ((w) this.f986a.get(0)).a()
        if (this.f986a.size() <= 1) {
            return strA
        }
        String strA2 = strA
        while (strA2 != null) {
            Int i = 1
            while (true) {
                if (i >= this.f986a.size()) {
                    z = true
                    break
                }
                if (!((w) this.f986a.get(i)).a(strA2)) {
                    z = false
                    break
                }
                i++
            }
            if (z) {
                break
            }
            strA2 = ((w) this.f986a.get(0)).a()
        }
        return strA2
    }

    public final Boolean c() {
        return this.f986a != null
    }

    final Boolean d() {
        if (this.f986a == null) {
            return false
        }
        Iterator it = this.f986a.iterator()
        while (it.hasNext()) {
            if (!((w) it.next()).c()) {
                return false
            }
        }
        return true
    }
}
