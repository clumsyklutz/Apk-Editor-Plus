package org.jf.dexlib2.writer.builder

import com.google.common.collect.Maps
import java.util.Collection
import java.util.Map
import java.util.concurrent.ConcurrentMap
import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.iface.reference.MethodHandleReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.writer.MethodHandleSection
import org.jf.util.ExceptionWithContext

class BuilderMethodHandlePool extends BaseBuilderPool implements MethodHandleSection<BuilderMethodHandleReference, BuilderFieldReference, BuilderMethodReference> {
    public final ConcurrentMap<MethodHandleReference, BuilderMethodHandleReference> internedItems

    constructor(DexBuilder dexBuilder) {
        super(dexBuilder)
        this.internedItems = Maps.newConcurrentMap()
    }

    @Override // org.jf.dexlib2.writer.MethodHandleSection
    fun getFieldReference(BuilderMethodHandleReference builderMethodHandleReference) {
        return (BuilderFieldReference) builderMethodHandleReference.getMemberReference()
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    fun getItemCount() {
        return this.internedItems.size()
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    fun getItemIndex(BuilderMethodHandleReference builderMethodHandleReference) {
        return builderMethodHandleReference.index
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    public Collection<? extends Map.Entry<? extends BuilderMethodHandleReference, Integer>> getItems() {
        return new BuilderMapEntryCollection<BuilderMethodHandleReference>(this, this.internedItems.values()) { // from class: org.jf.dexlib2.writer.builder.BuilderMethodHandlePool.1
            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun getValue(BuilderMethodHandleReference builderMethodHandleReference) {
                return builderMethodHandleReference.index
            }

            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun setValue(BuilderMethodHandleReference builderMethodHandleReference, Int i) {
                Int i2 = builderMethodHandleReference.index
                builderMethodHandleReference.index = i
                return i2
            }
        }
    }

    @Override // org.jf.dexlib2.writer.MethodHandleSection
    fun getMethodReference(BuilderMethodHandleReference builderMethodHandleReference) {
        return (BuilderMethodReference) builderMethodHandleReference.getMemberReference()
    }

    fun internMethodHandle(MethodHandleReference methodHandleReference) {
        BuilderReference builderReferenceInternFieldReference
        BuilderMethodHandleReference builderMethodHandleReference = this.internedItems.get(methodHandleReference)
        if (builderMethodHandleReference != null) {
            return builderMethodHandleReference
        }
        switch (methodHandleReference.getMethodHandleType()) {
            case 0:
            case 1:
            case 2:
            case 3:
                builderReferenceInternFieldReference = this.dexBuilder.internFieldReference((FieldReference) methodHandleReference.getMemberReference())
                break
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                builderReferenceInternFieldReference = this.dexBuilder.internMethodReference((MethodReference) methodHandleReference.getMemberReference())
                break
            default:
                throw ExceptionWithContext("Invalid method handle type: %d", Integer.valueOf(methodHandleReference.getMethodHandleType()))
        }
        BuilderMethodHandleReference builderMethodHandleReference2 = BuilderMethodHandleReference(methodHandleReference.getMethodHandleType(), builderReferenceInternFieldReference)
        BuilderMethodHandleReference builderMethodHandleReferencePutIfAbsent = this.internedItems.putIfAbsent(builderMethodHandleReference2, builderMethodHandleReference2)
        return builderMethodHandleReferencePutIfAbsent == null ? builderMethodHandleReference2 : builderMethodHandleReferencePutIfAbsent
    }
}
