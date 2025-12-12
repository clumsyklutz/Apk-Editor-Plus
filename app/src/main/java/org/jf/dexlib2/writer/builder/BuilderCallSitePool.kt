package org.jf.dexlib2.writer.builder

import com.google.common.collect.Maps
import java.util.Collection
import java.util.Map
import java.util.concurrent.ConcurrentMap
import org.jf.dexlib2.iface.reference.CallSiteReference
import org.jf.dexlib2.writer.CallSiteSection
import org.jf.dexlib2.writer.builder.BuilderEncodedValues
import org.jf.dexlib2.writer.util.CallSiteUtil

class BuilderCallSitePool extends BaseBuilderPool implements CallSiteSection<BuilderCallSiteReference, BuilderEncodedValues.BuilderArrayEncodedValue> {
    public final ConcurrentMap<CallSiteReference, BuilderCallSiteReference> internedItems

    constructor(DexBuilder dexBuilder) {
        super(dexBuilder)
        this.internedItems = Maps.newConcurrentMap()
    }

    @Override // org.jf.dexlib2.writer.CallSiteSection
    public BuilderEncodedValues.BuilderArrayEncodedValue getEncodedCallSite(BuilderCallSiteReference builderCallSiteReference) {
        return builderCallSiteReference.encodedCallSite
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    fun getItemCount() {
        return this.internedItems.size()
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    fun getItemIndex(BuilderCallSiteReference builderCallSiteReference) {
        return builderCallSiteReference.index
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    public Collection<? extends Map.Entry<? extends BuilderCallSiteReference, Integer>> getItems() {
        return new BuilderMapEntryCollection<BuilderCallSiteReference>(this, this.internedItems.values()) { // from class: org.jf.dexlib2.writer.builder.BuilderCallSitePool.1
            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun getValue(BuilderCallSiteReference builderCallSiteReference) {
                return builderCallSiteReference.index
            }

            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            fun setValue(BuilderCallSiteReference builderCallSiteReference, Int i) {
                Int i2 = builderCallSiteReference.index
                builderCallSiteReference.index = i
                return i2
            }
        }
    }

    fun internCallSite(CallSiteReference callSiteReference) {
        BuilderCallSiteReference builderCallSiteReference = this.internedItems.get(callSiteReference)
        if (builderCallSiteReference != null) {
            return builderCallSiteReference
        }
        BuilderCallSiteReference builderCallSiteReference2 = BuilderCallSiteReference(callSiteReference.getName(), ((BuilderEncodedArrayPool) this.dexBuilder.encodedArraySection).internArrayEncodedValue(CallSiteUtil.getEncodedCallSite(callSiteReference)))
        BuilderCallSiteReference builderCallSiteReferencePutIfAbsent = this.internedItems.putIfAbsent(builderCallSiteReference2, builderCallSiteReference2)
        return builderCallSiteReferencePutIfAbsent == null ? builderCallSiteReference2 : builderCallSiteReferencePutIfAbsent
    }
}
