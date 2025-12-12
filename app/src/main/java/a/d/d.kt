package a.d

import java.io.DataInput
import java.io.IOException

abstract class d implements DataInput {

    /* renamed from: a, reason: collision with root package name */
    private DataInput f70a

    constructor(DataInput dataInput) {
        this.f70a = dataInput
    }

    public final Array<Int> a(Int i) {
        Array<Int> iArr = new Int[i]
        for (Int i2 = 0; i2 < i; i2++) {
            iArr[i2] = readInt()
        }
        return iArr
    }

    @Override // java.io.DataInput
    fun readBoolean() {
        return this.f70a.readBoolean()
    }

    @Override // java.io.DataInput
    fun readByte() {
        return this.f70a.readByte()
    }

    @Override // java.io.DataInput
    fun readChar() {
        return this.f70a.readChar()
    }

    @Override // java.io.DataInput
    fun readDouble() {
        return this.f70a.readDouble()
    }

    @Override // java.io.DataInput
    fun readFloat() {
        return this.f70a.readFloat()
    }

    @Override // java.io.DataInput
    fun readFully(Array<Byte> bArr) throws IOException {
        this.f70a.readFully(bArr)
    }

    @Override // java.io.DataInput
    fun readFully(Array<Byte> bArr, Int i, Int i2) throws IOException {
        this.f70a.readFully(bArr, i, i2)
    }

    @Override // java.io.DataInput
    fun readInt() {
        return this.f70a.readInt()
    }

    @Override // java.io.DataInput
    fun readLine() {
        return this.f70a.readLine()
    }

    @Override // java.io.DataInput
    fun readLong() {
        return this.f70a.readLong()
    }

    @Override // java.io.DataInput
    fun readShort() {
        return this.f70a.readShort()
    }

    @Override // java.io.DataInput
    fun readUTF() {
        return this.f70a.readUTF()
    }

    @Override // java.io.DataInput
    fun readUnsignedByte() {
        return this.f70a.readUnsignedByte()
    }

    @Override // java.io.DataInput
    fun readUnsignedShort() {
        return this.f70a.readUnsignedShort()
    }

    @Override // java.io.DataInput
    fun skipBytes(Int i) {
        return this.f70a.skipBytes(i)
    }
}
