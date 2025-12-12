package org.jf.dexlib2.dexbacked.value

import org.jf.dexlib2.base.value.BaseMethodHandleEncodedValue
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.DexReader
import org.jf.dexlib2.dexbacked.reference.DexBackedMethodHandleReference
import org.jf.dexlib2.iface.reference.MethodHandleReference

class DexBackedMethodHandleEncodedValue extends BaseMethodHandleEncodedValue {
    public final DexBackedDexFile dexFile
    public final Int methodHandleIndex

    constructor(DexBackedDexFile dexBackedDexFile, DexReader dexReader, Int i) {
        this.dexFile = dexBackedDexFile
        this.methodHandleIndex = dexReader.readSizedSmallUint(i + 1)
    }

    @Override // org.jf.dexlib2.iface.value.MethodHandleEncodedValue
    fun getValue() {
        return DexBackedMethodHandleReference(this.dexFile, this.methodHandleIndex)
    }
}
