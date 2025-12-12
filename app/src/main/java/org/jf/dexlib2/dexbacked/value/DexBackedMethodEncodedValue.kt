package org.jf.dexlib2.dexbacked.value

import org.jf.dexlib2.base.value.BaseMethodEncodedValue
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.DexReader
import org.jf.dexlib2.dexbacked.reference.DexBackedMethodReference
import org.jf.dexlib2.iface.reference.MethodReference

class DexBackedMethodEncodedValue extends BaseMethodEncodedValue {
    public final DexBackedDexFile dexFile
    public final Int methodIndex

    constructor(DexBackedDexFile dexBackedDexFile, DexReader dexReader, Int i) {
        this.dexFile = dexBackedDexFile
        this.methodIndex = dexReader.readSizedSmallUint(i + 1)
    }

    @Override // org.jf.dexlib2.iface.value.MethodEncodedValue
    fun getValue() {
        return DexBackedMethodReference(this.dexFile, this.methodIndex)
    }
}
