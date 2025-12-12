package org.jf.dexlib2.dexbacked.value

import org.jf.dexlib2.base.value.BaseMethodTypeEncodedValue
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.DexReader
import org.jf.dexlib2.dexbacked.reference.DexBackedMethodProtoReference
import org.jf.dexlib2.iface.reference.MethodProtoReference

class DexBackedMethodTypeEncodedValue extends BaseMethodTypeEncodedValue {
    public final DexBackedDexFile dexFile
    public final Int methodProtoIndex

    constructor(DexBackedDexFile dexBackedDexFile, DexReader dexReader, Int i) {
        this.dexFile = dexBackedDexFile
        this.methodProtoIndex = dexReader.readSizedSmallUint(i + 1)
    }

    @Override // org.jf.dexlib2.iface.value.MethodTypeEncodedValue
    fun getValue() {
        return DexBackedMethodProtoReference(this.dexFile, this.methodProtoIndex)
    }
}
