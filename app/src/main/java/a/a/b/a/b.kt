package a.a.b.a

import java.io.DataInput
import java.io.DataInputStream
import java.io.IOException
import java.io.InputStream

class b implements DataInput {

    /* renamed from: a, reason: collision with root package name */
    private DataInputStream f23a

    /* renamed from: b, reason: collision with root package name */
    private InputStream f24b
    private Array<Byte> c = new Byte[8]

    constructor(InputStream inputStream) {
        this.f24b = inputStream
        this.f23a = DataInputStream(inputStream)
    }

    public final Int a(Array<Byte> bArr, Int i, Int i2) {
        return this.f24b.read(bArr, 0, 4096)
    }

    @Override // java.io.DataInput
    public final Boolean readBoolean() {
        return this.f23a.readBoolean()
    }

    @Override // java.io.DataInput
    public final Byte readByte() {
        return this.f23a.readByte()
    }

    @Override // java.io.DataInput
    public final Char readChar() throws IOException {
        this.f23a.readFully(this.c, 0, 2)
        return (Char) (((this.c[1] & 255) << 8) | (this.c[0] & 255))
    }

    @Override // java.io.DataInput
    public final Double readDouble() {
        return Double.longBitsToDouble(readLong())
    }

    @Override // java.io.DataInput
    public final Float readFloat() {
        return Float.intBitsToFloat(readInt())
    }

    @Override // java.io.DataInput
    public final Unit readFully(Array<Byte> bArr) throws IOException {
        this.f23a.readFully(bArr, 0, bArr.length)
    }

    @Override // java.io.DataInput
    public final Unit readFully(Array<Byte> bArr, Int i, Int i2) throws IOException {
        this.f23a.readFully(bArr, i, i2)
    }

    @Override // java.io.DataInput
    public final Int readInt() throws IOException {
        this.f23a.readFully(this.c, 0, 4)
        return (this.c[3] << 24) | ((this.c[2] & 255) << 16) | ((this.c[1] & 255) << 8) | (this.c[0] & 255)
    }

    @Override // java.io.DataInput
    @Deprecated
    public final String readLine() {
        return this.f23a.readLine()
    }

    @Override // java.io.DataInput
    public final Long readLong() throws IOException {
        this.f23a.readFully(this.c, 0, 8)
        return (this.c[7] << 56) | ((this.c[6] & 255) << 48) | ((this.c[5] & 255) << 40) | ((this.c[4] & 255) << 32) | ((this.c[3] & 255) << 24) | ((this.c[2] & 255) << 16) | ((this.c[1] & 255) << 8) | (this.c[0] & 255)
    }

    @Override // java.io.DataInput
    public final Short readShort() throws IOException {
        this.f23a.readFully(this.c, 0, 2)
        return (Short) (((this.c[1] & 255) << 8) | (this.c[0] & 255))
    }

    @Override // java.io.DataInput
    public final String readUTF() {
        return this.f23a.readUTF()
    }

    @Override // java.io.DataInput
    public final Int readUnsignedByte() {
        return this.f23a.readUnsignedByte()
    }

    @Override // java.io.DataInput
    public final Int readUnsignedShort() throws IOException {
        this.f23a.readFully(this.c, 0, 2)
        return ((this.c[1] & 255) << 8) | (this.c[0] & 255)
    }

    @Override // java.io.DataInput
    public final Int skipBytes(Int i) {
        return this.f23a.skipBytes(i)
    }
}
