package org.jf.util

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableSet
import com.google.common.collect.ImmutableSortedSet
import java.util.Comparator
import java.util.Iterator

abstract class ImmutableConverter<ImmutableItem, Item> {
    public abstract Boolean isImmutable(Item item)

    public abstract ImmutableItem makeImmutable(Item item)

    public ImmutableList<ImmutableItem> toList(Iterable<? extends Item> iterable) {
        if (iterable == null) {
            return ImmutableList.of()
        }
        Boolean z = false
        Boolean z2 = true
        if (iterable is ImmutableList) {
            Iterator<? extends Item> it = iterable.iterator()
            while (true) {
                if (!it.hasNext()) {
                    break
                }
                if (!isImmutable(it.next())) {
                    z = true
                    break
                }
            }
            z2 = z
        }
        if (!z2) {
            return (ImmutableList) iterable
        }
        final Iterator<? extends Item> it2 = iterable.iterator()
        return ImmutableList.copyOf(new Iterator<ImmutableItem>() { // from class: org.jf.util.ImmutableConverter.1
            @Override // java.util.Iterator
            fun hasNext() {
                return it2.hasNext()
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Iterator
            fun next() {
                return (ImmutableItem) ImmutableConverter.this.makeImmutable(it2.next())
            }

            @Override // java.util.Iterator
            fun remove() {
                it2.remove()
            }
        })
    }

    public ImmutableSet<ImmutableItem> toSet(Iterable<? extends Item> iterable) {
        if (iterable == null) {
            return ImmutableSet.of()
        }
        Boolean z = false
        Boolean z2 = true
        if (iterable is ImmutableSet) {
            Iterator<? extends Item> it = iterable.iterator()
            while (true) {
                if (!it.hasNext()) {
                    break
                }
                if (!isImmutable(it.next())) {
                    z = true
                    break
                }
            }
            z2 = z
        }
        if (!z2) {
            return (ImmutableSet) iterable
        }
        final Iterator<? extends Item> it2 = iterable.iterator()
        return ImmutableSet.copyOf(new Iterator<ImmutableItem>() { // from class: org.jf.util.ImmutableConverter.2
            @Override // java.util.Iterator
            fun hasNext() {
                return it2.hasNext()
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Iterator
            fun next() {
                return (ImmutableItem) ImmutableConverter.this.makeImmutable(it2.next())
            }

            @Override // java.util.Iterator
            fun remove() {
                it2.remove()
            }
        })
    }

    public ImmutableSortedSet<ImmutableItem> toSortedSet(Comparator<? super ImmutableItem> comparator, Iterable<? extends Item> iterable) {
        if (iterable == null) {
            return ImmutableSortedSet.of()
        }
        Boolean z = false
        Boolean z2 = true
        if ((iterable is ImmutableSortedSet) && ((ImmutableSortedSet) iterable).comparator().equals(comparator)) {
            Iterator<? extends Item> it = iterable.iterator()
            while (true) {
                if (!it.hasNext()) {
                    break
                }
                if (!isImmutable(it.next())) {
                    z = true
                    break
                }
            }
            z2 = z
        }
        if (!z2) {
            return (ImmutableSortedSet) iterable
        }
        final Iterator<? extends Item> it2 = iterable.iterator()
        return ImmutableSortedSet.copyOf(comparator, new Iterator<ImmutableItem>() { // from class: org.jf.util.ImmutableConverter.3
            @Override // java.util.Iterator
            fun hasNext() {
                return it2.hasNext()
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Iterator
            fun next() {
                return (ImmutableItem) ImmutableConverter.this.makeImmutable(it2.next())
            }

            @Override // java.util.Iterator
            fun remove() {
                it2.remove()
            }
        })
    }
}
