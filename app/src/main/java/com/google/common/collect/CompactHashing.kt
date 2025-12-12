package com.google.common.collect

import com.google.common.base.Objects
import java.util.Arrays

class CompactHashing {
    fun createTable(Int i) {
        if (i >= 2 && i <= 1073741824 && Integer.highestOneBit(i) == i) {
            return i <= 256 ? new Byte[i] : i <= 65536 ? new Short[i] : new Int[i]
        }
        StringBuilder sb = StringBuilder(52)
        sb.append("must be power of 2 between 2^1 and 2^30: ")
        sb.append(i)
        throw IllegalArgumentException(sb.toString())
    }

    fun getHashPrefix(Int i, Int i2) {
        return i & (i2 ^ (-1))
    }

    fun getNext(Int i, Int i2) {
        return i & i2
    }

    fun maskCombine(Int i, Int i2, Int i3) {
        return (i & (i3 ^ (-1))) | (i2 & i3)
    }

    fun newCapacity(Int i) {
        return (i < 32 ? 4 : 2) * (i + 1)
    }

    fun remove(Object obj, Object obj2, Int i, Object obj3, Array<Int> iArr, Array<Object> objArr, Array<Object> objArr2) {
        Int i2
        Int i3
        Int iSmearedHash = Hashing.smearedHash(obj)
        Int i4 = iSmearedHash & i
        Int iTableGet = tableGet(obj3, i4)
        if (iTableGet == 0) {
            return -1
        }
        Int hashPrefix = getHashPrefix(iSmearedHash, i)
        Int i5 = -1
        while (true) {
            i2 = iTableGet - 1
            i3 = iArr[i2]
            if (getHashPrefix(i3, i) == hashPrefix && Objects.equal(obj, objArr[i2]) && (objArr2 == null || Objects.equal(obj2, objArr2[i2]))) {
                break
            }
            Int next = getNext(i3, i)
            if (next == 0) {
                return -1
            }
            i5 = i2
            iTableGet = next
        }
        Int next2 = getNext(i3, i)
        if (i5 == -1) {
            tableSet(obj3, i4, next2)
        } else {
            iArr[i5] = maskCombine(iArr[i5], next2, i)
        }
        return i2
    }

    fun tableClear(Object obj) {
        if (obj is Array<Byte>) {
            Arrays.fill((Array<Byte>) obj, (Byte) 0)
        } else if (obj is Array<Short>) {
            Arrays.fill((Array<Short>) obj, (Short) 0)
        } else {
            Arrays.fill((Array<Int>) obj, 0)
        }
    }

    fun tableGet(Object obj, Int i) {
        return obj is Array<Byte> ? ((Array<Byte>) obj)[i] & 255 : obj is Array<Short> ? ((Array<Short>) obj)[i] & 65535 : ((Array<Int>) obj)[i]
    }

    fun tableSet(Object obj, Int i, Int i2) {
        if (obj is Array<Byte>) {
            ((Array<Byte>) obj)[i] = (Byte) i2
        } else if (obj is Array<Short>) {
            ((Array<Short>) obj)[i] = (Short) i2
        } else {
            ((Array<Int>) obj)[i] = i2
        }
    }

    fun tableSize(Int i) {
        return Math.max(4, Hashing.closedTableSize(i + 1, 1.0d))
    }
}
