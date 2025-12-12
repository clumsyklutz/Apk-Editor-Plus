package org.jf.dexlib2.builder

class LocatedLabels extends LocatedItems<Label> {
    @Override // org.jf.dexlib2.builder.LocatedItems
    fun getAddLocatedItemError() {
        return "Cannot add a label that is already placed.You must remove it from its current location first."
    }
}
