package org.jf.dexlib2.immutable.debug

import org.jf.dexlib2.iface.debug.EndLocal

class ImmutableEndLocal extends ImmutableDebugItem implements EndLocal {
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

    fun of(EndLocal endLocal) {
        return endLocal is ImmutableEndLocal ? (ImmutableEndLocal) endLocal : ImmutableEndLocal(endLocal.getCodeAddress(), endLocal.getRegister(), endLocal.getType(), endLocal.getName(), endLocal.getSignature())
    }

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    fun getDebugItemType() {
        return 5
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    fun getName() {
        return this.name
    }

    @Override // org.jf.dexlib2.iface.debug.EndLocal
    fun getRegister() {
        return this.register
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    fun getSignature() {
        return this.signature
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    fun getType() {
        return this.type
    }
}
