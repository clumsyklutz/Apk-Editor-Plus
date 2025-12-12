package com.google.common.collect

import android.support.v7.widget.ActivityChooserView
import com.google.common.base.Function
import com.google.common.base.Objects
import com.google.common.base.Preconditions
import com.google.common.collect.Sets
import java.util.AbstractCollection
import java.util.AbstractMap
import java.util.Collection
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.LinkedHashMap
import java.util.Map
import java.util.Set
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class Maps {

    /* renamed from: com.google.common.collect.Maps$1, reason: invalid class name */
    class AnonymousClass1 extends TransformedIterator<Map.Entry<Object, Object>, Object> {
    }

    public enum EntryFunction implements Function<Map.Entry<?, ?>, Object> {
        KEY { // from class: com.google.common.collect.Maps.EntryFunction.1
            @Override // com.google.common.base.Function
            fun apply(Map.Entry<?, ?> entry) {
                return entry.getKey()
            }
        },
        VALUE { // from class: com.google.common.collect.Maps.EntryFunction.2
            @Override // com.google.common.base.Function
            fun apply(Map.Entry<?, ?> entry) {
                return entry.getValue()
            }
        }

        /* synthetic */ EntryFunction(AnonymousClass1 anonymousClass1) {
            this()
        }
    }

    public static abstract class EntrySet<K, V> extends Sets.ImprovedAbstractSet<Map.Entry<K, V>> {
        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun clear() {
            map().clear()
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public abstract Boolean contains(Object obj)

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun isEmpty() {
            return map().isEmpty()
        }

        public abstract Map<K, V> map()

        @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun removeAll(Collection<?> collection) {
            try {
                return super.removeAll((Collection) Preconditions.checkNotNull(collection))
            } catch (UnsupportedOperationException unused) {
                return Sets.removeAllImpl(this, collection.iterator())
            }
        }

        @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun retainAll(Collection<?> collection) {
            try {
                return super.retainAll((Collection) Preconditions.checkNotNull(collection))
            } catch (UnsupportedOperationException unused) {
                HashSet hashSetNewHashSetWithExpectedSize = Sets.newHashSetWithExpectedSize(collection.size())
                for (Object obj : collection) {
                    if (contains(obj) && (obj is Map.Entry)) {
                        hashSetNewHashSetWithExpectedSize.add(((Map.Entry) obj).getKey())
                    }
                }
                return map().keySet().retainAll(hashSetNewHashSetWithExpectedSize)
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun size() {
            return map().size()
        }
    }

    public static class KeySet<K, V> extends Sets.ImprovedAbstractSet<K> {
        public final Map<K, V> map

        constructor(Map<K, V> map) {
            this.map = (Map) Preconditions.checkNotNull(map)
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun contains(Object obj) {
            return map().containsKey(obj)
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun isEmpty() {
            return map().isEmpty()
        }

        public Map<K, V> map() {
            return this.map
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun size() {
            return map().size()
        }
    }

    public static class Values<K, V> extends AbstractCollection<V> {
        public final Map<K, V> map

        constructor(Map<K, V> map) {
            this.map = (Map) Preconditions.checkNotNull(map)
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun clear() {
            map().clear()
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun contains(Object obj) {
            return map().containsValue(obj)
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun isEmpty() {
            return map().isEmpty()
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return Maps.valueIterator(map().entrySet().iterator())
        }

        public final Map<K, V> map() {
            return this.map
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun remove(Object obj) {
            try {
                return super.remove(obj)
            } catch (UnsupportedOperationException unused) {
                for (Map.Entry<K, V> entry : map().entrySet()) {
                    if (Objects.equal(obj, entry.getValue())) {
                        map().remove(entry.getKey())
                        return true
                    }
                }
                return false
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun removeAll(Collection<?> collection) {
            try {
                return super.removeAll((Collection) Preconditions.checkNotNull(collection))
            } catch (UnsupportedOperationException unused) {
                HashSet hashSetNewHashSet = Sets.newHashSet()
                for (Map.Entry<K, V> entry : map().entrySet()) {
                    if (collection.contains(entry.getValue())) {
                        hashSetNewHashSet.add(entry.getKey())
                    }
                }
                return map().keySet().removeAll(hashSetNewHashSet)
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun retainAll(Collection<?> collection) {
            try {
                return super.retainAll((Collection) Preconditions.checkNotNull(collection))
            } catch (UnsupportedOperationException unused) {
                HashSet hashSetNewHashSet = Sets.newHashSet()
                for (Map.Entry<K, V> entry : map().entrySet()) {
                    if (collection.contains(entry.getValue())) {
                        hashSetNewHashSet.add(entry.getKey())
                    }
                }
                return map().keySet().retainAll(hashSetNewHashSet)
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun size() {
            return map().size()
        }
    }

    public static abstract class ViewCachingAbstractMap<K, V> extends AbstractMap<K, V> {
        public transient Set<Map.Entry<K, V>> entrySet
        public transient Collection<V> values

        public abstract Set<Map.Entry<K, V>> createEntrySet()

        public Collection<V> createValues() {
            return Values(this)
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set = this.entrySet
            if (set != null) {
                return set
            }
            Set<Map.Entry<K, V>> setCreateEntrySet = createEntrySet()
            this.entrySet = setCreateEntrySet
            return setCreateEntrySet
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Collection<V> values() {
            Collection<V> collection = this.values
            if (collection != null) {
                return collection
            }
            Collection<V> collectionCreateValues = createValues()
            this.values = collectionCreateValues
            return collectionCreateValues
        }
    }

    fun capacity(Int i) {
        if (i >= 3) {
            return i < 1073741824 ? (Int) ((i / 0.75f) + 1.0f) : ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED
        }
        CollectPreconditions.checkNonnegative(i, "expectedSize")
        return i + 1
    }

    fun equalsImpl(Map<?, ?> map, Object obj) {
        if (map == obj) {
            return true
        }
        if (obj is Map) {
            return map.entrySet().equals(((Map) obj).entrySet())
        }
        return false
    }

    public static <K, V> Map.Entry<K, V> immutableEntry(K k, V v) {
        return ImmutableEntry(k, v)
    }

    public static <K> Function<Map.Entry<K, ?>, K> keyFunction() {
        return EntryFunction.KEY
    }

    public static <K> K keyOrNull(Map.Entry<K, ?> entry) {
        if (entry == null) {
            return null
        }
        return entry.getKey()
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return ConcurrentHashMap()
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>()
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>()
    }

    fun safeContainsKey(Map<?, ?> map, Object obj) {
        Preconditions.checkNotNull(map)
        try {
            return map.containsKey(obj)
        } catch (ClassCastException | NullPointerException unused) {
            return false
        }
    }

    public static <V> V safeGet(Map<?, V> map, Object obj) {
        Preconditions.checkNotNull(map)
        try {
            return map.get(obj)
        } catch (ClassCastException | NullPointerException unused) {
            return null
        }
    }

    public static <V> V safeRemove(Map<?, V> map, Object obj) {
        Preconditions.checkNotNull(map)
        try {
            return map.remove(obj)
        } catch (ClassCastException | NullPointerException unused) {
            return null
        }
    }

    fun toStringImpl(Map<?, ?> map) {
        StringBuilder sbNewStringBuilderForCollection = Collections2.newStringBuilderForCollection(map.size())
        sbNewStringBuilderForCollection.append('{')
        Boolean z = true
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!z) {
                sbNewStringBuilderForCollection.append(", ")
            }
            z = false
            sbNewStringBuilderForCollection.append(entry.getKey())
            sbNewStringBuilderForCollection.append('=')
            sbNewStringBuilderForCollection.append(entry.getValue())
        }
        sbNewStringBuilderForCollection.append('}')
        return sbNewStringBuilderForCollection.toString()
    }

    public static <V> Function<Map.Entry<?, V>, V> valueFunction() {
        return EntryFunction.VALUE
    }

    public static <K, V> Iterator<V> valueIterator(Iterator<Map.Entry<K, V>> it) {
        return new TransformedIterator<Map.Entry<K, V>, V>(it) { // from class: com.google.common.collect.Maps.2
            @Override // com.google.common.collect.TransformedIterator
            fun transform(Map.Entry<K, V> entry) {
                return entry.getValue()
            }
        }
    }
}
