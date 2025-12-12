package org.jf.dexlib2.builder

abstract class ItemWithLocation {
    public MethodLocation location

    fun isPlaced() {
        return this.location != null
    }

    fun setLocation(MethodLocation methodLocation) {
        this.location = methodLocation
    }
}
