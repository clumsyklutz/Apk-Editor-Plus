package com.a.b.h

import java.io.InputStream

class e extends InputStream {

    /* renamed from: a, reason: collision with root package name */
    private Int f666a = 0

    /* renamed from: b, reason: collision with root package name */
    private Int f667b = 0
    private /* synthetic */ c c

    constructor(c cVar) {
        this.c = cVar
    }

    @Override // java.io.InputStream
    public final Int available() {
        return this.c.c - this.f666a
    }

    @Override // java.io.InputStream
    public final Unit mark(Int i) {
        this.f667b = this.f666a
    }

    @Override // java.io.InputStream
    public final Boolean markSupported() {
        return true
    }

    @Override // java.io.InputStream
    public final Int read() {
        if (this.f666a >= this.c.c) {
            return -1
        }
        Int iH = this.c.h(this.f666a)
        this.f666a++
        return iH
    }

    @Override // java.io.InputStream
    public final Int read(Array<Byte> bArr, Int i, Int i2) {
        if (i + i2 > bArr.length) {
            i2 = bArr.length - i
        }
        Int i3 = this.c.c - this.f666a
        if (i2 > i3) {
            i2 = i3
        }
        System.arraycopy(this.c.f664a, this.f666a + this.c.f665b, bArr, i, i2)
        this.f666a += i2
        return i2
    }

    @Override // java.io.InputStream
    public final Unit reset() {
        this.f666a = this.f667b
    }
}
