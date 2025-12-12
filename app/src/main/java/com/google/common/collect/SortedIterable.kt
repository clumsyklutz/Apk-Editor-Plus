package com.google.common.collect

import java.util.Comparator

public interface SortedIterable<T> extends Iterable<T> {
    Comparator<? super T> comparator()
}
