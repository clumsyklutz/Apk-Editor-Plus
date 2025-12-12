package org.jf.util

import java.util.Comparator
import java.util.List

class LinearSearch {
    public static <T> Int linearSearch(List<? extends T> list, Comparator<T> comparator, T t, Int i) {
        Int iCompare
        if (i >= list.size()) {
            i = list.size() - 1
        }
        Int iCompare2 = comparator.compare(list.get(i), t)
        if (iCompare2 == 0) {
            return i
        }
        if (iCompare2 < 0) {
            do {
                i++
                if (i >= list.size()) {
                    return -(list.size() + 1)
                }
                iCompare = comparator.compare(list.get(i), t)
                if (iCompare == 0) {
                    return i
                }
            } while (iCompare <= 0);
            return -(i + 1)
        }
        for (Int i2 = i - 1; i2 >= 0; i2--) {
            Int iCompare3 = comparator.compare(list.get(i2), t)
            if (iCompare3 == 0) {
                return i2
            }
            if (iCompare3 < 0) {
                return -(i2 + 2)
            }
        }
        return -1
    }
}
