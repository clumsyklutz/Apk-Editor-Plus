package com.gmail.heagoo.apkeditor.a.a

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile
import java.util.Collections
import java.util.List

class o {

    /* renamed from: a, reason: collision with root package name */
    private static Int f798a = 1048833

    /* renamed from: b, reason: collision with root package name */
    private static Int f799b = 1048834
    private static Int c = 1048835
    private static Int d = 1048832
    private static Int e = 1048836
    private m f
    private l g
    private Int h = 29
    private List i
    private s j
    private Int k
    private Int l
    private Int m
    private Array<Byte> n
    private Int o

    constructor(InputStream inputStream, String str) throws IOException {
        this.f = m(inputStream)
        File file = File(str)
        if (file.exists()) {
            file.delete()
        }
        RandomAccessFile randomAccessFile = RandomAccessFile(str, "rw")
        randomAccessFile.setLength(0L)
        this.g = l(randomAccessFile)
        Int iA = this.f.a()
        this.k = this.f.a()
        this.g.a(iA)
        this.g.a(this.k)
        this.j = s()
        this.j.a(this.f)
        this.o = this.j.f806a
        new Object[1][0] = Integer.valueOf(this.j.f806a)
        new Object[1][0] = Integer.valueOf(this.j.f807b)
        new Object[1][0] = Integer.valueOf(this.j.c)
    }

    private fun a(Int i, Int i2) {
        Int i3 = 0
        List listA = this.j.a()
        Int size = listA.size() - 1
        while (size >= 0) {
            Int i4 = i >= ((Integer) listA.get(size)).intValue() ? i3 + 1 : i3
            size--
            i3 = i4
        }
        if (i3 > 0) {
            e.a(this.n, i2, i + i3)
        }
        return i + i3
    }

    private fun c(Int i) {
        return i == 3
    }

    public final Unit a() {
        Int iA
        Boolean z
        r rVar = r()
        rVar.a(this.f)
        this.j.a(this.g)
        this.k += this.j.f806a - this.o
        rVar.a(this.g)
        Int iA2 = this.j.a("uses-permission")
        Array<Byte> bArr = new Byte[56]
        Array<Byte> bArr2 = new Byte[24]
        do {
            m mVar = this.f
            iA = mVar.a()
            Int iA3 = mVar.a()
            this.n = new Byte[iA3]
            e.a(this.n, 0, iA)
            e.a(this.n, 4, iA3)
            if (iA3 > 8) {
                mVar.a(this.n, 8, iA3 - 8)
            }
            if (iA == f799b) {
                a(e.a(this.n, 16), 16)
                Int iA4 = a(e.a(this.n, 20), 20)
                Int iA5 = e.a(this.n, 28)
                if (this.j.b(iA4).equals("manifest")) {
                    for (Int i = 0; i < iA5; i++) {
                        a(e.a(this.n, (i * 20) + 36), (i * 20) + 36)
                        Int iA6 = a(e.a(this.n, (i * 20) + 36 + 4), (i * 20) + 36 + 4)
                        Int iA7 = a(e.a(this.n, (i * 20) + 36 + 8), (i * 20) + 36 + 8)
                        Int iA8 = e.a(this.n, ((i * 20) + 36) + 12) >> 16
                        Int i2 = ((iA8 >> 8) & 255) | ((iA8 & 255) << 8)
                        Int iA9 = e.a(this.n, (i * 20) + 36 + 16)
                        if (c(i2)) {
                            iA9 = a(iA9, (i * 20) + 36 + 16)
                        }
                        String strB = this.j.b(iA6)
                        if ("versionCode".equals(strB)) {
                            if (iA9 != -1) {
                                e.a(this.n, (i * 20) + 36 + 16, this.l)
                            } else if (iA7 != -1) {
                                e.a(this.n, (i * 20) + 36 + 8, this.l)
                            }
                        } else if ("installLocation".equals(strB)) {
                            if (iA9 != -1) {
                                e.a(this.n, (i * 20) + 36 + 16, this.m)
                            } else if (iA7 != -1) {
                                e.a(this.n, (i * 20) + 36 + 8, this.m)
                            }
                        }
                    }
                } else {
                    for (Int i3 = 0; i3 < iA5; i3++) {
                        a(e.a(this.n, (i3 * 20) + 36), (i3 * 20) + 36)
                        a(e.a(this.n, (i3 * 20) + 36 + 4), (i3 * 20) + 36 + 4)
                        a(e.a(this.n, (i3 * 20) + 36 + 8), (i3 * 20) + 36 + 8)
                        Int iA10 = e.a(this.n, ((i3 * 20) + 36) + 12) >> 16
                        Int i4 = ((iA10 & 255) << 8) | ((iA10 >> 8) & 255)
                        Int iA11 = e.a(this.n, (i3 * 20) + 36 + 16)
                        if (c(i4)) {
                            a(iA11, (i3 * 20) + 36 + 16)
                        }
                    }
                }
            } else if (iA == c || iA == f798a || iA == d) {
                a(e.a(this.n, 16), 16)
                a(e.a(this.n, 20), 20)
            }
            if (iA == b.f782b) {
                if (e.a(this.n, 20) == iA2) {
                    System.arraycopy(this.n, 0, bArr, 0, 56)
                }
                z = false
            } else if (iA == b.c && e.a(this.n, 20) == iA2) {
                System.arraycopy(this.n, 0, bArr2, 0, 24)
                z = true
            } else {
                z = false
            }
            this.g.a(this.n)
            if (z && this.i != null) {
                for (Int i5 = 1; i5 < this.i.size(); i5++) {
                    e.a(bArr, 44, this.h + i5)
                    e.a(bArr, 52, this.h + i5)
                    this.g.a(bArr)
                    this.k += 56
                    this.g.a(bArr2)
                    this.k += 24
                }
            }
        } while (iA != b.f781a);
        this.g.a(4, this.k)
        this.g.b()
    }

    public final Unit a(Int i) {
        this.m = i
    }

    public final Unit a(String str, String str2) {
        for (Int i = 0; i < this.j.f807b; i++) {
            if (this.j.b(i).equals(str)) {
                this.j.a(i, str2)
                return
            }
        }
    }

    public final Unit a(List list) {
        this.i = list
        this.h = this.j.a("aaaaaa.permission.READ_EXTERNAL_STORAGE")
        Collections.sort(list)
        this.j.a(this.h, (String) list.get(0))
        for (Int size = list.size() - 1; size > 0; size--) {
            this.j.a((String) list.get(size), this.h + 1)
        }
    }

    public final Unit b(Int i) {
        this.l = i
    }
}
