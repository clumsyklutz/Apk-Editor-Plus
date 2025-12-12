package android.arch.core.internal

import android.support.annotation.NonNull
import android.support.annotation.RestrictTo
import java.util.Iterator
import java.util.Map
import java.util.WeakHashMap

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class SafeIterableMap implements Iterable {
    private Entry mEnd
    private WeakHashMap mIterators = WeakHashMap()
    private Int mSize = 0
    private Entry mStart

    class AscendingIterator extends ListIterator {
        AscendingIterator(Entry entry, Entry entry2) {
            super(entry, entry2)
        }

        @Override // android.arch.core.internal.SafeIterableMap.ListIterator
        Entry backward(Entry entry) {
            return entry.mPrevious
        }

        @Override // android.arch.core.internal.SafeIterableMap.ListIterator
        Entry forward(Entry entry) {
            return entry.mNext
        }
    }

    class DescendingIterator extends ListIterator {
        DescendingIterator(Entry entry, Entry entry2) {
            super(entry, entry2)
        }

        @Override // android.arch.core.internal.SafeIterableMap.ListIterator
        Entry backward(Entry entry) {
            return entry.mNext
        }

        @Override // android.arch.core.internal.SafeIterableMap.ListIterator
        Entry forward(Entry entry) {
            return entry.mPrevious
        }
    }

    class Entry implements Map.Entry {

        @NonNull
        final Object mKey
        Entry mNext
        Entry mPrevious

        @NonNull
        final Object mValue

        Entry(@NonNull Object obj, @NonNull Object obj2) {
            this.mKey = obj
            this.mValue = obj2
        }

        @Override // java.util.Map.Entry
        fun equals(Object obj) {
            if (obj == this) {
                return true
            }
            if (!(obj is Entry)) {
                return false
            }
            Entry entry = (Entry) obj
            return this.mKey.equals(entry.mKey) && this.mValue.equals(entry.mValue)
        }

        @Override // java.util.Map.Entry
        @NonNull
        fun getKey() {
            return this.mKey
        }

        @Override // java.util.Map.Entry
        @NonNull
        fun getValue() {
            return this.mValue
        }

        @Override // java.util.Map.Entry
        fun setValue(Object obj) {
            throw UnsupportedOperationException("An entry modification is not supported")
        }

        fun toString() {
            return this.mKey + "=" + this.mValue
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    class IteratorWithAdditions implements SupportRemove, Iterator {
        private Boolean mBeforeStart
        private Entry mCurrent

        private constructor() {
            this.mBeforeStart = true
        }

        @Override // java.util.Iterator
        fun hasNext() {
            return this.mBeforeStart ? SafeIterableMap.this.mStart != null : (this.mCurrent == null || this.mCurrent.mNext == null) ? false : true
        }

        @Override // java.util.Iterator
        public Map.Entry next() {
            Entry entry
            IteratorWithAdditions iteratorWithAdditions
            if (this.mBeforeStart) {
                this.mBeforeStart = false
                entry = SafeIterableMap.this.mStart
                iteratorWithAdditions = this
            } else if (this.mCurrent != null) {
                entry = this.mCurrent.mNext
                iteratorWithAdditions = this
            } else {
                entry = null
                iteratorWithAdditions = this
            }
            iteratorWithAdditions.mCurrent = entry
            return this.mCurrent
        }

        @Override // android.arch.core.internal.SafeIterableMap.SupportRemove
        fun supportRemove(@NonNull Entry entry) {
            if (entry == this.mCurrent) {
                this.mCurrent = this.mCurrent.mPrevious
                this.mBeforeStart = this.mCurrent == null
            }
        }
    }

    abstract class ListIterator implements SupportRemove, Iterator {
        Entry mExpectedEnd
        Entry mNext

        ListIterator(Entry entry, Entry entry2) {
            this.mExpectedEnd = entry2
            this.mNext = entry
        }

        private fun nextNode() {
            if (this.mNext == this.mExpectedEnd || this.mExpectedEnd == null) {
                return null
            }
            return forward(this.mNext)
        }

        abstract Entry backward(Entry entry)

        abstract Entry forward(Entry entry)

        @Override // java.util.Iterator
        fun hasNext() {
            return this.mNext != null
        }

        @Override // java.util.Iterator
        public Map.Entry next() {
            Entry entry = this.mNext
            this.mNext = nextNode()
            return entry
        }

        @Override // android.arch.core.internal.SafeIterableMap.SupportRemove
        fun supportRemove(@NonNull Entry entry) {
            if (this.mExpectedEnd == entry && entry == this.mNext) {
                this.mNext = null
                this.mExpectedEnd = null
            }
            if (this.mExpectedEnd == entry) {
                this.mExpectedEnd = backward(this.mExpectedEnd)
            }
            if (this.mNext == entry) {
                this.mNext = nextNode()
            }
        }
    }

    interface SupportRemove {
        Unit supportRemove(@NonNull Entry entry)
    }

    fun descendingIterator() {
        DescendingIterator descendingIterator = DescendingIterator(this.mEnd, this.mStart)
        this.mIterators.put(descendingIterator, false)
        return descendingIterator
    }

    public Map.Entry eldest() {
        return this.mStart
    }

    fun equals(Object obj) {
        if (obj == this) {
            return true
        }
        if (!(obj is SafeIterableMap)) {
            return false
        }
        SafeIterableMap safeIterableMap = (SafeIterableMap) obj
        if (size() != safeIterableMap.size()) {
            return false
        }
        Iterator it = iterator()
        Iterator it2 = safeIterableMap.iterator()
        while (it.hasNext() && it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next()
            Object next = it2.next()
            if ((entry == null && next != null) || (entry != null && !entry.equals(next))) {
                return false
            }
        }
        return (it.hasNext() || it2.hasNext()) ? false : true
    }

    protected fun get(Object obj) {
        Entry entry = this.mStart
        while (entry != null && !entry.mKey.equals(obj)) {
            entry = entry.mNext
        }
        return entry
    }

    @Override // java.lang.Iterable
    @NonNull
    fun iterator() {
        AscendingIterator ascendingIterator = AscendingIterator(this.mStart, this.mEnd)
        this.mIterators.put(ascendingIterator, false)
        return ascendingIterator
    }

    fun iteratorWithAdditions() {
        IteratorWithAdditions iteratorWithAdditions = IteratorWithAdditions()
        this.mIterators.put(iteratorWithAdditions, false)
        return iteratorWithAdditions
    }

    public Map.Entry newest() {
        return this.mEnd
    }

    protected fun put(@NonNull Object obj, @NonNull Object obj2) {
        Entry entry = Entry(obj, obj2)
        this.mSize++
        if (this.mEnd == null) {
            this.mStart = entry
            this.mEnd = this.mStart
        } else {
            this.mEnd.mNext = entry
            entry.mPrevious = this.mEnd
            this.mEnd = entry
        }
        return entry
    }

    fun putIfAbsent(@NonNull Object obj, @NonNull Object obj2) {
        Entry entry = get(obj)
        if (entry != null) {
            return entry.mValue
        }
        put(obj, obj2)
        return null
    }

    fun remove(@NonNull Object obj) {
        Entry entry = get(obj)
        if (entry == null) {
            return null
        }
        this.mSize--
        if (!this.mIterators.isEmpty()) {
            Iterator it = this.mIterators.keySet().iterator()
            while (it.hasNext()) {
                ((SupportRemove) it.next()).supportRemove(entry)
            }
        }
        if (entry.mPrevious != null) {
            entry.mPrevious.mNext = entry.mNext
        } else {
            this.mStart = entry.mNext
        }
        if (entry.mNext != null) {
            entry.mNext.mPrevious = entry.mPrevious
        } else {
            this.mEnd = entry.mPrevious
        }
        entry.mNext = null
        entry.mPrevious = null
        return entry.mValue
    }

    fun size() {
        return this.mSize
    }

    fun toString() {
        StringBuilder sb = StringBuilder()
        sb.append("[")
        Iterator it = iterator()
        while (it.hasNext()) {
            sb.append(((Map.Entry) it.next()).toString())
            if (it.hasNext()) {
                sb.append(", ")
            }
        }
        sb.append("]")
        return sb.toString()
    }
}
