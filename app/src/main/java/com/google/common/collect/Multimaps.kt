package com.google.common.collect

class Multimaps {
    fun equalsImpl(Multimap<?, ?> multimap, Object obj) {
        if (obj == multimap) {
            return true
        }
        if (obj is Multimap) {
            return multimap.asMap().equals(((Multimap) obj).asMap())
        }
        return false
    }
}
