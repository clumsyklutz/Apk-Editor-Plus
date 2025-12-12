package com.gmail.heagoo.apkeditor.a

import android.R
import androidx.core.app.NotificationCompat
import com.gmail.heagoo.apkeditor.a.a.m
import com.gmail.heagoo.apkeditor.a.a.r
import com.gmail.heagoo.apkeditor.a.a.s
import java.io.IOException
import java.io.InputStream

class i implements e {

    /* renamed from: a, reason: collision with root package name */
    private m f826a

    /* renamed from: b, reason: collision with root package name */
    private Int f827b
    private s c
    private r d
    private h g
    private Int f = -1
    private g e = g()

    constructor(InputStream inputStream) {
        this.f826a = m(inputStream)
    }

    protected static Int a(Array<Byte> bArr, Int i) {
        return (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24)
    }

    fun a(Int i) {
        switch (i) {
            case R.attr.theme:
                return "theme"
            case R.attr.label:
                return "label"
            case R.attr.icon:
                return "icon"
            case R.attr.name:
                return "name"
            case R.attr.authorities:
                return "authorities"
            case R.attr.versionCode:
                return "versionCode"
            case R.attr.versionName:
                return "versionName"
            default:
                return null
        }
    }

    protected static Unit a(Array<Byte> bArr, Int i, Int i2) {
        bArr[i] = (Byte) i2
        bArr[i + 1] = (Byte) (i2 >> 8)
        bArr[i + 2] = (Byte) (i2 >> 16)
        bArr[i + 3] = (Byte) (i2 >>> 24)
    }

    public final g a() {
        return this.e
    }

    @Override // com.gmail.heagoo.apkeditor.a.e
    public final Unit a(String str, Int i, Int i2, Int i3, Int i4) {
        String strB = this.c.b(i)
        if ((strB == null || strB.equals("")) && i < this.d.a()) {
            strB = a(this.d.f804a[i])
        }
        if (i4 < 0) {
            i4 = i2
        }
        if ("uses-permission".equals(str)) {
            if ("name".equals(strB)) {
                this.e.v.add(this.c.b(i4))
                return
            }
            return
        }
        if ("manifest".equals(str)) {
            if ("versionCode".equals(strB)) {
                this.e.f822a = i4
                return
            }
            if ("versionName".equals(strB) && c.a(i3)) {
                this.e.k = i4
                this.e.d = this.c.b(i4)
                return
            } else if ("installLocation".equals(strB)) {
                this.e.f823b = i4
                return
            } else {
                if ("package".equals(strB) && c.a(i3)) {
                    this.e.l = i4
                    this.e.e = this.c.b(i4)
                    return
                }
                return
            }
        }
        if ("application".equals(str)) {
            if ("label".equals(strB)) {
                if (!c.a(i3)) {
                    this.e.n = i4
                    return
                }
                this.e.j = i4
                this.e.c = this.c.b(i4)
                return
            }
            if ("name".equals(strB)) {
                this.e.p.add(Integer.valueOf(i4))
                this.e.f = this.c.b(i4)
                return
            } else {
                if ("icon".equals(strB)) {
                    this.e.m = i4
                    return
                }
                return
            }
        }
        if ("activity".equals(str) || NotificationCompat.CATEGORY_SERVICE.equals(str) || "receiver".equals(str) || "provider".equals(str)) {
            if ("name".equals(strB) && c.a(i3)) {
                this.e.p.add(Integer.valueOf(i4))
                if ("activity".equals(str)) {
                    this.e.q.put(Integer.valueOf(i4), 0)
                } else if (NotificationCompat.CATEGORY_SERVICE.equals(str)) {
                    this.e.q.put(Integer.valueOf(i4), 1)
                } else if ("receiver".equals(str)) {
                    this.e.q.put(Integer.valueOf(i4), 2)
                } else if ("provider".equals(str)) {
                    this.e.q.put(Integer.valueOf(i4), 3)
                }
            }
            if ("activity".equals(str) && "name".equals(strB)) {
                this.f = i4
            }
            if ("provider".equals(str) && c.a(i3)) {
                if (this.g == null) {
                    this.g = h()
                }
                if ("authorities".equals(strB)) {
                    this.g.c = i4
                    this.g.f825b = this.c.b(i4)
                } else if ("name".equals(strB)) {
                    this.g.f824a = this.c.b(i4)
                }
                if (this.g.f825b == null || this.g.f824a == null) {
                    return
                }
                this.e.s.add(this.g)
                this.g = null
                return
            }
            return
        }
        if ("activity-alias".equals(str)) {
            if ("name".equals(strB)) {
                this.f = i4
                return
            } else {
                if ("targetActivity".equals(strB)) {
                    this.e.u.put(Integer.valueOf(this.f), Integer.valueOf(i4))
                    return
                }
                return
            }
        }
        if ("category".equals(str)) {
            if ("name".equals(strB) && "android.intent.category.LAUNCHER".equals(this.c.b(i4)) && this.f != -1) {
                this.e.t.add(Integer.valueOf(this.f))
                return
            }
            return
        }
        if ("permission".equals(str)) {
            if ("name".equals(strB)) {
                this.e.r.put(Integer.valueOf(i4), this.c.b(i4))
            }
        } else if ("uses-sdk".equals(str)) {
            if ("minSdkVersion".equals(strB)) {
                this.e.g = i4
            } else if ("targetSdkVersion".equals(strB)) {
                this.e.h = i4
            } else if ("maxSdkVersion".equals(strB)) {
                this.e.i = i4
            }
        }
    }

    public final Unit b() throws IOException {
        this.f826a.a()
        this.f827b = this.f826a.a()
        this.c = s()
        this.c.a(this.f826a)
        new Object[1][0] = Integer.valueOf(this.c.f806a)
        new Object[1][0] = Integer.valueOf(this.c.c())
        for (Int i = 0; i < this.c.c(); i++) {
            this.e.o.add(this.c.b(i))
        }
        this.d = r()
        this.d.a(this.f826a)
        while (c(this.c).a(this.f826a, this) != c.f820a) {
        }
        this.f827b += 20
    }
}
