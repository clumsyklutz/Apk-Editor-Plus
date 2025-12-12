package org.jf.dexlib2.dexbacked.reference

import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.iface.reference.Reference
import org.jf.util.ExceptionWithContext

abstract class DexBackedReference {
    fun makeReference(DexBackedDexFile dexBackedDexFile, Int i, Int i2) {
        switch (i) {
            case 0:
                return DexBackedStringReference(dexBackedDexFile, i2)
            case 1:
                return DexBackedTypeReference(dexBackedDexFile, i2)
            case 2:
                return DexBackedFieldReference(dexBackedDexFile, i2)
            case 3:
                return DexBackedMethodReference(dexBackedDexFile, i2)
            case 4:
                return DexBackedMethodProtoReference(dexBackedDexFile, i2)
            case 5:
                return DexBackedCallSiteReference(dexBackedDexFile, i2)
            case 6:
                return DexBackedMethodHandleReference(dexBackedDexFile, i2)
            default:
                throw ExceptionWithContext("Invalid reference type: %d", Integer.valueOf(i))
        }
    }
}
