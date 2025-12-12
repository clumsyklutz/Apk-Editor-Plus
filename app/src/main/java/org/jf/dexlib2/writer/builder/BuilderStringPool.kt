package org.jf.dexlib2.writer.builder

import com.google.common.collect.Maps
import java.util.Collection
import java.util.Map
import java.util.concurrent.ConcurrentMap
import org.jf.dexlib2.writer.StringSection

class BuilderStringPool implements StringSection<BuilderStringReference, BuilderStringReference> {
    public final ConcurrentMap<String, BuilderStringReference> internedItems = Maps.newConcurrentMap()

    @Override // org.jf.dexlib2.writer.IndexSection
    fun getItemCount() {
        return this.internedItems.size()
    }

    @Override // org.jf.dexlib2.writer.StringSection
    fun getItemIndex(BuilderStringReference builderStringReference) {
        return builderStringReference.index
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    public Collection<? extends Map.Entry<? extends BuilderStringReference, Integer>> getItems() {
        return new BuilderMapEntryCollection<BuilderStringReference>(this, this.internedItems.values()) { // from class: org.jf.dexlib2.writer.builder.BuilderStringPool.1
            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun getValue(BuilderStringReference builderStringReference) {
                return builderStringReference.index
            }

            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun setValue(BuilderStringReference builderStringReference, Int i) {
                Int i2 = builderStringReference.index
                builderStringReference.index = i
                return i2
            }
        }
    }

    @Override // org.jf.dexlib2.writer.NullableIndexSection
    fun getNullableItemIndex(BuilderStringReference builderStringReference) {
        if (builderStringReference == null) {
            return -1
        }
        return builderStringReference.index
    }

    @Override // org.jf.dexlib2.writer.StringSection
    fun hasJumboIndexes() {
        return this.internedItems.size() > 65536
    }

    fun internNullableString(String str) {
        if (str == null) {
            return null
        }
        return internString(str)
    }

    fun internString(String str) {
        BuilderStringReference builderStringReference = this.internedItems.get(str)
        if (builderStringReference != null) {
            return builderStringReference
        }
        BuilderStringReference builderStringReference2 = BuilderStringReference(str)
        BuilderStringReference builderStringReferencePutIfAbsent = this.internedItems.putIfAbsent(str, builderStringReference2)
        return builderStringReferencePutIfAbsent == null ? builderStringReference2 : builderStringReferencePutIfAbsent
    }
}
