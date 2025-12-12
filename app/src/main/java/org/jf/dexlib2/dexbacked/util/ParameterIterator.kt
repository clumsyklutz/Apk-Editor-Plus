package org.jf.dexlib2.dexbacked.util

import com.google.common.collect.ImmutableSet
import java.util.Iterator
import java.util.List
import java.util.Set
import org.jf.dexlib2.base.BaseMethodParameter
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.iface.MethodParameter

class ParameterIterator implements Iterator<MethodParameter> {
    public final Iterator<? extends Set<? extends Annotation>> parameterAnnotations
    public final Iterator<String> parameterNames
    public final Iterator<? extends CharSequence> parameterTypes

    constructor(List<? extends CharSequence> list, List<? extends Set<? extends Annotation>> list2, Iterator<String> it) {
        this.parameterTypes = list.iterator()
        this.parameterAnnotations = list2.iterator()
        this.parameterNames = it
    }

    @Override // java.util.Iterator
    fun hasNext() {
        return this.parameterTypes.hasNext()
    }

    @Override // java.util.Iterator
    fun next() {
        val string = this.parameterTypes.next().toString()
        final Set<? extends Annotation> next = this.parameterAnnotations.hasNext() ? this.parameterAnnotations.next() : ImmutableSet.of()
        val next2 = this.parameterNames.hasNext() ? this.parameterNames.next() : null
        return BaseMethodParameter(this) { // from class: org.jf.dexlib2.dexbacked.util.ParameterIterator.1
            @Override // org.jf.dexlib2.iface.MethodParameter
            public Set<? extends Annotation> getAnnotations() {
                return next
            }

            @Override // org.jf.dexlib2.iface.MethodParameter, org.jf.dexlib2.iface.debug.LocalInfo
            fun getName() {
                return next2
            }

            @Override // org.jf.dexlib2.iface.reference.TypeReference
            fun getType() {
                return string
            }
        }
    }

    @Override // java.util.Iterator
    fun remove() {
        throw UnsupportedOperationException()
    }
}
