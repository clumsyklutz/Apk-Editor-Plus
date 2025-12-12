package android.support.v4.util

import android.support.annotation.NonNull
import android.support.annotation.Nullable
import java.util.Collection
import java.util.Map
import java.util.Set

class ArrayMap extends SimpleArrayMap implements Map {

    @Nullable
    MapCollections mCollections

    constructor() {
    }

    constructor(Int i) {
        super(i)
    }

    constructor(SimpleArrayMap simpleArrayMap) {
        super(simpleArrayMap)
    }

    private fun getCollection() {
        if (this.mCollections == null) {
            this.mCollections = MapCollections() { // from class: android.support.v4.util.ArrayMap.1
                @Override // android.support.v4.util.MapCollections
                protected fun colClear() {
                    ArrayMap.this.clear()
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colGetEntry(Int i, Int i2) {
                    return ArrayMap.this.mArray[(i << 1) + i2]
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colGetMap() {
                    return ArrayMap.this
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colGetSize() {
                    return ArrayMap.this.mSize
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colIndexOfKey(Object obj) {
                    return ArrayMap.this.indexOfKey(obj)
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colIndexOfValue(Object obj) {
                    return ArrayMap.this.indexOfValue(obj)
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colPut(Object obj, Object obj2) {
                    ArrayMap.this.put(obj, obj2)
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colRemoveAt(Int i) {
                    ArrayMap.this.removeAt(i)
                }

                @Override // android.support.v4.util.MapCollections
                protected fun colSetValue(Int i, Object obj) {
                    return ArrayMap.this.setValueAt(i, obj)
                }
            }
        }
        return this.mCollections
    }

    fun containsAll(@NonNull Collection collection) {
        return MapCollections.containsAllHelper(this, collection)
    }

    @Override // java.util.Map
    fun entrySet() {
        return getCollection().getEntrySet()
    }

    @Override // java.util.Map
    fun keySet() {
        return getCollection().getKeySet()
    }

    @Override // java.util.Map
    fun putAll(Map map) {
        ensureCapacity(this.mSize + map.size())
        for (Map.Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue())
        }
    }

    fun removeAll(@NonNull Collection collection) {
        return MapCollections.removeAllHelper(this, collection)
    }

    fun retainAll(@NonNull Collection collection) {
        return MapCollections.retainAllHelper(this, collection)
    }

    @Override // java.util.Map
    fun values() {
        return getCollection().getValues()
    }
}
