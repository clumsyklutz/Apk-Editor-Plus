package org.jf.dexlib2.writer.builder

import com.google.common.base.Function
import com.google.common.collect.ImmutableList
import com.google.common.collect.Iterables
import com.google.common.collect.Maps
import java.util.Collection
import java.util.List
import java.util.Map
import java.util.concurrent.ConcurrentMap
import org.jf.dexlib2.writer.TypeListSection

class BuilderTypeListPool extends BaseBuilderPool implements TypeListSection<BuilderTypeReference, BuilderTypeList> {
    public final ConcurrentMap<List<? extends CharSequence>, BuilderTypeList> internedItems

    constructor(DexBuilder dexBuilder) {
        super(dexBuilder)
        this.internedItems = Maps.newConcurrentMap()
    }

    @Override // org.jf.dexlib2.writer.OffsetSection
    public Collection<? extends Map.Entry<? extends BuilderTypeList, Integer>> getItems() {
        return new BuilderMapEntryCollection<BuilderTypeList>(this, this.internedItems.values()) { // from class: org.jf.dexlib2.writer.builder.BuilderTypeListPool.2
            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun getValue(BuilderTypeList builderTypeList) {
                return builderTypeList.offset
            }

            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun setValue(BuilderTypeList builderTypeList, Int i) {
                Int i2 = builderTypeList.offset
                builderTypeList.offset = i
                return i2
            }
        }
    }

    @Override // org.jf.dexlib2.writer.TypeListSection, org.jf.dexlib2.writer.NullableOffsetSection
    fun getNullableItemOffset(BuilderTypeList builderTypeList) {
        if (builderTypeList == null || builderTypeList.size() == 0) {
            return 0
        }
        return builderTypeList.offset
    }

    @Override // org.jf.dexlib2.writer.TypeListSection
    public Collection<? extends BuilderTypeReference> getTypes(BuilderTypeList builderTypeList) {
        return builderTypeList == null ? BuilderTypeList.EMPTY : builderTypeList.types
    }

    fun internTypeList(List<? extends CharSequence> list) {
        if (list == null || list.size() == 0) {
            return BuilderTypeList.EMPTY
        }
        BuilderTypeList builderTypeList = this.internedItems.get(list)
        if (builderTypeList != null) {
            return builderTypeList
        }
        BuilderTypeList builderTypeList2 = BuilderTypeList(ImmutableList.copyOf(Iterables.transform(list, new Function<CharSequence, BuilderTypeReference>() { // from class: org.jf.dexlib2.writer.builder.BuilderTypeListPool.1
            @Override // com.google.common.base.Function
            fun apply(CharSequence charSequence) {
                return ((BuilderTypePool) BuilderTypeListPool.this.dexBuilder.typeSection).internType(charSequence.toString())
            }
        })))
        BuilderTypeList builderTypeListPutIfAbsent = this.internedItems.putIfAbsent(builderTypeList2, builderTypeList2)
        return builderTypeListPutIfAbsent == null ? builderTypeList2 : builderTypeListPutIfAbsent
    }
}
