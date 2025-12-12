package org.jf.dexlib2.immutable.debug

import org.jf.dexlib2.iface.debug.LineNumber

class ImmutableLineNumber extends ImmutableDebugItem implements LineNumber {
    public final Int lineNumber

    constructor(Int i, Int i2) {
        super(i)
        this.lineNumber = i2
    }

    fun of(LineNumber lineNumber) {
        return lineNumber is ImmutableLineNumber ? (ImmutableLineNumber) lineNumber : ImmutableLineNumber(lineNumber.getCodeAddress(), lineNumber.getLineNumber())
    }

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    fun getDebugItemType() {
        return 10
    }

    @Override // org.jf.dexlib2.iface.debug.LineNumber
    fun getLineNumber() {
        return this.lineNumber
    }
}
