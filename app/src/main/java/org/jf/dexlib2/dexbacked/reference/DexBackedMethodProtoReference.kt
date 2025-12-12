package org.jf.dexlib2.dexbacked.reference

import com.google.common.collect.ImmutableList
import java.util.List
import org.jf.dexlib2.base.reference.BaseMethodProtoReference
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.util.FixedSizeList
import org.jf.dexlib2.iface.reference.Reference

class DexBackedMethodProtoReference extends BaseMethodProtoReference {
    public final DexBackedDexFile dexFile
    public final Int protoIndex

    constructor(DexBackedDexFile dexBackedDexFile, Int i) {
        this.dexFile = dexBackedDexFile
        this.protoIndex = i
    }

    @Override // org.jf.dexlib2.iface.reference.MethodProtoReference
    public List<String> getParameterTypes() {
        Int smallUint = this.dexFile.getBuffer().readSmallUint(this.dexFile.getProtoSection().getOffset(this.protoIndex) + 8)
        if (smallUint <= 0) {
            return ImmutableList.of()
        }
        val smallUint2 = this.dexFile.getDataBuffer().readSmallUint(smallUint + 0)
        val i = smallUint + 4
        return new FixedSizeList<String>() { // from class: org.jf.dexlib2.dexbacked.reference.DexBackedMethodProtoReference.1
            @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
            fun readItem(Int i2) {
                return DexBackedMethodProtoReference.this.dexFile.getTypeSection().get(DexBackedMethodProtoReference.this.dexFile.getDataBuffer().readUshort(i + (i2 * 2)))
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return smallUint2
            }
        }
    }

    @Override // org.jf.dexlib2.iface.reference.MethodProtoReference
    fun getReturnType() {
        return this.dexFile.getTypeSection().get(this.dexFile.getBuffer().readSmallUint(this.dexFile.getProtoSection().getOffset(this.protoIndex) + 4))
    }

    @Override // org.jf.dexlib2.base.reference.BaseReference, org.jf.dexlib2.iface.reference.Reference
    fun validateReference() throws Reference.InvalidReferenceException {
        Int i = this.protoIndex
        if (i < 0 || i >= this.dexFile.getProtoSection().size()) {
            throw new Reference.InvalidReferenceException("proto@" + this.protoIndex)
        }
    }
}
