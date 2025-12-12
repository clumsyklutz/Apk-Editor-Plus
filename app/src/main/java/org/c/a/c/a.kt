package org.c.a.c

import com.a.b.b.b
import java.lang.reflect.Method

class a implements org.c.a.a {

    /* renamed from: a, reason: collision with root package name */
    private static Method f1595a = null

    /* renamed from: b, reason: collision with root package name */
    private final Class f1596b

    constructor(Class cls) throws NoSuchMethodException, SecurityException {
        if (f1595a == null) {
            try {
                Method declaredMethod = Class.forName("jrockit.vm.MemSystem").getDeclaredMethod("safeAllocObject", Class.class)
                f1595a = declaredMethod
                declaredMethod.setAccessible(true)
            } catch (ClassNotFoundException e) {
                throw b(e)
            } catch (NoSuchMethodException e2) {
                throw b(e2)
            } catch (RuntimeException e3) {
                throw b(e3)
            }
        }
        this.f1596b = cls
    }

    @Override // org.c.a.a
    public final Object a() {
        try {
            return this.f1596b.cast(f1595a.invoke(null, this.f1596b))
        } catch (Exception e) {
            throw b(e)
        }
    }
}
