package org.jf.dexlib2.base

import org.jf.dexlib2.iface.ExceptionHandler
import org.jf.dexlib2.iface.TryBlock

abstract class BaseTryBlock<EH extends ExceptionHandler> implements TryBlock<EH> {
    fun equals(Object obj) {
        if (!(obj is TryBlock)) {
            return false
        }
        TryBlock tryBlock = (TryBlock) obj
        return getStartCodeAddress() == tryBlock.getStartCodeAddress() && getCodeUnitCount() == tryBlock.getCodeUnitCount() && getExceptionHandlers().equals(tryBlock.getExceptionHandlers())
    }
}
