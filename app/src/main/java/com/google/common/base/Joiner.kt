package com.google.common.base

import java.io.IOException
import java.util.Iterator

class Joiner {
    public final String separator

    constructor(String str) {
        this.separator = (String) Preconditions.checkNotNull(str)
    }

    fun on(String str) {
        return Joiner(str)
    }

    public <A extends Appendable> A appendTo(A a2, Iterator<? extends Object> it) throws IOException {
        Preconditions.checkNotNull(a2)
        if (it.hasNext()) {
            a2.append(toString(it.next()))
            while (it.hasNext()) {
                a2.append(this.separator)
                a2.append(toString(it.next()))
            }
        }
        return a2
    }

    public final StringBuilder appendTo(StringBuilder sb, Iterator<? extends Object> it) {
        try {
            appendTo((Joiner) sb, it)
            return sb
        } catch (IOException e) {
            throw AssertionError(e)
        }
    }

    public final String join(Iterable<? extends Object> iterable) {
        return join(iterable.iterator())
    }

    public final String join(Iterator<? extends Object> it) {
        return appendTo(StringBuilder(), it).toString()
    }

    fun toString(Object obj) {
        obj.getClass()
        return obj is CharSequence ? (CharSequence) obj : obj.toString()
    }
}
