package org.c.a.e

import java.lang.reflect.Field
import sun.misc.Unsafe

class b implements org.c.a.a {

    /* renamed from: a, reason: collision with root package name */
    private static Unsafe f1600a

    /* renamed from: b, reason: collision with root package name */
    private final Class f1601b

    constructor(Class cls) throws NoSuchFieldException {
        if (f1600a == null) {
            try {
                Field declaredField = Unsafe.class.getDeclaredField("theUnsafe")
                declaredField.setAccessible(true)
                try {
                    f1600a = (Unsafe) declaredField.get(null)
                } catch (IllegalAccessException e) {
                    throw new com.a.b.b.b(e)
                }
            } catch (NoSuchFieldException e2) {
                throw new com.a.b.b.b(e2)
            }
        }
        this.f1601b = cls
    }

    @Override // org.c.a.a
    public final Object a() {
        try {
            return this.f1601b.cast(f1600a.allocateInstance(this.f1601b))
        } catch (InstantiationException e) {
            throw new com.a.b.b.b(e)
        }
    }
}
