package org.jf.dexlib2.writer.builder

import com.google.common.collect.Maps
import java.util.Collection
import java.util.Map
import java.util.concurrent.ConcurrentMap
import org.jf.dexlib2.iface.reference.MethodProtoReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.immutable.reference.ImmutableMethodProtoReference
import org.jf.dexlib2.util.MethodUtil
import org.jf.dexlib2.writer.ProtoSection

class BuilderProtoPool extends BaseBuilderPool implements ProtoSection<BuilderStringReference, BuilderTypeReference, BuilderMethodProtoReference, BuilderTypeList> {
    public final ConcurrentMap<MethodProtoReference, BuilderMethodProtoReference> internedItems

    constructor(DexBuilder dexBuilder) {
        super(dexBuilder)
        this.internedItems = Maps.newConcurrentMap()
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    fun getItemCount() {
        return this.internedItems.size()
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    fun getItemIndex(BuilderMethodProtoReference builderMethodProtoReference) {
        return builderMethodProtoReference.getIndex()
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    public Collection<? extends Map.Entry<? extends BuilderMethodProtoReference, Integer>> getItems() {
        return new BuilderMapEntryCollection<BuilderMethodProtoReference>(this, this.internedItems.values()) { // from class: org.jf.dexlib2.writer.builder.BuilderProtoPool.1
            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun getValue(BuilderMethodProtoReference builderMethodProtoReference) {
                return builderMethodProtoReference.index
            }

            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun setValue(BuilderMethodProtoReference builderMethodProtoReference, Int i) {
                Int i2 = builderMethodProtoReference.index
                builderMethodProtoReference.index = i
                return i2
            }
        }
    }

    @Override // org.jf.dexlib2.writer.ProtoSection
    fun getParameters(BuilderMethodProtoReference builderMethodProtoReference) {
        return builderMethodProtoReference.parameterTypes
    }

    @Override // org.jf.dexlib2.writer.ProtoSection
    fun getReturnType(BuilderMethodProtoReference builderMethodProtoReference) {
        return builderMethodProtoReference.returnType
    }

    @Override // org.jf.dexlib2.writer.ProtoSection
    fun getShorty(BuilderMethodProtoReference builderMethodProtoReference) {
        return builderMethodProtoReference.shorty
    }

    fun internMethodProto(MethodProtoReference methodProtoReference) {
        BuilderMethodProtoReference builderMethodProtoReference = this.internedItems.get(methodProtoReference)
        if (builderMethodProtoReference != null) {
            return builderMethodProtoReference
        }
        BuilderMethodProtoReference builderMethodProtoReference2 = BuilderMethodProtoReference(((BuilderStringPool) this.dexBuilder.stringSection).internString(MethodUtil.getShorty(methodProtoReference.getParameterTypes(), methodProtoReference.getReturnType())), ((BuilderTypeListPool) this.dexBuilder.typeListSection).internTypeList(methodProtoReference.getParameterTypes()), ((BuilderTypePool) this.dexBuilder.typeSection).internType(methodProtoReference.getReturnType()))
        BuilderMethodProtoReference builderMethodProtoReferencePutIfAbsent = this.internedItems.putIfAbsent(builderMethodProtoReference2, builderMethodProtoReference2)
        return builderMethodProtoReferencePutIfAbsent == null ? builderMethodProtoReference2 : builderMethodProtoReferencePutIfAbsent
    }

    fun internMethodProto(MethodReference methodReference) {
        return internMethodProto(ImmutableMethodProtoReference(methodReference.getParameterTypes(), methodReference.getReturnType()))
    }
}
