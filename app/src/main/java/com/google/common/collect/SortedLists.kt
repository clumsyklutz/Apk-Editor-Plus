package com.google.common.collect

import com.google.common.base.Function
import com.google.common.base.Preconditions
import java.util.Comparator
import java.util.List
import java.util.RandomAccess

class SortedLists {

    public enum KeyAbsentBehavior {
        NEXT_LOWER { // from class: com.google.common.collect.SortedLists.KeyAbsentBehavior.1
            @Override // com.google.common.collect.SortedLists.KeyAbsentBehavior
            fun resultIndex(Int i) {
                return i - 1
            }
        },
        NEXT_HIGHER { // from class: com.google.common.collect.SortedLists.KeyAbsentBehavior.2
            @Override // com.google.common.collect.SortedLists.KeyAbsentBehavior
            fun resultIndex(Int i) {
                return i
            }
        },
        INVERTED_INSERTION_INDEX { // from class: com.google.common.collect.SortedLists.KeyAbsentBehavior.3
            @Override // com.google.common.collect.SortedLists.KeyAbsentBehavior
            fun resultIndex(Int i) {
                return i ^ (-1)
            }
        }

        public abstract Int resultIndex(Int i)
    }

    public enum KeyPresentBehavior {
        ANY_PRESENT { // from class: com.google.common.collect.SortedLists.KeyPresentBehavior.1
            @Override // com.google.common.collect.SortedLists.KeyPresentBehavior
            public <E> Int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, Int i) {
                return i
            }
        },
        LAST_PRESENT { // from class: com.google.common.collect.SortedLists.KeyPresentBehavior.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.SortedLists.KeyPresentBehavior
            public <E> Int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, Int i) {
                Int size = list.size() - 1
                while (i < size) {
                    Int i2 = ((i + size) + 1) >>> 1
                    if (comparator.compare(list.get(i2), e) > 0) {
                        size = i2 - 1
                    } else {
                        i = i2
                    }
                }
                return i
            }
        },
        FIRST_PRESENT { // from class: com.google.common.collect.SortedLists.KeyPresentBehavior.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.SortedLists.KeyPresentBehavior
            public <E> Int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, Int i) {
                Int i2 = 0
                while (i2 < i) {
                    Int i3 = (i2 + i) >>> 1
                    if (comparator.compare(list.get(i3), e) < 0) {
                        i2 = i3 + 1
                    } else {
                        i = i3
                    }
                }
                return i2
            }
        },
        FIRST_AFTER { // from class: com.google.common.collect.SortedLists.KeyPresentBehavior.4
            @Override // com.google.common.collect.SortedLists.KeyPresentBehavior
            public <E> Int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, Int i) {
                return KeyPresentBehavior.LAST_PRESENT.resultIndex(comparator, e, list, i) + 1
            }
        },
        LAST_BEFORE { // from class: com.google.common.collect.SortedLists.KeyPresentBehavior.5
            @Override // com.google.common.collect.SortedLists.KeyPresentBehavior
            public <E> Int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, Int i) {
                return KeyPresentBehavior.FIRST_PRESENT.resultIndex(comparator, e, list, i) - 1
            }
        }

        public abstract <E> Int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, Int i)
    }

    public static <E, K extends Comparable> Int binarySearch(List<E> list, Function<? super E, K> function, K k, KeyPresentBehavior keyPresentBehavior, KeyAbsentBehavior keyAbsentBehavior) {
        Preconditions.checkNotNull(k)
        return binarySearch(list, function, k, Ordering.natural(), keyPresentBehavior, keyAbsentBehavior)
    }

    public static <E, K> Int binarySearch(List<E> list, Function<? super E, K> function, K k, Comparator<? super K> comparator, KeyPresentBehavior keyPresentBehavior, KeyAbsentBehavior keyAbsentBehavior) {
        return binarySearch((List<? extends K>) Lists.transform(list, function), k, comparator, keyPresentBehavior, keyAbsentBehavior)
    }

    public static <E> Int binarySearch(List<? extends E> list, E e, Comparator<? super E> comparator, KeyPresentBehavior keyPresentBehavior, KeyAbsentBehavior keyAbsentBehavior) {
        Preconditions.checkNotNull(comparator)
        Preconditions.checkNotNull(list)
        Preconditions.checkNotNull(keyPresentBehavior)
        Preconditions.checkNotNull(keyAbsentBehavior)
        if (!(list is RandomAccess)) {
            list = Lists.newArrayList(list)
        }
        Int i = 0
        Int size = list.size() - 1
        while (i <= size) {
            Int i2 = (i + size) >>> 1
            Int iCompare = comparator.compare(e, list.get(i2))
            if (iCompare < 0) {
                size = i2 - 1
            } else {
                if (iCompare <= 0) {
                    return i + keyPresentBehavior.resultIndex(comparator, e, list.subList(i, size + 1), i2 - i)
                }
                i = i2 + 1
            }
        }
        return keyAbsentBehavior.resultIndex(i)
    }
}
