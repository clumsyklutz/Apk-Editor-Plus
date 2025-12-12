package org.jf.dexlib2.dexbacked

import com.gmail.heagoo.neweditor.Token
import org.jf.dexlib2.dexbacked.DexBuffer
import org.jf.util.ExceptionWithContext
import org.jf.util.Utf8Utils

class DexReader<T extends DexBuffer> {
    public final T dexBuf
    public Int offset

    constructor(T t, Int i) {
        this.dexBuf = t
        this.offset = i
    }

    fun getOffset() {
        return this.offset
    }

    fun moveRelative(Int i) {
        this.offset += i
    }

    fun peekUbyte() {
        return this.dexBuf.readUbyte(this.offset)
    }

    fun peekUshort() {
        return this.dexBuf.readUshort(this.offset)
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x005a A[PHI: r2 r5
  0x005a: PHI (r2v7 Int) = (r2v6 Int), (r2v9 Int) binds: [B:5:0x0020, B:9:0x003a] A[DONT_GENERATE, DONT_INLINE]
  0x005a: PHI (r5v1 Int) = (r5v0 Int), (r5v4 Int) binds: [B:5:0x0020, B:9:0x003a] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun readBigUleb128() {
        /*
            r7 = this
            T extends org.jf.dexlib2.dexbacked.DexBuffer r0 = r7.dexBuf
            Int r1 = r0.baseOffset
            Int r2 = r7.offset
            Int r2 = r2 + r1
            Array<Byte> r0 = r0.buf
            Int r3 = r2 + 1
            r2 = r0[r2]
            r2 = r2 & 255(0xff, Float:3.57E-43)
            r4 = 127(0x7f, Float:1.78E-43)
            if (r2 <= r4) goto L5b
            Int r5 = r3 + 1
            r3 = r0[r3]
            r3 = r3 & 255(0xff, Float:3.57E-43)
            r2 = r2 & 127(0x7f, Float:1.78E-43)
            r6 = r3 & 127(0x7f, Float:1.78E-43)
            Int r6 = r6 << 7
            r2 = r2 | r6
            if (r3 <= r4) goto L5a
            Int r3 = r5 + 1
            r5 = r0[r5]
            r5 = r5 & 255(0xff, Float:3.57E-43)
            r6 = r5 & 127(0x7f, Float:1.78E-43)
            Int r6 = r6 << 14
            r2 = r2 | r6
            if (r5 <= r4) goto L5b
            Int r5 = r3 + 1
            r3 = r0[r3]
            r3 = r3 & 255(0xff, Float:3.57E-43)
            r6 = r3 & 127(0x7f, Float:1.78E-43)
            Int r6 = r6 << 21
            r2 = r2 | r6
            if (r3 <= r4) goto L5a
            Int r3 = r5 + 1
            r0 = r0[r5]
            if (r0 < 0) goto L46
            Int r0 = r0 << 28
            r2 = r2 | r0
            goto L5b
        L46:
            org.jf.util.ExceptionWithContext r0 = new org.jf.util.ExceptionWithContext
            r1 = 1
            java.lang.Array<Object> r1 = new java.lang.Object[r1]
            r2 = 0
            Int r3 = r7.offset
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r1[r2] = r3
            java.lang.String r2 = "Invalid uleb128 integer encountered at offset 0x%x"
            r0.<init>(r2, r1)
            throw r0
        L5a:
            r3 = r5
        L5b:
            Int r3 = r3 - r1
            r7.offset = r3
            return r2
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.dexlib2.dexbacked.DexReader.readBigUleb128():Int")
    }

    fun readByte() {
        Int i = this.offset
        Int i2 = this.dexBuf.readByte(i)
        this.offset = i + 1
        return i2
    }

    fun readLargeUleb128() {
        return readUleb128(true)
    }

    fun readSizedInt(Int i) {
        Int i2
        Int i3
        Int i4
        T t = this.dexBuf
        Int i5 = t.baseOffset
        Int i6 = this.offset + i5
        Array<Byte> bArr = t.buf
        if (i != 1) {
            if (i == 2) {
                i3 = bArr[i6] & 255
                i4 = bArr[i6 + 1] << 8
            } else if (i == 3) {
                i3 = (bArr[i6] & 255) | ((bArr[i6 + 1] & 255) << 8)
                i4 = bArr[i6 + 2] << Token.LITERAL4
            } else {
                if (i != 4) {
                    throw ExceptionWithContext("Invalid size %d for sized Int at offset 0x%x", Integer.valueOf(i), Integer.valueOf(this.offset))
                }
                i3 = (bArr[i6] & 255) | ((bArr[i6 + 1] & 255) << 8) | ((bArr[i6 + 2] & 255) << 16)
                i4 = bArr[i6 + 3] << 24
            }
            i2 = i4 | i3
        } else {
            i2 = bArr[i6]
        }
        this.offset = (i6 + i) - i5
        return i2
    }

    fun readSizedLong(Int i) {
        Int i2
        Int i3
        Int i4
        Long j
        Long j2
        Long j3
        Long j4
        Long j5
        T t = this.dexBuf
        Int i5 = t.baseOffset
        Int i6 = this.offset + i5
        Array<Byte> bArr = t.buf
        switch (i) {
            case 1:
                i2 = bArr[i6]
                j = i2
                this.offset = (i6 + i) - i5
                return j
            case 2:
                i3 = bArr[i6] & 255
                i4 = bArr[i6 + 1] << 8
                i2 = i4 | i3
                j = i2
                this.offset = (i6 + i) - i5
                return j
            case 3:
                i3 = (bArr[i6] & 255) | ((bArr[i6 + 1] & 255) << 8)
                i4 = bArr[i6 + 2] << Token.LITERAL4
                i2 = i4 | i3
                j = i2
                this.offset = (i6 + i) - i5
                return j
            case 4:
                j2 = (bArr[i6] & 255) | ((bArr[i6 + 1] & 255) << 8) | ((bArr[i6 + 2] & 255) << 16)
                j3 = bArr[i6 + 3] << 24
                j = j2 | j3
                this.offset = (i6 + i) - i5
                return j
            case 5:
                j2 = (bArr[i6] & 255) | ((bArr[i6 + 1] & 255) << 8) | ((bArr[i6 + 2] & 255) << 16) | ((255 & bArr[i6 + 3]) << 24)
                j3 = bArr[i6 + 4] << 32
                j = j2 | j3
                this.offset = (i6 + i) - i5
                return j
            case 6:
                j4 = ((255 & bArr[i6 + 4]) << 32) | (bArr[i6] & 255) | ((bArr[i6 + 1] & 255) << 8) | ((bArr[i6 + 2] & 255) << 16) | ((bArr[i6 + 3] & 255) << 24)
                j5 = bArr[i6 + 5] << 40
                j = j5 | j4
                this.offset = (i6 + i) - i5
                return j
            case 7:
                j4 = ((bArr[i6 + 4] & 255) << 32) | (bArr[i6] & 255) | ((bArr[i6 + 1] & 255) << 8) | ((bArr[i6 + 2] & 255) << 16) | ((bArr[i6 + 3] & 255) << 24) | ((255 & bArr[i6 + 5]) << 40)
                j5 = bArr[i6 + 6] << 48
                j = j5 | j4
                this.offset = (i6 + i) - i5
                return j
            case 8:
                j2 = ((255 & bArr[i6 + 6]) << 48) | ((bArr[i6 + 4] & 255) << 32) | (bArr[i6] & 255) | ((bArr[i6 + 1] & 255) << 8) | ((bArr[i6 + 2] & 255) << 16) | ((bArr[i6 + 3] & 255) << 24) | ((bArr[i6 + 5] & 255) << 40)
                j3 = bArr[i6 + 7] << 56
                j = j2 | j3
                this.offset = (i6 + i) - i5
                return j
            default:
                throw ExceptionWithContext("Invalid size %d for sized Long at offset 0x%x", Integer.valueOf(i), Integer.valueOf(this.offset))
        }
    }

    fun readSizedRightExtendedInt(Int i) {
        Int i2
        Int i3
        Byte b2
        T t = this.dexBuf
        Int i4 = t.baseOffset
        Int i5 = this.offset + i4
        Array<Byte> bArr = t.buf
        if (i != 1) {
            if (i == 2) {
                i3 = (bArr[i5] & 255) << 16
                b2 = bArr[i5 + 1]
            } else if (i == 3) {
                i3 = ((bArr[i5] & 255) << 8) | ((bArr[i5 + 1] & 255) << 16)
                b2 = bArr[i5 + 2]
            } else {
                if (i != 4) {
                    throw ExceptionWithContext("Invalid size %d for sized, right extended Int at offset 0x%x", Integer.valueOf(i), Integer.valueOf(this.offset))
                }
                i3 = (bArr[i5] & 255) | ((bArr[i5 + 1] & 255) << 8) | ((bArr[i5 + 2] & 255) << 16)
                b2 = bArr[i5 + 3]
            }
            i2 = (b2 << 24) | i3
        } else {
            i2 = bArr[i5] << 24
        }
        this.offset = (i5 + i) - i4
        return i2
    }

    fun readSizedRightExtendedLong(Int i) {
        Long j
        Long j2
        Byte b2
        T t = this.dexBuf
        Int i2 = t.baseOffset
        Int i3 = this.offset + i2
        Array<Byte> bArr = t.buf
        switch (i) {
            case 1:
                j = bArr[i3] << 56
                this.offset = (i3 + i) - i2
                return j
            case 2:
                j2 = (bArr[i3] & 255) << 48
                b2 = bArr[i3 + 1]
                j = j2 | (b2 << 56)
                this.offset = (i3 + i) - i2
                return j
            case 3:
                j2 = ((bArr[i3] & 255) << 40) | ((255 & bArr[i3 + 1]) << 48)
                b2 = bArr[i3 + 2]
                j = j2 | (b2 << 56)
                this.offset = (i3 + i) - i2
                return j
            case 4:
                j2 = ((bArr[i3] & 255) << 32) | ((bArr[i3 + 1] & 255) << 40) | ((255 & bArr[i3 + 2]) << 48)
                b2 = bArr[i3 + 3]
                j = j2 | (b2 << 56)
                this.offset = (i3 + i) - i2
                return j
            case 5:
                j2 = ((bArr[i3 + 1] & 255) << 32) | ((bArr[i3] & 255) << 24) | ((bArr[i3 + 2] & 255) << 40) | ((255 & bArr[i3 + 3]) << 48)
                b2 = bArr[i3 + 4]
                j = j2 | (b2 << 56)
                this.offset = (i3 + i) - i2
                return j
            case 6:
                j2 = ((bArr[i3 + 2] & 255) << 32) | ((bArr[i3] & 255) << 16) | ((bArr[i3 + 1] & 255) << 24) | ((bArr[i3 + 3] & 255) << 40) | ((255 & bArr[i3 + 4]) << 48)
                b2 = bArr[i3 + 5]
                j = j2 | (b2 << 56)
                this.offset = (i3 + i) - i2
                return j
            case 7:
                j2 = ((bArr[i3 + 3] & 255) << 32) | ((bArr[i3] & 255) << 8) | ((bArr[i3 + 1] & 255) << 16) | ((bArr[i3 + 2] & 255) << 24) | ((bArr[i3 + 4] & 255) << 40) | ((255 & bArr[i3 + 5]) << 48)
                b2 = bArr[i3 + 6]
                j = j2 | (b2 << 56)
                this.offset = (i3 + i) - i2
                return j
            case 8:
                j2 = ((bArr[i3 + 4] & 255) << 32) | (bArr[i3] & 255) | ((bArr[i3 + 1] & 255) << 8) | ((bArr[i3 + 2] & 255) << 16) | ((bArr[i3 + 3] & 255) << 24) | ((bArr[i3 + 5] & 255) << 40) | ((255 & bArr[i3 + 6]) << 48)
                b2 = bArr[i3 + 7]
                j = j2 | (b2 << 56)
                this.offset = (i3 + i) - i2
                return j
            default:
                throw ExceptionWithContext("Invalid size %d for sized, right extended Long at offset 0x%x", Integer.valueOf(i), Integer.valueOf(this.offset))
        }
    }

    fun readSizedSmallUint(Int i) {
        T t = this.dexBuf
        Int i2 = t.baseOffset
        Int i3 = this.offset + i2
        Array<Byte> bArr = t.buf
        Int i4 = 0
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        throw ExceptionWithContext("Invalid size %d for sized uint at offset 0x%x", Integer.valueOf(i), Integer.valueOf(this.offset))
                    }
                    Byte b2 = bArr[i3 + 3]
                    if (b2 < 0) {
                        throw ExceptionWithContext("Encountered valid sized uint that is out of range at offset 0x%x", Integer.valueOf(this.offset))
                    }
                    i4 = b2 << 24
                }
                i4 |= (bArr[i3 + 2] & 255) << 16
            }
            i4 |= (bArr[i3 + 1] & 255) << 8
        }
        Int i5 = (bArr[i3] & 255) | i4
        this.offset = (i3 + i) - i2
        return i5
    }

    fun readSleb128() {
        Int i
        T t = this.dexBuf
        Int i2 = t.baseOffset
        Int i3 = this.offset + i2
        Array<Byte> bArr = t.buf
        Int i4 = i3 + 1
        Int i5 = bArr[i3] & 255
        if (i5 <= 127) {
            i = (i5 << 25) >> 25
        } else {
            Int i6 = i4 + 1
            Int i7 = bArr[i4] & 255
            Int i8 = (i5 & 127) | ((i7 & 127) << 7)
            if (i7 <= 127) {
                i = (i8 << 18) >> 18
            } else {
                i4 = i6 + 1
                Int i9 = bArr[i6] & 255
                Int i10 = i8 | ((i9 & 127) << 14)
                if (i9 <= 127) {
                    i = (i10 << 11) >> 11
                } else {
                    i6 = i4 + 1
                    Int i11 = bArr[i4] & 255
                    Int i12 = i10 | ((i11 & 127) << 21)
                    if (i11 <= 127) {
                        i = (i12 << 4) >> 4
                    } else {
                        i4 = i6 + 1
                        Int i13 = bArr[i6] & 255
                        if (i13 > 127) {
                            throw ExceptionWithContext("Invalid sleb128 integer encountered at offset 0x%x", Integer.valueOf(this.offset))
                        }
                        i = (i13 << 28) | i12
                    }
                }
            }
            i4 = i6
        }
        this.offset = i4 - i2
        return i
    }

    fun readSmallUleb128() {
        return readUleb128(false)
    }

    fun readString(Int i) {
        Array<Int> iArr = new Int[1]
        T t = this.dexBuf
        String strUtf8BytesWithUtf16LengthToString = Utf8Utils.utf8BytesWithUtf16LengthToString(t.buf, t.baseOffset + this.offset, i, iArr)
        this.offset += iArr[0]
        return strUtf8BytesWithUtf16LengthToString
    }

    fun readUbyte() {
        Int i = this.offset
        Int ubyte = this.dexBuf.readUbyte(i)
        this.offset = i + 1
        return ubyte
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0073 A[PHI: r2 r5
  0x0073: PHI (r2v7 Int) = (r2v6 Int), (r2v9 Int) binds: [B:5:0x0020, B:9:0x003a] A[DONT_GENERATE, DONT_INLINE]
  0x0073: PHI (r5v1 Int) = (r5v0 Int), (r5v4 Int) binds: [B:5:0x0020, B:9:0x003a] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Int readUleb128(Boolean r9) {
        /*
            r8 = this
            T extends org.jf.dexlib2.dexbacked.DexBuffer r0 = r8.dexBuf
            Int r1 = r0.baseOffset
            Int r2 = r8.offset
            Int r2 = r2 + r1
            Array<Byte> r0 = r0.buf
            Int r3 = r2 + 1
            r2 = r0[r2]
            r2 = r2 & 255(0xff, Float:3.57E-43)
            r4 = 127(0x7f, Float:1.78E-43)
            if (r2 <= r4) goto L74
            Int r5 = r3 + 1
            r3 = r0[r3]
            r3 = r3 & 255(0xff, Float:3.57E-43)
            r2 = r2 & 127(0x7f, Float:1.78E-43)
            r6 = r3 & 127(0x7f, Float:1.78E-43)
            r7 = 7
            Int r6 = r6 << r7
            r2 = r2 | r6
            if (r3 <= r4) goto L73
            Int r3 = r5 + 1
            r5 = r0[r5]
            r5 = r5 & 255(0xff, Float:3.57E-43)
            r6 = r5 & 127(0x7f, Float:1.78E-43)
            Int r6 = r6 << 14
            r2 = r2 | r6
            if (r5 <= r4) goto L74
            Int r5 = r3 + 1
            r3 = r0[r3]
            r3 = r3 & 255(0xff, Float:3.57E-43)
            r6 = r3 & 127(0x7f, Float:1.78E-43)
            Int r6 = r6 << 21
            r2 = r2 | r6
            if (r3 <= r4) goto L73
            Int r3 = r5 + 1
            r0 = r0[r5]
            r4 = 0
            r5 = 1
            if (r0 < 0) goto L61
            r6 = r0 & 15
            if (r6 <= r7) goto L5d
            if (r9 == 0) goto L4b
            goto L5d
        L4b:
            org.jf.util.ExceptionWithContext r9 = new org.jf.util.ExceptionWithContext
            java.lang.Array<Object> r0 = new java.lang.Object[r5]
            Int r1 = r8.offset
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r0[r4] = r1
            java.lang.String r1 = "Encountered valid uleb128 that is out of range at offset 0x%x"
            r9.<init>(r1, r0)
            throw r9
        L5d:
            Int r9 = r0 << 28
            r2 = r2 | r9
            goto L74
        L61:
            org.jf.util.ExceptionWithContext r9 = new org.jf.util.ExceptionWithContext
            java.lang.Array<Object> r0 = new java.lang.Object[r5]
            Int r1 = r8.offset
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r0[r4] = r1
            java.lang.String r1 = "Invalid uleb128 integer encountered at offset 0x%x"
            r9.<init>(r1, r0)
            throw r9
        L73:
            r3 = r5
        L74:
            Int r3 = r3 - r1
            r8.offset = r3
            return r2
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.dexlib2.dexbacked.DexReader.readUleb128(Boolean):Int")
    }

    fun setOffset(Int i) {
        this.offset = i
    }

    fun skipByte() {
        this.offset++
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003c A[PHI: r2
  0x003c: PHI (r2v4 Int) = (r2v3 Int), (r2v6 Int) binds: [B:5:0x0013, B:9:0x001f] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun skipUleb128() {
        /*
            r4 = this
            T extends org.jf.dexlib2.dexbacked.DexBuffer r0 = r4.dexBuf
            Int r1 = r0.baseOffset
            Int r2 = r4.offset
            Int r2 = r2 + r1
            Array<Byte> r0 = r0.buf
            Int r3 = r2 + 1
            r2 = r0[r2]
            if (r2 >= 0) goto L3d
            Int r2 = r3 + 1
            r3 = r0[r3]
            if (r3 >= 0) goto L3c
            Int r3 = r2 + 1
            r2 = r0[r2]
            if (r2 >= 0) goto L3d
            Int r2 = r3 + 1
            r3 = r0[r3]
            if (r3 >= 0) goto L3c
            Int r3 = r2 + 1
            r0 = r0[r2]
            if (r0 < 0) goto L28
            goto L3d
        L28:
            org.jf.util.ExceptionWithContext r0 = new org.jf.util.ExceptionWithContext
            r1 = 1
            java.lang.Array<Object> r1 = new java.lang.Object[r1]
            r2 = 0
            Int r3 = r4.offset
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r1[r2] = r3
            java.lang.String r2 = "Invalid uleb128 integer encountered at offset 0x%x"
            r0.<init>(r2, r1)
            throw r0
        L3c:
            r3 = r2
        L3d:
            Int r3 = r3 - r1
            r4.offset = r3
            return
        */
        throw UnsupportedOperationException("Method not decompiled: org.jf.dexlib2.dexbacked.DexReader.skipUleb128():Unit")
    }
}
