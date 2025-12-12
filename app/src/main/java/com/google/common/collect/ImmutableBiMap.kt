package com.google.common.collect

import com.google.common.collect.ImmutableMap

abstract class ImmutableBiMap<K, V> extends ImmutableMap<K, V> implements BiMap<K, V> {

    public static final class Builder<K, V> extends ImmutableMap.Builder<K, V> {
        public ImmutableBiMap<K, V> build() {
            return buildOrThrow()
        }

        public ImmutableBiMap<K, V> buildOrThrow() {
            if (this.size == 0) {
                return ImmutableBiMap.of()
            }
            sortEntries()
            this.entriesUsed = true
            return RegularImmutableBiMap(this.alternatingKeysAndValues, this.size)
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        public Builder<K, V> put(K k, V v) {
            super.put((Builder<K, V>) k, (K) v)
            return this
        }
    }

    public static <K, V> ImmutableBiMap<K, V> of() {
        return RegularImmutableBiMap.EMPTY
    }

    @Override // com.google.common.collect.ImmutableMap
    public final ImmutableSet<V> createValues() {
        throw AssertionError("should never be called")
    }

    @Override // com.google.common.collect.BiMap
    public abstract ImmutableBiMap<V, K> inverse()

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public ImmutableSet<V> values() {
        return inverse().keySet()
    }
}
