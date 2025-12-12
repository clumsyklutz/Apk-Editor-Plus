package org.jf.dexlib2.immutable

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableSet
import java.util.Set
import org.jf.dexlib2.base.BaseMethodParameter
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.iface.MethodParameter
import org.jf.util.ImmutableConverter

class ImmutableMethodParameter extends BaseMethodParameter {
    public static final ImmutableConverter<ImmutableMethodParameter, MethodParameter> CONVERTER = new ImmutableConverter<ImmutableMethodParameter, MethodParameter>() { // from class: org.jf.dexlib2.immutable.ImmutableMethodParameter.1
        @Override // org.jf.util.ImmutableConverter
        fun isImmutable(MethodParameter methodParameter) {
            return methodParameter is ImmutableMethodParameter
        }

        @Override // org.jf.util.ImmutableConverter
        fun makeImmutable(MethodParameter methodParameter) {
            return ImmutableMethodParameter.of(methodParameter)
        }
    }
    public final ImmutableSet<? extends ImmutableAnnotation> annotations
    public final String name
    public final String type

    constructor(String str, Set<? extends Annotation> set, String str2) {
        this.type = str
        this.annotations = ImmutableAnnotation.immutableSetOf(set)
        this.name = str2
    }

    public static ImmutableList<ImmutableMethodParameter> immutableListOf(Iterable<? extends MethodParameter> iterable) {
        return CONVERTER.toList(iterable)
    }

    fun of(MethodParameter methodParameter) {
        return methodParameter is ImmutableMethodParameter ? (ImmutableMethodParameter) methodParameter : ImmutableMethodParameter(methodParameter.getType(), methodParameter.getAnnotations(), methodParameter.getName())
    }

    @Override // org.jf.dexlib2.iface.MethodParameter
    public Set<? extends Annotation> getAnnotations() {
        return this.annotations
    }

    @Override // org.jf.dexlib2.iface.MethodParameter, org.jf.dexlib2.iface.debug.LocalInfo
    fun getName() {
        return this.name
    }

    @Override // org.jf.dexlib2.base.BaseMethodParameter, org.jf.dexlib2.iface.debug.LocalInfo
    fun getSignature() {
        return null
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference
    fun getType() {
        return this.type
    }
}
