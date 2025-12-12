package com.google.common.collect

import com.google.common.base.Preconditions

class SingletonImmutableSet<E> extends ImmutableSet<E> {
    public final transient E element

    constructor(E e) {
        this.element = (E) Preconditions.checkNotNull(e)
    }

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
    public ImmutableList<E> asList() {
        return ImmutableList.of((Object) this.element)
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
    fun contains(Object obj) {
        return this.element.equals(obj)
    }

    @Override // com.google.common.collect.ImmutableCollection
    fun copyIntoArray(Array<Object> objArr, Int i) {
        objArr[i] = this.element
        return i + 1
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public final Int hashCode() {
        return this.element.hashCode()
    }

    @Override // com.google.common.collect.ImmutableCollection
    fun isPartialView() {
        return false
    }

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.element)
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    fun size() {
        return 1
    }

    @Override // java.util.AbstractCollection
    fun toString() {
        String string = this.element.toString()
        StringBuilder sb = StringBuilder(String.valueOf(string).length() + 2)
        sb.append('[')
        sb.append(string)
        sb.append(']')
        return sb.toString()
    }
}
