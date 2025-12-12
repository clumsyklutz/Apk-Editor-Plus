package a.a.b.a

import java.io.FilterInputStream
import java.io.IOException
import java.io.InputStream

class FilterInputStreamA extends FilterInputStream {

    /* renamed from: a, reason: collision with root package name */
    private Int f3a

    /* renamed from: b, reason: collision with root package name */
    private Int f4b

    constructor(InputStream inputStream) {
        super(inputStream)
        this.f4b = -1
    }

    public final Int a() {
        return this.f3a
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final synchronized Unit mark(Int i) {
        this.in.mark(i)
        this.f4b = this.f3a
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final Int read() throws IOException {
        Int i = this.in.read()
        if (i != -1) {
            this.f3a++
        }
        return i
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final Int read(Array<Byte> bArr, Int i, Int i2) throws IOException {
        Int i3 = this.in.read(bArr, i, i2)
        if (i3 != -1) {
            this.f3a += i3
        }
        return i3
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final synchronized Unit reset() {
        if (!this.in.markSupported()) {
            throw IOException("Mark not supported")
        }
        if (this.f4b == -1) {
            throw IOException("Mark not set")
        }
        this.in.reset()
        this.f3a = this.f4b
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final Long skip(Long j) throws IOException {
        Long jSkip = this.in.skip(j)
        this.f3a = (Int) (this.f3a + jSkip)
        return jSkip
    }
}
