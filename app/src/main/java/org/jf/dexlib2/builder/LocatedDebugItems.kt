package org.jf.dexlib2.builder

class LocatedDebugItems extends LocatedItems<BuilderDebugItem> {
    @Override // org.jf.dexlib2.builder.LocatedItems
    fun getAddLocatedItemError() {
        return "Cannot add a debug item that has already been added to a method.You must remove it from its current location first."
    }
}
