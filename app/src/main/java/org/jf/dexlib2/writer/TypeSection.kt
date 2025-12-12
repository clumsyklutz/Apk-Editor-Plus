package org.jf.dexlib2.writer

import org.jf.dexlib2.iface.reference.TypeReference

public interface TypeSection<StringKey, TypeKey, TypeRef extends TypeReference> extends NullableIndexSection<TypeKey> {
    Int getItemIndex(TypeRef typeref)

    StringKey getString(TypeKey typekey)
}
