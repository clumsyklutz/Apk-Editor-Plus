package org.jf.dexlib2.writer.pool

import java.util.Iterator
import java.util.List
import org.jf.dexlib2.iface.value.ArrayEncodedValue
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.writer.EncodedArraySection

class EncodedArrayPool extends BaseOffsetPool<ArrayEncodedValue> implements EncodedArraySection<ArrayEncodedValue, EncodedValue> {
    constructor(DexPool dexPool) {
        super(dexPool)
    }

    @Override // org.jf.dexlib2.writer.EncodedArraySection
    public List<? extends EncodedValue> getEncodedValueList(ArrayEncodedValue arrayEncodedValue) {
        return arrayEncodedValue.getValue()
    }

    fun intern(ArrayEncodedValue arrayEncodedValue) {
        if (((Integer) this.internedItems.put(arrayEncodedValue, 0)) == null) {
            Iterator<? extends EncodedValue> it = arrayEncodedValue.getValue().iterator()
            while (it.hasNext()) {
                this.dexPool.internEncodedValue(it.next())
            }
        }
    }
}
