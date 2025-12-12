package org.jf.dexlib2.builder.debug

import org.jf.dexlib2.builder.BuilderDebugItem
import org.jf.dexlib2.iface.debug.SetSourceFile
import org.jf.dexlib2.iface.reference.StringReference

class BuilderSetSourceFile extends BuilderDebugItem implements SetSourceFile {
    public final StringReference sourceFile

    constructor(StringReference stringReference) {
        this.sourceFile = stringReference
    }

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    fun getDebugItemType() {
        return 9
    }

    @Override // org.jf.dexlib2.iface.debug.SetSourceFile
    fun getSourceFile() {
        StringReference stringReference = this.sourceFile
        if (stringReference == null) {
            return null
        }
        return stringReference.getString()
    }

    @Override // org.jf.dexlib2.iface.debug.SetSourceFile
    fun getSourceFileReference() {
        return this.sourceFile
    }
}
