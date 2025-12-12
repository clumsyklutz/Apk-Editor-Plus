package org.jf.dexlib2.dexbacked.raw

import org.jf.dexlib2.dexbacked.DexBackedDexFile

class MapItem {
    public final DexBackedDexFile dexFile
    public final Int offset

    constructor(DexBackedDexFile dexBackedDexFile, Int i) {
        this.dexFile = dexBackedDexFile
        this.offset = i
    }

    fun getItemCount() {
        return this.dexFile.getDataBuffer().readSmallUint(this.offset + 4)
    }

    fun getOffset() {
        return this.dexFile.getDataBuffer().readSmallUint(this.offset + 8)
    }

    fun getType() {
        return this.dexFile.getDataBuffer().readUshort(this.offset + 0)
    }
}
