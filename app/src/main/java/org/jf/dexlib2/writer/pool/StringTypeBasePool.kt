package org.jf.dexlib2.writer.pool

import java.util.Collection
import java.util.Map
import org.jf.dexlib2.writer.NullableIndexSection
import org.jf.util.ExceptionWithContext

abstract class StringTypeBasePool extends BasePool<String, Integer> implements NullableIndexSection<CharSequence> {
    constructor(DexPool dexPool) {
        super(dexPool)
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    fun getItemIndex(CharSequence charSequence) {
        Integer num = (Integer) this.internedItems.get(charSequence.toString())
        if (num != null) {
            return num.intValue()
        }
        throw ExceptionWithContext("Item not found.: %s", charSequence.toString())
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    public Collection<Map.Entry<String, Integer>> getItems() {
        return this.internedItems.entrySet()
    }

    @Override // org.jf.dexlib2.writer.NullableIndexSection
    fun getNullableItemIndex(CharSequence charSequence) {
        if (charSequence == null) {
            return -1
        }
        return getItemIndex(charSequence)
    }
}
