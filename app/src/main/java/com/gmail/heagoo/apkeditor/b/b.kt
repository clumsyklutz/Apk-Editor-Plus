package com.gmail.heagoo.apkeditor.b

import androidx.appcompat.R
import java.util.ArrayList
import java.util.Collections
import java.util.List

class b {

    /* renamed from: a, reason: collision with root package name */
    private Array<Byte> f869a

    /* renamed from: b, reason: collision with root package name */
    private val f870b = b(52)
    private val c = b(56)
    private val d = b(60)
    private final Int e
    private final Int f
    private final Int g
    private final Int h
    private final Int i
    private final Int j
    private final Int k
    private final Int l
    private final Int m

    constructor(Array<Byte> bArr) throws i {
        this.f869a = bArr
        b(64)
        this.e = b(68)
        this.f = b(72)
        this.g = b(76)
        b(80)
        this.h = b(84)
        b(88)
        this.i = b(92)
        this.j = b(96)
        this.k = b(100)
        this.l = b(R.styleable.AppCompatTheme_buttonStyle)
        this.m = b(108)
    }

    fun a(Array<Byte> bArr, Int i) {
        return (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | (bArr[i + 3] << 24)
    }

    fun a(Int i, Array<Byte> bArr, Int i2) {
        bArr[i2] = (Byte) i
        bArr[i2 + 1] = (Byte) (i >> 8)
        bArr[i2 + 2] = (Byte) (i >> 16)
        bArr[i2 + 3] = (Byte) (i >>> 24)
    }

    private fun a(Int i, Int i2, Int i3, Int i4) {
        if (i < i2) {
            return false
        }
        a(i + i3, this.f869a, i4)
        return true
    }

    private fun b(Int i) throws i {
        Array<Byte> bArr = this.f869a
        Int i2 = (bArr[i + 3] << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16)
        if (i2 < 0) {
            throw i("out of range when read Int at offset 0x%x", Integer.valueOf(i))
        }
        return i2
    }

    public final Int a() {
        return this.j
    }

    public final Int a(Int i) throws i {
        if (i < 0 || i >= this.c) {
            throw i("String index out of bounds: %d", Integer.valueOf(i))
        }
        return this.d + (i << 2)
    }

    public final Unit a(Int i, Int i2) {
        if (i2 != 0) {
            a(a(this.f869a, 32) + i2, this.f869a, 32)
            a(this.f870b, i, i2, 52)
            a(this.d, i, i2, 60)
            a(this.e, i, i2, 68)
            a(this.g, i, i2, 76)
            a(this.h, i, i2, 84)
            a(this.i, i, i2, 92)
            a(this.k, i, i2, 100)
            if (a(this.m, i, i2, 108)) {
                return
            }
            a(this.l + i2, this.f869a, R.styleable.AppCompatTheme_buttonStyle)
        }
    }

    public final Int b() {
        return this.c
    }

    public final Int c() {
        return this.f
    }

    public final Int d() {
        return this.g
    }

    public final Int e() {
        return this.k
    }

    public final List f() throws i {
        Int iB = b(this.f870b)
        ArrayList arrayList = ArrayList(iB)
        for (Int i = 0; i < iB; i++) {
            Array<Byte> bArr = this.f869a
            Int i2 = this.f870b + 4 + (i * 12)
            a aVar = a()
            aVar.f867a = (Short) ((bArr[i2] & 255) | (bArr[i2 + 1] << 8))
            aVar.f868b = a(bArr, i2 + 4)
            aVar.c = a(bArr, i2 + 8)
            arrayList.add(aVar)
        }
        Collections.sort(arrayList, c(this))
        return arrayList
    }

    public final Int g() {
        return this.f870b
    }
}
