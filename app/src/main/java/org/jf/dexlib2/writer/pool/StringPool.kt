package org.jf.dexlib2.writer.pool

import org.jf.dexlib2.iface.reference.StringReference
import org.jf.dexlib2.writer.StringSection
import org.jf.util.ExceptionWithContext

class StringPool extends StringTypeBasePool implements StringSection<CharSequence, StringReference> {
    constructor(DexPool dexPool) {
        super(dexPool)
    }

    @Override // org.jf.dexlib2.writer.StringSection
    fun getItemIndex(StringReference stringReference) {
        Integer num = (Integer) this.internedItems.get(stringReference.toString())
        if (num != null) {
            return num.intValue()
        }
        throw ExceptionWithContext("Item not found.: %s", stringReference.toString())
    }

    @Override // org.jf.dexlib2.writer.StringSection
    fun hasJumboIndexes() {
        return this.internedItems.size() > 65536
    }

    fun intern(CharSequence charSequence) {
        this.internedItems.put(charSequence.toString(), 0)
    }

    fun internNullable(CharSequence charSequence) {
        if (charSequence != null) {
            intern(charSequence)
        }
    }
}
