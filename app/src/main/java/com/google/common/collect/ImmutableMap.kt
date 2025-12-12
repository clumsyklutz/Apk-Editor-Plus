package com.google.common.collect

import com.google.common.collect.ImmutableCollection
import java.io.Serializable
import java.util.AbstractMap
import java.util.Arrays
import java.util.Comparator
import java.util.Map

abstract class ImmutableMap<K, V> implements Map<K, V>, Serializable {
    public transient ImmutableSet<Map.Entry<K, V>> entrySet
    public transient ImmutableSet<K> keySet
    public transient ImmutableCollection<V> values

    public static class Builder<K, V> {
        public Array<Object> alternatingKeysAndValues
        public Boolean entriesUsed
        public Int size
        public Comparator<? super V> valueComparator

        constructor() {
            this(4)
        }

        constructor(Int i) {
            this.alternatingKeysAndValues = new Object[i * 2]
            this.size = 0
            this.entriesUsed = false
        }

        public final Unit ensureCapacity(Int i) {
            Int i2 = i * 2
            Array<Object> objArr = this.alternatingKeysAndValues
            if (i2 > objArr.length) {
                this.alternatingKeysAndValues = Arrays.copyOf(objArr, ImmutableCollection.Builder.expandedCapacity(objArr.length, i2))
                this.entriesUsed = false
            }
        }

        public Builder<K, V> put(K k, V v) {
            ensureCapacity(this.size + 1)
            CollectPreconditions.checkEntryNotNull(k, v)
            Array<Object> objArr = this.alternatingKeysAndValues
            Int i = this.size
            objArr[i * 2] = k
            objArr[(i * 2) + 1] = v
            this.size = i + 1
            return this
        }

        fun sortEntries() {
            Int i
            if (this.valueComparator != null) {
                if (this.entriesUsed) {
                    this.alternatingKeysAndValues = Arrays.copyOf(this.alternatingKeysAndValues, this.size * 2)
                }
                Map.Array<Entry> entryArr = new Map.Entry[this.size]
                Int i2 = 0
                while (true) {
                    i = this.size
                    if (i2 >= i) {
                        break
                    }
                    Int i3 = i2 * 2
                    Object obj = this.alternatingKeysAndValues[i3]
                    obj.getClass()
                    Object obj2 = this.alternatingKeysAndValues[i3 + 1]
                    obj2.getClass()
                    entryArr[i2] = new AbstractMap.SimpleImmutableEntry(obj, obj2)
                    i2++
                }
                Arrays.sort(entryArr, 0, i, Ordering.from(this.valueComparator).onResultOf(Maps.valueFunction()))
                for (Int i4 = 0; i4 < this.size; i4++) {
                    Int i5 = i4 * 2
                    this.alternatingKeysAndValues[i5] = entryArr[i4].getKey()
                    this.alternatingKeysAndValues[i5 + 1] = entryArr[i4].getValue()
                }
            }
        }
    }

    public static <K, V> ImmutableMap<K, V> of() {
        return (ImmutableMap<K, V>) RegularImmutableMap.EMPTY
    }

    @Override // java.util.Map
    @Deprecated
    public final Unit clear() {
        throw UnsupportedOperationException()
    }

    @Override // java.util.Map
    fun containsKey(Object obj) {
        return get(obj) != null
    }

    @Override // java.util.Map
    fun containsValue(Object obj) {
        return values().contains(obj)
    }

    public abstract ImmutableSet<Map.Entry<K, V>> createEntrySet()

    public abstract ImmutableSet<K> createKeySet()

    public abstract ImmutableCollection<V> createValues()

    @Override // java.util.Map
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        ImmutableSet<Map.Entry<K, V>> immutableSet = this.entrySet
        if (immutableSet != null) {
            return immutableSet
        }
        ImmutableSet<Map.Entry<K, V>> immutableSetCreateEntrySet = createEntrySet()
        this.entrySet = immutableSetCreateEntrySet
        return immutableSetCreateEntrySet
    }

    @Override // java.util.Map
    fun equals(Object obj) {
        return Maps.equalsImpl(this, obj)
    }

    @Override // java.util.Map
    public abstract V get(Object obj)

    @Override // java.util.Map
    public final V getOrDefault(Object obj, V v) {
        V v2 = get(obj)
        return v2 != null ? v2 : v
    }

    @Override // java.util.Map
    fun hashCode() {
        return Sets.hashCodeImpl(entrySet())
    }

    @Override // java.util.Map
    fun isEmpty() {
        return size() == 0
    }

    fun isHashCodeFast() {
        return false
    }

    public abstract Boolean isPartialView()

    @Override // java.util.Map
    public ImmutableSet<K> keySet() {
        ImmutableSet<K> immutableSet = this.keySet
        if (immutableSet != null) {
            return immutableSet
        }
        ImmutableSet<K> immutableSetCreateKeySet = createKeySet()
        this.keySet = immutableSetCreateKeySet
        return immutableSetCreateKeySet
    }

    @Override // java.util.Map
    @Deprecated
    public final V put(K k, V v) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.Map
    @Deprecated
    public final Unit putAll(Map<? extends K, ? extends V> map) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.Map
    @Deprecated
    public final V remove(Object obj) {
        throw UnsupportedOperationException()
    }

    fun toString() {
        return Maps.toStringImpl(this)
    }

    @Override // java.util.Map
    public ImmutableCollection<V> values() {
        ImmutableCollection<V> immutableCollection = this.values
        if (immutableCollection != null) {
            return immutableCollection
        }
        ImmutableCollection<V> immutableCollectionCreateValues = createValues()
        this.values = immutableCollectionCreateValues
        return immutableCollectionCreateValues
    }
}
