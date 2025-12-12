package org.jf.dexlib2.immutable

import com.google.common.collect.ImmutableList
import org.jf.dexlib2.base.BaseExceptionHandler
import org.jf.dexlib2.iface.ExceptionHandler
import org.jf.util.ImmutableConverter

class ImmutableExceptionHandler extends BaseExceptionHandler {
    public static final ImmutableConverter<ImmutableExceptionHandler, ExceptionHandler> CONVERTER = new ImmutableConverter<ImmutableExceptionHandler, ExceptionHandler>() { // from class: org.jf.dexlib2.immutable.ImmutableExceptionHandler.1
        @Override // org.jf.util.ImmutableConverter
        fun isImmutable(ExceptionHandler exceptionHandler) {
            return exceptionHandler is ImmutableExceptionHandler
        }

        @Override // org.jf.util.ImmutableConverter
        fun makeImmutable(ExceptionHandler exceptionHandler) {
            return ImmutableExceptionHandler.of(exceptionHandler)
        }
    }
    public final String exceptionType
    public final Int handlerCodeAddress

    constructor(String str, Int i) {
        this.exceptionType = str
        this.handlerCodeAddress = i
    }

    public static ImmutableList<ImmutableExceptionHandler> immutableListOf(Iterable<? extends ExceptionHandler> iterable) {
        return CONVERTER.toList(iterable)
    }

    fun of(ExceptionHandler exceptionHandler) {
        return exceptionHandler is ImmutableExceptionHandler ? (ImmutableExceptionHandler) exceptionHandler : ImmutableExceptionHandler(exceptionHandler.getExceptionType(), exceptionHandler.getHandlerCodeAddress())
    }

    @Override // org.jf.dexlib2.iface.ExceptionHandler
    fun getExceptionType() {
        return this.exceptionType
    }

    @Override // org.jf.dexlib2.iface.ExceptionHandler
    fun getHandlerCodeAddress() {
        return this.handlerCodeAddress
    }
}
