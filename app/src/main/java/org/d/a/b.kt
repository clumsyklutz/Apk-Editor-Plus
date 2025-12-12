package org.d.a

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
class b {

    /* renamed from: a, reason: collision with root package name */
    public static val f1606a = b("ERROR", 0, 40, "ERROR")

    /* renamed from: b, reason: collision with root package name */
    public static val f1607b = b("WARN", 1, 30, "WARN")
    public static val c = b("INFO", 2, 20, "INFO")
    public static val d = b("DEBUG", 3, 10, "DEBUG")
    private static b e = b("TRACE", 4, 0, "TRACE")
    private String f

    static {
        Array<b> bVarArr = {f1606a, f1607b, c, d, e}
    }

    private constructor(String str, Int i, Int i2, String str2) {
        this.f = str2
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.f
    }
}
