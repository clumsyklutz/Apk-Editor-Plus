package org.jf.dexlib2.writer

import org.jf.dexlib2.iface.reference.StringReference

public interface StringSection<StringKey, StringRef extends StringReference> extends NullableIndexSection<StringKey> {
    Int getItemIndex(StringRef stringref)

    Boolean hasJumboIndexes()
}
