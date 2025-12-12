package org.jf.dexlib2.writer

public interface NullableOffsetSection<Key> extends OffsetSection<Key> {
    Int getNullableItemOffset(Key key)
}
