package org.jf.dexlib2.writer.builder

import com.google.common.collect.ImmutableList
import java.util.List
import org.jf.dexlib2.base.reference.BaseCallSiteReference
import org.jf.dexlib2.iface.value.StringEncodedValue
import org.jf.dexlib2.writer.builder.BuilderEncodedValues

class BuilderCallSiteReference extends BaseCallSiteReference implements BuilderReference {
    public final BuilderEncodedValues.BuilderArrayEncodedValue encodedCallSite
    public Int index = -1
    public final String name

    constructor(String str, BuilderEncodedValues.BuilderArrayEncodedValue builderArrayEncodedValue) {
        this.name = str
        this.encodedCallSite = builderArrayEncodedValue
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    public List<? extends BuilderEncodedValues.BuilderEncodedValue> getExtraArguments() {
        if (this.encodedCallSite.elements.size() <= 3) {
            return ImmutableList.of()
        }
        List<? extends BuilderEncodedValues.BuilderEncodedValue> list = this.encodedCallSite.elements
        return list.subList(3, list.size())
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    fun getMethodHandle() {
        return ((BuilderEncodedValues.BuilderMethodHandleEncodedValue) this.encodedCallSite.elements.get(0)).getValue()
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    fun getMethodName() {
        return ((StringEncodedValue) this.encodedCallSite.elements.get(1)).getValue()
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    fun getMethodProto() {
        return ((BuilderEncodedValues.BuilderMethodTypeEncodedValue) this.encodedCallSite.elements.get(2)).getValue()
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    fun getName() {
        return this.name
    }
}
