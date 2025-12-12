package android.support.v4.util

import android.support.annotation.NonNull
import android.support.annotation.Nullable

class SparseArrayCompat implements Cloneable {
    private static val DELETED = Object()
    private Boolean mGarbage
    private Array<Int> mKeys
    private Int mSize
    private Array<Object> mValues

    constructor() {
        this(10)
    }

    constructor(Int i) {
        this.mGarbage = false
        if (i == 0) {
            this.mKeys = ContainerHelpers.EMPTY_INTS
            this.mValues = ContainerHelpers.EMPTY_OBJECTS
        } else {
            Int iIdealIntArraySize = ContainerHelpers.idealIntArraySize(i)
            this.mKeys = new Int[iIdealIntArraySize]
            this.mValues = new Object[iIdealIntArraySize]
        }
        this.mSize = 0
    }

    private fun gc() {
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
                    objArr[i3] = null
                }
                i2++
            }
        }
        this.mGarbage = false
        this.mSize = i2
    }

    fun append(Int i, Object obj) {
        if (this.mSize != 0 && i <= this.mKeys[this.mSize - 1]) {
            put(i, obj)
            return
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            gc()
        }
        Int i2 = this.mSize
        if (i2 >= this.mKeys.length) {
            Int iIdealIntArraySize = ContainerHelpers.idealIntArraySize(i2 + 1)
            Array<Int> iArr = new Int[iIdealIntArraySize]
            Array<Object> objArr = new Object[iIdealIntArraySize]
            System.arraycopy(this.mKeys, 0, iArr, 0, this.mKeys.length)
            System.arraycopy(this.mValues, 0, objArr, 0, this.mValues.length)
            this.mKeys = iArr
            this.mValues = objArr
        }
        this.mKeys[i2] = i
        this.mValues[i2] = obj
        this.mSize = i2 + 1
    }

    fun clear() {
        Int i = this.mSize
        Array<Object> objArr = this.mValues
        for (Int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null
        }
        this.mSize = 0
        this.mGarbage = false
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    fun m3clone() {
        try {
            SparseArrayCompat sparseArrayCompat = (SparseArrayCompat) super.clone()
            sparseArrayCompat.mKeys = (Array<Int>) this.mKeys.clone()
            sparseArrayCompat.mValues = (Array<Object>) this.mValues.clone()
            return sparseArrayCompat
        } catch (CloneNotSupportedException e) {
            throw AssertionError(e)
        }
    }

    fun containsKey(Int i) {
        return indexOfKey(i) >= 0
    }

    fun containsValue(Object obj) {
        return indexOfValue(obj) >= 0
    }

    fun delete(Int i) {
        Int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i)
        if (iBinarySearch < 0 || this.mValues[iBinarySearch] == DELETED) {
            return
        }
        this.mValues[iBinarySearch] = DELETED
        this.mGarbage = true
    }

    @Nullable
    fun get(Int i) {
        return get(i, null)
    }

    fun get(Int i, Object obj) {
        Int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i)
        return (iBinarySearch < 0 || this.mValues[iBinarySearch] == DELETED) ? obj : this.mValues[iBinarySearch]
    }

    fun indexOfKey(Int i) {
        if (this.mGarbage) {
            gc()
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, i)
    }

    fun indexOfValue(Object obj) {
        if (this.mGarbage) {
            gc()
        }
        for (Int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == obj) {
                return i
            }
        }
        return -1
    }

    fun isEmpty() {
        return size() == 0
    }

    fun keyAt(Int i) {
        if (this.mGarbage) {
            gc()
        }
        return this.mKeys[i]
    }

    fun put(Int i, Object obj) {
        Int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i)
        if (iBinarySearch >= 0) {
            this.mValues[iBinarySearch] = obj
            return
        }
        Int iBinarySearch2 = iBinarySearch ^ (-1)
        if (iBinarySearch2 < this.mSize && this.mValues[iBinarySearch2] == DELETED) {
            this.mKeys[iBinarySearch2] = i
            this.mValues[iBinarySearch2] = obj
            return
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            gc()
            iBinarySearch2 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i) ^ (-1)
        }
        if (this.mSize >= this.mKeys.length) {
            Int iIdealIntArraySize = ContainerHelpers.idealIntArraySize(this.mSize + 1)
            Array<Int> iArr = new Int[iIdealIntArraySize]
            Array<Object> objArr = new Object[iIdealIntArraySize]
            System.arraycopy(this.mKeys, 0, iArr, 0, this.mKeys.length)
            System.arraycopy(this.mValues, 0, objArr, 0, this.mValues.length)
            this.mKeys = iArr
            this.mValues = objArr
        }
        if (this.mSize - iBinarySearch2 != 0) {
            System.arraycopy(this.mKeys, iBinarySearch2, this.mKeys, iBinarySearch2 + 1, this.mSize - iBinarySearch2)
            System.arraycopy(this.mValues, iBinarySearch2, this.mValues, iBinarySearch2 + 1, this.mSize - iBinarySearch2)
        }
        this.mKeys[iBinarySearch2] = i
        this.mValues[iBinarySearch2] = obj
        this.mSize++
    }

    fun putAll(@NonNull SparseArrayCompat sparseArrayCompat) {
        Int size = sparseArrayCompat.size()
        for (Int i = 0; i < size; i++) {
            put(sparseArrayCompat.keyAt(i), sparseArrayCompat.valueAt(i))
        }
    }

    fun remove(Int i) {
        delete(i)
    }

    fun removeAt(Int i) {
        if (this.mValues[i] != DELETED) {
            this.mValues[i] = DELETED
            this.mGarbage = true
        }
    }

    fun removeAtRange(Int i, Int i2) {
        Int iMin = Math.min(this.mSize, i + i2)
        while (i < iMin) {
            removeAt(i)
            i++
        }
    }

    fun setValueAt(Int i, Object obj) {
        if (this.mGarbage) {
            gc()
        }
        this.mValues[i] = obj
    }

    fun size() {
        if (this.mGarbage) {
            gc()
        }
        return this.mSize
    }

    fun toString() {
        if (size() <= 0) {
            return "{}"
        }
        StringBuilder sb = StringBuilder(this.mSize * 28)
        sb.append('{')
        for (Int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ")
            }
            sb.append(keyAt(i))
            sb.append('=')
            Object objValueAt = valueAt(i)
            if (objValueAt != this) {
                sb.append(objValueAt)
            } else {
                sb.append("(this Map)")
            }
        }
        sb.append('}')
        return sb.toString()
    }

    fun valueAt(Int i) {
        if (this.mGarbage) {
            gc()
        }
        return this.mValues[i]
    }
}
