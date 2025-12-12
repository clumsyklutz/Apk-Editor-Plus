package com.google.common.collect

import java.util.Collection
import java.util.List
import java.util.Map

abstract class AbstractListMultimap<K, V> extends AbstractMapBasedMultimap<K, V> {
    constructor(Map<K, Collection<V>> map) {
        super(map)
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public Map<K, Collection<V>> asMap() {
        return super.asMap()
    }

    @Override // com.google.common.collect.AbstractMultimap
    fun equals(Object obj) {
        return super.equals(obj)
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Collection get(Object obj) {
        return get((AbstractListMultimap<K, V>) obj)
    }

    @Override // com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.Multimap
    public List<V> get(K k) {
        return (List) super.get((AbstractListMultimap<K, V>) k)
    }

    @Override // com.google.common.collect.AbstractMapBasedMultimap
    public Collection<V> wrapCollection(K k, Collection<V> collection) {
        return wrapList(k, (List) collection, null)
    }
}
