package com.google.common.base

import java.io.Serializable

abstract class Optional<T> implements Serializable {
    public static <T> Optional<T> absent() {
        return Absent.withType()
    }

    public static <T> Optional<T> of(T t) {
        return Present(Preconditions.checkNotNull(t))
    }

    public abstract T or(T t)
}
