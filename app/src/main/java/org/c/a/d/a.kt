package org.c.a.d

import com.a.b.b.b
import java.io.ObjectInputStream
import java.lang.reflect.Method

class a implements org.c.a.a {

    /* renamed from: a, reason: collision with root package name */
    private final Method f1597a

    /* renamed from: b, reason: collision with root package name */
    private final Array<Object> f1598b = {null, Boolean.FALSE}

    constructor(Class cls) {
        this.f1598b[0] = cls
        try {
            this.f1597a = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class, Boolean.TYPE)
            this.f1597a.setAccessible(true)
        } catch (NoSuchMethodException e) {
            throw b(e)
        } catch (RuntimeException e2) {
            throw b(e2)
        }
    }

    @Override // org.c.a.a
    public final Object a() {
        try {
            return this.f1597a.invoke(null, this.f1598b)
        } catch (Exception e) {
            throw b(e)
        }
    }
}
