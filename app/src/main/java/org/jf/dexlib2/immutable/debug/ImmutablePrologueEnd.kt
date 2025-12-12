package org.jf.dexlib2.immutable.debug

import org.jf.dexlib2.iface.debug.PrologueEnd

class ImmutablePrologueEnd extends ImmutableDebugItem implements PrologueEnd {
    constructor(Int i) {
        super(i)
    }

    fun of(PrologueEnd prologueEnd) {
        return prologueEnd is ImmutablePrologueEnd ? (ImmutablePrologueEnd) prologueEnd : ImmutablePrologueEnd(prologueEnd.getCodeAddress())
    }

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    fun getDebugItemType() {
        return 7
    }
}
