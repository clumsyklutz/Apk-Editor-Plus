package org.jf.dexlib2.builder

class Label extends ItemWithLocation {
    fun getCodeAddress() {
        return getLocation().getCodeAddress()
    }

    fun getLocation() {
        MethodLocation methodLocation = this.location
        if (methodLocation != null) {
            return methodLocation
        }
        throw IllegalStateException("Cannot get the location of a label that hasn't been placed yet.")
    }
}
