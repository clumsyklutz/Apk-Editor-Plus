package com.a.b.c.b

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
class t {

    /* renamed from: a, reason: collision with root package name */
    public static val f335a = t("START", 0)

    /* renamed from: b, reason: collision with root package name */
    public static val f336b = t("END_SIMPLY", 1)
    public static val c = t("END_REPLACED", 2)
    public static val d = t("END_MOVED", 3)
    public static val e = t("END_CLOBBERED_BY_PREV", 4)
    public static val f = t("END_CLOBBERED_BY_NEXT", 5)

    static {
        Array<t> tVarArr = {f335a, f336b, c, d, e, f}
    }

    private constructor(String str, Int i) {
    }
}
