package com.google.common.collect

import com.google.common.base.Preconditions

class RegularImmutableList<E> extends ImmutableList<E> {
    public static final ImmutableList<Object> EMPTY = RegularImmutableList(new Object[0], 0)
    public final transient Array<Object> array
    public final transient Int size

    constructor(Array<Object> objArr, Int i) {
        this.array = objArr
        this.size = i
    }

    @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection
    fun copyIntoArray(Array<Object> objArr, Int i) {
        System.arraycopy(this.array, 0, objArr, i, this.size)
        return i + this.size
    }

    @Override // java.util.List
    fun get(Int i) {
        Preconditions.checkElementIndex(i, this.size)
        E e = (E) this.array[i]
        e.getClass()
        return e
    }

    @Override // com.google.common.collect.ImmutableCollection
    public Array<Object> internalArray() {
        return this.array
    }

    @Override // com.google.common.collect.ImmutableCollection
    fun internalArrayEnd() {
        return this.size
    }

    @Override // com.google.common.collect.ImmutableCollection
    fun internalArrayStart() {
        return 0
    }

    @Override // com.google.common.collect.ImmutableCollection
    fun isPartialView() {
        return false
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    fun size() {
        return this.size
    }
}
