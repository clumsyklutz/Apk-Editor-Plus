package org.jf.dexlib2.immutable.reference

import org.jf.dexlib2.iface.reference.CallSiteReference
import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.iface.reference.MethodHandleReference
import org.jf.dexlib2.iface.reference.MethodProtoReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.iface.reference.Reference
import org.jf.dexlib2.iface.reference.StringReference
import org.jf.dexlib2.iface.reference.TypeReference
import org.jf.util.ExceptionWithContext

class ImmutableReferenceFactory {
    fun of(Int i, Reference reference) {
        switch (i) {
            case 0:
                return ImmutableStringReference.of((StringReference) reference)
            case 1:
                return ImmutableTypeReference.of((TypeReference) reference)
            case 2:
                return ImmutableFieldReference.of((FieldReference) reference)
            case 3:
                return ImmutableMethodReference.of((MethodReference) reference)
            case 4:
                return ImmutableMethodProtoReference.of((MethodProtoReference) reference)
            case 5:
                return ImmutableCallSiteReference.of((CallSiteReference) reference)
            case 6:
                return ImmutableMethodHandleReference.of((MethodHandleReference) reference)
            default:
                throw ExceptionWithContext("Invalid reference type: %d", Integer.valueOf(i))
        }
    }

    fun of(Reference reference) {
        if (reference is StringReference) {
            return ImmutableStringReference.of((StringReference) reference)
        }
        if (reference is TypeReference) {
            return ImmutableTypeReference.of((TypeReference) reference)
        }
        if (reference is FieldReference) {
            return ImmutableFieldReference.of((FieldReference) reference)
        }
        if (reference is MethodReference) {
            return ImmutableMethodReference.of((MethodReference) reference)
        }
        if (reference is MethodProtoReference) {
            return ImmutableMethodProtoReference.of((MethodProtoReference) reference)
        }
        if (reference is CallSiteReference) {
            return ImmutableCallSiteReference.of((CallSiteReference) reference)
        }
        if (reference is MethodHandleReference) {
            return ImmutableMethodHandleReference.of((MethodHandleReference) reference)
        }
        throw ExceptionWithContext("Invalid reference type", new Object[0])
    }
}
