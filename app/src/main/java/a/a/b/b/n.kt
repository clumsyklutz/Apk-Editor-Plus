package a.a.b.b

import android.util.SparseArray
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.charset.CharacterCodingException
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder
import java.util.Arrays

class n {
    private Array<Int> c
    private Array<Byte> d
    private Array<Int> e
    private Array<Int> f
    private Boolean g
    private Array<Int> h

    /* renamed from: a, reason: collision with root package name */
    private SparseArray f58a = SparseArray()

    /* renamed from: b, reason: collision with root package name */
    private Boolean f59b = false
    private val i = Charset.forName("UTF-16LE").newDecoder()
    private val j = Charset.forName("UTF-8").newDecoder()
    private val k = Charset.forName("CESU8").newDecoder()

    private constructor() {
    }

    fun a(a.d.f fVar) throws IOException {
        fVar.a(1835009, 0)
        Int i = fVar.readInt()
        Int i2 = fVar.readInt()
        Int i3 = fVar.readInt()
        Int i4 = fVar.readInt()
        Int i5 = fVar.readInt()
        Int i6 = fVar.readInt()
        n nVar = n()
        nVar.g = (i4 & 256) != 0
        nVar.c = fVar.a(i2)
        nVar.h = new Int[i2]
        for (Int i7 = 0; i7 < i2; i7++) {
            nVar.h[i7] = -1
        }
        if (i3 != 0) {
            nVar.e = fVar.a(i3)
        }
        nVar.d = new Byte[(i6 == 0 ? i : i6) - i5]
        fVar.readFully(nVar.d)
        if (i6 != 0) {
            Int i8 = i - i6
            nVar.f = fVar.a(i8 / 4)
            Int i9 = i8 % 4
            if (i9 > 0) {
                while (true) {
                    Int i10 = i9 - 1
                    if (i9 <= 0) {
                        break
                    }
                    fVar.readByte()
                    i9 = i10
                }
            }
        }
        return nVar
    }

    private fun a(Int i, Int i2) {
        Int i3
        try {
            return (this.g ? this.j : this.i).decode(ByteBuffer.wrap(this.d, i, i2)).toString()
        } catch (CharacterCodingException e) {
            if (!this.g) {
                return "DCD"
            }
            try {
                Array<Byte> bArrCopyOfRange = Arrays.copyOfRange(this.d, i, i + i2)
                Array<Byte> bArr = new Byte[i2]
                StringBuilder sb = StringBuilder()
                Int i4 = 0
                Int i5 = 0
                while (i4 < i2) {
                    if (bArrCopyOfRange[i4] != -19 || bArrCopyOfRange[i4 + 3] != -19 || bArrCopyOfRange[i4 + 1] < -96 || bArrCopyOfRange[i4 + 1] > -81 || bArrCopyOfRange[i4 + 4] < -80 || bArrCopyOfRange[i4 + 4] > -65) {
                        i3 = i5 + 1
                        bArr[i5] = bArrCopyOfRange[i4]
                    } else {
                        if (bArr[0] != 0) {
                            Array<Byte> bArrTrim = trim(bArr)
                            sb.append(this.j.decode(ByteBuffer.wrap(bArrTrim, 0, bArrTrim.length)).toString())
                        }
                        sb.append(this.k.decode(ByteBuffer.wrap(bArrCopyOfRange, i4, 6)).toString())
                        i4 += 5
                        Arrays.fill(bArr, (Byte) 0)
                        i3 = 0
                    }
                    i4++
                    i5 = i3
                }
                if (bArr[0] != 0) {
                    Array<Byte> bArrTrim2 = trim(bArr)
                    sb.append(this.j.decode(ByteBuffer.wrap(bArrTrim2, 0, bArrTrim2.length)).toString())
                }
                return sb.toString()
            } catch (CharacterCodingException e2) {
                return "CSU"
            }
        }
    }

    private fun a(String str, Int i, Int i2) {
        return i < str.length() ? i2 <= str.length() ? str.substring(i, i2) : str.substring(i) : ""
    }

    private fun a(String str, StringBuilder sb, Boolean z) {
        String strSubstring
        sb.append('<')
        if (z) {
            sb.append('/')
        }
        Int iIndexOf = str.indexOf(59)
        if (iIndexOf == -1) {
            sb.append(str)
        } else {
            sb.append(str.substring(0, iIndexOf))
            if (!z) {
                Boolean z2 = true
                while (z2) {
                    Int iIndexOf2 = str.indexOf(61, iIndexOf + 1)
                    if (iIndexOf2 != -1) {
                        sb.append(' ').append(str.substring(iIndexOf + 1, iIndexOf2)).append("=\"")
                        Int iIndexOf3 = str.indexOf(59, iIndexOf2 + 1)
                        if (iIndexOf3 != -1) {
                            strSubstring = str.substring(iIndexOf2 + 1, iIndexOf3)
                        } else {
                            strSubstring = str.substring(iIndexOf2 + 1)
                            z2 = false
                        }
                        sb.append(com.gmail.heagoo.a.c.a.a(strSubstring)).append('\"')
                        iIndexOf = iIndexOf3
                    } else {
                        z2 = false
                    }
                }
            }
        }
        sb.append('>')
    }

    public static final Array<Int> a(Array<Byte> bArr, Int i) {
        Int i2 = (bArr[i] & 128) != 0 ? i + 2 : i + 1
        Int i3 = bArr[i2]
        Int i4 = i2 + 1
        if ((i3 & 128) != 0) {
            i3 = ((i3 & 127) << 8) + (bArr[i4] & 255)
            i4++
        }
        return new Array<Int>{i4, i3}
    }

    public static final Array<Int> b(Array<Byte> bArr, Int i) {
        Int i2 = (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8)
        return (32768 & i2) != 0 ? new Array<Int>{4, (((i2 & 32767) << 16) + (((bArr[i + 3] & 255) << 8) + (bArr[i + 2] & 255))) << 1} : new Array<Int>{2, i2 << 1}
    }

    private static final Int c(Array<Byte> bArr, Int i) {
        return ((bArr[i + 1] & 255) << 8) | (bArr[i] & 255)
    }

    private static Array<Byte> trim(Array<Byte> bArr) {
        Int length = bArr.length - 1
        while (length >= 0 && bArr[length] == 0) {
            length--
        }
        return Arrays.copyOf(bArr, length + 1)
    }

    public final Int a() {
        if (this.c != null) {
            return this.c.length
        }
        return 0
    }

    public final Int a(String str) {
        if (str == null) {
            return -1
        }
        for (Int i = 0; i != this.c.length; i++) {
            Int i2 = this.c[i]
            Int iC = c(this.d, i2)
            if (iC == str.length()) {
                Int i3 = i2
                Int i4 = 0
                while (i4 != iC) {
                    i3 += 2
                    if (str.charAt(i4) != c(this.d, i3)) {
                        break
                    }
                    i4++
                }
                if (i4 == iC) {
                    return i
                }
            }
        }
        return -1
    }

    public final String a(Int i) {
        Int i2
        Int i3
        String str = (String) this.f58a.get(i)
        if (str != null) {
            return str
        }
        if (i < 0 || this.c == null || i >= this.c.length) {
            return null
        }
        Int i4 = this.c[i]
        if (this.g) {
            Array<Int> iArrA = a(this.d, i4)
            i2 = iArrA[0]
            i3 = iArrA[1]
        } else {
            Array<Int> iArrB = b(this.d, i4)
            i2 = iArrB[0] + i4
            i3 = iArrB[1]
        }
        return a(i2, i3)
    }

    public final Unit a(Int i, String str) {
        this.f58a.put(i, str)
    }

    public final String b(Int i, String str) {
        Array<Int> iArr
        if (str == null) {
            return str
        }
        if (this.e == null || this.f == null || i >= this.e.length) {
            iArr = null
        } else {
            Int i2 = this.e[i] / 4
            Int i3 = 0
            for (Int i4 = i2; i4 < this.f.length && this.f[i4] != -1; i4++) {
                i3++
            }
            if (i3 == 0 || i3 % 3 != 0) {
                iArr = null
            } else {
                iArr = new Int[i3]
                Int i5 = 0
                for (Int i6 = i2; i6 < this.f.length && this.f[i6] != -1; i6++) {
                    iArr[i5] = this.f[i6]
                    i5++
                }
            }
        }
        if (iArr == null) {
            this.f59b = false
            return com.gmail.heagoo.a.c.a.a(str)
        }
        this.f59b = true
        if (iArr[1] > str.length()) {
            return com.gmail.heagoo.a.c.a.a(str)
        }
        StringBuilder sb = StringBuilder(str.length() + 32)
        Array<Int> iArr2 = new Int[iArr.length / 3]
        Array<Boolean> zArr = new Boolean[iArr.length / 3]
        Int i7 = 0
        Int i8 = 0
        while (true) {
            Int i9 = -1
            for (Int i10 = 0; i10 != iArr.length; i10 += 3) {
                if (iArr[i10 + 1] != -1 && (i9 == -1 || iArr[i9 + 1] > iArr[i10 + 1])) {
                    i9 = i10
                }
            }
            Int length = i9 != -1 ? iArr[i9 + 1] : str.length()
            Int i11 = i8 - 1
            Int i12 = i7
            Int i13 = i11
            while (true) {
                if (i13 < 0) {
                    break
                }
                Int i14 = iArr2[i13]
                Int i15 = iArr[i14 + 2]
                if (i15 < length) {
                    if (i12 <= i15) {
                        sb.append(com.gmail.heagoo.a.c.a.a(a(str, i12, i15 + 1)))
                        i12 = i15 + 1
                    }
                    a(a(iArr[i14]), sb, true)
                    i13--
                } else if (iArr[i14 + 1] == -1 && i15 != -1) {
                    zArr[i13] = true
                }
            }
            Int i16 = i13 + 1
            if (i12 < length) {
                sb.append(com.gmail.heagoo.a.c.a.a(a(str, i12, length)))
                if (i13 >= 0 && zArr.length >= i13 && zArr[i13] && ((zArr.length > i13 + 1 && zArr[i13 + 1]) || zArr.length == 1)) {
                    a(a(iArr[iArr2[i13]]), sb, true)
                }
                i7 = length
            } else {
                i7 = i12
            }
            if (i9 == -1) {
                return sb.toString()
            }
            a(a(iArr[i9]), sb, false)
            iArr[i9 + 1] = -1
            i8 = i16 + 1
            iArr2[i16] = i9
        }
    }

    public final Boolean b() {
        return this.f59b
    }
}
