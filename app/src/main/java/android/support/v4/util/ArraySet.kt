package android.support.v4.util

import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import java.lang.reflect.Array
import java.util.Collection
import java.util.Iterator
import java.util.Map
import java.util.Set

class ArraySet implements Collection, Set {
    private static val BASE_SIZE = 4
    private static val CACHE_SIZE = 10
    private static val DEBUG = false
    private static final Array<Int> INT = new Int[0]
    private static final Array<Object> OBJECT = new Object[0]
    private static val TAG = "ArraySet"

    @Nullable
    private static Array<Object> sBaseCache
    private static Int sBaseCacheSize

    @Nullable
    private static Array<Object> sTwiceBaseCache
    private static Int sTwiceBaseCacheSize
    Array<Object> mArray
    private MapCollections mCollections
    private Array<Int> mHashes
    Int mSize

    constructor() {
        this(0)
    }

    constructor(Int i) {
        if (i == 0) {
            this.mHashes = INT
            this.mArray = OBJECT
        } else {
            allocArrays(i)
        }
        this.mSize = 0
    }

    constructor(@Nullable ArraySet arraySet) {
        this()
        if (arraySet != null) {
            addAll(arraySet)
        }
    }

    constructor(@Nullable Collection collection) {
        this()
        if (collection != null) {
            addAll(collection)
        }
    }

    private fun allocArrays(Int i) {
        if (i == 8) {
            synchronized (ArraySet.class) {
                if (sTwiceBaseCache != null) {
                    Array<Object> objArr = sTwiceBaseCache
                    this.mArray = objArr
                    sTwiceBaseCache = (Array<Object>) objArr[0]
                    this.mHashes = (Array<Int>) objArr[1]
                    objArr[1] = null
                    objArr[0] = null
                    sTwiceBaseCacheSize--
                    return
                }
            }
        } else if (i == 4) {
            synchronized (ArraySet.class) {
                if (sBaseCache != null) {
                    Array<Object> objArr2 = sBaseCache
                    this.mArray = objArr2
                    sBaseCache = (Array<Object>) objArr2[0]
                    this.mHashes = (Array<Int>) objArr2[1]
                    objArr2[1] = null
                    objArr2[0] = null
                    sBaseCacheSize--
                    return
                }
            }
        }
        this.mHashes = new Int[i]
        this.mArray = new Object[i]
    }

    private fun freeArrays(Array<Int> iArr, Array<Object> objArr, Int i) {
        if (iArr.length == 8) {
            synchronized (ArraySet.class) {
                if (sTwiceBaseCacheSize < 10) {
                    objArr[0] = sTwiceBaseCache
                    objArr[1] = iArr
                    for (Int i2 = i - 1; i2 >= 2; i2--) {
                        objArr[i2] = null
                    }
                    sTwiceBaseCache = objArr
                    sTwiceBaseCacheSize++
                }
            }
            return
        }
        if (iArr.length == 4) {
            synchronized (ArraySet.class) {
                if (sBaseCacheSize < 10) {
                    objArr[0] = sBaseCache
                    objArr[1] = iArr
                    for (Int i3 = i - 1; i3 >= 2; i3--) {
                        objArr[i3] = null
                    }
                    sBaseCache = objArr
                    sBaseCacheSize++
                }
            }
        }
    }

    private fun getCollection() {
        if (this.mCollections == null) {
            this.mCollections = MapCollections() { // from class: android.support.v4.util.ArraySet.1
                @Override // android.support.v4.util.MapCollections
                protected fun colClear() {
                    ArraySet.this.clear()
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colGetEntry(Int i, Int i2) {
                    return ArraySet.this.mArray[i]
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colGetMap() {
                    throw UnsupportedOperationException("not a map")
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colGetSize() {
                    return ArraySet.this.mSize
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colIndexOfKey(Object obj) {
                    return ArraySet.this.indexOf(obj)
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colIndexOfValue(Object obj) {
                    return ArraySet.this.indexOf(obj)
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colPut(Object obj, Object obj2) {
                    ArraySet.this.add(obj)
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colRemoveAt(Int i) {
                    ArraySet.this.removeAt(i)
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colSetValue(Int i, Object obj) {
                    throw UnsupportedOperationException("not a map")
                }
            }
        }
        return this.mCollections
    }

    private fun indexOf(Object obj, Int i) {
        Int i2 = this.mSize
        if (i2 == 0) {
            return -1
        }
        Int iBinarySearch = ContainerHelpers.binarySearch(this.mHashes, i2, i)
        if (iBinarySearch < 0 || obj.equals(this.mArray[iBinarySearch])) {
            return iBinarySearch
        }
        Int i3 = iBinarySearch + 1
        while (i3 < i2 && this.mHashes[i3] == i) {
            if (obj.equals(this.mArray[i3])) {
                return i3
            }
            i3++
        }
        for (Int i4 = iBinarySearch - 1; i4 >= 0 && this.mHashes[i4] == i; i4--) {
            if (obj.equals(this.mArray[i4])) {
                return i4
            }
        }
        return i3 ^ (-1)
    }

    private fun indexOfNull() {
        Int i = this.mSize
        if (i == 0) {
            return -1
        }
        Int iBinarySearch = ContainerHelpers.binarySearch(this.mHashes, i, 0)
        if (iBinarySearch < 0 || this.mArray[iBinarySearch] == null) {
            return iBinarySearch
        }
        Int i2 = iBinarySearch + 1
        while (i2 < i && this.mHashes[i2] == 0) {
            if (this.mArray[i2] == null) {
                return i2
            }
            i2++
        }
        for (Int i3 = iBinarySearch - 1; i3 >= 0 && this.mHashes[i3] == 0; i3--) {
            if (this.mArray[i3] == null) {
                return i3
            }
        }
        return i2 ^ (-1)
    }

    @Override // java.util.Collection, java.util.Set
    public final Boolean add(@Nullable Object obj) {
        Int iHashCode
        Int iIndexOf
        Int i = 8
        if (obj == null) {
            iIndexOf = indexOfNull()
            iHashCode = 0
        } else {
            iHashCode = obj.hashCode()
            iIndexOf = indexOf(obj, iHashCode)
        }
        if (iIndexOf >= 0) {
            return false
        }
        Int i2 = iIndexOf ^ (-1)
        if (this.mSize >= this.mHashes.length) {
            if (this.mSize >= 8) {
                i = this.mSize + (this.mSize >> 1)
            } else if (this.mSize < 4) {
                i = 4
            }
            Array<Int> iArr = this.mHashes
            Array<Object> objArr = this.mArray
            allocArrays(i)
            if (this.mHashes.length > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, iArr.length)
                System.arraycopy(objArr, 0, this.mArray, 0, objArr.length)
            }
            freeArrays(iArr, objArr, this.mSize)
        }
        if (i2 < this.mSize) {
            System.arraycopy(this.mHashes, i2, this.mHashes, i2 + 1, this.mSize - i2)
            System.arraycopy(this.mArray, i2, this.mArray, i2 + 1, this.mSize - i2)
        }
        this.mHashes[i2] = iHashCode
        this.mArray[i2] = obj
        this.mSize++
        return true
    }

    public final Unit addAll(@NonNull ArraySet arraySet) {
        Int i = arraySet.mSize
        ensureCapacity(this.mSize + i)
        if (this.mSize != 0) {
            for (Int i2 = 0; i2 < i; i2++) {
                add(arraySet.valueAt(i2))
            }
        } else if (i > 0) {
            System.arraycopy(arraySet.mHashes, 0, this.mHashes, 0, i)
            System.arraycopy(arraySet.mArray, 0, this.mArray, 0, i)
            this.mSize = i
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final Boolean addAll(@NonNull Collection collection) {
        ensureCapacity(this.mSize + collection.size())
        Boolean zAdd = false
        Iterator it = collection.iterator()
        while (it.hasNext()) {
            zAdd |= add(it.next())
        }
        return zAdd
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final Unit append(Object obj) {
        Int i = this.mSize
        Int iHashCode = obj == null ? 0 : obj.hashCode()
        if (i >= this.mHashes.length) {
            throw IllegalStateException("Array is full")
        }
        if (i > 0 && this.mHashes[i - 1] > iHashCode) {
            add(obj)
            return
        }
        this.mSize = i + 1
        this.mHashes[i] = iHashCode
        this.mArray[i] = obj
    }

    @Override // java.util.Collection, java.util.Set
    public final Unit clear() {
        if (this.mSize != 0) {
            freeArrays(this.mHashes, this.mArray, this.mSize)
            this.mHashes = INT
            this.mArray = OBJECT
            this.mSize = 0
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final Boolean contains(@Nullable Object obj) {
        return indexOf(obj) >= 0
    }

    @Override // java.util.Collection, java.util.Set
    public final Boolean containsAll(@NonNull Collection collection) {
        Iterator it = collection.iterator()
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false
            }
        }
        return true
    }

    public final Unit ensureCapacity(Int i) {
        if (this.mHashes.length < i) {
            Array<Int> iArr = this.mHashes
            Array<Object> objArr = this.mArray
            allocArrays(i)
            if (this.mSize > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, this.mSize)
                System.arraycopy(objArr, 0, this.mArray, 0, this.mSize)
            }
            freeArrays(iArr, objArr, this.mSize)
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (!(obj is Set)) {
            return false
        }
        Set set = (Set) obj
        if (size() != set.size()) {
            return false
        }
        for (Int i = 0; i < this.mSize; i++) {
            try {
                if (!set.contains(valueAt(i))) {
                    return false
                }
            } catch (ClassCastException e) {
                return false
            } catch (NullPointerException e2) {
                return false
            }
        }
        return true
    }

    @Override // java.util.Collection, java.util.Set
    public final Int hashCode() {
        Array<Int> iArr = this.mHashes
        Int i = this.mSize
        Int i2 = 0
        for (Int i3 = 0; i3 < i; i3++) {
            i2 += iArr[i3]
        }
        return i2
    }

    public final Int indexOf(@Nullable Object obj) {
        return obj == null ? indexOfNull() : indexOf(obj, obj.hashCode())
    }

    @Override // java.util.Collection, java.util.Set
    public final Boolean isEmpty() {
        return this.mSize <= 0
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return getCollection().getKeySet().iterator()
    }

    @Override // java.util.Collection, java.util.Set
    public final Boolean remove(@Nullable Object obj) {
        Int iIndexOf = indexOf(obj)
        if (iIndexOf < 0) {
            return false
        }
        removeAt(iIndexOf)
        return true
    }

    public final Boolean removeAll(@NonNull ArraySet arraySet) {
        Int i = arraySet.mSize
        Int i2 = this.mSize
        for (Int i3 = 0; i3 < i; i3++) {
            remove(arraySet.valueAt(i3))
        }
        return i2 != this.mSize
    }

    @Override // java.util.Collection, java.util.Set
    public final Boolean removeAll(@NonNull Collection collection) {
        Boolean zRemove = false
        Iterator it = collection.iterator()
        while (it.hasNext()) {
            zRemove |= remove(it.next())
        }
        return zRemove
    }

    public final Object removeAt(Int i) {
        Object obj = this.mArray[i]
        if (this.mSize <= 1) {
            freeArrays(this.mHashes, this.mArray, this.mSize)
            this.mHashes = INT
            this.mArray = OBJECT
            this.mSize = 0
        } else if (this.mHashes.length <= 8 || this.mSize >= this.mHashes.length / 3) {
            this.mSize--
            if (i < this.mSize) {
                System.arraycopy(this.mHashes, i + 1, this.mHashes, i, this.mSize - i)
                System.arraycopy(this.mArray, i + 1, this.mArray, i, this.mSize - i)
            }
            this.mArray[this.mSize] = null
        } else {
            Int i2 = this.mSize > 8 ? this.mSize + (this.mSize >> 1) : 8
            Array<Int> iArr = this.mHashes
            Array<Object> objArr = this.mArray
            allocArrays(i2)
            this.mSize--
            if (i > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, i)
                System.arraycopy(objArr, 0, this.mArray, 0, i)
            }
            if (i < this.mSize) {
                System.arraycopy(iArr, i + 1, this.mHashes, i, this.mSize - i)
                System.arraycopy(objArr, i + 1, this.mArray, i, this.mSize - i)
            }
        }
        return obj
    }

    @Override // java.util.Collection, java.util.Set
    public final Boolean retainAll(@NonNull Collection collection) {
        Boolean z = false
        for (Int i = this.mSize - 1; i >= 0; i--) {
            if (!collection.contains(this.mArray[i])) {
                removeAt(i)
                z = true
            }
        }
        return z
    }

    @Override // java.util.Collection, java.util.Set
    public final Int size() {
        return this.mSize
    }

    @Override // java.util.Collection, java.util.Set
    @NonNull
    public final Array<Object> toArray() {
        Array<Object> objArr = new Object[this.mSize]
        System.arraycopy(this.mArray, 0, objArr, 0, this.mSize)
        return objArr
    }

    @Override // java.util.Collection, java.util.Set
    @NonNull
    public final Array<Object> toArray(@NonNull Array<Object> objArr) {
        Array<Object> objArr2 = objArr.length < this.mSize ? (Array<Object>) Array.newInstance(objArr.getClass().getComponentType(), this.mSize) : objArr
        System.arraycopy(this.mArray, 0, objArr2, 0, this.mSize)
        if (objArr2.length > this.mSize) {
            objArr2[this.mSize] = null
        }
        return objArr2
    }

    public final String toString() {
        if (isEmpty()) {
            return "{}"
        }
        StringBuilder sb = StringBuilder(this.mSize * 14)
        sb.append('{')
        for (Int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ")
            }
            Object objValueAt = valueAt(i)
            if (objValueAt != this) {
                sb.append(objValueAt)
            } else {
                sb.append("(this Set)")
            }
        }
        sb.append('}')
        return sb.toString()
    }

    @Nullable
    public final Object valueAt(Int i) {
        return this.mArray[i]
    }
}
