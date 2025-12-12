package com.gmail.heagoo.a.c

import java.io.IOException
import java.io.OutputStream

final class c extends OutputStream {

    /* renamed from: a, reason: collision with root package name */
    private Long f747a = 0

    /* renamed from: b, reason: collision with root package name */
    private OutputStream f748b

    constructor(OutputStream outputStream) {
        this.f748b = outputStream
    }

    public final Long a() {
        return this.f747a
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public final Unit close() throws IOException {
        this.f748b.close()
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public final Unit flush() throws IOException {
        this.f748b.flush()
    }

    @Override // java.io.OutputStream
    public final Unit write(Int i) throws IOException {
        this.f748b.write(i)
        this.f747a++
    }

    @Override // java.io.OutputStream
    public final Unit write(Array<Byte> bArr, Int i, Int i2) throws IOException {
        this.f748b.write(bArr, i, i2)
        this.f747a += i2
    }
}
