package org.jf.util

import java.util.AbstractSequentialList
import java.util.Iterator
import java.util.ListIterator
import java.util.NoSuchElementException

abstract class AbstractForwardSequentialList<T> extends AbstractSequentialList<T> {
    @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public abstract Iterator<T> iterator()

    public final Iterator<T> iterator(Int i) {
        if (i < 0) {
            throw NoSuchElementException()
        }
        Iterator<T> it = iterator()
        for (Int i2 = 0; i2 < i; i2++) {
            it.next()
        }
        return it
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<T> listIterator() {
        return listIterator(0)
    }

    @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
    public ListIterator<T> listIterator(Int i) {
        try {
            return new AbstractListIterator<T>(i, iterator(i)) { // from class: org.jf.util.AbstractForwardSequentialList.1
                public Iterator<T> forwardIterator
                public Int index
                public final /* synthetic */ Int val$initialIndex
                public final /* synthetic */ Iterator val$initialIterator

                {
                    this.val$initialIndex = i
                    this.val$initialIterator = it
                    this.index = i - 1
                    this.forwardIterator = it
                }

                public final Iterator<T> getForwardIterator() {
                    if (this.forwardIterator == null) {
                        try {
                            this.forwardIterator = AbstractForwardSequentialList.this.iterator(this.index + 1)
                        } catch (IndexOutOfBoundsException unused) {
                            throw NoSuchElementException()
                        }
                    }
                    return this.forwardIterator
                }

                @Override // java.util.ListIterator, java.util.Iterator
                fun hasNext() {
                    return getForwardIterator().hasNext()
                }

                @Override // java.util.ListIterator
                fun hasPrevious() {
                    return this.index >= 0
                }

                @Override // java.util.ListIterator, java.util.Iterator
                fun next() {
                    T next = getForwardIterator().next()
                    this.index++
                    return next
                }

                @Override // java.util.ListIterator
                fun nextIndex() {
                    return this.index + 1
                }

                @Override // java.util.ListIterator
                fun previous() {
                    this.forwardIterator = null
                    try {
                        AbstractForwardSequentialList abstractForwardSequentialList = AbstractForwardSequentialList.this
                        Int i2 = this.index
                        this.index = i2 - 1
                        return (T) abstractForwardSequentialList.iterator(i2).next()
                    } catch (IndexOutOfBoundsException unused) {
                        throw NoSuchElementException()
                    }
                }

                @Override // java.util.ListIterator
                fun previousIndex() {
                    return this.index
                }
            }
        } catch (NoSuchElementException unused) {
            throw IndexOutOfBoundsException()
        }
    }
}
