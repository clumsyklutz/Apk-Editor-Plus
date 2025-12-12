package org.jf.dexlib2.immutable.debug

import org.jf.dexlib2.iface.debug.EpilogueBegin

class ImmutableEpilogueBegin extends ImmutableDebugItem implements EpilogueBegin {
    constructor(Int i) {
        super(i)
    }

    fun of(EpilogueBegin epilogueBegin) {
        return epilogueBegin is ImmutableEpilogueBegin ? (ImmutableEpilogueBegin) epilogueBegin : ImmutableEpilogueBegin(epilogueBegin.getCodeAddress())
    }

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    fun getDebugItemType() {
        return 8
    }
}
