package org.jf.dexlib2.writer

public interface NullableIndexSection<Key> extends IndexSection<Key> {
    Int getNullableItemIndex(Key key)
}
