package com.google.common.collect

class RegularImmutableSet<E> extends ImmutableSet<E> {
    public static final RegularImmutableSet<Object> EMPTY
    public static final Array<Object> EMPTY_ARRAY
    public final transient Array<Object> elements
    public final transient Int hashCode
    public final transient Int mask
    public final transient Int size
    public final transient Array<Object> table

    static {
        Array<Object> objArr = new Object[0]
        EMPTY_ARRAY = objArr
        EMPTY = new RegularImmutableSet<>(objArr, 0, objArr, 0, 0)
    }

    constructor(Array<Object> objArr, Int i, Array<Object> objArr2, Int i2, Int i3) {
        this.elements = objArr
        this.hashCode = i
        this.table = objArr2
        this.mask = i2
        this.size = i3
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
    fun contains(Object obj) {
        Array<Object> objArr = this.table
        if (obj == null || objArr.length == 0) {
            return false
        }
        Int iSmearedHash = Hashing.smearedHash(obj)
        while (true) {
            Int i = iSmearedHash & this.mask
            Object obj2 = objArr[i]
            if (obj2 == null) {
                return false
            }
            if (obj2.equals(obj)) {
                return true
            }
            iSmearedHash = i + 1
        }
    }

    @Override // com.google.common.collect.ImmutableCollection
    fun copyIntoArray(Array<Object> objArr, Int i) {
        System.arraycopy(this.elements, 0, objArr, i, this.size)
        return i + this.size
    }

    @Override // com.google.common.collect.ImmutableSet
    public ImmutableList<E> createAsList() {
        return ImmutableList.asImmutableList(this.elements, this.size)
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    fun hashCode() {
        return this.hashCode
    }

    @Override // com.google.common.collect.ImmutableCollection
    public Array<Object> internalArray() {
        return this.elements
    }

    @Override // com.google.common.collect.ImmutableCollection
    fun internalArrayEnd() {
        return this.size
    }

    @Override // com.google.common.collect.ImmutableCollection
    fun internalArrayStart() {
        return 0
    }

    @Override // com.google.common.collect.ImmutableSet
    fun isHashCodeFast() {
        return true
    }

    @Override // com.google.common.collect.ImmutableCollection
    fun isPartialView() {
        return false
    }

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public UnmodifiableIterator<E> iterator() {
        return asList().iterator()
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    fun size() {
        return this.size
    }
}
