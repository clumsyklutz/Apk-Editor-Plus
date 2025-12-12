package org.jf.dexlib2.writer.builder

import com.google.common.collect.Maps
import java.util.Collection
import java.util.Map
import java.util.concurrent.ConcurrentMap
import org.jf.dexlib2.writer.TypeSection

class BuilderTypePool extends BaseBuilderPool implements TypeSection<BuilderStringReference, BuilderTypeReference, BuilderTypeReference> {
    public final ConcurrentMap<String, BuilderTypeReference> internedItems

    constructor(DexBuilder dexBuilder) {
        super(dexBuilder)
        this.internedItems = Maps.newConcurrentMap()
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    fun getItemCount() {
        return this.internedItems.size()
    }

    @Override // org.jf.dexlib2.writer.TypeSection
    fun getItemIndex(BuilderTypeReference builderTypeReference) {
        return builderTypeReference.getIndex()
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    public Collection<? extends Map.Entry<? extends BuilderTypeReference, Integer>> getItems() {
        return new BuilderMapEntryCollection<BuilderTypeReference>(this, this.internedItems.values()) { // from class: org.jf.dexlib2.writer.builder.BuilderTypePool.1
            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun getValue(BuilderTypeReference builderTypeReference) {
                return builderTypeReference.index
            }

            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun setValue(BuilderTypeReference builderTypeReference, Int i) {
                Int i2 = builderTypeReference.index
                builderTypeReference.index = i
                return i2
            }
        }
    }

    @Override // org.jf.dexlib2.writer.NullableIndexSection
    fun getNullableItemIndex(BuilderTypeReference builderTypeReference) {
        if (builderTypeReference == null) {
            return -1
        }
        return builderTypeReference.index
    }

    @Override // org.jf.dexlib2.writer.TypeSection
    fun getString(BuilderTypeReference builderTypeReference) {
        return builderTypeReference.stringReference
    }

    fun internNullableType(String str) {
        if (str == null) {
            return null
        }
        return internType(str)
    }

    fun internType(String str) {
        BuilderTypeReference builderTypeReference = this.internedItems.get(str)
        if (builderTypeReference != null) {
            return builderTypeReference
        }
        BuilderTypeReference builderTypeReference2 = BuilderTypeReference(((BuilderStringPool) this.dexBuilder.stringSection).internString(str))
        BuilderTypeReference builderTypeReferencePutIfAbsent = this.internedItems.putIfAbsent(str, builderTypeReference2)
        return builderTypeReferencePutIfAbsent == null ? builderTypeReference2 : builderTypeReferencePutIfAbsent
    }
}
