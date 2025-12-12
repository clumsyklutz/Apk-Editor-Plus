package com.a.b.d.a

import java.util.HashMap

class a {

    /* renamed from: a, reason: collision with root package name */
    private val f421a = HashMap()

    public final Int a(Int i) {
        Integer num = (Integer) this.f421a.get(Integer.valueOf(i))
        if (num == null) {
            return -1
        }
        return num.intValue()
    }

    public final Unit a(Int i, Int i2) {
        this.f421a.put(Integer.valueOf(i), Integer.valueOf(i2))
    }
}
