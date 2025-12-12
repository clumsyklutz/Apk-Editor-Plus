package jadx.core.dex.trycatch

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute

class ExcHandlerAttr implements IAttribute {
    private final ExceptionHandler handler
    private final TryCatchBlock tryBlock

    constructor(TryCatchBlock tryCatchBlock, ExceptionHandler exceptionHandler) {
        this.tryBlock = tryCatchBlock
        this.handler = exceptionHandler
    }

    fun getHandler() {
        return this.handler
    }

    fun getTryBlock() {
        return this.tryBlock
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.EXC_HANDLER
    }

    fun toString() {
        String str
        StringBuilder sb = StringBuilder("ExcHandler: ")
        if (this.handler.isFinally()) {
            str = " FINALLY"
        } else {
            str = (this.handler.isCatchAll() ? "all" : this.handler.getCatchType()) + " " + this.handler.getArg()
        }
        return sb.append(str).toString()
    }
}
