package org.d.b

class g {

    /* renamed from: a, reason: collision with root package name */
    private static h f1614a

    /* renamed from: b, reason: collision with root package name */
    private static Boolean f1615b = false

    private constructor() {
    }

    fun a() {
        h hVar
        if (f1614a != null) {
            hVar = f1614a
        } else if (f1615b) {
            hVar = null
        } else {
            f1614a = b()
            f1615b = true
            hVar = f1614a
        }
        if (hVar == null) {
            return null
        }
        Array<Class> classContext = hVar.getClassContext()
        String name = g.class.getName()
        Int i = 0
        while (i < classContext.length && !name.equals(classContext[i].getName())) {
            i++
        }
        if (i >= classContext.length || i + 2 >= classContext.length) {
            throw IllegalStateException("Failed to find org.slf4j.helpers.Util or its caller in the stack; this should not happen")
        }
        return classContext[i + 2]
    }

    fun a(String str) {
        if (str == null) {
            throw IllegalArgumentException("null input")
        }
        try {
            return System.getProperty(str)
        } catch (SecurityException e) {
            return null
        }
    }

    fun a(String str, Throwable th) {
        System.err.println(str)
        System.err.println("Reported exception:")
        th.printStackTrace()
    }

    private fun b() {
        try {
            return h((Byte) 0)
        } catch (SecurityException e) {
            return null
        }
    }

    fun b(String str) {
        String strA = a(str)
        if (strA == null) {
            return false
        }
        return strA.equalsIgnoreCase("true")
    }

    fun c(String str) {
        System.err.println("SLF4J: " + str)
    }
}
