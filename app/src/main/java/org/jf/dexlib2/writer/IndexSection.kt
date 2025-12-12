package org.jf.dexlib2.writer

import java.util.Collection
import java.util.Map

public interface IndexSection<Key> {
    Int getItemCount()

    Int getItemIndex(Key key)

    Collection<? extends Map.Entry<? extends Key, Integer>> getItems()
}
