package com.google.common.collect

import java.util.Collection
import java.util.Map
import java.util.Set

abstract class AbstractMultimap<K, V> implements Multimap<K, V> {
    public transient Map<K, Collection<V>> asMap
    public transient Set<K> keySet

    @Override // com.google.common.collect.Multimap
    public Map<K, Collection<V>> asMap() {
        Map<K, Collection<V>> map = this.asMap
        if (map != null) {
            return map
        }
        Map<K, Collection<V>> mapCreateAsMap = createAsMap()
        this.asMap = mapCreateAsMap
        return mapCreateAsMap
    }

    public abstract Map<K, Collection<V>> createAsMap()

    public abstract Set<K> createKeySet()

    fun equals(Object obj) {
        return Multimaps.equalsImpl(this, obj)
    }

    fun hashCode() {
        return asMap().hashCode()
    }

    public Set<K> keySet() {
        Set<K> set = this.keySet
        if (set != null) {
            return set
        }
        Set<K> setCreateKeySet = createKeySet()
        this.keySet = setCreateKeySet
        return setCreateKeySet
    }

    fun toString() {
        return asMap().toString()
    }
}
