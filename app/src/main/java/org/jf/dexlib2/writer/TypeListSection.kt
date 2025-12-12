package org.jf.dexlib2.writer

import java.util.Collection

public interface TypeListSection<TypeKey, TypeListKey> extends NullableOffsetSection<TypeListKey> {
    @Override // org.jf.dexlib2.writer.NullableOffsetSection
    Int getNullableItemOffset(TypeListKey typelistkey)

    Collection<? extends TypeKey> getTypes(TypeListKey typelistkey)
}
