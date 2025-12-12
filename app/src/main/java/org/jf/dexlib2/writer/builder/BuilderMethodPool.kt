package org.jf.dexlib2.writer.builder

import com.google.common.collect.Maps
import java.util.Collection
import java.util.List
import java.util.Map
import java.util.concurrent.ConcurrentMap
import org.jf.dexlib2.base.reference.BaseMethodReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.writer.MethodSection

class BuilderMethodPool extends BaseBuilderPool implements MethodSection<BuilderStringReference, BuilderTypeReference, BuilderMethodProtoReference, BuilderMethodReference, BuilderMethod> {
    public final ConcurrentMap<MethodReference, BuilderMethodReference> internedItems

    public static class MethodKey extends BaseMethodReference {
        public final String definingClass
        public final String name
        public final List<? extends CharSequence> parameterTypes
        public final String returnType

        constructor(String str, String str2, List<? extends CharSequence> list, String str3) {
            this.definingClass = str
            this.name = str2
            this.parameterTypes = list
            this.returnType = str3
        }

        @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
        fun getDefiningClass() {
            return this.definingClass
        }

        @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
        fun getName() {
            return this.name
        }

        @Override // org.jf.dexlib2.iface.reference.MethodReference
        public List<? extends CharSequence> getParameterTypes() {
            return this.parameterTypes
        }

        @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
        fun getReturnType() {
            return this.returnType
        }
    }

    constructor(DexBuilder dexBuilder) {
        super(dexBuilder)
        this.internedItems = Maps.newConcurrentMap()
    }

    @Override // org.jf.dexlib2.writer.MethodSection
    fun getDefiningClass(BuilderMethodReference builderMethodReference) {
        return builderMethodReference.definingClass
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    fun getItemCount() {
        return this.internedItems.size()
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    fun getItemIndex(BuilderMethodReference builderMethodReference) {
        return builderMethodReference.index
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    public Collection<? extends Map.Entry<? extends BuilderMethodReference, Integer>> getItems() {
        return new BuilderMapEntryCollection<BuilderMethodReference>(this, this.internedItems.values()) { // from class: org.jf.dexlib2.writer.builder.BuilderMethodPool.1
            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun getValue(BuilderMethodReference builderMethodReference) {
                return builderMethodReference.index
            }

            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun setValue(BuilderMethodReference builderMethodReference, Int i) {
                Int i2 = builderMethodReference.index
                builderMethodReference.index = i
                return i2
            }
        }
    }

    @Override // org.jf.dexlib2.writer.MethodSection
    fun getMethodIndex(BuilderMethod builderMethod) {
        return builderMethod.methodReference.index
    }

    @Override // org.jf.dexlib2.writer.MethodSection
    fun getMethodReference(BuilderMethod builderMethod) {
        return builderMethod.methodReference
    }

    @Override // org.jf.dexlib2.writer.MethodSection
    fun getName(BuilderMethodReference builderMethodReference) {
        return builderMethodReference.name
    }

    @Override // org.jf.dexlib2.writer.MethodSection
    fun getPrototype(BuilderMethod builderMethod) {
        return builderMethod.methodReference.proto
    }

    @Override // org.jf.dexlib2.writer.MethodSection
    fun getPrototype(BuilderMethodReference builderMethodReference) {
        return builderMethodReference.proto
    }

    fun internMethod(String str, String str2, List<? extends CharSequence> list, String str3) {
        return internMethod(MethodKey(str, str2, list, str3))
    }

    fun internMethod(MethodReference methodReference) {
        BuilderMethodReference builderMethodReference = this.internedItems.get(methodReference)
        if (builderMethodReference != null) {
            return builderMethodReference
        }
        BuilderMethodReference builderMethodReference2 = BuilderMethodReference(((BuilderTypePool) this.dexBuilder.typeSection).internType(methodReference.getDefiningClass()), ((BuilderStringPool) this.dexBuilder.stringSection).internString(methodReference.getName()), ((BuilderProtoPool) this.dexBuilder.protoSection).internMethodProto(methodReference))
        BuilderMethodReference builderMethodReferencePutIfAbsent = this.internedItems.putIfAbsent(builderMethodReference2, builderMethodReference2)
        return builderMethodReferencePutIfAbsent == null ? builderMethodReference2 : builderMethodReferencePutIfAbsent
    }
}
