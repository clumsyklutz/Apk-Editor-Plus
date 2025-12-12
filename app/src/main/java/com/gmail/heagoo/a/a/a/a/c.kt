package com.gmail.heagoo.a.a.a.a

import java.io.EOFException
import java.io.IOException
import java.io.InputStream

class c {

    /* renamed from: a, reason: collision with root package name */
    private InputStream f728a

    /* renamed from: b, reason: collision with root package name */
    private Boolean f729b
    private Int c

    constructor() {
    }

    constructor(InputStream inputStream, Boolean z) {
        a(inputStream, false)
    }

    private fun a(InputStream inputStream, Boolean z) {
        this.f728a = inputStream
        this.f729b = z
        this.c = 0
    }

    private fun b(Int i) throws IOException {
        Int i2 = 0
        if (i < 0 || i > 4) {
            throw IllegalArgumentException()
        }
        if (this.f729b) {
            Int i3 = (i - 1) << 3
            while (i3 >= 0) {
                Int i4 = this.f728a.read()
                if (i4 == -1) {
                    throw EOFException()
                }
                this.c++
                Int i5 = (i4 << i3) | i2
                i3 -= 8
                i2 = i5
            }
        } else {
            Int i6 = i << 3
            Int i7 = 0
            while (i7 != i6) {
                Int i8 = this.f728a.read()
                if (i8 == -1) {
                    throw EOFException()
                }
                this.c++
                Int i9 = (i8 << i7) | i2
                i7 += 8
                i2 = i9
            }
        }
        return i2
    }

    public final Unit a() throws IOException {
        if (this.f728a == null) {
            return
        }
        try {
            this.f728a.close()
        } catch (IOException e) {
        }
        a(null, false)
    }

    public final Unit a(Array<Byte> bArr) throws IOException {
        Int i = 0
        Int length = bArr.length
        while (i < length) {
            Int i2 = this.f728a.read(bArr, i + 0, length - i)
            if (i2 == -1) {
                return
            }
            if (i2 > 0) {
                this.c += i2
                i += i2
            }
        }
    }

    public final Array<Int> a(Int i) {
        Array<Int> iArr = new Int[i]
        Int i2 = 0
        while (i > 0) {
            iArr[i2] = b(4)
            i--
            i2++
        }
        return iArr
    }

    public final Int b() {
        return b(1)
    }

    public final Int c() {
        return b(4)
    }

    public final Unit d() throws IOException {
        Long jSkip = this.f728a.skip(4L)
        this.c = (Int) (this.c + jSkip)
        if (jSkip != 4) {
            throw EOFException()
        }
    }
}
