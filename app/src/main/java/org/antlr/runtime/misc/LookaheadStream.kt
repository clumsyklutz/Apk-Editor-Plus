package org.antlr.runtime.misc

abstract class LookaheadStream<T> extends FastQueue<T> {
    public Int lastMarker
    public T prevElement
    public Int currentElementIndex = 0
    public T eof = null
    public Int markDepth = 0

    fun LB(Int i) {
        Int i2 = this.p - i
        if (i2 == -1) {
            return this.prevElement
        }
        if (i2 >= 0) {
            return this.data.get(i2)
        }
        if (i2 < -1) {
            throw UnsupportedOperationException("can't look more than one token before the beginning of this stream's buffer")
        }
        throw UnsupportedOperationException("can't look past the end of this stream's buffer using LB(Int)")
    }

    fun LT(Int i) {
        if (i == 0) {
            return null
        }
        if (i < 0) {
            return LB(-i)
        }
        syncAhead(i)
        return (this.p + i) + (-1) > this.data.size() ? this.eof : elementAt(i - 1)
    }

    fun consume() {
        syncAhead(1)
        remove()
        this.currentElementIndex++
    }

    fun fill(Int i) {
        for (Int i2 = 1; i2 <= i; i2++) {
            T tNextElement = nextElement()
            if (isEOF(tNextElement)) {
                this.eof = tNextElement
            }
            this.data.add(tNextElement)
        }
    }

    fun index() {
        return this.currentElementIndex
    }

    public abstract Boolean isEOF(T t)

    fun mark() {
        this.markDepth++
        Int i = this.p
        this.lastMarker = i
        return i
    }

    public abstract T nextElement()

    @Override // org.antlr.runtime.misc.FastQueue
    fun remove() {
        T tElementAt = elementAt(0)
        Int i = this.p + 1
        this.p = i
        if (i == this.data.size() && this.markDepth == 0) {
            this.prevElement = tElementAt
            clear()
        }
        return tElementAt
    }

    fun rewind(Int i) {
        this.markDepth--
        this.currentElementIndex -= this.p - i
        this.p = i
    }

    @Override // org.antlr.runtime.misc.FastQueue
    fun size() {
        throw UnsupportedOperationException("streams are of unknown size")
    }

    fun syncAhead(Int i) {
        Int size = (((this.p + i) - 1) - this.data.size()) + 1
        if (size > 0) {
            fill(size)
        }
    }
}
