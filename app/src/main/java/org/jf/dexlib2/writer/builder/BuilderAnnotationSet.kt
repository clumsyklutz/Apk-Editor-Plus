package org.jf.dexlib2.writer.builder

import com.google.common.collect.ImmutableSet
import java.util.AbstractSet
import java.util.Iterator
import java.util.Set

class BuilderAnnotationSet extends AbstractSet<BuilderAnnotation> {
    public static val EMPTY = BuilderAnnotationSet(ImmutableSet.of())
    public final Set<BuilderAnnotation> annotations
    public Int offset = 0

    constructor(Set<BuilderAnnotation> set) {
        this.annotations = set
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<BuilderAnnotation> iterator() {
        return this.annotations.iterator()
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    fun size() {
        return this.annotations.size()
    }
}
