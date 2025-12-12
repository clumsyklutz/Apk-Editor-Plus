package com.google.common.collect

class NullnessCasts {
    public static <T> T uncheckedCastNullableTToT(T t) {
        return t
    }

    public static <T> T unsafeNull() {
        return null
    }
}
