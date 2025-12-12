package org.jf.util

abstract class NibbleUtils {
    fun extractHighSignedNibble(Int i) {
        return (i << 24) >> 28
    }

    fun extractHighUnsignedNibble(Int i) {
        return (i & 240) >>> 4
    }

    fun extractLowUnsignedNibble(Int i) {
        return i & 15
    }
}
