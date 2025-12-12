package org.jf.dexlib2.dexbacked

import java.util.List
import org.jf.dexlib2.base.BaseTryBlock
import org.jf.dexlib2.dexbacked.util.VariableSizeList

class DexBackedTryBlock extends BaseTryBlock<DexBackedExceptionHandler> {
    public final DexBackedDexFile dexFile
    public final Int handlersStartOffset
    public final Int tryItemOffset

    constructor(DexBackedDexFile dexBackedDexFile, Int i, Int i2) {
        this.dexFile = dexBackedDexFile
        this.tryItemOffset = i
        this.handlersStartOffset = i2
    }

    @Override // org.jf.dexlib2.iface.TryBlock
    fun getCodeUnitCount() {
        return this.dexFile.getDataBuffer().readUshort(this.tryItemOffset + 4)
    }

    @Override // org.jf.dexlib2.iface.TryBlock
    public List<? extends DexBackedExceptionHandler> getExceptionHandlers() {
        DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(this.handlersStartOffset + this.dexFile.getDataBuffer().readUshort(this.tryItemOffset + 6))
        Int sleb128 = dexReader.readSleb128()
        if (sleb128 > 0) {
            return new VariableSizeList<DexBackedTypedExceptionHandler>(this.dexFile.getDataBuffer(), dexReader.getOffset(), sleb128) { // from class: org.jf.dexlib2.dexbacked.DexBackedTryBlock.1
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeList
                fun readNextItem(DexReader dexReader2, Int i) {
                    return DexBackedTypedExceptionHandler(DexBackedTryBlock.this.dexFile, dexReader2)
                }
            }
        }
        val i = (sleb128 * (-1)) + 1
        return new VariableSizeList<DexBackedExceptionHandler>(this.dexFile.getDataBuffer(), dexReader.getOffset(), i) { // from class: org.jf.dexlib2.dexbacked.DexBackedTryBlock.2
            @Override // org.jf.dexlib2.dexbacked.util.VariableSizeList
            fun readNextItem(DexReader dexReader2, Int i2) {
                return i2 == i + (-1) ? DexBackedCatchAllExceptionHandler(dexReader2) : DexBackedTypedExceptionHandler(DexBackedTryBlock.this.dexFile, dexReader2)
            }
        }
    }

    @Override // org.jf.dexlib2.iface.TryBlock
    fun getStartCodeAddress() {
        return this.dexFile.getDataBuffer().readSmallUint(this.tryItemOffset + 0)
    }
}
