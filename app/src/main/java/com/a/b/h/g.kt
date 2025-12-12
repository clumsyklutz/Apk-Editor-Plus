package com.a.b.h

import java.util.Arrays

class g extends p implements s {

    /* renamed from: a, reason: collision with root package name */
    private Array<Object> f670a

    constructor(Int i) {
        super(i != 0)
        try {
            this.f670a = new Object[i]
        } catch (NegativeArraySizeException e) {
            throw IllegalArgumentException("size < 0")
        }
    }

    private fun a(Int i) {
        if (i < 0) {
            throw IndexOutOfBoundsException("n < 0")
        }
        throw IndexOutOfBoundsException("n >= size()")
    }

    private fun a(String str, String str2, String str3, Boolean z) {
        Int length = this.f670a.length
        StringBuffer stringBuffer = StringBuffer((length * 10) + 10)
        if (str != null) {
            stringBuffer.append(str)
        }
        for (Int i = 0; i < length; i++) {
            if (i != 0 && str2 != null) {
                stringBuffer.append(str2)
            }
            if (z) {
                stringBuffer.append(((s) this.f670a[i]).d())
            } else {
                stringBuffer.append(this.f670a[i])
            }
        }
        if (str3 != null) {
            stringBuffer.append(str3)
        }
        return stringBuffer.toString()
    }

    public final String a(String str, String str2, String str3) {
        return a(str, str2, str3, false)
    }

    protected final Unit a(Int i, Object obj) {
        l()
        try {
            this.f670a[i] = obj
        } catch (ArrayIndexOutOfBoundsException e) {
            a(i)
        }
    }

    public final String b(String str, String str2, String str3) {
        return a(str, str2, str3, true)
    }

    fun d() {
        String name = getClass().getName()
        return a(name.substring(name.lastIndexOf(46) + 1) + '{', ", ", "}", true)
    }

    public final Int d_() {
        return this.f670a.length
    }

    protected final Object e(Int i) {
        try {
            Object obj = this.f670a[i]
            if (obj == null) {
                throw NullPointerException("unset: " + i)
            }
            return obj
        } catch (ArrayIndexOutOfBoundsException e) {
            return a(i)
        }
    }

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        return Arrays.equals(this.f670a, ((g) obj).f670a)
    }

    protected final Object f(Int i) {
        return this.f670a[i]
    }

    fun hashCode() {
        return Arrays.hashCode(this.f670a)
    }

    fun i() {
        Int i = 0
        Int length = this.f670a.length
        Int i2 = 0
        for (Int i3 = 0; i3 < length; i3++) {
            if (this.f670a[i3] != null) {
                i2++
            }
        }
        if (length == i2) {
            return
        }
        l()
        Array<Object> objArr = new Object[i2]
        for (Int i4 = 0; i4 < length; i4++) {
            Object obj = this.f670a[i4]
            if (obj != null) {
                objArr[i] = obj
                i++
            }
        }
        this.f670a = objArr
        if (i2 == 0) {
            b_()
        }
    }

    fun toString() {
        String name = getClass().getName()
        return a(name.substring(name.lastIndexOf(46) + 1) + '{', ", ", "}", false)
    }
}
