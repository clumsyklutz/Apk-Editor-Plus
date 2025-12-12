package org.jf.dexlib2.dexbacked.raw

import org.jf.dexlib2.VersionMap
import org.jf.dexlib2.dexbacked.DexBuffer

class HeaderItem {
    public static final Array<Byte> MAGIC_VALUE = {100, 101, 120, 10, 0, 0, 0, 0}

    fun getEndian(Array<Byte> bArr, Int i) {
        return DexBuffer(bArr).readInt(i + 40)
    }

    public static Array<Byte> getMagicForApi(Int i) {
        return getMagicForDexVersion(VersionMap.mapApiToDexVersion(i))
    }

    public static Array<Byte> getMagicForDexVersion(Int i) {
        Array<Byte> bArr = (Array<Byte>) MAGIC_VALUE.clone()
        if (i < 0 || i > 999) {
            throw IllegalArgumentException("dexVersion must be within [0, 999]")
        }
        for (Int i2 = 6; i2 >= 4; i2--) {
            bArr[i2] = (Byte) ((i % 10) + 48)
            i /= 10
        }
        return bArr
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

    fun isSupportedDexVersion(Int i) {
        return VersionMap.mapDexVersionToApi(i) != -1
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
