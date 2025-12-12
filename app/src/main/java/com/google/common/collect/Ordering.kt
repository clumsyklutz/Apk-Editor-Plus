package com.google.common.collect

import com.google.common.base.Function
import java.util.Arrays
import java.util.Comparator
import java.util.List
import java.util.Map

abstract class Ordering<T> implements Comparator<T> {
    public static <T> Ordering<T> from(Comparator<T> comparator) {
        return comparator is Ordering ? (Ordering) comparator : ComparatorOrdering(comparator)
    }

    public static <C extends Comparable> Ordering<C> natural() {
        return NaturalOrdering.INSTANCE
    }

    public static Ordering<Object> usingToString() {
        return UsingToStringOrdering.INSTANCE
    }

    @Override // java.util.Comparator
    public abstract Int compare(T t, T t2)

    public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> iterable) {
        return ImmutableList.sortedCopyOf(this, iterable)
    }

    public <T2 extends T> Ordering<Map.Entry<T2, ?>> onKeys() {
        return (Ordering<Map.Entry<T2, ?>>) onResultOf(Maps.keyFunction())
    }

    public <F> Ordering<F> onResultOf(Function<F, ? extends T> function) {
        return ByFunctionOrdering(function, this)
    }

    public <S extends T> Ordering<S> reverse() {
        return ReverseOrdering(this)
    }

    public <E extends T> List<E> sortedCopy(Iterable<E> iterable) {
        Array<Object> array = Iterables.toArray(iterable)
        Arrays.sort(array, this)
        return Lists.newArrayList(Arrays.asList(array))
    }
}
