package com.google.common.collect

import com.google.common.base.Preconditions
import com.google.common.collect.Maps
import java.io.Serializable
import java.util.AbstractCollection
import java.util.Collection
import java.util.ConcurrentModificationException
import java.util.Iterator
import java.util.List
import java.util.ListIterator
import java.util.Map
import java.util.RandomAccess
import java.util.Set

abstract class AbstractMapBasedMultimap<K, V> extends AbstractMultimap<K, V> implements Serializable {
    public transient Map<K, Collection<V>> map
    public transient Int totalSize

    class AsMap extends Maps.ViewCachingAbstractMap<K, Collection<V>> {
        public final transient Map<K, Collection<V>> submap

        class AsMapEntries extends Maps.EntrySet<K, Collection<V>> {
            constructor() {
            }

            @Override // com.google.common.collect.Maps.EntrySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            fun contains(Object obj) {
                return Collections2.safeContains(AsMap.this.submap.entrySet(), obj)
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return AsMap.this.AsMapIterator()
            }

            @Override // com.google.common.collect.Maps.EntrySet
            public Map<K, Collection<V>> map() {
                return AsMap.this
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            fun remove(Object obj) {
                if (!contains(obj)) {
                    return false
                }
                Map.Entry entry = (Map.Entry) obj
                entry.getClass()
                AbstractMapBasedMultimap.this.removeValuesForKey(entry.getKey())
                return true
            }
        }

        class AsMapIterator implements Iterator<Map.Entry<K, Collection<V>>> {
            public Collection<V> collection
            public final Iterator<Map.Entry<K, Collection<V>>> delegateIterator

            constructor() {
                this.delegateIterator = AsMap.this.submap.entrySet().iterator()
            }

            @Override // java.util.Iterator
            fun hasNext() {
                return this.delegateIterator.hasNext()
            }

            @Override // java.util.Iterator
            public Map.Entry<K, Collection<V>> next() {
                Map.Entry<K, Collection<V>> next = this.delegateIterator.next()
                this.collection = next.getValue()
                return AsMap.this.wrapEntry(next)
            }

            @Override // java.util.Iterator
            fun remove() {
                Preconditions.checkState(this.collection != null, "no calls to next() since the last call to remove()")
                this.delegateIterator.remove()
                AbstractMapBasedMultimap.access$220(AbstractMapBasedMultimap.this, this.collection.size())
                this.collection.clear()
                this.collection = null
            }
        }

        constructor(Map<K, Collection<V>> map) {
            this.submap = map
        }

        @Override // java.util.AbstractMap, java.util.Map
        fun clear() {
            if (this.submap == AbstractMapBasedMultimap.this.map) {
                AbstractMapBasedMultimap.this.clear()
            } else {
                Iterators.clear(AsMapIterator())
            }
        }

        @Override // java.util.AbstractMap, java.util.Map
        fun containsKey(Object obj) {
            return Maps.safeContainsKey(this.submap, obj)
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public Set<Map.Entry<K, Collection<V>>> createEntrySet() {
            return AsMapEntries()
        }

        @Override // java.util.AbstractMap, java.util.Map
        fun equals(Object obj) {
            return this == obj || this.submap.equals(obj)
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Collection<V> get(Object obj) {
            Collection<V> collection = (Collection) Maps.safeGet(this.submap, obj)
            if (collection == null) {
                return null
            }
            return AbstractMapBasedMultimap.this.wrapCollection(obj, collection)
        }

        @Override // java.util.AbstractMap, java.util.Map
        fun hashCode() {
            return this.submap.hashCode()
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            return AbstractMapBasedMultimap.this.keySet()
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Collection<V> remove(Object obj) {
            Collection<V> collectionRemove = this.submap.remove(obj)
            if (collectionRemove == null) {
                return null
            }
            Collection<V> collectionCreateCollection = AbstractMapBasedMultimap.this.createCollection()
            collectionCreateCollection.addAll(collectionRemove)
            AbstractMapBasedMultimap.access$220(AbstractMapBasedMultimap.this, collectionRemove.size())
            collectionRemove.clear()
            return collectionCreateCollection
        }

        @Override // java.util.AbstractMap, java.util.Map
        fun size() {
            return this.submap.size()
        }

        @Override // java.util.AbstractMap
        fun toString() {
            return this.submap.toString()
        }

        public Map.Entry<K, Collection<V>> wrapEntry(Map.Entry<K, Collection<V>> entry) {
            K key = entry.getKey()
            return Maps.immutableEntry(key, AbstractMapBasedMultimap.this.wrapCollection(key, entry.getValue()))
        }
    }

    class KeySet extends Maps.KeySet<K, Collection<V>> {
        constructor(Map<K, Collection<V>> map) {
            super(map)
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun clear() {
            Iterators.clear(iterator())
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun containsAll(Collection<?> collection) {
            return map().keySet().containsAll(collection)
        }

        @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
        fun equals(Object obj) {
            return this == obj || map().keySet().equals(obj)
        }

        @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
        fun hashCode() {
            return map().keySet().hashCode()
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            final Iterator<Map.Entry<K, Collection<V>>> it = map().entrySet().iterator()
            return new Iterator<K>() { // from class: com.google.common.collect.AbstractMapBasedMultimap.KeySet.1
                public Map.Entry<K, Collection<V>> entry

                @Override // java.util.Iterator
                fun hasNext() {
                    return it.hasNext()
                }

                @Override // java.util.Iterator
                fun next() {
                    Map.Entry<K, Collection<V>> entry = (Map.Entry) it.next()
                    this.entry = entry
                    return entry.getKey()
                }

                @Override // java.util.Iterator
                fun remove() {
                    Preconditions.checkState(this.entry != null, "no calls to next() since the last call to remove()")
                    Collection<V> value = this.entry.getValue()
                    it.remove()
                    AbstractMapBasedMultimap.access$220(AbstractMapBasedMultimap.this, value.size())
                    value.clear()
                    this.entry = null
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun remove(Object obj) {
            Int size
            Collection<V> collectionRemove = map().remove(obj)
            if (collectionRemove != null) {
                size = collectionRemove.size()
                collectionRemove.clear()
                AbstractMapBasedMultimap.access$220(AbstractMapBasedMultimap.this, size)
            } else {
                size = 0
            }
            return size > 0
        }
    }

    class RandomAccessWrappedList extends AbstractMapBasedMultimap<K, V>.WrappedList implements RandomAccess {
        constructor(AbstractMapBasedMultimap abstractMapBasedMultimap, K k, List<V> list, AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection) {
            super(k, list, wrappedCollection)
        }
    }

    class WrappedCollection extends AbstractCollection<V> {
        public final AbstractMapBasedMultimap<K, V>.WrappedCollection ancestor
        public final Collection<V> ancestorDelegate
        public Collection<V> delegate
        public final K key

        class WrappedIterator implements Iterator<V> {
            public final Iterator<V> delegateIterator
            public final Collection<V> originalDelegate

            constructor() {
                Collection<V> collection = WrappedCollection.this.delegate
                this.originalDelegate = collection
                this.delegateIterator = AbstractMapBasedMultimap.iteratorOrListIterator(collection)
            }

            constructor(Iterator<V> it) {
                this.originalDelegate = WrappedCollection.this.delegate
                this.delegateIterator = it
            }

            public Iterator<V> getDelegateIterator() {
                validateIterator()
                return this.delegateIterator
            }

            @Override // java.util.Iterator
            fun hasNext() {
                validateIterator()
                return this.delegateIterator.hasNext()
            }

            @Override // java.util.Iterator
            fun next() {
                validateIterator()
                return this.delegateIterator.next()
            }

            @Override // java.util.Iterator
            fun remove() {
                this.delegateIterator.remove()
                AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this)
                WrappedCollection.this.removeIfEmpty()
            }

            fun validateIterator() {
                WrappedCollection.this.refreshIfEmpty()
                if (WrappedCollection.this.delegate != this.originalDelegate) {
                    throw ConcurrentModificationException()
                }
            }
        }

        constructor(K k, Collection<V> collection, AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection) {
            this.key = k
            this.delegate = collection
            this.ancestor = wrappedCollection
            this.ancestorDelegate = wrappedCollection == null ? null : wrappedCollection.getDelegate()
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun add(V v) {
            refreshIfEmpty()
            Boolean zIsEmpty = this.delegate.isEmpty()
            Boolean zAdd = this.delegate.add(v)
            if (zAdd) {
                AbstractMapBasedMultimap.access$208(AbstractMapBasedMultimap.this)
                if (zIsEmpty) {
                    addToMap()
                }
            }
            return zAdd
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun addAll(Collection<? extends V> collection) {
            if (collection.isEmpty()) {
                return false
            }
            Int size = size()
            Boolean zAddAll = this.delegate.addAll(collection)
            if (zAddAll) {
                AbstractMapBasedMultimap.access$212(AbstractMapBasedMultimap.this, this.delegate.size() - size)
                if (size == 0) {
                    addToMap()
                }
            }
            return zAddAll
        }

        fun addToMap() {
            AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection = this.ancestor
            if (wrappedCollection != null) {
                wrappedCollection.addToMap()
            } else {
                AbstractMapBasedMultimap.this.map.put(this.key, this.delegate)
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun clear() {
            Int size = size()
            if (size == 0) {
                return
            }
            this.delegate.clear()
            AbstractMapBasedMultimap.access$220(AbstractMapBasedMultimap.this, size)
            removeIfEmpty()
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun contains(Object obj) {
            refreshIfEmpty()
            return this.delegate.contains(obj)
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun containsAll(Collection<?> collection) {
            refreshIfEmpty()
            return this.delegate.containsAll(collection)
        }

        @Override // java.util.Collection
        fun equals(Object obj) {
            if (obj == this) {
                return true
            }
            refreshIfEmpty()
            return this.delegate.equals(obj)
        }

        public AbstractMapBasedMultimap<K, V>.WrappedCollection getAncestor() {
            return this.ancestor
        }

        public Collection<V> getDelegate() {
            return this.delegate
        }

        K getKey() {
            return this.key
        }

        @Override // java.util.Collection
        fun hashCode() {
            refreshIfEmpty()
            return this.delegate.hashCode()
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            refreshIfEmpty()
            return WrappedIterator()
        }

        fun refreshIfEmpty() {
            Collection<V> collection
            AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection = this.ancestor
            if (wrappedCollection != null) {
                wrappedCollection.refreshIfEmpty()
                if (this.ancestor.getDelegate() != this.ancestorDelegate) {
                    throw ConcurrentModificationException()
                }
            } else {
                if (!this.delegate.isEmpty() || (collection = (Collection) AbstractMapBasedMultimap.this.map.get(this.key)) == null) {
                    return
                }
                this.delegate = collection
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun remove(Object obj) {
            refreshIfEmpty()
            Boolean zRemove = this.delegate.remove(obj)
            if (zRemove) {
                AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this)
                removeIfEmpty()
            }
            return zRemove
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun removeAll(Collection<?> collection) {
            if (collection.isEmpty()) {
                return false
            }
            Int size = size()
            Boolean zRemoveAll = this.delegate.removeAll(collection)
            if (zRemoveAll) {
                AbstractMapBasedMultimap.access$212(AbstractMapBasedMultimap.this, this.delegate.size() - size)
                removeIfEmpty()
            }
            return zRemoveAll
        }

        fun removeIfEmpty() {
            AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection = this.ancestor
            if (wrappedCollection != null) {
                wrappedCollection.removeIfEmpty()
            } else if (this.delegate.isEmpty()) {
                AbstractMapBasedMultimap.this.map.remove(this.key)
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun retainAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection)
            Int size = size()
            Boolean zRetainAll = this.delegate.retainAll(collection)
            if (zRetainAll) {
                AbstractMapBasedMultimap.access$212(AbstractMapBasedMultimap.this, this.delegate.size() - size)
                removeIfEmpty()
            }
            return zRetainAll
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        fun size() {
            refreshIfEmpty()
            return this.delegate.size()
        }

        @Override // java.util.AbstractCollection
        fun toString() {
            refreshIfEmpty()
            return this.delegate.toString()
        }
    }

    class WrappedList extends AbstractMapBasedMultimap<K, V>.WrappedCollection implements List<V> {

        class WrappedListIterator extends AbstractMapBasedMultimap<K, V>.WrappedCollection.WrappedIterator implements ListIterator<V> {
            constructor() {
                super()
            }

            constructor(Int i) {
                super(WrappedList.this.getListDelegate().listIterator(i))
            }

            @Override // java.util.ListIterator
            fun add(V v) {
                Boolean zIsEmpty = WrappedList.this.isEmpty()
                getDelegateListIterator().add(v)
                AbstractMapBasedMultimap.access$208(AbstractMapBasedMultimap.this)
                if (zIsEmpty) {
                    WrappedList.this.addToMap()
                }
            }

            public final ListIterator<V> getDelegateListIterator() {
                return (ListIterator) getDelegateIterator()
            }

            @Override // java.util.ListIterator
            fun hasPrevious() {
                return getDelegateListIterator().hasPrevious()
            }

            @Override // java.util.ListIterator
            fun nextIndex() {
                return getDelegateListIterator().nextIndex()
            }

            @Override // java.util.ListIterator
            fun previous() {
                return getDelegateListIterator().previous()
            }

            @Override // java.util.ListIterator
            fun previousIndex() {
                return getDelegateListIterator().previousIndex()
            }

            @Override // java.util.ListIterator
            fun set(V v) {
                getDelegateListIterator().set(v)
            }
        }

        constructor(K k, List<V> list, AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection) {
            super(k, list, wrappedCollection)
        }

        @Override // java.util.List
        fun add(Int i, V v) {
            refreshIfEmpty()
            Boolean zIsEmpty = getDelegate().isEmpty()
            getListDelegate().add(i, v)
            AbstractMapBasedMultimap.access$208(AbstractMapBasedMultimap.this)
            if (zIsEmpty) {
                addToMap()
            }
        }

        @Override // java.util.List
        fun addAll(Int i, Collection<? extends V> collection) {
            if (collection.isEmpty()) {
                return false
            }
            Int size = size()
            Boolean zAddAll = getListDelegate().addAll(i, collection)
            if (zAddAll) {
                AbstractMapBasedMultimap.access$212(AbstractMapBasedMultimap.this, getDelegate().size() - size)
                if (size == 0) {
                    addToMap()
                }
            }
            return zAddAll
        }

        @Override // java.util.List
        fun get(Int i) {
            refreshIfEmpty()
            return getListDelegate().get(i)
        }

        public List<V> getListDelegate() {
            return (List) getDelegate()
        }

        @Override // java.util.List
        fun indexOf(Object obj) {
            refreshIfEmpty()
            return getListDelegate().indexOf(obj)
        }

        @Override // java.util.List
        fun lastIndexOf(Object obj) {
            refreshIfEmpty()
            return getListDelegate().lastIndexOf(obj)
        }

        @Override // java.util.List
        public ListIterator<V> listIterator() {
            refreshIfEmpty()
            return WrappedListIterator()
        }

        @Override // java.util.List
        public ListIterator<V> listIterator(Int i) {
            refreshIfEmpty()
            return WrappedListIterator(i)
        }

        @Override // java.util.List
        fun remove(Int i) {
            refreshIfEmpty()
            V vRemove = getListDelegate().remove(i)
            AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this)
            removeIfEmpty()
            return vRemove
        }

        @Override // java.util.List
        fun set(Int i, V v) {
            refreshIfEmpty()
            return getListDelegate().set(i, v)
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.List
        public List<V> subList(Int i, Int i2) {
            refreshIfEmpty()
            return AbstractMapBasedMultimap.this.wrapList(getKey(), getListDelegate().subList(i, i2), getAncestor() == null ? this : getAncestor())
        }
    }

    constructor(Map<K, Collection<V>> map) {
        Preconditions.checkArgument(map.isEmpty())
        this.map = map
    }

    public static /* synthetic */ Int access$208(AbstractMapBasedMultimap abstractMapBasedMultimap) {
        Int i = abstractMapBasedMultimap.totalSize
        abstractMapBasedMultimap.totalSize = i + 1
        return i
    }

    public static /* synthetic */ Int access$210(AbstractMapBasedMultimap abstractMapBasedMultimap) {
        Int i = abstractMapBasedMultimap.totalSize
        abstractMapBasedMultimap.totalSize = i - 1
        return i
    }

    public static /* synthetic */ Int access$212(AbstractMapBasedMultimap abstractMapBasedMultimap, Int i) {
        Int i2 = abstractMapBasedMultimap.totalSize + i
        abstractMapBasedMultimap.totalSize = i2
        return i2
    }

    public static /* synthetic */ Int access$220(AbstractMapBasedMultimap abstractMapBasedMultimap, Int i) {
        Int i2 = abstractMapBasedMultimap.totalSize - i
        abstractMapBasedMultimap.totalSize = i2
        return i2
    }

    public static <E> Iterator<E> iteratorOrListIterator(Collection<E> collection) {
        return collection is List ? ((List) collection).listIterator() : collection.iterator()
    }

    fun clear() {
        Iterator<Collection<V>> it = this.map.values().iterator()
        while (it.hasNext()) {
            it.next().clear()
        }
        this.map.clear()
        this.totalSize = 0
    }

    @Override // com.google.common.collect.AbstractMultimap
    public Map<K, Collection<V>> createAsMap() {
        return AsMap(this.map)
    }

    public abstract Collection<V> createCollection()

    public Collection<V> createCollection(K k) {
        return createCollection()
    }

    @Override // com.google.common.collect.AbstractMultimap
    public Set<K> createKeySet() {
        return KeySet(this.map)
    }

    @Override // com.google.common.collect.Multimap
    public Collection<V> get(K k) {
        Collection<V> collectionCreateCollection = this.map.get(k)
        if (collectionCreateCollection == null) {
            collectionCreateCollection = createCollection(k)
        }
        return wrapCollection(k, collectionCreateCollection)
    }

    public final Unit removeValuesForKey(Object obj) {
        Collection collection = (Collection) Maps.safeRemove(this.map, obj)
        if (collection != null) {
            Int size = collection.size()
            collection.clear()
            this.totalSize -= size
        }
    }

    public abstract Collection<V> wrapCollection(K k, Collection<V> collection)

    public final List<V> wrapList(K k, List<V> list, AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection) {
        return list is RandomAccess ? RandomAccessWrappedList(this, k, list, wrappedCollection) : WrappedList(k, list, wrappedCollection)
    }
}
