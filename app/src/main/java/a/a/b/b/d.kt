package a.a.b.b

import java.io.EOFException

class d {

    /* renamed from: a, reason: collision with root package name */
    public final Short f48a

    /* renamed from: b, reason: collision with root package name */
    public final Int f49b
    public final Int c

    private constructor(Short s, Int i, Int i2, Int i3) {
        this.f48a = s
        this.f49b = i3
        this.c = i3 + i2
    }

    fun a(a.d.f fVar, a.a.b.a.FilterInputStreamA aVar) {
        Int iA = aVar.a()
        try {
            Array<Byte> bArr = new Byte[8]
            fVar.readFully(bArr)
            return d((Short) (((bArr[1] & 255) << 8) | (bArr[0] & 255)), ((bArr[3] & 255) << 8) | (bArr[2] & 255), ((bArr[7] & 255) << 24) | ((bArr[6] & 255) << 16) | ((bArr[5] & 255) << 8) | (bArr[4] & 255), iA)
        } catch (EOFException e) {
            return d((Short) -1, 0, 0, aVar.a())
        }
    }
}
