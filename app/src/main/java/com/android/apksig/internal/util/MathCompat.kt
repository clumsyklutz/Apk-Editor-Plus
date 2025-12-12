package com.android.apksig.internal.util

class MathCompat {
    fun toIntExact(Long j) {
        Int i = (Int) j
        if (i == j) {
            return i
        }
        throw ArithmeticException("integer overflow")
    }
}
