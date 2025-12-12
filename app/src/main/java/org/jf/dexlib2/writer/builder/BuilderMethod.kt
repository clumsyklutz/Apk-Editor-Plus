package org.jf.dexlib2.writer.builder

import java.util.List
import java.util.Set
import org.jf.dexlib2.HiddenApiRestriction
import org.jf.dexlib2.base.reference.BaseMethodReference
import org.jf.dexlib2.iface.Method
import org.jf.dexlib2.iface.MethodImplementation

class BuilderMethod extends BaseMethodReference implements Method {
    public final Int accessFlags
    public final BuilderAnnotationSet annotations
    public final Set<HiddenApiRestriction> hiddenApiRestrictions
    public final MethodImplementation methodImplementation
    public final BuilderMethodReference methodReference
    public final List<? extends BuilderMethodParameter> parameters
    public Int annotationSetRefListOffset = 0
    public Int codeItemOffset = 0

    constructor(BuilderMethodReference builderMethodReference, List<? extends BuilderMethodParameter> list, Int i, BuilderAnnotationSet builderAnnotationSet, Set<HiddenApiRestriction> set, MethodImplementation methodImplementation) {
        this.methodReference = builderMethodReference
        this.parameters = list
        this.accessFlags = i
        this.annotations = builderAnnotationSet
        this.hiddenApiRestrictions = set
        this.methodImplementation = methodImplementation
    }

    @Override // org.jf.dexlib2.iface.Method
    fun getAccessFlags() {
        return this.accessFlags
    }

    @Override // org.jf.dexlib2.iface.Method
    fun getAnnotations() {
        return this.annotations
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getDefiningClass() {
        return this.methodReference.definingClass.getType()
    }

    @Override // org.jf.dexlib2.iface.Method
    public Set<HiddenApiRestriction> getHiddenApiRestrictions() {
        return this.hiddenApiRestrictions
    }

    @Override // org.jf.dexlib2.iface.Method
    fun getImplementation() {
        return this.methodImplementation
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getName() {
        return this.methodReference.name.getString()
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference
    fun getParameterTypes() {
        return this.methodReference.proto.parameterTypes
    }

    @Override // org.jf.dexlib2.iface.Method
    public List<? extends BuilderMethodParameter> getParameters() {
        return this.parameters
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getReturnType() {
        return this.methodReference.proto.returnType.getType()
    }
}
