package com.google.common.collect

import com.google.common.base.Preconditions
import java.util.AbstractMap
import java.util.Comparator
import java.util.Map
import java.util.NavigableMap
import java.util.SortedMap

class ImmutableSortedMap<K, V> extends ImmutableSortedMapFauxverideShim<K, V> implements NavigableMap<K, V> {
    public static final ImmutableSortedMap<Comparable, Object> NATURAL_EMPTY_MAP
    public transient ImmutableSortedMap<K, V> descendingMap
    public final transient RegularImmutableSortedSet<K> keySet
    public final transient ImmutableList<V> valueList

    static {
        Ordering.natural()
        NATURAL_EMPTY_MAP = new ImmutableSortedMap<>(ImmutableSortedSet.emptySet(Ordering.natural()), ImmutableList.of())
    }

    constructor(RegularImmutableSortedSet<K> regularImmutableSortedSet, ImmutableList<V> immutableList) {
        this(regularImmutableSortedSet, immutableList, null)
    }

    constructor(RegularImmutableSortedSet<K> regularImmutableSortedSet, ImmutableList<V> immutableList, ImmutableSortedMap<K, V> immutableSortedMap) {
        this.keySet = regularImmutableSortedSet
        this.valueList = immutableList
        this.descendingMap = immutableSortedMap
    }

    public static <K, V> ImmutableSortedMap<K, V> emptyMap(Comparator<? super K> comparator) {
        return Ordering.natural().equals(comparator) ? of() : new ImmutableSortedMap<>(ImmutableSortedSet.emptySet(comparator), ImmutableList.of())
    }

    public static <K, V> ImmutableSortedMap<K, V> of() {
        return (ImmutableSortedMap<K, V>) NATURAL_EMPTY_MAP
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> ceilingEntry(K k) {
        return tailMap((ImmutableSortedMap<K, V>) k, true).firstEntry()
    }

    @Override // java.util.NavigableMap
    fun ceilingKey(K k) {
        return (K) Maps.keyOrNull(ceilingEntry(k))
    }

    @Override // java.util.SortedMap
    public Comparator<? super K> comparator() {
        return keySet().comparator()
    }

    @Override // com.google.common.collect.ImmutableMap
    public ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return isEmpty() ? ImmutableSet.of() : new ImmutableMapEntrySet<K, V>() { // from class: com.google.common.collect.ImmutableSortedMap.1EntrySet
            @Override // com.google.common.collect.ImmutableSet
            public ImmutableList<Map.Entry<K, V>> createAsList() {
                return new ImmutableList<Map.Entry<K, V>>() { // from class: com.google.common.collect.ImmutableSortedMap.1EntrySet.1
                    @Override // java.util.List
                    public Map.Entry<K, V> get(Int i) {
                        return new AbstractMap.SimpleImmutableEntry(ImmutableSortedMap.this.keySet.asList().get(i), ImmutableSortedMap.this.valueList.get(i))
                    }

                    @Override // com.google.common.collect.ImmutableCollection
                    fun isPartialView() {
                        return true
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                    fun size() {
                        return ImmutableSortedMap.this.size()
                    }
                }
            }

            @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
            public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
                return asList().iterator()
            }

            @Override // com.google.common.collect.ImmutableMapEntrySet
            public ImmutableMap<K, V> map() {
                return ImmutableSortedMap.this
            }
        }
    }

    @Override // com.google.common.collect.ImmutableMap
    public ImmutableSet<K> createKeySet() {
        throw AssertionError("should never be called")
    }

    @Override // com.google.common.collect.ImmutableMap
    public ImmutableCollection<V> createValues() {
        throw AssertionError("should never be called")
    }

    @Override // java.util.NavigableMap
    public ImmutableSortedSet<K> descendingKeySet() {
        return this.keySet.descendingSet()
    }

    @Override // java.util.NavigableMap
    public ImmutableSortedMap<K, V> descendingMap() {
        ImmutableSortedMap<K, V> immutableSortedMap = this.descendingMap
        return immutableSortedMap == null ? isEmpty() ? emptyMap(Ordering.from(comparator()).reverse()) : new ImmutableSortedMap<>((RegularImmutableSortedSet) this.keySet.descendingSet(), this.valueList.reverse(), this) : immutableSortedMap
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        return super.entrySet()
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> firstEntry() {
        if (isEmpty()) {
            return null
        }
        return entrySet().asList().get(0)
    }

    @Override // java.util.SortedMap
    fun firstKey() {
        return keySet().first()
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> floorEntry(K k) {
        return headMap((ImmutableSortedMap<K, V>) k, true).lastEntry()
    }

    @Override // java.util.NavigableMap
    fun floorKey(K k) {
        return (K) Maps.keyOrNull(floorEntry(k))
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    fun get(Object obj) {
        Int iIndexOf = this.keySet.indexOf(obj)
        if (iIndexOf == -1) {
            return null
        }
        return this.valueList.get(iIndexOf)
    }

    public final ImmutableSortedMap<K, V> getSubMap(Int i, Int i2) {
        return (i == 0 && i2 == size()) ? this : i == i2 ? emptyMap(comparator()) : new ImmutableSortedMap<>(this.keySet.getSubSet(i, i2), this.valueList.subList(i, i2))
    }

    @Override // java.util.NavigableMap, java.util.SortedMap
    public ImmutableSortedMap<K, V> headMap(K k) {
        return headMap((ImmutableSortedMap<K, V>) k, false)
    }

    @Override // java.util.NavigableMap
    public ImmutableSortedMap<K, V> headMap(K k, Boolean z) {
        return getSubMap(0, this.keySet.headIndex(Preconditions.checkNotNull(k), z))
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableMap
    public /* bridge */ /* synthetic */ NavigableMap headMap(Object obj, Boolean z) {
        return headMap((ImmutableSortedMap<K, V>) obj, z)
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableMap, java.util.SortedMap
    public /* bridge */ /* synthetic */ SortedMap headMap(Object obj) {
        return headMap((ImmutableSortedMap<K, V>) obj)
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> higherEntry(K k) {
        return tailMap((ImmutableSortedMap<K, V>) k, false).firstEntry()
    }

    @Override // java.util.NavigableMap
    fun higherKey(K k) {
        return (K) Maps.keyOrNull(higherEntry(k))
    }

    @Override // com.google.common.collect.ImmutableMap
    fun isPartialView() {
        return this.keySet.isPartialView() || this.valueList.isPartialView()
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public ImmutableSortedSet<K> keySet() {
        return this.keySet
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> lastEntry() {
        if (isEmpty()) {
            return null
        }
        return entrySet().asList().get(size() - 1)
    }

    @Override // java.util.SortedMap
    fun lastKey() {
        return keySet().last()
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> lowerEntry(K k) {
        return headMap((ImmutableSortedMap<K, V>) k, false).lastEntry()
    }

    @Override // java.util.NavigableMap
    fun lowerKey(K k) {
        return (K) Maps.keyOrNull(lowerEntry(k))
    }

    @Override // java.util.NavigableMap
    public ImmutableSortedSet<K> navigableKeySet() {
        return this.keySet
    }

    @Override // java.util.NavigableMap
    @Deprecated
    public final Map.Entry<K, V> pollFirstEntry() {
        throw UnsupportedOperationException()
    }

    @Override // java.util.NavigableMap
    @Deprecated
    public final Map.Entry<K, V> pollLastEntry() {
        throw UnsupportedOperationException()
    }

    @Override // java.util.Map
    fun size() {
        return this.valueList.size()
    }

    @Override // java.util.NavigableMap, java.util.SortedMap
    public ImmutableSortedMap<K, V> subMap(K k, K k2) {
        return subMap((Boolean) k, true, (Boolean) k2, false)
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableMap
    public ImmutableSortedMap<K, V> subMap(K k, Boolean z, K k2, Boolean z2) {
        Preconditions.checkNotNull(k)
        Preconditions.checkNotNull(k2)
        Preconditions.checkArgument(comparator().compare(k, k2) <= 0, "expected fromKey <= toKey but %s > %s", k, k2)
        return headMap((ImmutableSortedMap<K, V>) k2, z2).tailMap((ImmutableSortedMap<K, V>) k, z)
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableMap
    public /* bridge */ /* synthetic */ NavigableMap subMap(Object obj, Boolean z, Object obj2, Boolean z2) {
        return subMap((Boolean) obj, z, (Boolean) obj2, z2)
    }

    @Override // java.util.NavigableMap, java.util.SortedMap
    public ImmutableSortedMap<K, V> tailMap(K k) {
        return tailMap((ImmutableSortedMap<K, V>) k, true)
    }

    @Override // java.util.NavigableMap
    public ImmutableSortedMap<K, V> tailMap(K k, Boolean z) {
        return getSubMap(this.keySet.tailIndex(Preconditions.checkNotNull(k), z), size())
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableMap
    public /* bridge */ /* synthetic */ NavigableMap tailMap(Object obj, Boolean z) {
        return tailMap((ImmutableSortedMap<K, V>) obj, z)
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableMap, java.util.SortedMap
    public /* bridge */ /* synthetic */ SortedMap tailMap(Object obj) {
        return tailMap((ImmutableSortedMap<K, V>) obj)
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public ImmutableCollection<V> values() {
        return this.valueList
    }
}
