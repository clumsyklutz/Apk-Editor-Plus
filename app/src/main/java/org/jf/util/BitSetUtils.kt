package org.jf.util

import java.util.BitSet

class BitSetUtils {
    fun bitSetOfIndexes(Int... iArr) {
        BitSet bitSet = BitSet()
        for (Int i : iArr) {
            bitSet.set(i)
        }
        return bitSet
    }
}
