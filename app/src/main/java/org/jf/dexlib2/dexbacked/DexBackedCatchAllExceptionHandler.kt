package org.jf.dexlib2.dexbacked

class DexBackedCatchAllExceptionHandler extends DexBackedExceptionHandler {
    public final Int handlerCodeAddress

    constructor(DexReader dexReader) {
        this.handlerCodeAddress = dexReader.readSmallUleb128()
    }

    @Override // org.jf.dexlib2.iface.ExceptionHandler
    fun getExceptionType() {
        return null
    }

    @Override // org.jf.dexlib2.iface.ExceptionHandler
    fun getHandlerCodeAddress() {
        return this.handlerCodeAddress
    }
}
