package org.jf.dexlib2.dexbacked

import org.jf.util.ExceptionWithContext

class DexBuffer {
    public final Int baseOffset
    public final Array<Byte> buf

    constructor(Array<Byte> bArr) {
        this(bArr, 0)
    }

    constructor(Array<Byte> bArr, Int i) {
        this.buf = bArr
        this.baseOffset = i
    }

    fun getBaseOffset() {
        return this.baseOffset
    }

    fun readByte(Int i) {
        return this.buf[this.baseOffset + i]
    }

    fun readInt(Int i) {
        Array<Byte> bArr = this.buf
        Int i2 = i + this.baseOffset
        return (bArr[i2 + 3] << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16)
    }

    fun readLong(Int i) {
        Array<Byte> bArr = this.buf
        Int i2 = i + this.baseOffset
        return (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16) | ((bArr[i2 + 3] & 255) << 24) | ((bArr[i2 + 4] & 255) << 32) | ((bArr[i2 + 5] & 255) << 40) | ((bArr[i2 + 6] & 255) << 48) | (bArr[i2 + 7] << 56)
    }

    fun readLongAsSmallUint(Int i) {
        Array<Byte> bArr = this.buf
        Int i2 = i + this.baseOffset
        Long j = (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16) | ((bArr[i2 + 3] & 255) << 24) | ((bArr[i2 + 4] & 255) << 32) | ((bArr[i2 + 5] & 255) << 40) | ((bArr[i2 + 6] & 255) << 48) | (bArr[i2 + 7] << 56)
        if (j < 0 || j > 2147483647L) {
            throw ExceptionWithContext("Encountered out-of-range ulong at offset 0x%x", Integer.valueOf(i2))
        }
        return (Int) j
    }

    fun readOptionalUint(Int i) {
        Array<Byte> bArr = this.buf
        Int i2 = i + this.baseOffset
        Int i3 = (bArr[i2 + 3] << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16)
        if (i3 >= -1) {
            return i3
        }
        throw ExceptionWithContext("Encountered optional uint that is out of range at offset 0x%x", Integer.valueOf(i2))
    }

    fun readShort(Int i) {
        Array<Byte> bArr = this.buf
        Int i2 = i + this.baseOffset
        return (bArr[i2 + 1] << 8) | (bArr[i2] & 255)
    }

    fun readSmallUint(Int i) {
        Array<Byte> bArr = this.buf
        Int i2 = i + this.baseOffset
        Int i3 = (bArr[i2 + 3] << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16)
        if (i3 >= 0) {
            return i3
        }
        throw ExceptionWithContext("Encountered small uint that is out of range at offset 0x%x", Integer.valueOf(i2))
    }

    fun readUbyte(Int i) {
        return this.buf[i + this.baseOffset] & 255
    }

    fun readUshort(Int i) {
        Array<Byte> bArr = this.buf
        Int i2 = i + this.baseOffset
        return ((bArr[i2 + 1] & 255) << 8) | (bArr[i2] & 255)
    }

    public DexReader<? extends DexBuffer> readerAt(Int i) {
        return new DexReader<>(this, i)
    }
}
