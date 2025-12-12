package org.jf.dexlib2.builder

import org.jf.dexlib2.iface.debug.DebugItem

abstract class BuilderDebugItem extends ItemWithLocation implements DebugItem {
    @Override // org.jf.dexlib2.iface.debug.DebugItem
    fun getCodeAddress() {
        MethodLocation methodLocation = this.location
        if (methodLocation != null) {
            return methodLocation.getCodeAddress()
        }
        throw IllegalStateException("Cannot get the address of a BuilderDebugItem that isn't associated with a method.")
    }
}
