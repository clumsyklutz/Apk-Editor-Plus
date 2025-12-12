package com.google.common.collect

import java.util.Map

abstract class ImmutableMapEntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {
    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
    fun contains(Object obj) {
        if (!(obj is Map.Entry)) {
            return false
        }
        Map.Entry entry = (Map.Entry) obj
        V v = map().get(entry.getKey())
        return v != null && v.equals(entry.getValue())
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    fun hashCode() {
        return map().hashCode()
    }

    @Override // com.google.common.collect.ImmutableSet
    fun isHashCodeFast() {
        return map().isHashCodeFast()
    }

    @Override // com.google.common.collect.ImmutableCollection
    fun isPartialView() {
        return map().isPartialView()
    }

    public abstract ImmutableMap<K, V> map()

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    fun size() {
        return map().size()
    }
}
