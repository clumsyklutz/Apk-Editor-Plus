package org.jf.dexlib2.writer

import java.util.Collection
import java.util.Map

public interface OffsetSection<Key> {
    Int getItemOffset(Key key)

    Collection<? extends Map.Entry<? extends Key, Integer>> getItems()
}
