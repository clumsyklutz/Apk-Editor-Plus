package org.jf.dexlib2.writer.builder

import com.google.common.collect.Maps
import java.util.Collection
import java.util.Map
import java.util.concurrent.ConcurrentMap
import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.immutable.reference.ImmutableFieldReference
import org.jf.dexlib2.writer.FieldSection

class BuilderFieldPool extends BaseBuilderPool implements FieldSection<BuilderStringReference, BuilderTypeReference, BuilderFieldReference, BuilderField> {
    public final ConcurrentMap<FieldReference, BuilderFieldReference> internedItems

    constructor(DexBuilder dexBuilder) {
        super(dexBuilder)
        this.internedItems = Maps.newConcurrentMap()
    }

    @Override // org.jf.dexlib2.writer.FieldSection
    fun getDefiningClass(BuilderFieldReference builderFieldReference) {
        return builderFieldReference.definingClass
    }

    @Override // org.jf.dexlib2.writer.FieldSection
    fun getFieldIndex(BuilderField builderField) {
        return builderField.fieldReference.getIndex()
    }

    @Override // org.jf.dexlib2.writer.FieldSection
    fun getFieldType(BuilderFieldReference builderFieldReference) {
        return builderFieldReference.fieldType
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    fun getItemCount() {
        return this.internedItems.size()
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    fun getItemIndex(BuilderFieldReference builderFieldReference) {
        return builderFieldReference.index
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    public Collection<? extends Map.Entry<? extends BuilderFieldReference, Integer>> getItems() {
        return new BuilderMapEntryCollection<BuilderFieldReference>(this, this.internedItems.values()) { // from class: org.jf.dexlib2.writer.builder.BuilderFieldPool.1
            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun getValue(BuilderFieldReference builderFieldReference) {
                return builderFieldReference.index
            }

            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun setValue(BuilderFieldReference builderFieldReference, Int i) {
                Int i2 = builderFieldReference.index
                builderFieldReference.index = i
                return i2
            }
        }
    }

    @Override // org.jf.dexlib2.writer.FieldSection
    fun getName(BuilderFieldReference builderFieldReference) {
        return builderFieldReference.name
    }

    fun internField(String str, String str2, String str3) {
        return internField(ImmutableFieldReference(str, str2, str3))
    }

    fun internField(FieldReference fieldReference) {
        BuilderFieldReference builderFieldReference = this.internedItems.get(fieldReference)
        if (builderFieldReference != null) {
            return builderFieldReference
        }
        BuilderFieldReference builderFieldReference2 = BuilderFieldReference(((BuilderTypePool) this.dexBuilder.typeSection).internType(fieldReference.getDefiningClass()), ((BuilderStringPool) this.dexBuilder.stringSection).internString(fieldReference.getName()), ((BuilderTypePool) this.dexBuilder.typeSection).internType(fieldReference.getType()))
        BuilderFieldReference builderFieldReferencePutIfAbsent = this.internedItems.putIfAbsent(builderFieldReference2, builderFieldReference2)
        return builderFieldReferencePutIfAbsent == null ? builderFieldReference2 : builderFieldReferencePutIfAbsent
    }
}
