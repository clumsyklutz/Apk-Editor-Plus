package org.jf.dexlib2.writer.pool

import org.jf.dexlib2.iface.reference.MethodProtoReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.writer.MethodSection

class MethodPool extends BaseIndexPool<MethodReference> implements MethodSection<CharSequence, CharSequence, MethodProtoReference, MethodReference, PoolMethod> {
    constructor(DexPool dexPool) {
        super(dexPool)
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jf.dexlib2.writer.MethodSection
    fun getDefiningClass(MethodReference methodReference) {
        return methodReference.getDefiningClass()
    }

    @Override // org.jf.dexlib2.writer.MethodSection
    fun getMethodIndex(PoolMethod poolMethod) {
        return getItemIndex(poolMethod)
    }

    @Override // org.jf.dexlib2.writer.MethodSection
    fun getMethodReference(PoolMethod poolMethod) {
        return poolMethod
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jf.dexlib2.writer.MethodSection
    fun getName(MethodReference methodReference) {
        return methodReference.getName()
    }

    @Override // org.jf.dexlib2.writer.MethodSection
    fun getPrototype(MethodReference methodReference) {
        return PoolMethodProto(methodReference)
    }

    @Override // org.jf.dexlib2.writer.MethodSection
    fun getPrototype(PoolMethod poolMethod) {
        return PoolMethodProto(poolMethod)
    }

    fun intern(MethodReference methodReference) {
        if (((Integer) this.internedItems.put(methodReference, 0)) == null) {
            ((TypePool) this.dexPool.typeSection).intern(methodReference.getDefiningClass())
            ((ProtoPool) this.dexPool.protoSection).intern(PoolMethodProto(methodReference))
            ((StringPool) this.dexPool.stringSection).intern(methodReference.getName())
        }
    }
}
