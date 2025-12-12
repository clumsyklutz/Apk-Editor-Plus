package com.google.common.collect

import java.io.Serializable

class ImmutableEntry<K, V> extends AbstractMapEntry<K, V> implements Serializable {
    public final K key
    public final V value

    constructor(K k, V v) {
        this.key = k
        this.value = v
    }

    @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
    public final K getKey() {
        return this.key
    }

    @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
    public final V getValue() {
        return this.value
    }

    @Override // java.util.Map.Entry
    public final V setValue(V v) {
        throw UnsupportedOperationException()
    }
}
