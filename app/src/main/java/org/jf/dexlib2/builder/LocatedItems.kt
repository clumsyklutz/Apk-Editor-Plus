package org.jf.dexlib2.builder

import com.google.common.collect.ImmutableList
import java.util.AbstractSet
import java.util.ArrayList
import java.util.Iterator
import java.util.List
import java.util.Set
import org.jf.dexlib2.builder.ItemWithLocation

abstract class LocatedItems<T extends ItemWithLocation> {
    public List<T> items = null

    public final Unit addItem(T t) {
        if (this.items == null) {
            this.items = ArrayList(1)
        }
        this.items.add(t)
    }

    public abstract String getAddLocatedItemError()

    public final List<T> getItems() {
        List<T> list = this.items
        return list == null ? ImmutableList.of() : list
    }

    public Set<T> getModifiableItems(final MethodLocation methodLocation) {
        return new AbstractSet<T>() { // from class: org.jf.dexlib2.builder.LocatedItems.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            fun add(T t) {
                if (t.isPlaced()) {
                    throw IllegalArgumentException(LocatedItems.this.getAddLocatedItemError())
                }
                t.setLocation(methodLocation)
                LocatedItems.this.addItem(t)
                return true
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<T> iterator() {
                val it = LocatedItems.this.getItems().iterator()
                return (Iterator<T>) new Iterator<T>(this) { // from class: org.jf.dexlib2.builder.LocatedItems.1.1
                    public T currentItem = null

                    @Override // java.util.Iterator
                    fun hasNext() {
                        return it.hasNext()
                    }

                    @Override // java.util.Iterator
                    fun next() {
                        T t = (T) it.next()
                        this.currentItem = t
                        return t
                    }

                    @Override // java.util.Iterator
                    fun remove() {
                        T t = this.currentItem
                        if (t != null) {
                            t.setLocation(null)
                        }
                        it.remove()
                    }
                }
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            fun size() {
                return LocatedItems.this.getItems().size()
            }
        }
    }

    fun mergeItemsIntoNext(MethodLocation methodLocation, LocatedItems<T> locatedItems) {
        List<T> list
        if (locatedItems == this || (list = this.items) == null) {
            return
        }
        Iterator<T> it = list.iterator()
        while (it.hasNext()) {
            it.next().setLocation(methodLocation)
        }
        List<T> list2 = this.items
        list2.addAll(locatedItems.getItems())
        locatedItems.items = list2
        this.items = null
    }
}
