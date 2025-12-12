package com.google.common.collect

import com.google.common.collect.RegularImmutableMap
import java.util.Map

class RegularImmutableBiMap<K, V> extends ImmutableBiMap<K, V> {
    public static final RegularImmutableBiMap<Object, Object> EMPTY = new RegularImmutableBiMap<>()
    public final transient Array<Object> alternatingKeysAndValues
    public final transient RegularImmutableBiMap<V, K> inverse
    public final transient Object keyHashTable
    public final transient Int keyOffset
    public final transient Int size

    /* JADX WARN: Multi-variable type inference failed */
    constructor() {
        this.keyHashTable = null
        this.alternatingKeysAndValues = new Object[0]
        this.keyOffset = 0
        this.size = 0
        this.inverse = this
    }

    constructor(Object obj, Array<Object> objArr, Int i, RegularImmutableBiMap<V, K> regularImmutableBiMap) {
        this.keyHashTable = obj
        this.alternatingKeysAndValues = objArr
        this.keyOffset = 1
        this.size = i
        this.inverse = regularImmutableBiMap
    }

    constructor(Array<Object> objArr, Int i) {
        this.alternatingKeysAndValues = objArr
        this.size = i
        this.keyOffset = 0
        Int iChooseTableSize = i >= 2 ? ImmutableSet.chooseTableSize(i) : 0
        this.keyHashTable = RegularImmutableMap.createHashTable(objArr, i, iChooseTableSize, 0)
        this.inverse = new RegularImmutableBiMap<>(RegularImmutableMap.createHashTable(objArr, i, iChooseTableSize, 1), objArr, i, this)
    }

    @Override // com.google.common.collect.ImmutableMap
    public ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return new RegularImmutableMap.EntrySet(this, this.alternatingKeysAndValues, this.keyOffset, this.size)
    }

    @Override // com.google.common.collect.ImmutableMap
    public ImmutableSet<K> createKeySet() {
        return new RegularImmutableMap.KeySet(this, new RegularImmutableMap.KeysOrValuesAsList(this.alternatingKeysAndValues, this.keyOffset, this.size))
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    fun get(Object obj) {
        V v = (V) RegularImmutableMap.get(this.keyHashTable, this.alternatingKeysAndValues, this.size, this.keyOffset, obj)
        if (v == null) {
            return null
        }
        return v
    }

    @Override // com.google.common.collect.BiMap
    public ImmutableBiMap<V, K> inverse() {
        return this.inverse
    }

    @Override // com.google.common.collect.ImmutableMap
    fun isPartialView() {
        return false
    }

    @Override // java.util.Map
    fun size() {
        return this.size
    }
}
