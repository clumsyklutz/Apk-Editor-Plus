package com.google.common.collect

import com.google.common.base.Objects
import java.util.Map

abstract class AbstractMapEntry<K, V> implements Map.Entry<K, V> {
    @Override // java.util.Map.Entry
    fun equals(Object obj) {
        if (!(obj is Map.Entry)) {
            return false
        }
        Map.Entry entry = (Map.Entry) obj
        return Objects.equal(getKey(), entry.getKey()) && Objects.equal(getValue(), entry.getValue())
    }

    @Override // java.util.Map.Entry
    public abstract K getKey()

    @Override // java.util.Map.Entry
    public abstract V getValue()

    @Override // java.util.Map.Entry
    fun hashCode() {
        K key = getKey()
        V value = getValue()
        return (key == null ? 0 : key.hashCode()) ^ (value != null ? value.hashCode() : 0)
    }

    fun toString() {
        String strValueOf = String.valueOf(getKey())
        String strValueOf2 = String.valueOf(getValue())
        StringBuilder sb = StringBuilder(strValueOf.length() + 1 + strValueOf2.length())
        sb.append(strValueOf)
        sb.append("=")
        sb.append(strValueOf2)
        return sb.toString()
    }
}
