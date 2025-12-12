package org.c.a.a

import java.io.ObjectStreamClass
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class b implements org.c.a.a {

    /* renamed from: a, reason: collision with root package name */
    private final Class f1589a

    /* renamed from: b, reason: collision with root package name */
    private val f1590b = b()
    private val c = c()

    constructor(Class cls) {
        this.f1589a = cls
    }

    private fun b() throws NoSuchMethodException, SecurityException {
        try {
            Method declaredMethod = ObjectStreamClass.class.getDeclaredMethod("newInstance", Class.class, Integer.TYPE)
            declaredMethod.setAccessible(true)
            return declaredMethod
        } catch (NoSuchMethodException e) {
            throw new com.a.b.b.b(e)
        } catch (RuntimeException e2) {
            throw new com.a.b.b.b(e2)
        }
    }

    private fun c() throws NoSuchMethodException, SecurityException {
        try {
            Method declaredMethod = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", Class.class)
            declaredMethod.setAccessible(true)
            return (Integer) declaredMethod.invoke(null, Object.class)
        } catch (IllegalAccessException e) {
            throw new com.a.b.b.b(e)
        } catch (NoSuchMethodException e2) {
            throw new com.a.b.b.b(e2)
        } catch (RuntimeException e3) {
            throw new com.a.b.b.b(e3)
        } catch (InvocationTargetException e4) {
            throw new com.a.b.b.b(e4)
        }
    }

    @Override // org.c.a.a
    public final Object a() {
        try {
            return this.f1589a.cast(this.f1590b.invoke(null, this.f1589a, this.c))
        } catch (Exception e) {
            throw new com.a.b.b.b(e)
        }
    }
}
