package android.support.v4.util

import android.support.annotation.Nullable
import java.lang.reflect.Array
import java.util.Collection
import java.util.Iterator
import java.util.Map
import java.util.NoSuchElementException
import java.util.Set

abstract class MapCollections {

    @Nullable
    EntrySet mEntrySet

    @Nullable
    KeySet mKeySet

    @Nullable
    ValuesCollection mValues

    final class ArrayIterator implements Iterator {
        Boolean mCanRemove = false
        Int mIndex
        final Int mOffset
        Int mSize

        ArrayIterator(Int i) {
            this.mOffset = i
            this.mSize = MapCollections.this.colGetSize()
        }

        @Override // java.util.Iterator
        public final Boolean hasNext() {
            return this.mIndex < this.mSize
        }

        @Override // java.util.Iterator
        public final Object next() {
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            Object objColGetEntry = MapCollections.this.colGetEntry(this.mIndex, this.mOffset)
            this.mIndex++
            this.mCanRemove = true
            return objColGetEntry
        }

        @Override // java.util.Iterator
        public final Unit remove() {
            if (!this.mCanRemove) {
                throw IllegalStateException()
            }
            this.mIndex--
            this.mSize--
            this.mCanRemove = false
            MapCollections.this.colRemoveAt(this.mIndex)
        }
    }

    final class EntrySet implements Set {
        EntrySet() {
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean add(Map.Entry entry) {
            throw UnsupportedOperationException()
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean addAll(Collection collection) {
            Int iColGetSize = MapCollections.this.colGetSize()
            Iterator it = collection.iterator()
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next()
                MapCollections.this.colPut(entry.getKey(), entry.getValue())
            }
            return iColGetSize != MapCollections.this.colGetSize()
        }

        @Override // java.util.Set, java.util.Collection
        public final Unit clear() {
            MapCollections.this.colClear()
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean contains(Object obj) {
            if (!(obj is Map.Entry)) {
                return false
            }
            Map.Entry entry = (Map.Entry) obj
            Int iColIndexOfKey = MapCollections.this.colIndexOfKey(entry.getKey())
            if (iColIndexOfKey >= 0) {
                return ContainerHelpers.equal(MapCollections.this.colGetEntry(iColIndexOfKey, 1), entry.getValue())
            }
            return false
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean containsAll(Collection collection) {
            Iterator it = collection.iterator()
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false
                }
            }
            return true
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean equals(Object obj) {
            return MapCollections.equalsSetHelper(this, obj)
        }

        @Override // java.util.Set, java.util.Collection
        public final Int hashCode() {
            Int iColGetSize = MapCollections.this.colGetSize() - 1
            Int iHashCode = 0
            while (iColGetSize >= 0) {
                Object objColGetEntry = MapCollections.this.colGetEntry(iColGetSize, 0)
                Object objColGetEntry2 = MapCollections.this.colGetEntry(iColGetSize, 1)
                iColGetSize--
                iHashCode += (objColGetEntry2 == null ? 0 : objColGetEntry2.hashCode()) ^ (objColGetEntry == null ? 0 : objColGetEntry.hashCode())
            }
            return iHashCode
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean isEmpty() {
            return MapCollections.this.colGetSize() == 0
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public final Iterator iterator() {
            return MapCollections.this.MapIterator()
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean remove(Object obj) {
            throw UnsupportedOperationException()
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean removeAll(Collection collection) {
            throw UnsupportedOperationException()
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean retainAll(Collection collection) {
            throw UnsupportedOperationException()
        }

        @Override // java.util.Set, java.util.Collection
        public final Int size() {
            return MapCollections.this.colGetSize()
        }

        @Override // java.util.Set, java.util.Collection
        public final Array<Object> toArray() {
            throw UnsupportedOperationException()
        }

        @Override // java.util.Set, java.util.Collection
        public final Array<Object> toArray(Array<Object> objArr) {
            throw UnsupportedOperationException()
        }
    }

    final class KeySet implements Set {
        KeySet() {
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean add(Object obj) {
            throw UnsupportedOperationException()
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean addAll(Collection collection) {
            throw UnsupportedOperationException()
        }

        @Override // java.util.Set, java.util.Collection
        public final Unit clear() {
            MapCollections.this.colClear()
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean contains(Object obj) {
            return MapCollections.this.colIndexOfKey(obj) >= 0
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean containsAll(Collection collection) {
            return MapCollections.containsAllHelper(MapCollections.this.colGetMap(), collection)
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean equals(Object obj) {
            return MapCollections.equalsSetHelper(this, obj)
        }

        @Override // java.util.Set, java.util.Collection
        public final Int hashCode() {
            Int iHashCode = 0
            for (Int iColGetSize = MapCollections.this.colGetSize() - 1; iColGetSize >= 0; iColGetSize--) {
                Object objColGetEntry = MapCollections.this.colGetEntry(iColGetSize, 0)
                iHashCode += objColGetEntry == null ? 0 : objColGetEntry.hashCode()
            }
            return iHashCode
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean isEmpty() {
            return MapCollections.this.colGetSize() == 0
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public final Iterator iterator() {
            return MapCollections.this.ArrayIterator(0)
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean remove(Object obj) {
            Int iColIndexOfKey = MapCollections.this.colIndexOfKey(obj)
            if (iColIndexOfKey < 0) {
                return false
            }
            MapCollections.this.colRemoveAt(iColIndexOfKey)
            return true
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean removeAll(Collection collection) {
            return MapCollections.removeAllHelper(MapCollections.this.colGetMap(), collection)
        }

        @Override // java.util.Set, java.util.Collection
        public final Boolean retainAll(Collection collection) {
            return MapCollections.retainAllHelper(MapCollections.this.colGetMap(), collection)
        }

        @Override // java.util.Set, java.util.Collection
        public final Int size() {
            return MapCollections.this.colGetSize()
        }

        @Override // java.util.Set, java.util.Collection
        public final Array<Object> toArray() {
            return MapCollections.this.toArrayHelper(0)
        }

        @Override // java.util.Set, java.util.Collection
        public final Array<Object> toArray(Array<Object> objArr) {
            return MapCollections.this.toArrayHelper(objArr, 0)
        }
    }

    final class MapIterator implements Iterator, Map.Entry {
        Int mEnd
        Boolean mEntryValid = false
        Int mIndex = -1

        MapIterator() {
            this.mEnd = MapCollections.this.colGetSize() - 1
        }

        @Override // java.util.Map.Entry
        public final Boolean equals(Object obj) {
            if (!this.mEntryValid) {
                throw IllegalStateException("This container does not support retaining Map.Entry objects")
            }
            if (!(obj is Map.Entry)) {
                return false
            }
            Map.Entry entry = (Map.Entry) obj
            return ContainerHelpers.equal(entry.getKey(), MapCollections.this.colGetEntry(this.mIndex, 0)) && ContainerHelpers.equal(entry.getValue(), MapCollections.this.colGetEntry(this.mIndex, 1))
        }

        @Override // java.util.Map.Entry
        public final Object getKey() {
            if (this.mEntryValid) {
                return MapCollections.this.colGetEntry(this.mIndex, 0)
            }
            throw IllegalStateException("This container does not support retaining Map.Entry objects")
        }

        @Override // java.util.Map.Entry
        public final Object getValue() {
            if (this.mEntryValid) {
                return MapCollections.this.colGetEntry(this.mIndex, 1)
            }
            throw IllegalStateException("This container does not support retaining Map.Entry objects")
        }

        @Override // java.util.Iterator
        public final Boolean hasNext() {
            return this.mIndex < this.mEnd
        }

        @Override // java.util.Map.Entry
        public final Int hashCode() {
            if (!this.mEntryValid) {
                throw IllegalStateException("This container does not support retaining Map.Entry objects")
            }
            Object objColGetEntry = MapCollections.this.colGetEntry(this.mIndex, 0)
            Object objColGetEntry2 = MapCollections.this.colGetEntry(this.mIndex, 1)
            return (objColGetEntry2 != null ? objColGetEntry2.hashCode() : 0) ^ (objColGetEntry == null ? 0 : objColGetEntry.hashCode())
        }

        @Override // java.util.Iterator
        public final Map.Entry next() {
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            this.mIndex++
            this.mEntryValid = true
            return this
        }

        @Override // java.util.Iterator
        public final Unit remove() {
            if (!this.mEntryValid) {
                throw IllegalStateException()
            }
            MapCollections.this.colRemoveAt(this.mIndex)
            this.mIndex--
            this.mEnd--
            this.mEntryValid = false
        }

        @Override // java.util.Map.Entry
        public final Object setValue(Object obj) {
            if (this.mEntryValid) {
                return MapCollections.this.colSetValue(this.mIndex, obj)
            }
            throw IllegalStateException("This container does not support retaining Map.Entry objects")
        }

        public final String toString() {
            return getKey() + "=" + getValue()
        }
    }

    final class ValuesCollection implements Collection {
        ValuesCollection() {
        }

        @Override // java.util.Collection
        public final Boolean add(Object obj) {
            throw UnsupportedOperationException()
        }

        @Override // java.util.Collection
        public final Boolean addAll(Collection collection) {
            throw UnsupportedOperationException()
        }

        @Override // java.util.Collection
        public final Unit clear() {
            MapCollections.this.colClear()
        }

        @Override // java.util.Collection
        public final Boolean contains(Object obj) {
            return MapCollections.this.colIndexOfValue(obj) >= 0
        }

        @Override // java.util.Collection
        public final Boolean containsAll(Collection collection) {
            Iterator it = collection.iterator()
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false
                }
            }
            return true
        }

        @Override // java.util.Collection
        public final Boolean isEmpty() {
            return MapCollections.this.colGetSize() == 0
        }

        @Override // java.util.Collection, java.lang.Iterable
        public final Iterator iterator() {
            return MapCollections.this.ArrayIterator(1)
        }

        @Override // java.util.Collection
        public final Boolean remove(Object obj) {
            Int iColIndexOfValue = MapCollections.this.colIndexOfValue(obj)
            if (iColIndexOfValue < 0) {
                return false
            }
            MapCollections.this.colRemoveAt(iColIndexOfValue)
            return true
        }

        @Override // java.util.Collection
        public final Boolean removeAll(Collection collection) {
            Int i = 0
            Int iColGetSize = MapCollections.this.colGetSize()
            Boolean z = false
            while (i < iColGetSize) {
                if (collection.contains(MapCollections.this.colGetEntry(i, 1))) {
                    MapCollections.this.colRemoveAt(i)
                    i--
                    iColGetSize--
                    z = true
                }
                i++
            }
            return z
        }

        @Override // java.util.Collection
        public final Boolean retainAll(Collection collection) {
            Int i = 0
            Int iColGetSize = MapCollections.this.colGetSize()
            Boolean z = false
            while (i < iColGetSize) {
                if (!collection.contains(MapCollections.this.colGetEntry(i, 1))) {
                    MapCollections.this.colRemoveAt(i)
                    i--
                    iColGetSize--
                    z = true
                }
                i++
            }
            return z
        }

        @Override // java.util.Collection
        public final Int size() {
            return MapCollections.this.colGetSize()
        }

        @Override // java.util.Collection
        public final Array<Object> toArray() {
            return MapCollections.this.toArrayHelper(1)
        }

        @Override // java.util.Collection
        public final Array<Object> toArray(Array<Object> objArr) {
            return MapCollections.this.toArrayHelper(objArr, 1)
        }
    }

    MapCollections() {
    }

    fun containsAllHelper(Map map, Collection collection) {
        Iterator it = collection.iterator()
        while (it.hasNext()) {
            if (!map.containsKey(it.next())) {
                return false
            }
        }
        return true
    }

    fun equalsSetHelper(Set set, Object obj) {
        if (set == obj) {
            return true
        }
        if (!(obj is Set)) {
            return false
        }
        Set set2 = (Set) obj
        try {
            if (set.size() == set2.size()) {
                if (set.containsAll(set2)) {
                    return true
                }
            }
            return false
        } catch (ClassCastException e) {
            return false
        } catch (NullPointerException e2) {
            return false
        }
    }

    fun removeAllHelper(Map map, Collection collection) {
        Int size = map.size()
        Iterator it = collection.iterator()
        while (it.hasNext()) {
            map.remove(it.next())
        }
        return size != map.size()
    }

    fun retainAllHelper(Map map, Collection collection) {
        Int size = map.size()
        Iterator it = map.keySet().iterator()
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove()
            }
        }
        return size != map.size()
    }

    protected abstract Unit colClear()

    protected abstract Object colGetEntry(Int i, Int i2)

    protected abstract Map colGetMap()

    protected abstract Int colGetSize()

    protected abstract Int colIndexOfKey(Object obj)

    protected abstract Int colIndexOfValue(Object obj)

    protected abstract Unit colPut(Object obj, Object obj2)

    protected abstract Unit colRemoveAt(Int i)

    protected abstract Object colSetValue(Int i, Object obj)

    fun getEntrySet() {
        if (this.mEntrySet == null) {
            this.mEntrySet = EntrySet()
        }
        return this.mEntrySet
    }

    fun getKeySet() {
        if (this.mKeySet == null) {
            this.mKeySet = KeySet()
        }
        return this.mKeySet
    }

    fun getValues() {
        if (this.mValues == null) {
            this.mValues = ValuesCollection()
        }
        return this.mValues
    }

    public Array<Object> toArrayHelper(Int i) {
        Int iColGetSize = colGetSize()
        Array<Object> objArr = new Object[iColGetSize]
        for (Int i2 = 0; i2 < iColGetSize; i2++) {
            objArr[i2] = colGetEntry(i2, i)
        }
        return objArr
    }

    public Array<Object> toArrayHelper(Array<Object> objArr, Int i) {
        Int iColGetSize = colGetSize()
        Array<Object> objArr2 = objArr.length < iColGetSize ? (Array<Object>) Array.newInstance(objArr.getClass().getComponentType(), iColGetSize) : objArr
        for (Int i2 = 0; i2 < iColGetSize; i2++) {
            objArr2[i2] = colGetEntry(i2, i)
        }
        if (objArr2.length > iColGetSize) {
            objArr2[iColGetSize] = null
        }
        return objArr2
    }
}
