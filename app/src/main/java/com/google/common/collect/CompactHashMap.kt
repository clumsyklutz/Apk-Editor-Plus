package com.google.common.collect

import com.google.common.base.Objects
import com.google.common.base.Preconditions
import com.google.common.primitives.Ints
import java.io.Serializable
import java.util.AbstractCollection
import java.util.AbstractMap
import java.util.AbstractSet
import java.util.Arrays
import java.util.Collection
import java.util.ConcurrentModificationException
import java.util.Iterator
import java.util.LinkedHashMap
import java.util.Map
import java.util.NoSuchElementException
import java.util.Set

class CompactHashMap<K, V> extends AbstractMap<K, V> implements Serializable {
    public static val NOT_FOUND = Object()
    public transient Array<Int> entries
    public transient Set<Map.Entry<K, V>> entrySetView
    public transient Set<K> keySetView
    public transient Array<Object> keys
    public transient Int metadata
    public transient Int size
    public transient Object table
    public transient Array<Object> values
    public transient Collection<V> valuesView

    class EntrySetView extends AbstractSet<Map.Entry<K, V>> {
        constructor() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun clear() {
            CompactHashMap.this.clear()
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun contains(Object obj) {
            Map<K, V> mapDelegateOrNull = CompactHashMap.this.delegateOrNull()
            if (mapDelegateOrNull != null) {
                return mapDelegateOrNull.entrySet().contains(obj)
            }
            if (!(obj is Map.Entry)) {
                return false
            }
            Map.Entry entry = (Map.Entry) obj
            Int iIndexOf = CompactHashMap.this.indexOf(entry.getKey())
            return iIndexOf != -1 && Objects.equal(CompactHashMap.this.value(iIndexOf), entry.getValue())
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return CompactHashMap.this.entrySetIterator()
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun remove(Object obj) {
            Map<K, V> mapDelegateOrNull = CompactHashMap.this.delegateOrNull()
            if (mapDelegateOrNull != null) {
                return mapDelegateOrNull.entrySet().remove(obj)
            }
            if (!(obj is Map.Entry)) {
                return false
            }
            Map.Entry entry = (Map.Entry) obj
            if (CompactHashMap.this.needsAllocArrays()) {
                return false
            }
            Int iHashTableMask = CompactHashMap.this.hashTableMask()
            Int iRemove = CompactHashing.remove(entry.getKey(), entry.getValue(), iHashTableMask, CompactHashMap.this.requireTable(), CompactHashMap.this.requireEntries(), CompactHashMap.this.requireKeys(), CompactHashMap.this.requireValues())
            if (iRemove == -1) {
                return false
            }
            CompactHashMap.this.moveLastEntry(iRemove, iHashTableMask)
            CompactHashMap.access$1210(CompactHashMap.this)
            CompactHashMap.this.incrementModCount()
            return true
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun size() {
            return CompactHashMap.this.size()
        }
    }

    abstract class Itr<T> implements Iterator<T> {
        public Int currentIndex
        public Int expectedMetadata
        public Int indexToRemove

        constructor() {
            this.expectedMetadata = CompactHashMap.this.metadata
            this.currentIndex = CompactHashMap.this.firstEntryIndex()
            this.indexToRemove = -1
        }

        public final Unit checkForConcurrentModification() {
            if (CompactHashMap.this.metadata != this.expectedMetadata) {
                throw ConcurrentModificationException()
            }
        }

        public abstract T getOutput(Int i)

        @Override // java.util.Iterator
        fun hasNext() {
            return this.currentIndex >= 0
        }

        fun incrementExpectedModCount() {
            this.expectedMetadata += 32
        }

        @Override // java.util.Iterator
        fun next() {
            checkForConcurrentModification()
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            Int i = this.currentIndex
            this.indexToRemove = i
            T output = getOutput(i)
            this.currentIndex = CompactHashMap.this.getSuccessor(this.currentIndex)
            return output
        }

        @Override // java.util.Iterator
        fun remove() {
            checkForConcurrentModification()
            CollectPreconditions.checkRemove(this.indexToRemove >= 0)
            incrementExpectedModCount()
            CompactHashMap compactHashMap = CompactHashMap.this
            compactHashMap.remove(compactHashMap.key(this.indexToRemove))
            this.currentIndex = CompactHashMap.this.adjustAfterRemove(this.currentIndex, this.indexToRemove)
            this.indexToRemove = -1
        }
    }

    class KeySetView extends AbstractSet<K> {
        constructor() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun clear() {
            CompactHashMap.this.clear()
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun contains(Object obj) {
            return CompactHashMap.this.containsKey(obj)
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return CompactHashMap.this.keySetIterator()
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun remove(Object obj) {
            Map<K, V> mapDelegateOrNull = CompactHashMap.this.delegateOrNull()
            return mapDelegateOrNull != null ? mapDelegateOrNull.keySet().remove(obj) : CompactHashMap.this.removeHelper(obj) != CompactHashMap.NOT_FOUND
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun size() {
            return CompactHashMap.this.size()
        }
    }

    class MapEntry extends AbstractMapEntry<K, V> {
        public final K key
        public Int lastKnownIndex

        constructor(Int i) {
            this.key = (K) CompactHashMap.this.key(i)
            this.lastKnownIndex = i
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        fun getKey() {
            return this.key
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        fun getValue() {
            Map<K, V> mapDelegateOrNull = CompactHashMap.this.delegateOrNull()
            if (mapDelegateOrNull != null) {
                return (V) NullnessCasts.uncheckedCastNullableTToT(mapDelegateOrNull.get(this.key))
            }
            updateLastKnownIndex()
            Int i = this.lastKnownIndex
            return i == -1 ? (V) NullnessCasts.unsafeNull() : (V) CompactHashMap.this.value(i)
        }

        @Override // java.util.Map.Entry
        fun setValue(V v) {
            Map<K, V> mapDelegateOrNull = CompactHashMap.this.delegateOrNull()
            if (mapDelegateOrNull != null) {
                return (V) NullnessCasts.uncheckedCastNullableTToT(mapDelegateOrNull.put(this.key, v))
            }
            updateLastKnownIndex()
            Int i = this.lastKnownIndex
            if (i == -1) {
                CompactHashMap.this.put(this.key, v)
                return (V) NullnessCasts.unsafeNull()
            }
            V v2 = (V) CompactHashMap.this.value(i)
            CompactHashMap.this.setValue(this.lastKnownIndex, v)
            return v2
        }

        public final Unit updateLastKnownIndex() {
            Int i = this.lastKnownIndex
            if (i == -1 || i >= CompactHashMap.this.size() || !Objects.equal(this.key, CompactHashMap.this.key(this.lastKnownIndex))) {
                this.lastKnownIndex = CompactHashMap.this.indexOf(this.key)
            }
        }
    }

    class ValuesView extends AbstractCollection<V> {
        constructor() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun clear() {
            CompactHashMap.this.clear()
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return CompactHashMap.this.valuesIterator()
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun size() {
            return CompactHashMap.this.size()
        }
    }

    constructor(Int i) {
        init(i)
    }

    public static /* synthetic */ Int access$1210(CompactHashMap compactHashMap) {
        Int i = compactHashMap.size
        compactHashMap.size = i - 1
        return i
    }

    public static <K, V> CompactHashMap<K, V> createWithExpectedSize(Int i) {
        return new CompactHashMap<>(i)
    }

    fun accessEntry(Int i) {
    }

    fun adjustAfterRemove(Int i, Int i2) {
        return i - 1
    }

    fun allocArrays() {
        Preconditions.checkState(needsAllocArrays(), "Arrays already allocated")
        Int i = this.metadata
        Int iTableSize = CompactHashing.tableSize(i)
        this.table = CompactHashing.createTable(iTableSize)
        setHashTableMask(iTableSize - 1)
        this.entries = new Int[i]
        this.keys = new Object[i]
        this.values = new Object[i]
        return i
    }

    @Override // java.util.AbstractMap, java.util.Map
    fun clear() {
        if (needsAllocArrays()) {
            return
        }
        incrementModCount()
        Map<K, V> mapDelegateOrNull = delegateOrNull()
        if (mapDelegateOrNull != null) {
            this.metadata = Ints.constrainToRange(size(), 3, 1073741823)
            mapDelegateOrNull.clear()
            this.table = null
            this.size = 0
            return
        }
        Arrays.fill(requireKeys(), 0, this.size, (Object) null)
        Arrays.fill(requireValues(), 0, this.size, (Object) null)
        CompactHashing.tableClear(requireTable())
        Arrays.fill(requireEntries(), 0, this.size, 0)
        this.size = 0
    }

    @Override // java.util.AbstractMap, java.util.Map
    fun containsKey(Object obj) {
        Map<K, V> mapDelegateOrNull = delegateOrNull()
        return mapDelegateOrNull != null ? mapDelegateOrNull.containsKey(obj) : indexOf(obj) != -1
    }

    @Override // java.util.AbstractMap, java.util.Map
    fun containsValue(Object obj) {
        Map<K, V> mapDelegateOrNull = delegateOrNull()
        if (mapDelegateOrNull != null) {
            return mapDelegateOrNull.containsValue(obj)
        }
        for (Int i = 0; i < this.size; i++) {
            if (Objects.equal(obj, value(i))) {
                return true
            }
        }
        return false
    }

    public Map<K, V> convertToHashFloodingResistantImplementation() {
        Map<K, V> mapCreateHashFloodingResistantDelegate = createHashFloodingResistantDelegate(hashTableMask() + 1)
        Int iFirstEntryIndex = firstEntryIndex()
        while (iFirstEntryIndex >= 0) {
            mapCreateHashFloodingResistantDelegate.put(key(iFirstEntryIndex), value(iFirstEntryIndex))
            iFirstEntryIndex = getSuccessor(iFirstEntryIndex)
        }
        this.table = mapCreateHashFloodingResistantDelegate
        this.entries = null
        this.keys = null
        this.values = null
        incrementModCount()
        return mapCreateHashFloodingResistantDelegate
    }

    public Set<Map.Entry<K, V>> createEntrySet() {
        return EntrySetView()
    }

    public Map<K, V> createHashFloodingResistantDelegate(Int i) {
        return LinkedHashMap(i, 1.0f)
    }

    public Set<K> createKeySet() {
        return KeySetView()
    }

    public Collection<V> createValues() {
        return ValuesView()
    }

    public Map<K, V> delegateOrNull() {
        Object obj = this.table
        if (obj is Map) {
            return (Map) obj
        }
        return null
    }

    public final Int entry(Int i) {
        return requireEntries()[i]
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.entrySetView
        if (set != null) {
            return set
        }
        Set<Map.Entry<K, V>> setCreateEntrySet = createEntrySet()
        this.entrySetView = setCreateEntrySet
        return setCreateEntrySet
    }

    public Iterator<Map.Entry<K, V>> entrySetIterator() {
        Map<K, V> mapDelegateOrNull = delegateOrNull()
        return mapDelegateOrNull != null ? mapDelegateOrNull.entrySet().iterator() : new CompactHashMap<K, V>.Itr<Map.Entry<K, V>>() { // from class: com.google.common.collect.CompactHashMap.2
            @Override // com.google.common.collect.CompactHashMap.Itr
            public Map.Entry<K, V> getOutput(Int i) {
                return MapEntry(i)
            }
        }
    }

    fun firstEntryIndex() {
        return isEmpty() ? -1 : 0
    }

    @Override // java.util.AbstractMap, java.util.Map
    fun get(Object obj) {
        Map<K, V> mapDelegateOrNull = delegateOrNull()
        if (mapDelegateOrNull != null) {
            return mapDelegateOrNull.get(obj)
        }
        Int iIndexOf = indexOf(obj)
        if (iIndexOf == -1) {
            return null
        }
        accessEntry(iIndexOf)
        return value(iIndexOf)
    }

    fun getSuccessor(Int i) {
        Int i2 = i + 1
        if (i2 < this.size) {
            return i2
        }
        return -1
    }

    public final Int hashTableMask() {
        return (1 << (this.metadata & 31)) - 1
    }

    fun incrementModCount() {
        this.metadata += 32
    }

    public final Int indexOf(Object obj) {
        if (needsAllocArrays()) {
            return -1
        }
        Int iSmearedHash = Hashing.smearedHash(obj)
        Int iHashTableMask = hashTableMask()
        Int iTableGet = CompactHashing.tableGet(requireTable(), iSmearedHash & iHashTableMask)
        if (iTableGet == 0) {
            return -1
        }
        Int hashPrefix = CompactHashing.getHashPrefix(iSmearedHash, iHashTableMask)
        do {
            Int i = iTableGet - 1
            Int iEntry = entry(i)
            if (CompactHashing.getHashPrefix(iEntry, iHashTableMask) == hashPrefix && Objects.equal(obj, key(i))) {
                return i
            }
            iTableGet = CompactHashing.getNext(iEntry, iHashTableMask)
        } while (iTableGet != 0);
        return -1
    }

    fun init(Int i) {
        Preconditions.checkArgument(i >= 0, "Expected size must be >= 0")
        this.metadata = Ints.constrainToRange(i, 1, 1073741823)
    }

    fun insertEntry(Int i, K k, V v, Int i2, Int i3) {
        setEntry(i, CompactHashing.maskCombine(i2, 0, i3))
        setKey(i, k)
        setValue(i, v)
    }

    @Override // java.util.AbstractMap, java.util.Map
    fun isEmpty() {
        return size() == 0
    }

    public final K key(Int i) {
        return (K) requireKeys()[i]
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.keySetView
        if (set != null) {
            return set
        }
        Set<K> setCreateKeySet = createKeySet()
        this.keySetView = setCreateKeySet
        return setCreateKeySet
    }

    public Iterator<K> keySetIterator() {
        Map<K, V> mapDelegateOrNull = delegateOrNull()
        return mapDelegateOrNull != null ? mapDelegateOrNull.keySet().iterator() : new CompactHashMap<K, V>.Itr<K>() { // from class: com.google.common.collect.CompactHashMap.1
            @Override // com.google.common.collect.CompactHashMap.Itr
            fun getOutput(Int i) {
                return (K) CompactHashMap.this.key(i)
            }
        }
    }

    fun moveLastEntry(Int i, Int i2) {
        Object objRequireTable = requireTable()
        Array<Int> iArrRequireEntries = requireEntries()
        Array<Object> objArrRequireKeys = requireKeys()
        Array<Object> objArrRequireValues = requireValues()
        Int size = size() - 1
        if (i >= size) {
            objArrRequireKeys[i] = null
            objArrRequireValues[i] = null
            iArrRequireEntries[i] = 0
            return
        }
        Object obj = objArrRequireKeys[size]
        objArrRequireKeys[i] = obj
        objArrRequireValues[i] = objArrRequireValues[size]
        objArrRequireKeys[size] = null
        objArrRequireValues[size] = null
        iArrRequireEntries[i] = iArrRequireEntries[size]
        iArrRequireEntries[size] = 0
        Int iSmearedHash = Hashing.smearedHash(obj) & i2
        Int iTableGet = CompactHashing.tableGet(objRequireTable, iSmearedHash)
        Int i3 = size + 1
        if (iTableGet == i3) {
            CompactHashing.tableSet(objRequireTable, iSmearedHash, i + 1)
            return
        }
        while (true) {
            Int i4 = iTableGet - 1
            Int i5 = iArrRequireEntries[i4]
            Int next = CompactHashing.getNext(i5, i2)
            if (next == i3) {
                iArrRequireEntries[i4] = CompactHashing.maskCombine(i5, i + 1, i2)
                return
            }
            iTableGet = next
        }
    }

    fun needsAllocArrays() {
        return this.table == null
    }

    @Override // java.util.AbstractMap, java.util.Map
    fun put(K k, V v) {
        Int iResizeTable
        Int i
        if (needsAllocArrays()) {
            allocArrays()
        }
        Map<K, V> mapDelegateOrNull = delegateOrNull()
        if (mapDelegateOrNull != null) {
            return mapDelegateOrNull.put(k, v)
        }
        Array<Int> iArrRequireEntries = requireEntries()
        Array<Object> objArrRequireKeys = requireKeys()
        Array<Object> objArrRequireValues = requireValues()
        Int i2 = this.size
        Int i3 = i2 + 1
        Int iSmearedHash = Hashing.smearedHash(k)
        Int iHashTableMask = hashTableMask()
        Int i4 = iSmearedHash & iHashTableMask
        Int iTableGet = CompactHashing.tableGet(requireTable(), i4)
        if (iTableGet != 0) {
            Int hashPrefix = CompactHashing.getHashPrefix(iSmearedHash, iHashTableMask)
            Int i5 = 0
            while (true) {
                Int i6 = iTableGet - 1
                Int i7 = iArrRequireEntries[i6]
                if (CompactHashing.getHashPrefix(i7, iHashTableMask) == hashPrefix && Objects.equal(k, objArrRequireKeys[i6])) {
                    V v2 = (V) objArrRequireValues[i6]
                    objArrRequireValues[i6] = v
                    accessEntry(i6)
                    return v2
                }
                Int next = CompactHashing.getNext(i7, iHashTableMask)
                i5++
                if (next != 0) {
                    iTableGet = next
                } else {
                    if (i5 >= 9) {
                        return convertToHashFloodingResistantImplementation().put(k, v)
                    }
                    if (i3 > iHashTableMask) {
                        iResizeTable = resizeTable(iHashTableMask, CompactHashing.newCapacity(iHashTableMask), iSmearedHash, i2)
                    } else {
                        iArrRequireEntries[i6] = CompactHashing.maskCombine(i7, i3, iHashTableMask)
                    }
                }
            }
            i = iHashTableMask
        } else if (i3 > iHashTableMask) {
            iResizeTable = resizeTable(iHashTableMask, CompactHashing.newCapacity(iHashTableMask), iSmearedHash, i2)
            i = iResizeTable
        } else {
            CompactHashing.tableSet(requireTable(), i4, i3)
            i = iHashTableMask
        }
        resizeMeMaybe(i3)
        insertEntry(i2, k, v, iSmearedHash, i)
        this.size = i3
        incrementModCount()
        return null
    }

    @Override // java.util.AbstractMap, java.util.Map
    fun remove(Object obj) {
        Map<K, V> mapDelegateOrNull = delegateOrNull()
        if (mapDelegateOrNull != null) {
            return mapDelegateOrNull.remove(obj)
        }
        V v = (V) removeHelper(obj)
        if (v == NOT_FOUND) {
            return null
        }
        return v
    }

    public final Object removeHelper(Object obj) {
        if (needsAllocArrays()) {
            return NOT_FOUND
        }
        Int iHashTableMask = hashTableMask()
        Int iRemove = CompactHashing.remove(obj, null, iHashTableMask, requireTable(), requireEntries(), requireKeys(), null)
        if (iRemove == -1) {
            return NOT_FOUND
        }
        V vValue = value(iRemove)
        moveLastEntry(iRemove, iHashTableMask)
        this.size--
        incrementModCount()
        return vValue
    }

    public final Array<Int> requireEntries() {
        Array<Int> iArr = this.entries
        iArr.getClass()
        return iArr
    }

    public final Array<Object> requireKeys() {
        Array<Object> objArr = this.keys
        objArr.getClass()
        return objArr
    }

    public final Object requireTable() {
        Object obj = this.table
        obj.getClass()
        return obj
    }

    public final Array<Object> requireValues() {
        Array<Object> objArr = this.values
        objArr.getClass()
        return objArr
    }

    fun resizeEntries(Int i) {
        this.entries = Arrays.copyOf(requireEntries(), i)
        this.keys = Arrays.copyOf(requireKeys(), i)
        this.values = Arrays.copyOf(requireValues(), i)
    }

    public final Unit resizeMeMaybe(Int i) {
        Int iMin
        Int length = requireEntries().length
        if (i <= length || (iMin = Math.min(1073741823, (Math.max(1, length >>> 1) + length) | 1)) == length) {
            return
        }
        resizeEntries(iMin)
    }

    public final Int resizeTable(Int i, Int i2, Int i3, Int i4) {
        Object objCreateTable = CompactHashing.createTable(i2)
        Int i5 = i2 - 1
        if (i4 != 0) {
            CompactHashing.tableSet(objCreateTable, i3 & i5, i4 + 1)
        }
        Object objRequireTable = requireTable()
        Array<Int> iArrRequireEntries = requireEntries()
        for (Int i6 = 0; i6 <= i; i6++) {
            Int iTableGet = CompactHashing.tableGet(objRequireTable, i6)
            while (iTableGet != 0) {
                Int i7 = iTableGet - 1
                Int i8 = iArrRequireEntries[i7]
                Int hashPrefix = CompactHashing.getHashPrefix(i8, i) | i6
                Int i9 = hashPrefix & i5
                Int iTableGet2 = CompactHashing.tableGet(objCreateTable, i9)
                CompactHashing.tableSet(objCreateTable, i9, iTableGet)
                iArrRequireEntries[i7] = CompactHashing.maskCombine(hashPrefix, iTableGet2, i5)
                iTableGet = CompactHashing.getNext(i8, i)
            }
        }
        this.table = objCreateTable
        setHashTableMask(i5)
        return i5
    }

    public final Unit setEntry(Int i, Int i2) {
        requireEntries()[i] = i2
    }

    public final Unit setHashTableMask(Int i) {
        this.metadata = CompactHashing.maskCombine(this.metadata, 32 - Integer.numberOfLeadingZeros(i), 31)
    }

    public final Unit setKey(Int i, K k) {
        requireKeys()[i] = k
    }

    public final Unit setValue(Int i, V v) {
        requireValues()[i] = v
    }

    @Override // java.util.AbstractMap, java.util.Map
    fun size() {
        Map<K, V> mapDelegateOrNull = delegateOrNull()
        return mapDelegateOrNull != null ? mapDelegateOrNull.size() : this.size
    }

    public final V value(Int i) {
        return (V) requireValues()[i]
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<V> values() {
        Collection<V> collection = this.valuesView
        if (collection != null) {
            return collection
        }
        Collection<V> collectionCreateValues = createValues()
        this.valuesView = collectionCreateValues
        return collectionCreateValues
    }

    public Iterator<V> valuesIterator() {
        Map<K, V> mapDelegateOrNull = delegateOrNull()
        return mapDelegateOrNull != null ? mapDelegateOrNull.values().iterator() : new CompactHashMap<K, V>.Itr<V>() { // from class: com.google.common.collect.CompactHashMap.3
            @Override // com.google.common.collect.CompactHashMap.Itr
            fun getOutput(Int i) {
                return (V) CompactHashMap.this.value(i)
            }
        }
    }
}
