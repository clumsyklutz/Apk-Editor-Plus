package org.jf.dexlib2.immutable.debug

import org.jf.dexlib2.base.reference.BaseStringReference
import org.jf.dexlib2.iface.debug.SetSourceFile
import org.jf.dexlib2.iface.reference.StringReference

class ImmutableSetSourceFile extends ImmutableDebugItem implements SetSourceFile {
    public final String sourceFile

    constructor(Int i, String str) {
        super(i)
        this.sourceFile = str
    }

    fun of(SetSourceFile setSourceFile) {
        return setSourceFile is ImmutableSetSourceFile ? (ImmutableSetSourceFile) setSourceFile : ImmutableSetSourceFile(setSourceFile.getCodeAddress(), setSourceFile.getSourceFile())
    }

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    fun getDebugItemType() {
        return 9
    }

    @Override // org.jf.dexlib2.iface.debug.SetSourceFile
    fun getSourceFile() {
        return this.sourceFile
    }

    @Override // org.jf.dexlib2.iface.debug.SetSourceFile
    fun getSourceFileReference() {
        if (this.sourceFile == null) {
            return null
        }
        return BaseStringReference() { // from class: org.jf.dexlib2.immutable.debug.ImmutableSetSourceFile.1
            @Override // org.jf.dexlib2.iface.reference.StringReference
            fun getString() {
                return ImmutableSetSourceFile.this.sourceFile
            }
        }
    }
}
