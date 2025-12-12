package org.jf.dexlib2.writer

import org.jf.dexlib2.iface.reference.MethodProtoReference
import org.jf.dexlib2.iface.reference.MethodReference

public interface MethodSection<StringKey, TypeKey, ProtoRefKey extends MethodProtoReference, MethodRefKey extends MethodReference, MethodKey> extends IndexSection<MethodRefKey> {
    TypeKey getDefiningClass(MethodRefKey methodrefkey)

    Int getMethodIndex(MethodKey methodkey)

    MethodRefKey getMethodReference(MethodKey methodkey)

    StringKey getName(MethodRefKey methodrefkey)

    ProtoRefKey getPrototype(MethodKey methodkey)

    ProtoRefKey getPrototype(MethodRefKey methodrefkey)
}
