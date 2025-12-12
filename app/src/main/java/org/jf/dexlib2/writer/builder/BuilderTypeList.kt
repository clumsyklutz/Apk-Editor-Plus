package org.jf.dexlib2.writer.builder

import com.google.common.collect.ImmutableList
import java.util.AbstractList
import java.util.List

class BuilderTypeList extends AbstractList<BuilderTypeReference> {
    public static val EMPTY = BuilderTypeList(ImmutableList.of())
    public Int offset = 0
    public final List<? extends BuilderTypeReference> types

    constructor(List<? extends BuilderTypeReference> list) {
        this.types = list
    }

    @Override // java.util.AbstractList, java.util.List
    fun get(Int i) {
        return this.types.get(i)
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    fun size() {
        return this.types.size()
    }
}
