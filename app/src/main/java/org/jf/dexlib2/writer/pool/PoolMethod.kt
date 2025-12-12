package org.jf.dexlib2.writer.pool

import com.google.common.base.Function
import java.util.List
import java.util.Set
import org.jf.dexlib2.HiddenApiRestriction
import org.jf.dexlib2.base.reference.BaseMethodReference
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.iface.Method
import org.jf.dexlib2.iface.MethodImplementation
import org.jf.dexlib2.iface.MethodParameter

class PoolMethod extends BaseMethodReference implements Method {
    public static final Function<Method, PoolMethod> TRANSFORM = new Function<Method, PoolMethod>() { // from class: org.jf.dexlib2.writer.pool.PoolMethod.1
        @Override // com.google.common.base.Function
        fun apply(Method method) {
            return PoolMethod(method)
        }
    }
    public Int annotationSetRefListOffset = 0
    public Int codeItemOffset = 0
    public final Method method

    constructor(Method method) {
        this.method = method
    }

    @Override // org.jf.dexlib2.iface.Method
    fun getAccessFlags() {
        return this.method.getAccessFlags()
    }

    @Override // org.jf.dexlib2.iface.Method
    public Set<? extends Annotation> getAnnotations() {
        return this.method.getAnnotations()
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getDefiningClass() {
        return this.method.getDefiningClass()
    }

    @Override // org.jf.dexlib2.iface.Method
    public Set<HiddenApiRestriction> getHiddenApiRestrictions() {
        return this.method.getHiddenApiRestrictions()
    }

    @Override // org.jf.dexlib2.iface.Method
    fun getImplementation() {
        return this.method.getImplementation()
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getName() {
        return this.method.getName()
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference
    public List<? extends CharSequence> getParameterTypes() {
        return this.method.getParameterTypes()
    }

    @Override // org.jf.dexlib2.iface.Method
    public List<? extends MethodParameter> getParameters() {
        return this.method.getParameters()
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getReturnType() {
        return this.method.getReturnType()
    }
}
