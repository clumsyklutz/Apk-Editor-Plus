package org.jf.dexlib2.writer.pool

import com.google.common.collect.Maps
import java.util.Map

class BasePool<Key, Value> {
    public final DexPool dexPool
    public final Map<Key, Value> internedItems = Maps.newLinkedHashMap()

    constructor(DexPool dexPool) {
        this.dexPool = dexPool
    }

    fun getItemCount() {
        return this.internedItems.size()
    }
}
