package a.a.b.a

import androidx.core.internal.view.SupportMenu
import com.gmail.heagoo.neweditor.Token
import java.io.Serializable

class c implements Serializable {
    private static Int z = 0

    /* renamed from: a, reason: collision with root package name */
    public final Boolean f25a

    /* renamed from: b, reason: collision with root package name */
    private Short f26b
    private Short c
    private Array<Char> d
    private Array<Char> e
    private Byte f
    private Byte g
    private Int h
    private Byte i
    private Byte j
    private Byte k
    private Short l
    private Short m
    private Short n
    private Byte o
    private Byte p
    private Short q
    private Short r
    private Short s
    private final Array<Char> t
    private final Array<Char> u
    private final Byte v
    private final Byte w
    private String x
    private final Int y

    constructor() {
        this.f26b = (Short) 0
        this.c = (Short) 0
        this.d = new Array<Char>{0, 0}
        this.e = new Array<Char>{0, 0}
        this.f = (Byte) 0
        this.g = (Byte) 0
        this.h = 0
        this.i = (Byte) 0
        this.j = (Byte) 0
        this.k = (Byte) 0
        this.l = (Short) 0
        this.m = (Short) 0
        this.n = (Short) 0
        this.o = (Byte) 0
        this.p = (Byte) 0
        this.q = (Short) 0
        this.r = (Short) 0
        this.s = (Short) 0
        this.t = null
        this.u = null
        this.v = (Byte) 0
        this.w = (Byte) 0
        this.f25a = false
        this.x = ""
        this.y = 0
    }

    constructor(Short s, Short s2, Array<Char> cArr, Array<Char> cArr2, Byte b2, Byte b3, Int i, Byte b4, Byte b5, Byte b6, Short s3, Short s4, Short s5, Byte b7, Byte b8, Short s6, Short s7, Short s8, Array<Char> cArr3, Array<Char> cArr4, Byte b9, Byte b10, Boolean z2, Int i2) {
        Boolean z3
        if (b2 < 0 || b2 > 3) {
            StringBuilder("Invalid orientation value: ").append((Int) b2)
            b2 = 0
            z3 = true
        } else {
            z3 = z2
        }
        if (b3 < 0 || b3 > 3) {
            StringBuilder("Invalid touchscreen value: ").append((Int) b3)
            b3 = 0
            z3 = true
        }
        if (i < -1) {
            StringBuilder("Invalid density value: ").append(i)
            i = 0
            z3 = true
        }
        if (b4 < 0 || b4 > 3) {
            StringBuilder("Invalid keyboard value: ").append((Int) b4)
            b4 = 0
            z3 = true
        }
        if (b5 < 0 || b5 > 4) {
            StringBuilder("Invalid navigation value: ").append((Int) b5)
            b5 = 0
            z3 = true
        }
        cArr3 = (cArr3 == null || cArr3.length == 0 || cArr3[0] == 0) ? null : cArr3
        cArr4 = (cArr4 == null || cArr4.length == 0 || cArr4[0] == 0) ? null : cArr4
        this.f26b = s
        this.c = s2
        this.d = cArr
        this.e = cArr2
        this.f = b2
        this.g = b3
        this.h = i
        this.i = b4
        this.j = b5
        this.k = b6
        this.l = s3
        this.m = s4
        this.n = s5
        this.o = b7
        this.p = b8
        this.q = s6
        this.r = s7
        this.s = s8
        this.t = cArr3
        this.u = cArr4
        this.v = b9
        this.w = b10
        this.f25a = z3
        this.y = i2
        StringBuilder sb = StringBuilder()
        if (this.f26b != 0) {
            sb.append("-mcc").append(String.format("%03d", Short.valueOf(this.f26b)))
            if (this.c == -1) {
                sb.append("-mnc00")
            } else if (this.c != 0) {
                sb.append("-mnc")
                if (this.y > 32) {
                    sb.append((Int) this.c)
                } else if (this.c <= 0 || this.c >= 10) {
                    sb.append(String.format("%03d", Short.valueOf(this.c)))
                } else {
                    sb.append(String.format("%02d", Short.valueOf(this.c)))
                }
            }
        } else if (this.c != 0) {
            sb.append("-mnc").append((Int) this.c)
        }
        StringBuilder sb2 = StringBuilder()
        if (this.u == null && this.t == null && ((this.e[0] != 0 || this.d[0] != 0) && this.e.length != 3)) {
            sb2.append("-").append(this.d)
            if (this.e[0] != 0) {
                sb2.append("-r").append(this.e)
            }
        } else if (this.d[0] != 0 || this.e[0] != 0) {
            sb2.append("-b+")
            if (this.d[0] != 0) {
                sb2.append(this.d)
            }
            if (this.t != null && this.t.length == 4) {
                sb2.append("+").append(this.t)
            }
            if ((this.e.length == 2 || this.e.length == 3) && this.e[0] != 0) {
                sb2.append("+").append(this.e)
            }
            if (this.u != null && this.u.length >= 5) {
                sb2.append("+").append(a(this.u))
            }
        }
        sb.append(sb2.toString())
        switch (this.o & 192) {
            case 64:
                sb.append("-ldltr")
                break
            case 128:
                sb.append("-ldrtl")
                break
        }
        if (this.q != 0) {
            sb.append("-sw").append((Int) this.q).append("dp")
        }
        if (this.r != 0) {
            sb.append("-w").append((Int) this.r).append("dp")
        }
        if (this.s != 0) {
            sb.append("-h").append((Int) this.s).append("dp")
        }
        switch (this.o & Token.LITERAL3) {
            case 1:
                sb.append("-small")
                break
            case 2:
                sb.append("-normal")
                break
            case 3:
                sb.append("-large")
                break
            case 4:
                sb.append("-xlarge")
                break
        }
        switch (this.o & 48) {
            case 16:
                sb.append("-notlong")
                break
            case 32:
                sb.append("-Long")
                break
        }
        switch (this.v & 3) {
            case 1:
                sb.append("-notround")
                break
            case 2:
                sb.append("-round")
                break
        }
        switch (this.w & 12) {
            case 4:
                sb.append("-lowdr")
                break
            case 8:
                sb.append("-highdr")
                break
        }
        switch (this.w & 3) {
            case 1:
                sb.append("-nowidecg")
                break
            case 2:
                sb.append("-widecg")
                break
        }
        switch (this.f) {
            case 1:
                sb.append("-port")
                break
            case 2:
                sb.append("-land")
                break
            case 3:
                sb.append("-square")
                break
        }
        switch (this.p & Token.LITERAL3) {
            case 2:
                sb.append("-desk")
                break
            case 3:
                sb.append("-car")
                break
            case 4:
                sb.append("-television")
                break
            case 5:
                sb.append("-appliance")
                break
            case 6:
                sb.append("-watch")
                break
            case 7:
                sb.append("-vrheadset")
                break
            case 11:
                sb.append("-godzillaui")
                break
            case 12:
                sb.append("-smallui")
                break
            case 13:
                sb.append("-mediumui")
                break
            case 14:
                sb.append("-largeui")
                break
            case 15:
                sb.append("-hugeui")
                break
        }
        switch (this.p & 48) {
            case 16:
                sb.append("-notnight")
                break
            case 32:
                sb.append("-night")
                break
        }
        switch (this.h) {
            case 0:
                break
            case 120:
                sb.append("-ldpi")
                break
            case 160:
                sb.append("-mdpi")
                break
            case 213:
                sb.append("-tvdpi")
                break
            case 240:
                sb.append("-hdpi")
                break
            case 320:
                sb.append("-xhdpi")
                break
            case 480:
                sb.append("-xxhdpi")
                break
            case 640:
                sb.append("-xxxhdpi")
                break
            case 65534:
                sb.append("-anydpi")
                break
            case SupportMenu.USER_MASK /* 65535 */:
                sb.append("-nodpi")
                break
            default:
                sb.append('-').append(this.h).append("dpi")
                break
        }
        switch (this.g) {
            case 1:
                sb.append("-notouch")
                break
            case 2:
                sb.append("-stylus")
                break
            case 3:
                sb.append("-finger")
                break
        }
        switch (this.k & 3) {
            case 1:
                sb.append("-keysexposed")
                break
            case 2:
                sb.append("-keyshidden")
                break
            case 3:
                sb.append("-keyssoft")
                break
        }
        switch (this.i) {
            case 1:
                sb.append("-nokeys")
                break
            case 2:
                sb.append("-qwerty")
                break
            case 3:
                sb.append("-12key")
                break
        }
        switch (this.k & 12) {
            case 4:
                sb.append("-navexposed")
                break
            case 8:
                sb.append("-navhidden")
                break
        }
        switch (this.j) {
            case 1:
                sb.append("-nonav")
                break
            case 2:
                sb.append("-dpad")
                break
            case 3:
                sb.append("-trackball")
                break
            case 4:
                sb.append("-wheel")
                break
        }
        if (this.l != 0 && this.m != 0) {
            if (this.l > this.m) {
                sb.append(String.format("-%dx%d", Short.valueOf(this.l), Short.valueOf(this.m)))
            } else {
                sb.append(String.format("-%dx%d", Short.valueOf(this.m), Short.valueOf(this.l)))
            }
        }
        if (this.n > 0) {
            if (this.n >= (((this.p & Token.LITERAL3) != 7 && (this.w & 3) == 0 && (this.w & 12) == 0) ? (this.v & 3) != 0 ? (Short) 23 : this.h == 65534 ? (Short) 21 : (this.q == 0 && this.r == 0 && this.s == 0) ? (this.p & 63) != 0 ? (Short) 8 : ((this.o & 63) == 0 && this.h == 0) ? (Short) 0 : (Short) 4 : (Short) 13 : (Short) 26)) {
                sb.append("-v").append((Int) this.n)
            }
        }
        if (this.f25a) {
            StringBuilder sbAppend = sb.append("-ERR")
            Int i3 = z
            z = i3 + 1
            sbAppend.append(i3)
        }
        this.x = sb.toString()
    }

    private fun a(Array<Char> cArr) {
        StringBuilder sb = StringBuilder()
        for (Char c : cArr) {
            sb.append(Character.toUpperCase(c))
        }
        return sb.toString()
    }

    public final String a() {
        return this.x
    }

    public final Boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            return this.x.equals(((c) obj).x)
        }
        return false
    }

    public final Int hashCode() {
        return this.x.hashCode() + 527
    }

    public final String toString() {
        return !this.x.equals("") ? this.x : "[DEFAULT]"
    }
}
