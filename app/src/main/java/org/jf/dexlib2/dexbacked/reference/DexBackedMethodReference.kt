package org.jf.dexlib2.dexbacked.reference

import com.google.common.collect.ImmutableList
import java.util.List
import org.jf.dexlib2.base.reference.BaseMethodReference
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.util.FixedSizeList
import org.jf.dexlib2.iface.reference.Reference

class DexBackedMethodReference extends BaseMethodReference {
    public final DexBackedDexFile dexFile
    public final Int methodIndex
    public Int protoIdItemOffset

    constructor(DexBackedDexFile dexBackedDexFile, Int i) {
        this.dexFile = dexBackedDexFile
        this.methodIndex = i
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getDefiningClass() {
        return this.dexFile.getTypeSection().get(this.dexFile.getBuffer().readUshort(this.dexFile.getMethodSection().getOffset(this.methodIndex) + 0))
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getName() {
        return this.dexFile.getStringSection().get(this.dexFile.getBuffer().readSmallUint(this.dexFile.getMethodSection().getOffset(this.methodIndex) + 4))
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference
    public List<String> getParameterTypes() {
        Int smallUint = this.dexFile.getBuffer().readSmallUint(getProtoIdItemOffset() + 8)
        if (smallUint <= 0) {
            return ImmutableList.of()
        }
        val smallUint2 = this.dexFile.getDataBuffer().readSmallUint(smallUint + 0)
        val i = smallUint + 4
        return new FixedSizeList<String>() { // from class: org.jf.dexlib2.dexbacked.reference.DexBackedMethodReference.1
            @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
            fun readItem(Int i2) {
                return DexBackedMethodReference.this.dexFile.getTypeSection().get(DexBackedMethodReference.this.dexFile.getDataBuffer().readUshort(i + (i2 * 2)))
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return smallUint2
            }
        }
    }

    public final Int getProtoIdItemOffset() {
        if (this.protoIdItemOffset == 0) {
            this.protoIdItemOffset = this.dexFile.getProtoSection().getOffset(this.dexFile.getBuffer().readUshort(this.dexFile.getMethodSection().getOffset(this.methodIndex) + 2))
        }
        return this.protoIdItemOffset
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getReturnType() {
        return this.dexFile.getTypeSection().get(this.dexFile.getBuffer().readSmallUint(getProtoIdItemOffset() + 4))
    }

    @Override // org.jf.dexlib2.base.reference.BaseReference, org.jf.dexlib2.iface.reference.Reference
    fun validateReference() throws Reference.InvalidReferenceException {
        Int i = this.methodIndex
        if (i < 0 || i >= this.dexFile.getMethodSection().size()) {
            throw new Reference.InvalidReferenceException("method@" + this.methodIndex)
        }
    }
}
