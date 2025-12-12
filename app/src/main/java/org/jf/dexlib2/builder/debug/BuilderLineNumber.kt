package org.jf.dexlib2.builder.debug

import org.jf.dexlib2.builder.BuilderDebugItem
import org.jf.dexlib2.iface.debug.LineNumber

class BuilderLineNumber extends BuilderDebugItem implements LineNumber {
    public final Int lineNumber

    constructor(Int i) {
        this.lineNumber = i
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
