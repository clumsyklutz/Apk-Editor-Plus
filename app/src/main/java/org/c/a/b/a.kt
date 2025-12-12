package org.c.a.b

import java.lang.reflect.InvocationTargetException

class a extends b {
    constructor(Class cls) {
        super(cls)
    }

    @Override // org.c.a.a
    public final Object a() {
        try {
            return this.c.cast(f1593a.invoke(f1594b, this.c, Object.class))
        } catch (IllegalAccessException e) {
            throw new com.a.b.b.b(e)
        } catch (RuntimeException e2) {
            throw new com.a.b.b.b(e2)
        } catch (InvocationTargetException e3) {
            throw new com.a.b.b.b(e3)
        }
    }
}
