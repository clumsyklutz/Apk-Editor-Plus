package org.jf.dexlib2.immutable

import com.google.common.collect.ImmutableList
import java.util.List
import org.jf.dexlib2.base.BaseTryBlock
import org.jf.dexlib2.iface.ExceptionHandler
import org.jf.dexlib2.iface.TryBlock
import org.jf.util.ImmutableConverter

class ImmutableTryBlock extends BaseTryBlock<ImmutableExceptionHandler> {
    public static final ImmutableConverter<ImmutableTryBlock, TryBlock<? extends ExceptionHandler>> CONVERTER = new ImmutableConverter<ImmutableTryBlock, TryBlock<? extends ExceptionHandler>>() { // from class: org.jf.dexlib2.immutable.ImmutableTryBlock.1
        @Override // org.jf.util.ImmutableConverter
        public /* bridge */ /* synthetic */ Boolean isImmutable(TryBlock<? extends ExceptionHandler> tryBlock) {
            return isImmutable2((TryBlock) tryBlock)
        }

        /* renamed from: isImmutable, reason: avoid collision after fix types in other method */
        fun isImmutable2(TryBlock tryBlock) {
            return tryBlock is ImmutableTryBlock
        }

        @Override // org.jf.util.ImmutableConverter
        fun makeImmutable(TryBlock<? extends ExceptionHandler> tryBlock) {
            return ImmutableTryBlock.of(tryBlock)
        }
    }
    public final Int codeUnitCount
    public final ImmutableList<? extends ImmutableExceptionHandler> exceptionHandlers
    public final Int startCodeAddress

    constructor(Int i, Int i2, List<? extends ExceptionHandler> list) {
        this.startCodeAddress = i
        this.codeUnitCount = i2
        this.exceptionHandlers = ImmutableExceptionHandler.immutableListOf(list)
    }

    public static ImmutableList<ImmutableTryBlock> immutableListOf(List<? extends TryBlock<? extends ExceptionHandler>> list) {
        return CONVERTER.toList(list)
    }

    fun of(TryBlock<? extends ExceptionHandler> tryBlock) {
        return tryBlock is ImmutableTryBlock ? (ImmutableTryBlock) tryBlock : ImmutableTryBlock(tryBlock.getStartCodeAddress(), tryBlock.getCodeUnitCount(), tryBlock.getExceptionHandlers())
    }

    @Override // org.jf.dexlib2.iface.TryBlock
    fun getCodeUnitCount() {
        return this.codeUnitCount
    }

    @Override // org.jf.dexlib2.iface.TryBlock
    public ImmutableList<? extends ImmutableExceptionHandler> getExceptionHandlers() {
        return this.exceptionHandlers
    }

    @Override // org.jf.dexlib2.iface.TryBlock
    fun getStartCodeAddress() {
        return this.startCodeAddress
    }
}
