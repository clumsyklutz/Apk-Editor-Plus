package com.google.common.collect

import java.util.ArrayList
import java.util.List

class ArrayListMultimap<K, V> extends ArrayListMultimapGwtSerializationDependencies<K, V> {
    public transient Int expectedValuesPerKey

    constructor() {
        this(12, 3)
    }

    constructor(Int i, Int i2) {
        super(Platform.newHashMapWithExpectedSize(i))
        CollectPreconditions.checkNonnegative(i2, "expectedValuesPerKey")
        this.expectedValuesPerKey = i2
    }

    public static <K, V> ArrayListMultimap<K, V> create() {
        return new ArrayListMultimap<>()
    }

    @Override // com.google.common.collect.AbstractMapBasedMultimap
    public List<V> createCollection() {
        return ArrayList(this.expectedValuesPerKey)
    }
}
