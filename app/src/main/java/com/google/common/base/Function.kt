package com.google.common.base

public interface Function<F, T> {
    T apply(F f)

    Boolean equals(Object obj)
}
