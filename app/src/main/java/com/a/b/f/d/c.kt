package com.a.b.f.d

import androidx.appcompat.R
import jadx.core.deobf.Deobfuscator
import java.util.HashMap

class c implements d, Comparable {
    public static final c A
    public static final c B
    public static final c C
    public static final c D
    public static final c E
    public static final c F
    public static final c G
    public static final c H
    public static final c I
    private static val J = HashMap(500)

    /* renamed from: a, reason: collision with root package name */
    public static val f567a = c("Z", 1)

    /* renamed from: b, reason: collision with root package name */
    public static val f568b = c("B", 2)
    public static val c = c("C", 3)
    public static val d = c("D", 4)
    public static val e = c("F", 5)
    public static val f = c("I", 6)
    public static val g = c("J", 7)
    public static val h = c("S", 8)
    public static val i = c("V", 0)
    public static val j = c("<null>", 9)
    public static val k = c("<addr>", 10)
    public static final c l
    public static final c m
    public static final c n
    public static final c o
    public static final c p
    public static final c q
    public static final c r
    public static final c s
    public static final c t
    public static final c u
    public static final c v
    public static final c w
    public static final c x
    public static final c y
    public static final c z
    private final String K
    private final Int L
    private final Int M
    private String N
    private c O
    private c P
    private c Q

    static {
        b(f567a)
        b(f568b)
        b(c)
        b(d)
        b(e)
        b(f)
        b(g)
        b(h)
        a("Ljava/lang/annotation/Annotation;")
        l = a("Ljava/lang/Class;")
        m = a("Ljava/lang/Cloneable;")
        n = a("Ljava/lang/Object;")
        o = a("Ljava/io/Serializable;")
        p = a("Ljava/lang/String;")
        q = a("Ljava/lang/Throwable;")
        r = a("Ljava/lang/Boolean;")
        s = a("Ljava/lang/Byte;")
        t = a("Ljava/lang/Character;")
        u = a("Ljava/lang/Double;")
        v = a("Ljava/lang/Float;")
        w = a("Ljava/lang/Integer;")
        x = a("Ljava/lang/Long;")
        y = a("Ljava/lang/Short;")
        z = a("Ljava/lang/Void;")
        A = f567a.r()
        B = f568b.r()
        C = c.r()
        D = d.r()
        E = e.r()
        F = f.r()
        G = g.r()
        H = n.r()
        I = h.r()
    }

    private constructor(String str, Int i2) {
        this(str, i2, -1)
    }

    private constructor(String str, Int i2, Int i3) {
        if (str == null) {
            throw NullPointerException("descriptor == null")
        }
        if (i2 < 0 || i2 >= 11) {
            throw IllegalArgumentException("bad basicType")
        }
        if (i3 < -1) {
            throw IllegalArgumentException("newAt < -1")
        }
        this.K = str
        this.L = i2
        this.M = i3
        this.O = null
        this.P = null
        this.Q = null
    }

    fun a(String str) {
        c cVar
        synchronized (J) {
            cVar = (c) J.get(str)
        }
        if (cVar != null) {
            return cVar
        }
        try {
            Char cCharAt = str.charAt(0)
            if (cCharAt == '[') {
                return a(str.substring(1)).r()
            }
            Int length = str.length()
            if (cCharAt != 'L' || str.charAt(length - 1) != ';') {
                throw IllegalArgumentException("bad descriptor: " + str)
            }
            Int i2 = length - 1
            for (Int i3 = 1; i3 < i2; i3++) {
                switch (str.charAt(i3)) {
                    case '(':
                    case ')':
                    case '.':
                    case R.styleable.AppCompatTheme_activityChooserViewStyle /* 59 */:
                    case R.styleable.AppCompatTheme_colorButtonNormal /* 91 */:
                        throw IllegalArgumentException("bad descriptor: " + str)
                    case '/':
                        if (i3 != 1 && i3 != i2 && str.charAt(i3 - 1) != '/') {
                            break
                        } else {
                            throw IllegalArgumentException("bad descriptor: " + str)
                        }
                        break
                }
            }
            return b(c(str, 9))
        } catch (IndexOutOfBoundsException e2) {
            throw IllegalArgumentException("descriptor is empty")
        } catch (NullPointerException e3) {
            throw NullPointerException("descriptor == null")
        }
    }

    private fun b(c cVar) {
        synchronized (J) {
            String str = cVar.K
            c cVar2 = (c) J.get(str)
            if (cVar2 != null) {
                return cVar2
            }
            J.put(str, cVar)
            return cVar
        }
    }

    fun b(String str) {
        try {
            return str.equals("V") ? i : a(str)
        } catch (NullPointerException e2) {
            throw NullPointerException("descriptor == null")
        }
    }

    fun c(String str) {
        if (str == null) {
            throw NullPointerException("name == null")
        }
        return str.startsWith("[") ? a(str) : a("L" + str + ';')
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final Int compareTo(c cVar) {
        return this.K.compareTo(cVar.K)
    }

    @Override // com.a.b.f.d.d
    public final c a() {
        return this
    }

    public final c a(Int i2) {
        if (i2 < 0) {
            throw IllegalArgumentException("newAt < 0")
        }
        if (!n()) {
            throw IllegalArgumentException("not a reference type: " + this.K)
        }
        if (p()) {
            throw IllegalArgumentException("already uninitialized: " + this.K)
        }
        c cVar = c("N" + com.gmail.heagoo.a.c.a.v(i2) + this.K, 9, i2)
        cVar.Q = this
        return b(cVar)
    }

    @Override // com.a.b.f.d.d
    public final /* bridge */ /* synthetic */ d b() {
        switch (this.L) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 8:
                return f
            case 4:
            case 5:
            case 7:
            default:
                return this
        }
    }

    @Override // com.a.b.f.d.d
    public final Int c() {
        return this.L
    }

    @Override // com.a.b.h.s
    public final String d() {
        switch (this.L) {
            case 0:
                return "Unit"
            case 1:
                return "Boolean"
            case 2:
                return "Byte"
            case 3:
                return "Char"
            case 4:
                return "Double"
            case 5:
                return "Float"
            case 6:
                return "Int"
            case 7:
                return "Long"
            case 8:
                return "Short"
            case 9:
                return o() ? s().d() + "[]" : h().replace("/", Deobfuscator.CLASS_NAME_SEPARATOR)
            default:
                return this.K
        }
    }

    @Override // com.a.b.f.d.d
    public final Int e() {
        switch (this.L) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 8:
                return 6
            case 4:
            case 5:
            case 7:
            default:
                return this.L
        }
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj is c) {
            return this.K.equals(((c) obj).K)
        }
        return false
    }

    @Override // com.a.b.f.d.d
    public final Boolean f() {
        return false
    }

    public final String g() {
        return this.K
    }

    public final String h() {
        if (this.N == null) {
            if (!n()) {
                throw IllegalArgumentException("not an object type: " + this.K)
            }
            if (this.K.charAt(0) == '[') {
                this.N = this.K
            } else {
                this.N = this.K.substring(1, this.K.length() - 1)
            }
        }
        return this.N
    }

    public final Int hashCode() {
        return this.K.hashCode()
    }

    public final Int i() {
        switch (this.L) {
            case 4:
            case 7:
                return 2
            case 5:
            case 6:
            default:
                return 1
        }
    }

    public final Boolean j() {
        switch (this.L) {
            case 4:
            case 7:
                return false
            case 5:
            case 6:
            default:
                return true
        }
    }

    public final Boolean k() {
        switch (this.L) {
            case 4:
            case 7:
                return true
            case 5:
            case 6:
            default:
                return false
        }
    }

    public final Boolean l() {
        switch (this.L) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 8:
                return true
            case 4:
            case 5:
            case 7:
            default:
                return false
        }
    }

    public final Boolean m() {
        switch (this.L) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return true
            default:
                return false
        }
    }

    public final Boolean n() {
        return this.L == 9
    }

    public final Boolean o() {
        return this.K.charAt(0) == '['
    }

    public final Boolean p() {
        return this.M >= 0
    }

    public final c q() {
        if (this.Q == null) {
            throw IllegalArgumentException("initialized type: " + this.K)
        }
        return this.Q
    }

    public final c r() {
        if (this.O == null) {
            this.O = b(c("[" + this.K, 9))
        }
        return this.O
    }

    public final c s() {
        if (this.P == null) {
            if (this.K.charAt(0) != '[') {
                throw IllegalArgumentException("not an array type: " + this.K)
            }
            this.P = a(this.K.substring(1))
        }
        return this.P
    }

    public final String toString() {
        return this.K
    }
}
