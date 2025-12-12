package org.jf.dexlib2.writer.pool

import org.jf.dexlib2.writer.NullableOffsetSection

abstract class BaseNullableOffsetPool<Key> extends BaseOffsetPool<Key> implements NullableOffsetSection<Key> {
    constructor(DexPool dexPool) {
        super(dexPool)
    }

    @Override // org.jf.dexlib2.writer.NullableOffsetSection
    fun getNullableItemOffset(Key key) {
        if (key == null) {
            return 0
        }
        return getItemOffset(key)
    }
}
