package org.jf.dexlib2.writer.pool

import java.util.Collection
import java.util.List
import org.jf.dexlib2.iface.reference.MethodProtoReference
import org.jf.dexlib2.util.MethodUtil
import org.jf.dexlib2.writer.ProtoSection
import org.jf.dexlib2.writer.pool.TypeListPool

class ProtoPool extends BaseIndexPool<MethodProtoReference> implements ProtoSection<CharSequence, CharSequence, MethodProtoReference, TypeListPool.Key<? extends Collection<? extends CharSequence>>> {
    constructor(DexPool dexPool) {
        super(dexPool)
    }

    @Override // org.jf.dexlib2.writer.ProtoSection
    public TypeListPool.Key<List<? extends CharSequence>> getParameters(MethodProtoReference methodProtoReference) {
        return new TypeListPool.Key<>(methodProtoReference.getParameterTypes())
    }

    @Override // org.jf.dexlib2.writer.ProtoSection
    fun getReturnType(MethodProtoReference methodProtoReference) {
        return methodProtoReference.getReturnType()
    }

    @Override // org.jf.dexlib2.writer.ProtoSection
    fun getShorty(MethodProtoReference methodProtoReference) {
        return MethodUtil.getShorty(methodProtoReference.getParameterTypes(), methodProtoReference.getReturnType())
    }

    fun intern(MethodProtoReference methodProtoReference) {
        if (((Integer) this.internedItems.put(methodProtoReference, 0)) == null) {
            ((StringPool) this.dexPool.stringSection).intern(getShorty(methodProtoReference))
            ((TypePool) this.dexPool.typeSection).intern(methodProtoReference.getReturnType())
            ((TypeListPool) this.dexPool.typeListSection).intern(methodProtoReference.getParameterTypes())
        }
    }
}
