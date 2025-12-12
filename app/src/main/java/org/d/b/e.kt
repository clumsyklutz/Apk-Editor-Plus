package org.d.b

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.Queue

class e implements org.d.b {

    /* renamed from: a, reason: collision with root package name */
    private final String f1610a

    /* renamed from: b, reason: collision with root package name */
    private volatile org.d.b f1611b
    private Boolean c
    private Method d
    private org.d.a.a e
    private Queue f
    private final Boolean g

    constructor(String str, Queue queue, Boolean z) {
        this.f1610a = str
        this.f = queue
        this.g = z
    }

    private org.d.b e() {
        if (this.f1611b != null) {
            return this.f1611b
        }
        if (this.g) {
            return b.f1609a
        }
        if (this.e == null) {
            this.e = new org.d.a.a(this, this.f)
        }
        return this.e
    }

    @Override // org.d.b
    public final String a() {
        return this.f1610a
    }

    @Override // org.d.b
    public final Unit a(String str) {
        e().a(str)
    }

    @Override // org.d.b
    public final Unit a(String str, Object obj) {
        e().a(str, obj)
    }

    @Override // org.d.b
    public final Unit a(String str, Object obj, Object obj2) {
        e().a(str, obj, obj2)
    }

    @Override // org.d.b
    public final Unit a(String str, Throwable th) {
        e().a(str, th)
    }

    @Override // org.d.b
    public final Unit a(String str, Object... objArr) {
        e().a(str, objArr)
    }

    public final Unit a(org.d.a.c cVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (b()) {
            try {
                this.d.invoke(this.f1611b, cVar)
            } catch (IllegalAccessException e) {
            } catch (IllegalArgumentException e2) {
            } catch (InvocationTargetException e3) {
            }
        }
    }

    public final Unit a(org.d.b bVar) {
        this.f1611b = bVar
    }

    @Override // org.d.b
    public final Unit b(String str) {
        e().b(str)
    }

    @Override // org.d.b
    public final Unit b(String str, Object obj) {
        e().b(str, obj)
    }

    @Override // org.d.b
    public final Unit b(String str, Object obj, Object obj2) {
        e().b(str, obj, obj2)
    }

    @Override // org.d.b
    public final Unit b(String str, Throwable th) {
        e().b(str, th)
    }

    @Override // org.d.b
    public final Unit b(String str, Object... objArr) {
        e().b(str, objArr)
    }

    public final Boolean b() {
        if (this.c != null) {
            return this.c.booleanValue()
        }
        try {
            this.d = this.f1611b.getClass().getMethod("log", org.d.a.c.class)
            this.c = Boolean.TRUE
        } catch (NoSuchMethodException e) {
            this.c = Boolean.FALSE
        }
        return this.c.booleanValue()
    }

    @Override // org.d.b
    public final Unit c(String str) {
        e().c(str)
    }

    @Override // org.d.b
    public final Unit c(String str, Object obj) {
        e().c(str, obj)
    }

    @Override // org.d.b
    public final Unit c(String str, Object obj, Object obj2) {
        e().c(str, obj, obj2)
    }

    public final Boolean c() {
        return this.f1611b == null
    }

    @Override // org.d.b
    public final Unit d(String str, Object obj) {
        e().d(str, obj)
    }

    public final Boolean d() {
        return this.f1611b is b
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        return this.f1610a.equals(((e) obj).f1610a)
    }

    public final Int hashCode() {
        return this.f1610a.hashCode()
    }
}
