package org.jf.dexlib2.immutable.debug

import org.jf.dexlib2.iface.debug.RestartLocal

class ImmutableRestartLocal extends ImmutableDebugItem implements RestartLocal {
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

    fun of(RestartLocal restartLocal) {
        return restartLocal is ImmutableRestartLocal ? (ImmutableRestartLocal) restartLocal : ImmutableRestartLocal(restartLocal.getCodeAddress(), restartLocal.getRegister(), restartLocal.getType(), restartLocal.getName(), restartLocal.getSignature())
    }

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    fun getDebugItemType() {
        return 6
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    fun getName() {
        return this.name
    }

    @Override // org.jf.dexlib2.iface.debug.RestartLocal
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
