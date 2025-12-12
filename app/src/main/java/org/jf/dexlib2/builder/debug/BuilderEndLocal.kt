package org.jf.dexlib2.builder.debug

import org.jf.dexlib2.builder.BuilderDebugItem
import org.jf.dexlib2.iface.debug.EndLocal

class BuilderEndLocal extends BuilderDebugItem implements EndLocal {
    public final Int register

    constructor(Int i) {
        this.register = i
    }

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    fun getDebugItemType() {
        return 5
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    fun getName() {
        return null
    }

    @Override // org.jf.dexlib2.iface.debug.EndLocal
    fun getRegister() {
        return this.register
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    fun getSignature() {
        return null
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    fun getType() {
        return null
    }
}
