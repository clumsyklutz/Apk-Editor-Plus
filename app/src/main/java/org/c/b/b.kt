package org.c.b

class b {

    /* renamed from: a, reason: collision with root package name */
    public static final String f1602a

    /* renamed from: b, reason: collision with root package name */
    public static final String f1603b
    public static final String c
    public static final Int d
    private static String e

    static {
        System.getProperty("java.specification.version")
        f1602a = System.getProperty("java.runtime.version")
        f1603b = System.getProperty("java.vm.info")
        c = System.getProperty("java.vm.version")
        System.getProperty("java.vm.vendor")
        e = System.getProperty("java.vm.name")
        d = !e.startsWith("Dalvik") ? 0 : a()
    }

    private fun a() throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("android.os.Build$VERSION")
            try {
                try {
                    return ((Integer) cls.getField("SDK_INT").get(null)).intValue()
                } catch (IllegalAccessException e2) {
                    throw RuntimeException(e2)
                }
            } catch (NoSuchFieldException e3) {
                return a(cls)
            }
        } catch (ClassNotFoundException e4) {
            throw new com.a.b.b.b(e4)
        }
    }

    private fun a(Class cls) {
        try {
            try {
                return Integer.parseInt((String) cls.getField("SDK").get(null))
            } catch (IllegalAccessException e2) {
                throw RuntimeException(e2)
            }
        } catch (NoSuchFieldException e3) {
            throw new com.a.b.b.b(e3)
        }
    }

    fun a(String str) {
        return e.startsWith(str)
    }
}
