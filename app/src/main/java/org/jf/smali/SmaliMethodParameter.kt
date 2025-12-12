package org.jf.smali

import com.google.common.collect.ImmutableSet
import com.google.common.primitives.Ints
import java.util.Comparator
import java.util.Set
import org.jf.dexlib2.base.BaseMethodParameter
import org.jf.dexlib2.iface.Annotation

class SmaliMethodParameter extends BaseMethodParameter implements WithRegister {
    public static final Comparator<WithRegister> COMPARATOR = new Comparator<WithRegister>() { // from class: org.jf.smali.SmaliMethodParameter.1
        @Override // java.util.Comparator
        fun compare(WithRegister withRegister, WithRegister withRegister2) {
            return Ints.compare(withRegister.getRegister(), withRegister2.getRegister())
        }
    }
    public Set<? extends Annotation> annotations = ImmutableSet.of()
    public String name
    public final Int register
    public final String type

    constructor(Int i, String str) {
        this.register = i
        this.type = str
    }

    @Override // org.jf.dexlib2.iface.MethodParameter
    public Set<? extends Annotation> getAnnotations() {
        return this.annotations
    }

    @Override // org.jf.dexlib2.iface.MethodParameter, org.jf.dexlib2.iface.debug.LocalInfo
    fun getName() {
        return this.name
    }

    @Override // org.jf.smali.WithRegister
    fun getRegister() {
        return this.register
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
