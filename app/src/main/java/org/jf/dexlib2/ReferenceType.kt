package org.jf.dexlib2

import org.jf.dexlib2.iface.reference.CallSiteReference
import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.iface.reference.MethodHandleReference
import org.jf.dexlib2.iface.reference.MethodProtoReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.iface.reference.Reference
import org.jf.dexlib2.iface.reference.StringReference
import org.jf.dexlib2.iface.reference.TypeReference
import org.jf.util.ExceptionWithContext

class ReferenceType {

    public static class InvalidReferenceTypeException extends ExceptionWithContext {
        public final Int referenceType

        constructor(Int i) {
            super("Invalid reference type: %d", Integer.valueOf(i))
            this.referenceType = i
        }

        fun getReferenceType() {
            return this.referenceType
        }
    }

    fun getReferenceType(Reference reference) {
        if (reference is StringReference) {
            return 0
        }
        if (reference is TypeReference) {
            return 1
        }
        if (reference is FieldReference) {
            return 2
        }
        if (reference is MethodReference) {
            return 3
        }
        if (reference is MethodProtoReference) {
            return 4
        }
        if (reference is CallSiteReference) {
            return 5
        }
        if (reference is MethodHandleReference) {
            return 6
        }
        throw IllegalStateException("Invalid reference")
    }

    fun validateReferenceType(Int i) {
        if (i < 0 || i > 4) {
            throw InvalidReferenceTypeException(i)
        }
    }
}
