package org.jf.dexlib2.writer.pool

import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.iface.reference.MethodHandleReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.writer.MethodHandleSection
import org.jf.util.ExceptionWithContext

class MethodHandlePool extends BaseIndexPool<MethodHandleReference> implements MethodHandleSection<MethodHandleReference, FieldReference, MethodReference> {
    constructor(DexPool dexPool) {
        super(dexPool)
    }

    @Override // org.jf.dexlib2.writer.MethodHandleSection
    fun getFieldReference(MethodHandleReference methodHandleReference) {
        return (FieldReference) methodHandleReference.getMemberReference()
    }

    @Override // org.jf.dexlib2.writer.MethodHandleSection
    fun getMethodReference(MethodHandleReference methodHandleReference) {
        return (MethodReference) methodHandleReference.getMemberReference()
    }

    fun intern(MethodHandleReference methodHandleReference) {
        if (((Integer) this.internedItems.put(methodHandleReference, 0)) == null) {
            switch (methodHandleReference.getMethodHandleType()) {
                case 0:
                case 1:
                case 2:
                case 3:
                    ((FieldPool) this.dexPool.fieldSection).intern((FieldReference) methodHandleReference.getMemberReference())
                    return
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    ((MethodPool) this.dexPool.methodSection).intern((MethodReference) methodHandleReference.getMemberReference())
                    return
                default:
                    throw ExceptionWithContext("Invalid method handle type: %d", Integer.valueOf(methodHandleReference.getMethodHandleType()))
            }
        }
    }
}
