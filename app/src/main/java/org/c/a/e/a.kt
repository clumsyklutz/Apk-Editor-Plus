package org.c.a.e

import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class a implements org.c.a.a {

    /* renamed from: a, reason: collision with root package name */
    private final Constructor f1599a

    constructor(Class cls) {
        this.f1599a = a(cls, c())
        this.f1599a.setAccessible(true)
    }

    private fun a(Class cls) {
        try {
            return cls.getDeclaredMethod("getReflectionFactory", new Class[0]).invoke(null, new Object[0])
        } catch (IllegalAccessException e) {
            throw new com.a.b.b.b(e)
        } catch (IllegalArgumentException e2) {
            throw new com.a.b.b.b(e2)
        } catch (NoSuchMethodException e3) {
            throw new com.a.b.b.b(e3)
        } catch (InvocationTargetException e4) {
            throw new com.a.b.b.b(e4)
        }
    }

    fun a(Class cls, Constructor constructor) {
        Class clsB = b()
        try {
            return (Constructor) b(clsB).invoke(a(clsB), cls, constructor)
        } catch (IllegalAccessException e) {
            throw new com.a.b.b.b(e)
        } catch (IllegalArgumentException e2) {
            throw new com.a.b.b.b(e2)
        } catch (InvocationTargetException e3) {
            throw new com.a.b.b.b(e3)
        }
    }

    private fun b() {
        try {
            return Class.forName("sun.reflect.ReflectionFactory")
        } catch (ClassNotFoundException e) {
            throw new com.a.b.b.b(e)
        }
    }

    private fun b(Class cls) {
        try {
            return cls.getDeclaredMethod("newConstructorForSerialization", Class.class, Constructor.class)
        } catch (NoSuchMethodException e) {
            throw new com.a.b.b.b(e)
        }
    }

    private fun c() {
        try {
            return Object.class.getConstructor(null)
        } catch (NoSuchMethodException e) {
            throw new com.a.b.b.b(e)
        }
    }

    @Override // org.c.a.a
    fun a() {
        try {
            return this.f1599a.newInstance(null)
        } catch (Exception e) {
            throw new com.a.b.b.b(e)
        }
    }
}
