package com.gmail.heagoo.apkeditor.a.a

import java.io.IOException
import java.io.RandomAccessFile

class l {

    /* renamed from: a, reason: collision with root package name */
    private RandomAccessFile f793a

    /* renamed from: b, reason: collision with root package name */
    private Int f794b = 0

    constructor(RandomAccessFile randomAccessFile) {
        this.f793a = randomAccessFile
    }

    public final Int a() {
        return this.f794b
    }

    public final Unit a(Int i) throws IOException {
        Array<Byte> bArr = new Byte[4]
        e.a(bArr, 0, i)
        this.f793a.write(bArr)
        this.f794b += 4
    }

    public final Unit a(Int i, Int i2) throws IOException {
        Long filePointer = this.f793a.getFilePointer()
        this.f793a.seek(4L)
        a(i2)
        this.f793a.seek(filePointer)
    }

    public final Unit a(Array<Byte> bArr) throws IOException {
        this.f793a.write(bArr)
        this.f794b += bArr.length
    }

    public final Unit a(Array<Byte> bArr, Int i, Int i2) throws IOException {
        this.f793a.write(bArr, 0, i2)
        this.f794b += i2
    }

    public final Unit a(Array<Int> iArr) throws IOException {
        Array<Byte> bArr = new Byte[iArr.length * 4]
        for (Int i = 0; i < iArr.length; i++) {
            e.a(bArr, i * 4, iArr[i])
        }
        a(bArr)
    }

    public final Unit b() {
        this.f793a.close()
    }

    public final Unit b(Int i) throws IOException {
        Array<Byte> bArr = new Byte[2]
        e.b(bArr, 0, 0)
        this.f793a.write(bArr)
        this.f794b += 2
    }
}
