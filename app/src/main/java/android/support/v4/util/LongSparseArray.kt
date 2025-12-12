package android.support.v4.util

import android.support.annotation.NonNull
import android.support.annotation.Nullable

class LongSparseArray implements Cloneable {
    private static val DELETED = Object()
    private Boolean mGarbage
    private Array<Long> mKeys
    private Int mSize
    private Array<Object> mValues

    constructor() {
        this(10)
    }

    constructor(Int i) {
        this.mGarbage = false
        if (i == 0) {
            this.mKeys = ContainerHelpers.EMPTY_LONGS
            this.mValues = ContainerHelpers.EMPTY_OBJECTS
        } else {
            Int iIdealLongArraySize = ContainerHelpers.idealLongArraySize(i)
            this.mKeys = new Long[iIdealLongArraySize]
            this.mValues = new Object[iIdealLongArraySize]
        }
        this.mSize = 0
    }

    private fun gc() {
        Int i = this.mSize
        Array<Long> jArr = this.mKeys
        Array<Object> objArr = this.mValues
        Int i2 = 0
        for (Int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3]
            if (obj != DELETED) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3]
                    objArr[i2] = obj
                    objArr[i3] = null
                }
                i2++
            }
        }
        this.mGarbage = false
        this.mSize = i2
    }

    fun append(Long j, Object obj) {
        if (this.mSize != 0 && j <= this.mKeys[this.mSize - 1]) {
            put(j, obj)
            return
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            gc()
        }
        Int i = this.mSize
        if (i >= this.mKeys.length) {
            Int iIdealLongArraySize = ContainerHelpers.idealLongArraySize(i + 1)
            Array<Long> jArr = new Long[iIdealLongArraySize]
            Array<Object> objArr = new Object[iIdealLongArraySize]
            System.arraycopy(this.mKeys, 0, jArr, 0, this.mKeys.length)
            System.arraycopy(this.mValues, 0, objArr, 0, this.mValues.length)
            this.mKeys = jArr
            this.mValues = objArr
        }
        this.mKeys[i] = j
        this.mValues[i] = obj
        this.mSize = i + 1
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
    fun m2clone() {
        try {
            LongSparseArray longSparseArray = (LongSparseArray) super.clone()
            longSparseArray.mKeys = (Array<Long>) this.mKeys.clone()
            longSparseArray.mValues = (Array<Object>) this.mValues.clone()
            return longSparseArray
        } catch (CloneNotSupportedException e) {
            throw AssertionError(e)
        }
    }

    fun containsKey(Long j) {
        return indexOfKey(j) >= 0
    }

    fun containsValue(Object obj) {
        return indexOfValue(obj) >= 0
    }

    fun delete(Long j) {
        Int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j)
        if (iBinarySearch < 0 || this.mValues[iBinarySearch] == DELETED) {
            return
        }
        this.mValues[iBinarySearch] = DELETED
        this.mGarbage = true
    }

    @Nullable
    fun get(Long j) {
        return get(j, null)
    }

    fun get(Long j, Object obj) {
        Int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j)
        return (iBinarySearch < 0 || this.mValues[iBinarySearch] == DELETED) ? obj : this.mValues[iBinarySearch]
    }

    fun indexOfKey(Long j) {
        if (this.mGarbage) {
            gc()
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, j)
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

    fun put(Long j, Object obj) {
        Int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j)
        if (iBinarySearch >= 0) {
            this.mValues[iBinarySearch] = obj
            return
        }
        Int iBinarySearch2 = iBinarySearch ^ (-1)
        if (iBinarySearch2 < this.mSize && this.mValues[iBinarySearch2] == DELETED) {
            this.mKeys[iBinarySearch2] = j
            this.mValues[iBinarySearch2] = obj
            return
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            gc()
            iBinarySearch2 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j) ^ (-1)
        }
        if (this.mSize >= this.mKeys.length) {
            Int iIdealLongArraySize = ContainerHelpers.idealLongArraySize(this.mSize + 1)
            Array<Long> jArr = new Long[iIdealLongArraySize]
            Array<Object> objArr = new Object[iIdealLongArraySize]
            System.arraycopy(this.mKeys, 0, jArr, 0, this.mKeys.length)
            System.arraycopy(this.mValues, 0, objArr, 0, this.mValues.length)
            this.mKeys = jArr
            this.mValues = objArr
        }
        if (this.mSize - iBinarySearch2 != 0) {
            System.arraycopy(this.mKeys, iBinarySearch2, this.mKeys, iBinarySearch2 + 1, this.mSize - iBinarySearch2)
            System.arraycopy(this.mValues, iBinarySearch2, this.mValues, iBinarySearch2 + 1, this.mSize - iBinarySearch2)
        }
        this.mKeys[iBinarySearch2] = j
        this.mValues[iBinarySearch2] = obj
        this.mSize++
    }

    fun putAll(@NonNull LongSparseArray longSparseArray) {
        Int size = longSparseArray.size()
        for (Int i = 0; i < size; i++) {
            put(longSparseArray.keyAt(i), longSparseArray.valueAt(i))
        }
    }

    fun remove(Long j) {
        delete(j)
    }

    fun removeAt(Int i) {
        if (this.mValues[i] != DELETED) {
            this.mValues[i] = DELETED
            this.mGarbage = true
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
