package org.jf.dexlib2.dexbacked.raw

import org.jf.dexlib2.dexbacked.DexBuffer

class OdexHeaderItem {
    public static final Array<Byte> MAGIC_VALUE = {100, 101, 121, 10, 0, 0, 0, 0}
    public static final Array<Int> SUPPORTED_ODEX_VERSIONS = {35, 36}

    fun getDexOffset(Array<Byte> bArr) {
        return DexBuffer(bArr).readSmallUint(8)
    }

    fun getVersion(Array<Byte> bArr, Int i) {
        if (verifyMagic(bArr, i)) {
            return getVersionUnchecked(bArr, i)
        }
        return -1
    }

    fun getVersionUnchecked(Array<Byte> bArr, Int i) {
        return ((bArr[i + 4] - 48) * 100) + ((bArr[i + 5] - 48) * 10) + (bArr[i + 6] - 48)
    }

    fun isSupportedOdexVersion(Int i) {
        Int i2 = 0
        while (true) {
            Array<Int> iArr = SUPPORTED_ODEX_VERSIONS
            if (i2 >= iArr.length) {
                return false
            }
            if (iArr[i2] == i) {
                return true
            }
            i2++
        }
    }

    fun verifyMagic(Array<Byte> bArr, Int i) {
        if (bArr.length - i < 8) {
            return false
        }
        Int i2 = 0
        while (true) {
            if (i2 >= 4) {
                for (Int i3 = 4; i3 < 7; i3++) {
                    Int i4 = i + i3
                    if (bArr[i4] < 48 || bArr[i4] > 57) {
                        return false
                    }
                }
                return bArr[i + 7] == MAGIC_VALUE[7]
            }
            if (bArr[i + i2] != MAGIC_VALUE[i2]) {
                return false
            }
            i2++
        }
    }
}
