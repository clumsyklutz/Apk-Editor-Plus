package org.jf.dexlib2.writer.pool

import java.util.Collection
import java.util.Map
import org.jf.dexlib2.writer.IndexSection
import org.jf.util.ExceptionWithContext

abstract class BaseIndexPool<Key> extends BasePool<Key, Integer> implements IndexSection<Key> {
    constructor(DexPool dexPool) {
        super(dexPool)
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    fun getItemIndex(Key key) {
        Integer num = (Integer) this.internedItems.get(key)
        if (num != null) {
            return num.intValue()
        }
        throw ExceptionWithContext("Item not found.: %s", getItemString(key))
    }

    fun getItemString(Key key) {
        return key.toString()
    }

    @Override // org.jf.dexlib2.writer.IndexSection
    public Collection<? extends Map.Entry<? extends Key, Integer>> getItems() {
        return this.internedItems.entrySet()
    }
}
