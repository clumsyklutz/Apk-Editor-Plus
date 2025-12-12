package com.google.common.primitives

class Booleans {
    fun compare(Boolean z, Boolean z2) {
        if (z == z2) {
            return 0
        }
        return z ? 1 : -1
    }
}
