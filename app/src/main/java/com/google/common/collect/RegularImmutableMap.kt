package com.google.common.collect

import com.google.common.base.Preconditions
import java.util.AbstractMap
import java.util.Map

class RegularImmutableMap<K, V> extends ImmutableMap<K, V> {
    public static final ImmutableMap<Object, Object> EMPTY = RegularImmutableMap(null, new Object[0], 0)
    public final transient Array<Object> alternatingKeysAndValues
    public final transient Object hashTable
    public final transient Int size

    public static class EntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {
        public final transient Array<Object> alternatingKeysAndValues
        public final transient Int keyOffset
        public final transient ImmutableMap<K, V> map
        public final transient Int size

        constructor(ImmutableMap<K, V> immutableMap, Array<Object> objArr, Int i, Int i2) {
            this.map = immutableMap
            this.alternatingKeysAndValues = objArr
            this.keyOffset = i
            this.size = i2
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
        fun contains(Object obj) {
            if (!(obj is Map.Entry)) {
                return false
            }
            Map.Entry entry = (Map.Entry) obj
            Object key = entry.getKey()
            Object value = entry.getValue()
            return value != null && value.equals(this.map.get(key))
        }

        @Override // com.google.common.collect.ImmutableCollection
        fun copyIntoArray(Array<Object> objArr, Int i) {
            return asList().copyIntoArray(objArr, i)
        }

        @Override // com.google.common.collect.ImmutableSet
        public ImmutableList<Map.Entry<K, V>> createAsList() {
            return new ImmutableList<Map.Entry<K, V>>() { // from class: com.google.common.collect.RegularImmutableMap.EntrySet.1
                @Override // java.util.List
                public Map.Entry<K, V> get(Int i) {
                    Preconditions.checkElementIndex(i, EntrySet.this.size)
                    Int i2 = i * 2
                    Object obj = EntrySet.this.alternatingKeysAndValues[EntrySet.this.keyOffset + i2]
                    obj.getClass()
                    Object obj2 = EntrySet.this.alternatingKeysAndValues[i2 + (EntrySet.this.keyOffset ^ 1)]
                    obj2.getClass()
                    return new AbstractMap.SimpleImmutableEntry(obj, obj2)
                }

                @Override // com.google.common.collect.ImmutableCollection
                fun isPartialView() {
                    return true
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                fun size() {
                    return EntrySet.this.size
                }
            }
        }

        @Override // com.google.common.collect.ImmutableCollection
        fun isPartialView() {
            return true
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return asList().iterator()
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun size() {
            return this.size
        }
    }

    public static final class KeySet<K> extends ImmutableSet<K> {
        public final transient ImmutableList<K> list
        public final transient ImmutableMap<K, ?> map

        constructor(ImmutableMap<K, ?> immutableMap, ImmutableList<K> immutableList) {
            this.map = immutableMap
            this.list = immutableList
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
        public ImmutableList<K> asList() {
            return this.list
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
        fun contains(Object obj) {
            return this.map.get(obj) != null
        }

        @Override // com.google.common.collect.ImmutableCollection
        fun copyIntoArray(Array<Object> objArr, Int i) {
            return asList().copyIntoArray(objArr, i)
        }

        @Override // com.google.common.collect.ImmutableCollection
        fun isPartialView() {
            return true
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public UnmodifiableIterator<K> iterator() {
            return asList().iterator()
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun size() {
            return this.map.size()
        }
    }

    public static final class KeysOrValuesAsList extends ImmutableList<Object> {
        public final transient Array<Object> alternatingKeysAndValues
        public final transient Int offset
        public final transient Int size

        constructor(Array<Object> objArr, Int i, Int i2) {
            this.alternatingKeysAndValues = objArr
            this.offset = i
            this.size = i2
        }

        @Override // java.util.List
        fun get(Int i) {
            Preconditions.checkElementIndex(i, this.size)
            Object obj = this.alternatingKeysAndValues[(i * 2) + this.offset]
            obj.getClass()
            return obj
        }

        @Override // com.google.common.collect.ImmutableCollection
        fun isPartialView() {
            return true
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        fun size() {
            return this.size
        }
    }

    constructor(Object obj, Array<Object> objArr, Int i) {
        this.hashTable = obj
        this.alternatingKeysAndValues = objArr
        this.size = i
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0045, code lost:
    
        r11[r5] = (Byte) r1
        r2 = r2 + 1
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x008b, code lost:
    
        r11[r5] = (Short) r1
        r2 = r2 + 1
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00c8, code lost:
    
        r11[r6] = r1
        r2 = r2 + 1
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object createHashTable(java.lang.Array<Object> r9, Int r10, Int r11, Int r12) {
        /*
            Method dump skipped, instructions count: 222
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularImmutableMap.createHashTable(java.lang.Array<Object>, Int, Int, Int):java.lang.Object")
    }

    fun duplicateKeyException(Object obj, Object obj2, Array<Object> objArr, Int i) {
        String strValueOf = String.valueOf(obj)
        String strValueOf2 = String.valueOf(obj2)
        String strValueOf3 = String.valueOf(objArr[i])
        String strValueOf4 = String.valueOf(objArr[i ^ 1])
        StringBuilder sb = StringBuilder(strValueOf.length() + 39 + strValueOf2.length() + strValueOf3.length() + strValueOf4.length())
        sb.append("Multiple entries with same key: ")
        sb.append(strValueOf)
        sb.append("=")
        sb.append(strValueOf2)
        sb.append(" and ")
        sb.append(strValueOf3)
        sb.append("=")
        sb.append(strValueOf4)
        return IllegalArgumentException(sb.toString())
    }

    fun get(Object obj, Array<Object> objArr, Int i, Int i2, Object obj2) {
        if (obj2 == null) {
            return null
        }
        if (i == 1) {
            Object obj3 = objArr[i2]
            obj3.getClass()
            if (!obj3.equals(obj2)) {
                return null
            }
            Object obj4 = objArr[i2 ^ 1]
            obj4.getClass()
            return obj4
        }
        if (obj == null) {
            return null
        }
        if (obj is Array<Byte>) {
            Array<Byte> bArr = (Array<Byte>) obj
            Int length = bArr.length - 1
            Int iSmear = Hashing.smear(obj2.hashCode())
            while (true) {
                Int i3 = iSmear & length
                Int i4 = bArr[i3] & 255
                if (i4 == 255) {
                    return null
                }
                if (obj2.equals(objArr[i4])) {
                    return objArr[i4 ^ 1]
                }
                iSmear = i3 + 1
            }
        } else if (obj is Array<Short>) {
            Array<Short> sArr = (Array<Short>) obj
            Int length2 = sArr.length - 1
            Int iSmear2 = Hashing.smear(obj2.hashCode())
            while (true) {
                Int i5 = iSmear2 & length2
                Int i6 = sArr[i5] & 65535
                if (i6 == 65535) {
                    return null
                }
                if (obj2.equals(objArr[i6])) {
                    return objArr[i6 ^ 1]
                }
                iSmear2 = i5 + 1
            }
        } else {
            Array<Int> iArr = (Array<Int>) obj
            Int length3 = iArr.length - 1
            Int iSmear3 = Hashing.smear(obj2.hashCode())
            while (true) {
                Int i7 = iSmear3 & length3
                Int i8 = iArr[i7]
                if (i8 == -1) {
                    return null
                }
                if (obj2.equals(objArr[i8])) {
                    return objArr[i8 ^ 1]
                }
                iSmear3 = i7 + 1
            }
        }
    }

    @Override // com.google.common.collect.ImmutableMap
    public ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return EntrySet(this, this.alternatingKeysAndValues, 0, this.size)
    }

    @Override // com.google.common.collect.ImmutableMap
    public ImmutableSet<K> createKeySet() {
        return KeySet(this, KeysOrValuesAsList(this.alternatingKeysAndValues, 0, this.size))
    }

    @Override // com.google.common.collect.ImmutableMap
    public ImmutableCollection<V> createValues() {
        return KeysOrValuesAsList(this.alternatingKeysAndValues, 1, this.size)
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    fun get(Object obj) {
        V v = (V) get(this.hashTable, this.alternatingKeysAndValues, this.size, 0, obj)
        if (v == null) {
            return null
        }
        return v
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
