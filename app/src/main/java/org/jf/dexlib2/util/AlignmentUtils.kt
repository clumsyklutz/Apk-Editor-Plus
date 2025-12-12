package org.jf.dexlib2.util

abstract class AlignmentUtils {
    fun alignOffset(Int i, Int i2) {
        Int i3 = i2 - 1
        return (i + i3) & (i3 ^ (-1))
    }
}
