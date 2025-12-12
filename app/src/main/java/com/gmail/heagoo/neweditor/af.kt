package com.gmail.heagoo.neweditor

import androidx.appcompat.R
import java.util.Arrays
import java.util.Hashtable
import java.util.Map
import java.util.regex.Matcher
import java.util.regex.Pattern

class af {
    private static /* synthetic */ Boolean m

    /* renamed from: b, reason: collision with root package name */
    private x f1499b
    private ae c
    private z d
    private ag e
    private t f
    private Int h
    private Int i
    private Int j
    private Int k
    private Boolean l

    /* renamed from: a, reason: collision with root package name */
    private val f1498a = Hashtable(64)
    private val g = z()

    static {
        m = !af.class.desiredAssertionStatus()
    }

    private fun a(w wVar, w wVar2) {
        switch (wVar.f) {
            case -2:
                return wVar2.j
            case -1:
                return this.e.c.j()
            default:
                return wVar.f
        }
    }

    private fun a() {
        w wVar
        if (this.e.f1500a == null || (wVar = this.e.f1500a.f1501b) == null || (this.e.f1500a.f1501b.f1533a & 1024) == 0) {
            return
        }
        if (this.j != this.h) {
            this.c.a(wVar.j, this.h - this.d.c, this.j - this.h, this.e)
        }
        this.h = this.j
        this.e = this.e.f1500a
        this.f = this.e.c.f()
        this.e.a(null)
    }

    private fun a(ae aeVar, Byte b2, Int i, Int i2, ag agVar) {
        Int i3 = i + i2
        Int i4 = i
        while (i < i3) {
            if (Character.isWhitespace(this.d.f1537a[this.d.c + i])) {
                if (i4 != i) {
                    aeVar.a(b2, i4, i - i4, agVar)
                }
                aeVar.a(b2, i, 1, agVar)
                i4 = i + 1
            }
            i++
        }
        if (i4 != i3) {
            aeVar.a(b2, i4, i3 - i4, agVar)
        }
    }

    private fun a(Boolean z) {
        Byte bA
        Int i = this.j - this.h
        if (i == 0) {
            return
        }
        if (this.e.c.g()) {
            Boolean z2 = false
            Boolean zMatches = false
            for (Int i2 = this.h; i2 < this.j; i2++) {
                if (Character.isDigit(this.d.f1537a[i2])) {
                    zMatches = true
                } else {
                    z2 = true
                }
            }
            if (z2) {
                Pattern patternH = this.e.c.h()
                if (zMatches) {
                    if (patternH == null) {
                        zMatches = false
                    } else {
                        Int i3 = this.d.f1538b
                        Int i4 = this.d.c
                        this.d.c = this.h
                        this.d.f1538b = i
                        zMatches = patternH.matcher(aa(this.d)).matches()
                        this.d.c = i4
                        this.d.f1538b = i3
                    }
                }
            }
            if (zMatches) {
                this.c.a((Byte) 5, this.h - this.d.c, i, this.e)
                this.h = this.j
                return
            }
        }
        if (this.f == null || (bA = this.f.a(this.d, this.h, i)) == 0) {
            if (z) {
                this.c.a(this.e.c.j(), this.h - this.d.c, i, this.e)
                this.h = this.j
            }
        } else {
            this.c.a(bA, this.h - this.d.c, i, this.e)
            this.h = this.j
        }
    }

    private fun a(Int i, Int i2) {
        if ((i2 & 2) == 2) {
            if (i != this.d.c) {
                return false
            }
        } else if ((i2 & 4) == 4) {
            if (i != this.k) {
                return false
            }
        } else if ((i2 & 8) == 8 && i != this.h) {
            return false
        }
        return true
    }

    private fun a(w wVar) {
        Int iEnd
        Matcher matcher
        aa aaVar
        Boolean z
        Array<Char> cArrA = null
        if (wVar.l == null) {
            if (wVar.k != null && this.j + wVar.k.length < this.d.f1537a.length) {
                Int i = 0
                while (true) {
                    if (i >= wVar.k.length) {
                        z = true
                        break
                    }
                    if (Character.toUpperCase(this.d.f1537a[this.j + i]) != wVar.k[i]) {
                        z = false
                        break
                    }
                    i++
                }
                if (!z) {
                    return false
                }
            }
        } else if (-1 == Arrays.binarySearch(wVar.l, Character.toUpperCase(this.d.f1537a[this.j]))) {
            return false
        }
        if (!a((wVar.f1533a & 4) != 0 ? this.h : this.j, wVar.h)) {
            return false
        }
        if ((wVar.f1533a & 8192) == 0) {
            this.g.f1537a = wVar.g
            this.g.c = 0
            this.g.f1538b = this.g.f1537a.length
            iEnd = this.g.f1538b
            if (!com.gmail.heagoo.a.c.a.a(this.e.c.e(), this.d, this.j, this.g.f1537a)) {
                return false
            }
            matcher = null
            aaVar = null
        } else {
            aa aaVar2 = aa(this.d, this.j - this.d.c, this.d.f1538b - (this.j - this.d.c))
            Matcher matcher2 = wVar.i.matcher(aaVar2)
            if (!matcher2.lookingAt()) {
                return false
            }
            if (matcher2.start() != 0) {
                throw InternalError("Can't happen")
            }
            iEnd = matcher2.end()
            if (iEnd == 0) {
                matcher = matcher2
                aaVar = aaVar2
                iEnd = 1
            } else {
                matcher = matcher2
                aaVar = aaVar2
            }
        }
        if ((wVar.f1533a & 2048) == 2048) {
            this.j += this.g.f1538b
        } else {
            if (this.e.f1501b != null) {
                b(this.e.f1501b)
            }
            a((wVar.f1533a & 4) != 4)
            switch (wVar.f1533a & 255) {
                case 0:
                    this.e.d = null
                    if ((wVar.f1533a & 8192) != 0) {
                        a(this.c, wVar.j, this.j - this.d.c, iEnd, this.e)
                    } else {
                        this.c.a(wVar.j, this.j - this.d.c, iEnd, this.e)
                    }
                    if (wVar.f1534b != null) {
                        this.e = ag(wVar.f1534b, this.e.f1500a)
                        this.f = this.e.c.f()
                        break
                    }
                    break
                case 2:
                case 16:
                    this.e.a(wVar)
                    Byte bA = a(wVar, this.e.f1501b)
                    if ((wVar.f1533a & 8192) != 0) {
                        a(this.c, bA, this.j - this.d.c, iEnd, this.e)
                    } else {
                        this.c.a(bA, this.j - this.d.c, iEnd, this.e)
                    }
                    if (aaVar != null && wVar.c != null) {
                        cArrA = a(matcher, wVar.c)
                    }
                    this.e.d = cArrA
                    this.e = ag(wVar.f1534b, this.e)
                    this.f = this.e.c.f()
                    break
                case 4:
                    this.e.d = null
                    if (this.j != this.h) {
                        this.c.a(wVar.j, this.h - this.d.c, this.j - this.h, this.e)
                    }
                    this.c.a(a(wVar, wVar), this.j - this.d.c, this.g.f1538b, this.e)
                    break
                case 8:
                    this.c.a(a(wVar, wVar), this.j - this.d.c, this.g.f1538b, this.e)
                    this.e.d = null
                    this.e.a(wVar)
                    break
                default:
                    throw InternalError("Unhandled major action")
            }
            this.j += iEnd - 1
            this.h = this.j + 1
        }
        return true
    }

    private static Array<Char> a(Matcher matcher, Array<Char> cArr) {
        Char cCharAt
        StringBuilder sb = StringBuilder()
        Int i = 0
        while (i < cArr.length) {
            Char c = cArr[i]
            if (c != '$' && c != '~') {
                sb.append(c)
            } else if (i == cArr.length - 1) {
                sb.append(c)
            } else {
                Char c2 = cArr[i + 1]
                if (Character.isDigit(c2)) {
                    if (c == '$') {
                        sb.append(matcher.group(c2 - '0'))
                    } else {
                        String strGroup = matcher.group(c2 - '0')
                        if (strGroup.length() == 1) {
                            switch (strGroup.charAt(0)) {
                                case '(':
                                    cCharAt = ')'
                                    break
                                case ')':
                                    cCharAt = '('
                                    break
                                case R.styleable.AppCompatTheme_toolbarStyle /* 60 */:
                                    cCharAt = '>'
                                    break
                                case R.styleable.AppCompatTheme_popupMenuStyle /* 62 */:
                                    cCharAt = '<'
                                    break
                                case R.styleable.AppCompatTheme_colorButtonNormal /* 91 */:
                                    cCharAt = ']'
                                    break
                                case R.styleable.AppCompatTheme_controlBackground /* 93 */:
                                    cCharAt = '['
                                    break
                                case '{':
                                    cCharAt = '}'
                                    break
                                case '}':
                                    cCharAt = '{'
                                    break
                                default:
                                    cCharAt = 0
                                    break
                            }
                            if (cCharAt == 0) {
                                cCharAt = strGroup.charAt(0)
                            }
                            sb.append(cCharAt)
                        } else {
                            sb.append(c)
                        }
                    }
                    i++
                } else {
                    sb.append(c)
                }
            }
            i++
        }
        Array<Char> cArr2 = new Char[sb.length()]
        sb.getChars(0, sb.length(), cArr2, 0)
        return cArr2
    }

    private fun b(w wVar) {
        if (!a((wVar.f1533a & 4) != 0 ? this.h : this.j, wVar.d)) {
            return false
        }
        if ((wVar.f1533a & 8) == 0) {
            if (this.e.d != null) {
                this.g.f1537a = this.e.d
            } else {
                this.g.f1537a = wVar.c
            }
            this.g.c = 0
            this.g.f1538b = this.g.f1537a.length
            if (!com.gmail.heagoo.a.c.a.a(this.e.c.e(), this.d, this.j, this.g.f1537a)) {
                return false
            }
        }
        if (!m && (wVar.f1533a & 2048) != 0) {
            throw AssertionError()
        }
        if ((this.e.f1501b.f1533a & 8) != 0) {
            if (this.j != this.h) {
                this.c.a(this.e.f1501b.j, this.h - this.d.c, this.j - this.h, this.e)
            }
            this.h = this.j
            this.e.a(null)
        }
        return true
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x012a A[Catch: all -> 0x0179, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x001b, B:7:0x002d, B:8:0x004b, B:11:0x0053, B:14:0x005c, B:15:0x007c, B:17:0x0082, B:38:0x0155, B:19:0x008c, B:21:0x0092, B:23:0x009a, B:25:0x00a4, B:27:0x00c8, B:29:0x00ce, B:30:0x00d5, B:37:0x0152, B:32:0x012a, B:33:0x0140, B:35:0x0146, B:45:0x017e, B:47:0x0188, B:49:0x018c, B:50:0x0192, B:52:0x0198, B:53:0x019f, B:55:0x01ac, B:56:0x01c3, B:57:0x01de, B:59:0x01e2, B:61:0x01ec, B:63:0x01fe, B:65:0x0209, B:67:0x020f, B:68:0x0216, B:69:0x0238, B:71:0x0242, B:72:0x0249, B:73:0x0250, B:75:0x0256, B:77:0x025e, B:80:0x0268, B:81:0x027f, B:39:0x015e), top: B:85:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x017c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized com.gmail.heagoo.neweditor.ag a(com.gmail.heagoo.neweditor.ag r10, com.gmail.heagoo.neweditor.ae r11, com.gmail.heagoo.neweditor.z r12) {
        /*
            Method dump skipped, instructions count: 673
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.neweditor.af.a(com.gmail.heagoo.neweditor.ag, com.gmail.heagoo.neweditor.ae, com.gmail.heagoo.neweditor.z):com.gmail.heagoo.neweditor.ag")
    }

    public final x a(String str) {
        return (x) this.f1498a.get(str)
    }

    public final Unit a(x xVar) {
        this.f1498a.put(xVar.b(), xVar)
        if (xVar.b().equals("MAIN")) {
            this.f1499b = xVar
        }
    }
}
