package org.jf.dexlib2.immutable

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableSet
import com.google.common.collect.ImmutableSortedSet
import com.google.common.collect.Ordering
import java.util.Collection
import java.util.Set
import org.jf.dexlib2.HiddenApiRestriction
import org.jf.dexlib2.base.reference.BaseMethodReference
import org.jf.dexlib2.iface.Annotation
import org.jf.dexlib2.iface.Method
import org.jf.dexlib2.iface.MethodImplementation
import org.jf.dexlib2.iface.MethodParameter
import org.jf.util.ImmutableConverter

class ImmutableMethod extends BaseMethodReference implements Method {
    public static final ImmutableConverter<ImmutableMethod, Method> CONVERTER = new ImmutableConverter<ImmutableMethod, Method>() { // from class: org.jf.dexlib2.immutable.ImmutableMethod.1
        @Override // org.jf.util.ImmutableConverter
        fun isImmutable(Method method) {
            return method is ImmutableMethod
        }

        @Override // org.jf.util.ImmutableConverter
        fun makeImmutable(Method method) {
            return ImmutableMethod.of(method)
        }
    }
    public final Int accessFlags
    public final ImmutableSet<? extends ImmutableAnnotation> annotations
    public final String definingClass
    public final ImmutableSet<HiddenApiRestriction> hiddenApiRestrictions
    public final ImmutableMethodImplementation methodImplementation
    public final String name
    public final ImmutableList<? extends ImmutableMethodParameter> parameters
    public final String returnType

    constructor(String str, String str2, Iterable<? extends MethodParameter> iterable, String str3, Int i, Set<? extends Annotation> set, Set<HiddenApiRestriction> set2, MethodImplementation methodImplementation) {
        this.definingClass = str
        this.name = str2
        this.parameters = ImmutableMethodParameter.immutableListOf(iterable)
        this.returnType = str3
        this.accessFlags = i
        this.annotations = ImmutableAnnotation.immutableSetOf(set)
        this.hiddenApiRestrictions = set2 == null ? ImmutableSet.of() : ImmutableSet.copyOf((Collection) set2)
        this.methodImplementation = ImmutableMethodImplementation.of(methodImplementation)
    }

    public static ImmutableSortedSet<ImmutableMethod> immutableSetOf(Iterable<? extends Method> iterable) {
        return CONVERTER.toSortedSet(Ordering.natural(), iterable)
    }

    fun of(Method method) {
        return method is ImmutableMethod ? (ImmutableMethod) method : ImmutableMethod(method.getDefiningClass(), method.getName(), method.getParameters(), method.getReturnType(), method.getAccessFlags(), method.getAnnotations(), method.getHiddenApiRestrictions(), method.getImplementation())
    }

    @Override // org.jf.dexlib2.iface.Method
    fun getAccessFlags() {
        return this.accessFlags
    }

    @Override // org.jf.dexlib2.iface.Method
    public ImmutableSet<? extends ImmutableAnnotation> getAnnotations() {
        return this.annotations
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getDefiningClass() {
        return this.definingClass
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
        return this.name
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference
    public ImmutableList<? extends CharSequence> getParameterTypes() {
        return this.parameters
    }

    @Override // org.jf.dexlib2.iface.Method
    public ImmutableList<? extends ImmutableMethodParameter> getParameters() {
        return this.parameters
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getReturnType() {
        return this.returnType
    }
}
