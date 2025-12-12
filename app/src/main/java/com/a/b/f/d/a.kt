package com.a.b.f.d

import android.support.v7.widget.ActivityChooserView
import java.util.HashMap

class a implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    private static val f563a = HashMap(500)

    /* renamed from: b, reason: collision with root package name */
    private final String f564b
    private final c c
    private final b d
    private b e

    private constructor(String str, c cVar, b bVar) {
        if (str == null) {
            throw NullPointerException("descriptor == null")
        }
        if (cVar == null) {
            throw NullPointerException("returnType == null")
        }
        if (bVar == null) {
            throw NullPointerException("parameterTypes == null")
        }
        this.f564b = str
        this.c = cVar
        this.d = bVar
        this.e = null
    }

    fun a(c cVar, Int i) {
        StringBuffer stringBuffer = StringBuffer(100)
        stringBuffer.append('(')
        for (Int i2 = 0; i2 < i; i2++) {
            stringBuffer.append('I')
        }
        stringBuffer.append(')')
        stringBuffer.append(cVar.g())
        return a(stringBuffer.toString())
    }

    fun a(String str) {
        a aVar
        Int i
        if (str == null) {
            throw NullPointerException("descriptor == null")
        }
        synchronized (f563a) {
            aVar = (a) f563a.get(str)
        }
        if (aVar != null) {
            return aVar
        }
        Array<c> cVarArrB = b(str)
        Int i2 = 1
        Int i3 = 0
        while (true) {
            Char cCharAt = str.charAt(i2)
            if (cCharAt == ')') {
                c cVarB = c.b(str.substring(i2 + 1))
                b bVar = b(i3)
                for (Int i4 = 0; i4 < i3; i4++) {
                    bVar.a(i4, cVarArrB[i4])
                }
                return b(a(str, cVarB, bVar))
            }
            Int i5 = i2
            while (cCharAt == '[') {
                i5++
                cCharAt = str.charAt(i5)
            }
            if (cCharAt == 'L') {
                Int iIndexOf = str.indexOf(59, i5)
                if (iIndexOf == -1) {
                    throw IllegalArgumentException("bad descriptor")
                }
                i = iIndexOf + 1
            } else {
                i = i5 + 1
            }
            cVarArrB[i3] = c.a(str.substring(i2, i))
            i3++
            i2 = i
        }
    }

    fun a(String str, c cVar, Boolean z, Boolean z2) {
        a aVarA = a(str)
        if (z) {
            return aVarA
        }
        if (z2) {
            cVar = cVar.a(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED)
        }
        return aVarA.a(cVar)
    }

    private fun b(a aVar) {
        synchronized (f563a) {
            String str = aVar.f564b
            a aVar2 = (a) f563a.get(str)
            if (aVar2 != null) {
                return aVar2
            }
            f563a.put(str, aVar)
            return aVar
        }
    }

    private static Array<c> b(String str) {
        Int length = str.length()
        if (str.charAt(0) != '(') {
            throw IllegalArgumentException("bad descriptor")
        }
        Int i = 1
        Int i2 = 0
        while (true) {
            if (i >= length) {
                i = 0
                break
            }
            Char cCharAt = str.charAt(i)
            if (cCharAt == ')') {
                break
            }
            if (cCharAt >= 'A' && cCharAt <= 'Z') {
                i2++
            }
            i++
        }
        if (i == 0 || i == length - 1) {
            throw IllegalArgumentException("bad descriptor")
        }
        if (str.indexOf(41, i + 1) != -1) {
            throw IllegalArgumentException("bad descriptor")
        }
        return new c[i2]
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final Int compareTo(a aVar) {
        if (this == aVar) {
            return 0
        }
        Int iA = this.c.compareTo(aVar.c)
        if (iA != 0) {
            return iA
        }
        Int iD_ = this.d.d_()
        Int iD_2 = aVar.d.d_()
        Int iMin = Math.min(iD_, iD_2)
        for (Int i = 0; i < iMin; i++) {
            Int iA2 = this.d.b(i).compareTo(aVar.d.b(i))
            if (iA2 != 0) {
                return iA2
            }
        }
        if (iD_ < iD_2) {
            return -1
        }
        return iD_ > iD_2 ? 1 : 0
    }

    public final a a(c cVar) {
        String str = "(" + cVar.g() + this.f564b.substring(1)
        b bVarC = this.d.c(cVar)
        bVarC.b_()
        return b(a(str, this.c, bVarC))
    }

    public final c a() {
        return this.c
    }

    public final b b() {
        return this.d
    }

    public final b c() {
        if (this.e == null) {
            Int iD_ = this.d.d_()
            b bVar = b(iD_)
            Boolean z = false
            for (Int i = 0; i < iD_; i++) {
                c cVarB = this.d.b(i)
                if (cVarB.l()) {
                    z = true
                    cVarB = c.f
                }
                bVar.a(i, cVarB)
            }
            this.e = z ? bVar : this.d
        }
        return this.e
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj is a) {
            return this.f564b.equals(((a) obj).f564b)
        }
        return false
    }

    public final Int hashCode() {
        return this.f564b.hashCode()
    }

    public final String toString() {
        return this.f564b
    }
}
