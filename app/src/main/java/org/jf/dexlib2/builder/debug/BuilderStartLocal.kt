package org.jf.dexlib2.builder.debug

import org.jf.dexlib2.builder.BuilderDebugItem
import org.jf.dexlib2.iface.debug.StartLocal
import org.jf.dexlib2.iface.reference.StringReference
import org.jf.dexlib2.iface.reference.TypeReference

class BuilderStartLocal extends BuilderDebugItem implements StartLocal {
    public final StringReference name
    public final Int register
    public final StringReference signature
    public final TypeReference type

    constructor(Int i, StringReference stringReference, TypeReference typeReference, StringReference stringReference2) {
        this.register = i
        this.name = stringReference
        this.type = typeReference
        this.signature = stringReference2
    }

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    fun getDebugItemType() {
        return 3
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    fun getName() {
        StringReference stringReference = this.name
        if (stringReference == null) {
            return null
        }
        return stringReference.getString()
    }

    @Override // org.jf.dexlib2.iface.debug.StartLocal
    fun getNameReference() {
        return this.name
    }

    @Override // org.jf.dexlib2.iface.debug.StartLocal
    fun getRegister() {
        return this.register
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    fun getSignature() {
        StringReference stringReference = this.signature
        if (stringReference == null) {
            return null
        }
        return stringReference.getString()
    }

    @Override // org.jf.dexlib2.iface.debug.StartLocal
    fun getSignatureReference() {
        return this.signature
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    fun getType() {
        TypeReference typeReference = this.type
        if (typeReference == null) {
            return null
        }
        return typeReference.getType()
    }

    @Override // org.jf.dexlib2.iface.debug.StartLocal
    fun getTypeReference() {
        return this.type
    }
}
