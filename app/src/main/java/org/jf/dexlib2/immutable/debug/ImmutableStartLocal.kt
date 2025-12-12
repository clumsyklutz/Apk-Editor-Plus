package org.jf.dexlib2.immutable.debug

import org.jf.dexlib2.base.reference.BaseStringReference
import org.jf.dexlib2.base.reference.BaseTypeReference
import org.jf.dexlib2.iface.debug.StartLocal
import org.jf.dexlib2.iface.reference.StringReference
import org.jf.dexlib2.iface.reference.TypeReference

class ImmutableStartLocal extends ImmutableDebugItem implements StartLocal {
    public final String name
    public final Int register
    public final String signature
    public final String type

    constructor(Int i, Int i2, String str, String str2, String str3) {
        super(i)
        this.register = i2
        this.name = str
        this.type = str2
        this.signature = str3
    }

    fun of(StartLocal startLocal) {
        return startLocal is ImmutableStartLocal ? (ImmutableStartLocal) startLocal : ImmutableStartLocal(startLocal.getCodeAddress(), startLocal.getRegister(), startLocal.getName(), startLocal.getType(), startLocal.getSignature())
    }

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    fun getDebugItemType() {
        return 3
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    fun getName() {
        return this.name
    }

    @Override // org.jf.dexlib2.iface.debug.StartLocal
    fun getNameReference() {
        if (this.name == null) {
            return null
        }
        return BaseStringReference() { // from class: org.jf.dexlib2.immutable.debug.ImmutableStartLocal.1
            @Override // org.jf.dexlib2.iface.reference.StringReference
            fun getString() {
                return ImmutableStartLocal.this.name
            }
        }
    }

    @Override // org.jf.dexlib2.iface.debug.StartLocal
    fun getRegister() {
        return this.register
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    fun getSignature() {
        return this.signature
    }

    @Override // org.jf.dexlib2.iface.debug.StartLocal
    fun getSignatureReference() {
        if (this.signature == null) {
            return null
        }
        return BaseStringReference() { // from class: org.jf.dexlib2.immutable.debug.ImmutableStartLocal.3
            @Override // org.jf.dexlib2.iface.reference.StringReference
            fun getString() {
                return ImmutableStartLocal.this.signature
            }
        }
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    fun getType() {
        return this.type
    }

    @Override // org.jf.dexlib2.iface.debug.StartLocal
    fun getTypeReference() {
        if (this.type == null) {
            return null
        }
        return BaseTypeReference() { // from class: org.jf.dexlib2.immutable.debug.ImmutableStartLocal.2
            @Override // org.jf.dexlib2.iface.reference.TypeReference
            fun getType() {
                return ImmutableStartLocal.this.type
            }
        }
    }
}
