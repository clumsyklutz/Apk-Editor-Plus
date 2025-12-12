package org.jf.dexlib2.writer.pool

import org.jf.dexlib2.iface.reference.TypeReference
import org.jf.dexlib2.writer.TypeSection

class TypePool extends StringTypeBasePool implements TypeSection<CharSequence, CharSequence, TypeReference> {
    constructor(DexPool dexPool) {
        super(dexPool)
    }

    @Override // org.jf.dexlib2.writer.TypeSection
    fun getItemIndex(TypeReference typeReference) {
        return getItemIndex((CharSequence) typeReference.getType())
    }

    @Override // org.jf.dexlib2.writer.TypeSection
    fun getString(CharSequence charSequence) {
        return charSequence
    }

    fun intern(CharSequence charSequence) {
        String string = charSequence.toString()
        if (((Integer) this.internedItems.put(string, 0)) == null) {
            ((StringPool) this.dexPool.stringSection).intern(string)
        }
    }

    fun internNullable(CharSequence charSequence) {
        if (charSequence != null) {
            intern(charSequence)
        }
    }
}
