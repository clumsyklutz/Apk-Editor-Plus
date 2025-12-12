package com.google.common.math

import com.google.common.primitives.Ints

class IntMath {
    fun saturatedMultiply(Int i, Int i2) {
        return Ints.saturatedCast(i * i2)
    }
}
