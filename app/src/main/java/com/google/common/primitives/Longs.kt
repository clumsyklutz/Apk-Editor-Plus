package com.google.common.primitives

class Longs {
    fun compare(Long j, Long j2) {
        if (j < j2) {
            return -1
        }
        return j > j2 ? 1 : 0
    }
}
