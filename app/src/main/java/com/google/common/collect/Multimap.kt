package com.google.common.collect

import java.util.Collection
import java.util.Map

public interface Multimap<K, V> {
    Map<K, Collection<V>> asMap()

    Collection<V> get(K k)
}
