package com.google.common.collect

import java.util.Collection
import java.util.Set

public interface Multiset<E> extends Collection<E> {
    Set<E> elementSet()
}
