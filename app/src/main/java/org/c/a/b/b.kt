package org.c.a.b

import java.io.IOException
import java.io.ObjectInputStream
import java.lang.reflect.Method

abstract class b implements org.c.a.a {

    /* renamed from: a, reason: collision with root package name */
    static Method f1593a = null

    /* renamed from: b, reason: collision with root package name */
    static ObjectInputStream f1594b
    protected final Class c

    constructor(Class cls) throws NoSuchMethodException, SecurityException {
        this.c = cls
        if (f1593a == null) {
            try {
                Method declaredMethod = ObjectInputStream.class.getDeclaredMethod("newObject", Class.class, Class.class)
                f1593a = declaredMethod
                declaredMethod.setAccessible(true)
                f1594b = c()
            } catch (IOException e) {
                throw new com.a.b.b.b(e)
            } catch (NoSuchMethodException e2) {
                throw new com.a.b.b.b(e2)
            } catch (RuntimeException e3) {
                throw new com.a.b.b.b(e3)
            }
        }
    }
}
