package org.jf.dexlib2.dexbacked

class DexBackedTypedExceptionHandler extends DexBackedExceptionHandler {
    public final DexBackedDexFile dexFile
    public final Int handlerCodeAddress
    public final Int typeId

    constructor(DexBackedDexFile dexBackedDexFile, DexReader dexReader) {
        this.dexFile = dexBackedDexFile
        this.typeId = dexReader.readSmallUleb128()
        this.handlerCodeAddress = dexReader.readSmallUleb128()
    }

    @Override // org.jf.dexlib2.iface.ExceptionHandler
    fun getExceptionType() {
        return this.dexFile.getTypeSection().get(this.typeId)
    }

    @Override // org.jf.dexlib2.iface.ExceptionHandler
    fun getHandlerCodeAddress() {
        return this.handlerCodeAddress
    }
}
