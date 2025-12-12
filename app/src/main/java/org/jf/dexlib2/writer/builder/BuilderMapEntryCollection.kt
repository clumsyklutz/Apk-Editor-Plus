package org.jf.dexlib2.writer.builder

import java.util.AbstractCollection
import java.util.Collection
import java.util.Iterator
import java.util.Map

abstract class BuilderMapEntryCollection<Key> extends AbstractCollection<Map.Entry<Key, Integer>> {
    public final Collection<Key> keys

    class MapEntry implements Map.Entry<Key, Integer> {
        public Key key

        constructor() {
        }

        @Override // java.util.Map.Entry
        fun getKey() {
            return this.key
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Map.Entry
        fun getValue() {
            return Integer.valueOf(BuilderMapEntryCollection.this.getValue(this.key))
        }

        @Override // java.util.Map.Entry
        fun setValue(Integer num) {
            return Integer.valueOf(BuilderMapEntryCollection.this.setValue(this.key, num.intValue()))
        }
    }

    constructor(Collection<Key> collection) {
        this.keys = collection
    }

    public abstract Int getValue(Key key)

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<Map.Entry<Key, Integer>> iterator() {
        final Iterator<Key> it = this.keys.iterator()
        return new Iterator<Map.Entry<Key, Integer>>() { // from class: org.jf.dexlib2.writer.builder.BuilderMapEntryCollection.1
            @Override // java.util.Iterator
            fun hasNext() {
                return it.hasNext()
            }

            @Override // java.util.Iterator
            public Map.Entry<Key, Integer> next() {
                MapEntry mapEntry = MapEntry()
                mapEntry.key = it.next()
                return mapEntry
            }

            @Override // java.util.Iterator
            fun remove() {
                throw UnsupportedOperationException()
            }
        }
    }

    public abstract Int setValue(Key key, Int i)

    @Override // java.util.AbstractCollection, java.util.Collection
    fun size() {
        return this.keys.size()
    }
}
