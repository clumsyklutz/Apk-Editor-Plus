package android.support.v4.util

import android.support.annotation.NonNull
import android.support.annotation.Nullable
import java.util.ConcurrentModificationException
import java.util.Map

class SimpleArrayMap {
    private static val BASE_SIZE = 4
    private static val CACHE_SIZE = 10
    private static val CONCURRENT_MODIFICATION_EXCEPTIONS = true
    private static val DEBUG = false
    private static val TAG = "ArrayMap"

    @Nullable
    static Array<Object> mBaseCache
    static Int mBaseCacheSize

    @Nullable
    static Array<Object> mTwiceBaseCache
    static Int mTwiceBaseCacheSize
    Array<Object> mArray
    Array<Int> mHashes
    Int mSize

    constructor() {
        this.mHashes = ContainerHelpers.EMPTY_INTS
        this.mArray = ContainerHelpers.EMPTY_OBJECTS
        this.mSize = 0
    }

    constructor(Int i) {
        if (i == 0) {
            this.mHashes = ContainerHelpers.EMPTY_INTS
            this.mArray = ContainerHelpers.EMPTY_OBJECTS
        } else {
            allocArrays(i)
        }
        this.mSize = 0
    }

    constructor(SimpleArrayMap simpleArrayMap) {
        this()
        if (simpleArrayMap != null) {
            putAll(simpleArrayMap)
        }
    }

    private fun allocArrays(Int i) {
        if (i == 8) {
            synchronized (ArrayMap.class) {
                if (mTwiceBaseCache != null) {
                    Array<Object> objArr = mTwiceBaseCache
                    this.mArray = objArr
                    mTwiceBaseCache = (Array<Object>) objArr[0]
                    this.mHashes = (Array<Int>) objArr[1]
                    objArr[1] = null
                    objArr[0] = null
                    mTwiceBaseCacheSize--
                    return
                }
            }
        } else if (i == 4) {
            synchronized (ArrayMap.class) {
                if (mBaseCache != null) {
                    Array<Object> objArr2 = mBaseCache
                    this.mArray = objArr2
                    mBaseCache = (Array<Object>) objArr2[0]
                    this.mHashes = (Array<Int>) objArr2[1]
                    objArr2[1] = null
                    objArr2[0] = null
                    mBaseCacheSize--
                    return
                }
            }
        }
        this.mHashes = new Int[i]
        this.mArray = new Object[i << 1]
    }

    private fun binarySearchHashes(Array<Int> iArr, Int i, Int i2) {
        try {
            return ContainerHelpers.binarySearch(iArr, i, i2)
        } catch (ArrayIndexOutOfBoundsException e) {
            throw ConcurrentModificationException()
        }
    }

    private fun freeArrays(Array<Int> iArr, Array<Object> objArr, Int i) {
        if (iArr.length == 8) {
            synchronized (ArrayMap.class) {
                if (mTwiceBaseCacheSize < 10) {
                    objArr[0] = mTwiceBaseCache
                    objArr[1] = iArr
                    for (Int i2 = (i << 1) - 1; i2 >= 2; i2--) {
                        objArr[i2] = null
                    }
                    mTwiceBaseCache = objArr
                    mTwiceBaseCacheSize++
                }
            }
            return
        }
        if (iArr.length == 4) {
            synchronized (ArrayMap.class) {
                if (mBaseCacheSize < 10) {
                    objArr[0] = mBaseCache
                    objArr[1] = iArr
                    for (Int i3 = (i << 1) - 1; i3 >= 2; i3--) {
                        objArr[i3] = null
                    }
                    mBaseCache = objArr
                    mBaseCacheSize++
                }
            }
        }
    }

    fun clear() {
        if (this.mSize > 0) {
            Array<Int> iArr = this.mHashes
            Array<Object> objArr = this.mArray
            Int i = this.mSize
            this.mHashes = ContainerHelpers.EMPTY_INTS
            this.mArray = ContainerHelpers.EMPTY_OBJECTS
            this.mSize = 0
            freeArrays(iArr, objArr, i)
        }
        if (this.mSize > 0) {
            throw ConcurrentModificationException()
        }
    }

    fun containsKey(@Nullable Object obj) {
        if (indexOfKey(obj) >= 0) {
            return CONCURRENT_MODIFICATION_EXCEPTIONS
        }
        return false
    }

    fun containsValue(Object obj) {
        if (indexOfValue(obj) >= 0) {
            return CONCURRENT_MODIFICATION_EXCEPTIONS
        }
        return false
    }

    fun ensureCapacity(Int i) {
        Int i2 = this.mSize
        if (this.mHashes.length < i) {
            Array<Int> iArr = this.mHashes
            Array<Object> objArr = this.mArray
            allocArrays(i)
            if (this.mSize > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, i2)
                System.arraycopy(objArr, 0, this.mArray, 0, i2 << 1)
            }
            freeArrays(iArr, objArr, i2)
        }
        if (this.mSize != i2) {
            throw ConcurrentModificationException()
        }
    }

    fun equals(Object obj) {
        if (this == obj) {
            return CONCURRENT_MODIFICATION_EXCEPTIONS
        }
        if (obj is SimpleArrayMap) {
            SimpleArrayMap simpleArrayMap = (SimpleArrayMap) obj
            if (size() != simpleArrayMap.size()) {
                return false
            }
            for (Int i = 0; i < this.mSize; i++) {
                try {
                    Object objKeyAt = keyAt(i)
                    Object objValueAt = valueAt(i)
                    Object obj2 = simpleArrayMap.get(objKeyAt)
                    if (objValueAt == null) {
                        if (obj2 != null || !simpleArrayMap.containsKey(objKeyAt)) {
                            return false
                        }
                    } else if (!objValueAt.equals(obj2)) {
                        return false
                    }
                } catch (ClassCastException e) {
                    return false
                } catch (NullPointerException e2) {
                    return false
                }
            }
            return CONCURRENT_MODIFICATION_EXCEPTIONS
        }
        if (!(obj is Map)) {
            return false
        }
        Map map = (Map) obj
        if (size() != map.size()) {
            return false
        }
        for (Int i2 = 0; i2 < this.mSize; i2++) {
            try {
                Object objKeyAt2 = keyAt(i2)
                Object objValueAt2 = valueAt(i2)
                Object obj3 = map.get(objKeyAt2)
                if (objValueAt2 == null) {
                    if (obj3 != null || !map.containsKey(objKeyAt2)) {
                        return false
                    }
                } else if (!objValueAt2.equals(obj3)) {
                    return false
                }
            } catch (ClassCastException e3) {
                return false
            } catch (NullPointerException e4) {
                return false
            }
        }
        return CONCURRENT_MODIFICATION_EXCEPTIONS
    }

    @Nullable
    fun get(Object obj) {
        Int iIndexOfKey = indexOfKey(obj)
        if (iIndexOfKey >= 0) {
            return this.mArray[(iIndexOfKey << 1) + 1]
        }
        return null
    }

    fun hashCode() {
        Array<Int> iArr = this.mHashes
        Array<Object> objArr = this.mArray
        Int i = this.mSize
        Int i2 = 1
        Int i3 = 0
        Int iHashCode = 0
        while (i3 < i) {
            Object obj = objArr[i2]
            iHashCode += (obj == null ? 0 : obj.hashCode()) ^ iArr[i3]
            i3++
            i2 += 2
        }
        return iHashCode
    }

    Int indexOf(Object obj, Int i) {
        Int i2 = this.mSize
        if (i2 == 0) {
            return -1
        }
        Int iBinarySearchHashes = binarySearchHashes(this.mHashes, i2, i)
        if (iBinarySearchHashes < 0 || obj.equals(this.mArray[iBinarySearchHashes << 1])) {
            return iBinarySearchHashes
        }
        Int i3 = iBinarySearchHashes + 1
        while (i3 < i2 && this.mHashes[i3] == i) {
            if (obj.equals(this.mArray[i3 << 1])) {
                return i3
            }
            i3++
        }
        for (Int i4 = iBinarySearchHashes - 1; i4 >= 0 && this.mHashes[i4] == i; i4--) {
            if (obj.equals(this.mArray[i4 << 1])) {
                return i4
            }
        }
        return i3 ^ (-1)
    }

    fun indexOfKey(@Nullable Object obj) {
        return obj == null ? indexOfNull() : indexOf(obj, obj.hashCode())
    }

    Int indexOfNull() {
        Int i = this.mSize
        if (i == 0) {
            return -1
        }
        Int iBinarySearchHashes = binarySearchHashes(this.mHashes, i, 0)
        if (iBinarySearchHashes < 0 || this.mArray[iBinarySearchHashes << 1] == null) {
            return iBinarySearchHashes
        }
        Int i2 = iBinarySearchHashes + 1
        while (i2 < i && this.mHashes[i2] == 0) {
            if (this.mArray[i2 << 1] == null) {
                return i2
            }
            i2++
        }
        for (Int i3 = iBinarySearchHashes - 1; i3 >= 0 && this.mHashes[i3] == 0; i3--) {
            if (this.mArray[i3 << 1] == null) {
                return i3
            }
        }
        return i2 ^ (-1)
    }

    Int indexOfValue(Object obj) {
        Int i = 1
        Int i2 = this.mSize << 1
        Array<Object> objArr = this.mArray
        if (obj == null) {
            while (i < i2) {
                if (objArr[i] == null) {
                    return i >> 1
                }
                i += 2
            }
        } else {
            while (i < i2) {
                if (obj.equals(objArr[i])) {
                    return i >> 1
                }
                i += 2
            }
        }
        return -1
    }

    fun isEmpty() {
        if (this.mSize <= 0) {
            return CONCURRENT_MODIFICATION_EXCEPTIONS
        }
        return false
    }

    fun keyAt(Int i) {
        return this.mArray[i << 1]
    }

    @Nullable
    fun put(Object obj, Object obj2) {
        Int iHashCode
        Int iIndexOf
        Int i = 8
        Int i2 = this.mSize
        if (obj == null) {
            iIndexOf = indexOfNull()
            iHashCode = 0
        } else {
            iHashCode = obj.hashCode()
            iIndexOf = indexOf(obj, iHashCode)
        }
        if (iIndexOf >= 0) {
            Int i3 = (iIndexOf << 1) + 1
            Object obj3 = this.mArray[i3]
            this.mArray[i3] = obj2
            return obj3
        }
        Int i4 = iIndexOf ^ (-1)
        if (i2 >= this.mHashes.length) {
            if (i2 >= 8) {
                i = (i2 >> 1) + i2
            } else if (i2 < 4) {
                i = 4
            }
            Array<Int> iArr = this.mHashes
            Array<Object> objArr = this.mArray
            allocArrays(i)
            if (i2 != this.mSize) {
                throw ConcurrentModificationException()
            }
            if (this.mHashes.length > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, iArr.length)
                System.arraycopy(objArr, 0, this.mArray, 0, objArr.length)
            }
            freeArrays(iArr, objArr, i2)
        }
        if (i4 < i2) {
            System.arraycopy(this.mHashes, i4, this.mHashes, i4 + 1, i2 - i4)
            System.arraycopy(this.mArray, i4 << 1, this.mArray, (i4 + 1) << 1, (this.mSize - i4) << 1)
        }
        if (i2 != this.mSize || i4 >= this.mHashes.length) {
            throw ConcurrentModificationException()
        }
        this.mHashes[i4] = iHashCode
        this.mArray[i4 << 1] = obj
        this.mArray[(i4 << 1) + 1] = obj2
        this.mSize++
        return null
    }

    fun putAll(@NonNull SimpleArrayMap simpleArrayMap) {
        Int i = simpleArrayMap.mSize
        ensureCapacity(this.mSize + i)
        if (this.mSize != 0) {
            for (Int i2 = 0; i2 < i; i2++) {
                put(simpleArrayMap.keyAt(i2), simpleArrayMap.valueAt(i2))
            }
        } else if (i > 0) {
            System.arraycopy(simpleArrayMap.mHashes, 0, this.mHashes, 0, i)
            System.arraycopy(simpleArrayMap.mArray, 0, this.mArray, 0, i << 1)
            this.mSize = i
        }
    }

    @Nullable
    fun remove(Object obj) {
        Int iIndexOfKey = indexOfKey(obj)
        if (iIndexOfKey >= 0) {
            return removeAt(iIndexOfKey)
        }
        return null
    }

    fun removeAt(Int i) {
        Int i2
        Object obj = this.mArray[(i << 1) + 1]
        Int i3 = this.mSize
        if (i3 <= 1) {
            freeArrays(this.mHashes, this.mArray, i3)
            this.mHashes = ContainerHelpers.EMPTY_INTS
            this.mArray = ContainerHelpers.EMPTY_OBJECTS
            i2 = 0
        } else {
            Int i4 = i3 - 1
            if (this.mHashes.length <= 8 || this.mSize >= this.mHashes.length / 3) {
                if (i < i4) {
                    System.arraycopy(this.mHashes, i + 1, this.mHashes, i, i4 - i)
                    System.arraycopy(this.mArray, (i + 1) << 1, this.mArray, i << 1, (i4 - i) << 1)
                }
                this.mArray[i4 << 1] = null
                this.mArray[(i4 << 1) + 1] = null
                i2 = i4
            } else {
                Int i5 = i3 > 8 ? (i3 >> 1) + i3 : 8
                Array<Int> iArr = this.mHashes
                Array<Object> objArr = this.mArray
                allocArrays(i5)
                if (i3 != this.mSize) {
                    throw ConcurrentModificationException()
                }
                if (i > 0) {
                    System.arraycopy(iArr, 0, this.mHashes, 0, i)
                    System.arraycopy(objArr, 0, this.mArray, 0, i << 1)
                }
                if (i < i4) {
                    System.arraycopy(iArr, i + 1, this.mHashes, i, i4 - i)
                    System.arraycopy(objArr, (i + 1) << 1, this.mArray, i << 1, (i4 - i) << 1)
                }
                i2 = i4
            }
        }
        if (i3 != this.mSize) {
            throw ConcurrentModificationException()
        }
        this.mSize = i2
        return obj
    }

    fun setValueAt(Int i, Object obj) {
        Int i2 = (i << 1) + 1
        Object obj2 = this.mArray[i2]
        this.mArray[i2] = obj
        return obj2
    }

    fun size() {
        return this.mSize
    }

    fun toString() {
        if (isEmpty()) {
            return "{}"
        }
        StringBuilder sb = StringBuilder(this.mSize * 28)
        sb.append('{')
        for (Int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ")
            }
            Object objKeyAt = keyAt(i)
            if (objKeyAt != this) {
                sb.append(objKeyAt)
            } else {
                sb.append("(this Map)")
            }
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
        return this.mArray[(i << 1) + 1]
    }
}
