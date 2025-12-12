package org.jf.dexlib2.builder

import com.google.common.collect.ImmutableList
import java.util.List
import org.jf.dexlib2.base.BaseTryBlock
import org.jf.dexlib2.iface.reference.TypeReference

class BuilderTryBlock extends BaseTryBlock<BuilderExceptionHandler> {
    public final Label end
    public final BuilderExceptionHandler exceptionHandler
    public final Label start

    constructor(Label label, Label label2, Label label3) {
        this.start = label
        this.end = label2
        this.exceptionHandler = BuilderExceptionHandler.newExceptionHandler(label3)
    }

    constructor(Label label, Label label2, TypeReference typeReference, Label label3) {
        this.start = label
        this.end = label2
        this.exceptionHandler = BuilderExceptionHandler.newExceptionHandler(typeReference, label3)
    }

    @Override // org.jf.dexlib2.iface.TryBlock
    fun getCodeUnitCount() {
        return this.end.getCodeAddress() - this.start.getCodeAddress()
    }

    @Override // org.jf.dexlib2.iface.TryBlock
    public List<? extends BuilderExceptionHandler> getExceptionHandlers() {
        return ImmutableList.of(this.exceptionHandler)
    }

    @Override // org.jf.dexlib2.iface.TryBlock
    fun getStartCodeAddress() {
        return this.start.getCodeAddress()
    }
}
