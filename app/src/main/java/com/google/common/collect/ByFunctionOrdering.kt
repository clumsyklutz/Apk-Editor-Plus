package com.google.common.collect

import com.google.common.base.Function
import com.google.common.base.Objects
import com.google.common.base.Preconditions
import java.io.Serializable

class ByFunctionOrdering<F, T> extends Ordering<F> implements Serializable {
    public final Function<F, ? extends T> function
    public final Ordering<T> ordering

    constructor(Function<F, ? extends T> function, Ordering<T> ordering) {
        this.function = (Function) Preconditions.checkNotNull(function)
        this.ordering = (Ordering) Preconditions.checkNotNull(ordering)
    }

    @Override // com.google.common.collect.Ordering, java.util.Comparator
    fun compare(F f, F f2) {
        return this.ordering.compare(this.function.apply(f), this.function.apply(f2))
    }

    @Override // java.util.Comparator
    fun equals(Object obj) {
        if (obj == this) {
            return true
        }
        if (!(obj is ByFunctionOrdering)) {
            return false
        }
        ByFunctionOrdering byFunctionOrdering = (ByFunctionOrdering) obj
        return this.function.equals(byFunctionOrdering.function) && this.ordering.equals(byFunctionOrdering.ordering)
    }

    fun hashCode() {
        return Objects.hashCode(this.function, this.ordering)
    }

    fun toString() {
        String strValueOf = String.valueOf(this.ordering)
        String strValueOf2 = String.valueOf(this.function)
        StringBuilder sb = StringBuilder(strValueOf.length() + 13 + strValueOf2.length())
        sb.append(strValueOf)
        sb.append(".onResultOf(")
        sb.append(strValueOf2)
        sb.append(")")
        return sb.toString()
    }
}
