package org.c.a.a

import java.io.ObjectInputStream
import java.lang.reflect.Method

class a implements org.c.a.a {

    /* renamed from: a, reason: collision with root package name */
    private final Class f1587a

    /* renamed from: b, reason: collision with root package name */
    private val f1588b = b()

    constructor(Class cls) {
        this.f1587a = cls
    }

    private fun b() throws NoSuchMethodException, SecurityException {
        try {
            Method declaredMethod = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class, Class.class)
            declaredMethod.setAccessible(true)
            return declaredMethod
        } catch (NoSuchMethodException e) {
            throw new com.a.b.b.b(e)
        } catch (RuntimeException e2) {
            throw new com.a.b.b.b(e2)
        }
    }

    @Override // org.c.a.a
    public final Object a() {
        try {
            return this.f1587a.cast(this.f1588b.invoke(null, this.f1587a, Object.class))
        } catch (Exception e) {
            throw new com.a.b.b.b(e)
        }
    }
}
