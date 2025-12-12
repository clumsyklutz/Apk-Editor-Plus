package com.a.b.f.c

abstract class a implements com.a.b.h.s, Comparable {
    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final Int compareTo(a aVar) {
        Class<?> cls = getClass()
        Class<?> cls2 = aVar.getClass()
        return cls != cls2 ? cls.getName().compareTo(cls2.getName()) : b(aVar)
    }

    protected abstract Int b(a aVar)

    public abstract Boolean g()

    public abstract String h()
}
