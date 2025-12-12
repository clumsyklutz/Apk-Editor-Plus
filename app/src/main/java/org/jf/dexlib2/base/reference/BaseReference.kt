package org.jf.dexlib2.base.reference

import org.jf.dexlib2.iface.reference.Reference

abstract class BaseReference implements Reference {
    @Override // org.jf.dexlib2.iface.reference.Reference
    fun validateReference() throws Reference.InvalidReferenceException {
    }
}
