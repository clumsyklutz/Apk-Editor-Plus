package org.jf.util

class SparseIntArray {
    public Array<Int> mKeys
    public Int mSize = 0
    public Array<Int> mValues

    constructor(Int i) {
        this.mKeys = new Int[i]
        this.mValues = new Int[i]
    }

    fun binarySearch(Array<Int> iArr, Int i, Int i2, Int i3) {
        Int i4 = i2 + i
        Int i5 = i - 1
        Int i6 = i4
        while (i6 - i5 > 1) {
            Int i7 = (i6 + i5) / 2
            if (iArr[i7] < i3) {
                i5 = i7
            } else {
                i6 = i7
            }
        }
        return i6 == i4 ? i4 ^ (-1) : iArr[i6] == i3 ? i6 : i6 ^ (-1)
    }

    fun append(Int i, Int i2) {
        Int i3 = this.mSize
        if (i3 != 0 && i <= this.mKeys[i3 - 1]) {
            put(i, i2)
            return
        }
        Array<Int> iArr = this.mKeys
        if (i3 >= iArr.length) {
            Int iMax = Math.max(i3 + 1, iArr.length * 2)
            Array<Int> iArr2 = new Int[iMax]
            Array<Int> iArr3 = new Int[iMax]
            Array<Int> iArr4 = this.mKeys
            System.arraycopy(iArr4, 0, iArr2, 0, iArr4.length)
            Array<Int> iArr5 = this.mValues
            System.arraycopy(iArr5, 0, iArr3, 0, iArr5.length)
            this.mKeys = iArr2
            this.mValues = iArr3
        }
        this.mKeys[i3] = i
        this.mValues[i3] = i2
        this.mSize = i3 + 1
    }

    fun get(Int i, Int i2) {
        Int iBinarySearch = binarySearch(this.mKeys, 0, this.mSize, i)
        return iBinarySearch < 0 ? i2 : this.mValues[iBinarySearch]
    }

    fun put(Int i, Int i2) {
        Int iBinarySearch = binarySearch(this.mKeys, 0, this.mSize, i)
        if (iBinarySearch >= 0) {
            this.mValues[iBinarySearch] = i2
            return
        }
        Int i3 = iBinarySearch ^ (-1)
        Int i4 = this.mSize
        Array<Int> iArr = this.mKeys
        if (i4 >= iArr.length) {
            Int iMax = Math.max(i4 + 1, iArr.length * 2)
            Array<Int> iArr2 = new Int[iMax]
            Array<Int> iArr3 = new Int[iMax]
            Array<Int> iArr4 = this.mKeys
            System.arraycopy(iArr4, 0, iArr2, 0, iArr4.length)
            Array<Int> iArr5 = this.mValues
            System.arraycopy(iArr5, 0, iArr3, 0, iArr5.length)
            this.mKeys = iArr2
            this.mValues = iArr3
        }
        Int i5 = this.mSize
        if (i5 - i3 != 0) {
            Array<Int> iArr6 = this.mKeys
            Int i6 = i3 + 1
            System.arraycopy(iArr6, i3, iArr6, i6, i5 - i3)
            Array<Int> iArr7 = this.mValues
            System.arraycopy(iArr7, i3, iArr7, i6, this.mSize - i3)
        }
        this.mKeys[i3] = i
        this.mValues[i3] = i2
        this.mSize++
    }
}
