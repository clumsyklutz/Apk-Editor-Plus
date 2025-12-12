package org.antlr.runtime.misc

import java.util.ArrayList
import java.util.List
import java.util.NoSuchElementException

class FastQueue<T> {
    public List<T> data = ArrayList()
    public Int p = 0
    public Int range = -1

    fun add(T t) {
        this.data.add(t)
    }

    fun clear() {
        this.p = 0
        this.data.clear()
    }

    fun elementAt(Int i) {
        Int i2 = this.p + i
        if (i2 >= this.data.size()) {
            StringBuilder sb = StringBuilder()
            sb.append("queue index ")
            sb.append(i2)
            sb.append(" > last index ")
            sb.append(this.data.size() - 1)
            throw NoSuchElementException(sb.toString())
        }
        if (i2 >= 0) {
            if (i2 > this.range) {
                this.range = i2
            }
            return this.data.get(i2)
        }
        throw NoSuchElementException("queue index " + i2 + " < 0")
    }

    fun remove() {
        T tElementAt = elementAt(0)
        Int i = this.p + 1
        this.p = i
        if (i == this.data.size()) {
            clear()
        }
        return tElementAt
    }

    fun size() {
        return this.data.size() - this.p
    }

    fun toString() {
        StringBuilder sb = StringBuilder()
        Int size = size()
        Int i = 0
        while (i < size) {
            sb.append(elementAt(i))
            i++
            if (i < size) {
                sb.append(" ")
            }
        }
        return sb.toString()
    }
}
