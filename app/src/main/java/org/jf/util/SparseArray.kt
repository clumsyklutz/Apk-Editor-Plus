package org.jf.util

import java.util.Arrays
import java.util.Collections
import java.util.List

class SparseArray<E> {
    public static val DELETED = Object()
    public Array<Int> mKeys
    public Array<Object> mValues
    public Boolean mGarbage = false
    public Int mSize = 0

    constructor(Int i) {
        this.mKeys = new Int[i]
        this.mValues = new Object[i]
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

    fun append(Int i, E e) {
        Int i2 = this.mSize
        if (i2 != 0 && i <= this.mKeys[i2 - 1]) {
            put(i, e)
            return
        }
        if (this.mGarbage && i2 >= this.mKeys.length) {
            gc()
        }
        Int i3 = this.mSize
        Array<Int> iArr = this.mKeys
        if (i3 >= iArr.length) {
            Int iMax = Math.max(i3 + 1, iArr.length * 2)
            Array<Int> iArr2 = new Int[iMax]
            Array<Object> objArr = new Object[iMax]
            Array<Int> iArr3 = this.mKeys
            System.arraycopy(iArr3, 0, iArr2, 0, iArr3.length)
            Array<Object> objArr2 = this.mValues
            System.arraycopy(objArr2, 0, objArr, 0, objArr2.length)
            this.mKeys = iArr2
            this.mValues = objArr
        }
        this.mKeys[i3] = i
        this.mValues[i3] = e
        this.mSize = i3 + 1
    }

    fun ensureCapacity(Int i) {
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            gc()
        }
        Array<Int> iArr = this.mKeys
        if (iArr.length < i) {
            Array<Int> iArr2 = new Int[i]
            Array<Object> objArr = new Object[i]
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length)
            Array<Object> objArr2 = this.mValues
            System.arraycopy(objArr2, 0, objArr, 0, objArr2.length)
            this.mKeys = iArr2
            this.mValues = objArr
        }
    }

    public final Unit gc() {
        Int i = this.mSize
        Array<Int> iArr = this.mKeys
        Array<Object> objArr = this.mValues
        Int i2 = 0
        for (Int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3]
            if (obj != DELETED) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3]
                    objArr[i2] = obj
                }
                i2++
            }
        }
        this.mGarbage = false
        this.mSize = i2
    }

    fun get(Int i) {
        return get(i, null)
    }

    fun get(Int i, E e) {
        Int iBinarySearch = binarySearch(this.mKeys, 0, this.mSize, i)
        if (iBinarySearch >= 0) {
            Array<Object> objArr = this.mValues
            if (objArr[iBinarySearch] != DELETED) {
                return (E) objArr[iBinarySearch]
            }
        }
        return e
    }

    public List<E> getValues() {
        return Collections.unmodifiableList(Arrays.asList(this.mValues))
    }

    fun keyAt(Int i) {
        if (this.mGarbage) {
            gc()
        }
        return this.mKeys[i]
    }

    fun put(Int i, E e) {
        Int iBinarySearch = binarySearch(this.mKeys, 0, this.mSize, i)
        if (iBinarySearch >= 0) {
            this.mValues[iBinarySearch] = e
            return
        }
        Int iBinarySearch2 = iBinarySearch ^ (-1)
        Int i2 = this.mSize
        if (iBinarySearch2 < i2) {
            Array<Object> objArr = this.mValues
            if (objArr[iBinarySearch2] == DELETED) {
                this.mKeys[iBinarySearch2] = i
                objArr[iBinarySearch2] = e
                return
            }
        }
        if (this.mGarbage && i2 >= this.mKeys.length) {
            gc()
            iBinarySearch2 = binarySearch(this.mKeys, 0, this.mSize, i) ^ (-1)
        }
        Int i3 = this.mSize
        Array<Int> iArr = this.mKeys
        if (i3 >= iArr.length) {
            Int iMax = Math.max(i3 + 1, iArr.length * 2)
            Array<Int> iArr2 = new Int[iMax]
            Array<Object> objArr2 = new Object[iMax]
            Array<Int> iArr3 = this.mKeys
            System.arraycopy(iArr3, 0, iArr2, 0, iArr3.length)
            Array<Object> objArr3 = this.mValues
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length)
            this.mKeys = iArr2
            this.mValues = objArr2
        }
        Int i4 = this.mSize
        if (i4 - iBinarySearch2 != 0) {
            Array<Int> iArr4 = this.mKeys
            Int i5 = iBinarySearch2 + 1
            System.arraycopy(iArr4, iBinarySearch2, iArr4, i5, i4 - iBinarySearch2)
            Array<Object> objArr4 = this.mValues
            System.arraycopy(objArr4, iBinarySearch2, objArr4, i5, this.mSize - iBinarySearch2)
        }
        this.mKeys[iBinarySearch2] = i
        this.mValues[iBinarySearch2] = e
        this.mSize++
    }

    fun size() {
        if (this.mGarbage) {
            gc()
        }
        return this.mSize
    }

    fun valueAt(Int i) {
        if (this.mGarbage) {
            gc()
        }
        return (E) this.mValues[i]
    }
}
