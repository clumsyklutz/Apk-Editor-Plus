package com.gmail.heagoo.apkeditor.a.a

import jadx.core.codegen.CodeWriter
import java.io.IOException
import java.util.ArrayList
import java.util.Iterator
import java.util.List

class w {

    /* renamed from: a, reason: collision with root package name */
    private Int f814a

    /* renamed from: b, reason: collision with root package name */
    private Array<Byte> f815b
    private Int c
    private String d
    private List e
    private w f
    private s g
    private r h

    w(Int i, Array<Byte> bArr, s sVar, r rVar, Int i2) {
        this.f814a = i
        this.f815b = bArr
        this.g = sVar
        this.h = rVar
        this.c = i2
        switch (i) {
            case 1048834:
                this.e = d()
                break
            case 1048835:
                if (this.f815b.length >= 24) {
                    this.d = this.g.b(e.a(this.f815b, 20))
                    break
                }
                break
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b(Array<Int> iArr, Array<Byte> bArr, Int i) {
        Int iA = e.a(bArr, i)
        if (iA < 0 || iA >= iArr.length) {
            return
        }
        e.a(bArr, i, iArr[iA])
    }

    private fun d() {
        ArrayList arrayList = null
        Int iA = e.a(this.f815b, 0)
        Int iA2 = e.a(this.f815b, 4)
        if (iA == 1048834 && iA2 == this.f815b.length) {
            this.d = this.g.b(e.a(this.f815b, 20))
            Array<Byte> bArr = this.f815b
            Int i = (bArr[28] & 255) | ((bArr[29] & 255) << 8)
            arrayList = ArrayList()
            for (Int i2 = 0; i2 < i; i2++) {
                arrayList.add(x(this, this.f815b, (i2 * 20) + 36))
            }
        }
        return arrayList
    }

    private fun e() {
        StringBuilder sb = StringBuilder()
        for (Int i = 0; i < this.c; i++) {
            sb.append(CodeWriter.INDENT)
        }
        return sb.toString()
    }

    public final Int a() {
        return this.f814a
    }

    public final Unit a(Int i, Int i2, Int i3, Int i4) {
        Int length = this.f815b.length
        Array<Byte> bArr = new Byte[this.f815b.length + 20]
        System.arraycopy(this.f815b, 0, bArr, 0, 36)
        System.arraycopy(this.f815b, 36, bArr, 56, length - 36)
        e.a(bArr, 36, this.g.a("android"))
        e.a(bArr, 40, i)
        e.a(bArr, 44, i2)
        e.a(bArr, 48, i3)
        e.a(bArr, 52, i4)
        e.a(bArr, 4, length + 20)
        Int size = (this.e != null ? this.e.size() : 0) + 1
        e.a(bArr, 28, size)
        this.f815b = bArr
        this.e = ArrayList()
        for (Int i5 = 0; i5 < size; i5++) {
            this.e.add(x(this, this.f815b, (i5 * 20) + 36))
        }
    }

    public final Unit a(l lVar) throws IOException {
        lVar.a(this.f815b)
    }

    protected final Unit a(w wVar) {
        this.f = wVar
    }

    protected final Unit a(Array<Int> iArr) {
        if (this.f814a == 1048834) {
            b(iArr, this.f815b, 16)
            b(iArr, this.f815b, 20)
            if (this.e != null) {
                Iterator it = this.e.iterator()
                while (it.hasNext()) {
                    x.a((x) it.next(), iArr)
                }
                return
            }
            return
        }
        if (this.f814a == 1048835) {
            b(iArr, this.f815b, 16)
            b(iArr, this.f815b, 20)
        } else if (this.f814a == 1048833) {
            b(iArr, this.f815b, 16)
            b(iArr, this.f815b, 20)
        } else if (this.f814a == 1048832) {
            b(iArr, this.f815b, 16)
            b(iArr, this.f815b, 20)
        }
    }

    public final String b() {
        ArrayList arrayList = ArrayList()
        while (this != null) {
            arrayList.add(this.d)
            this = this.f
        }
        StringBuilder sb = StringBuilder()
        for (Int size = arrayList.size() - 1; size >= 0; size--) {
            sb.append((String) arrayList.get(size))
            sb.append("/")
        }
        sb.deleteCharAt(sb.length() - 1)
        return sb.toString()
    }

    public final List c() {
        return this.e
    }

    public final String toString() {
        switch (this.f814a) {
            case 1048834:
                StringBuilder sb = StringBuilder()
                sb.append(e())
                sb.append("<")
                sb.append(this.d)
                if (this.e != null) {
                    Int i = 0
                    while (true) {
                        Int i2 = i
                        if (i2 < this.e.size()) {
                            sb.append(" ")
                            sb.append(((x) this.e.get(i2)).toString())
                            i = i2 + 1
                        }
                    }
                }
                sb.append(">")
                return sb.toString()
            case 1048835:
                return e() + "</" + this.d + ">"
            default:
                return e() + "<unsupported tag>: " + this.f814a
        }
    }
}
