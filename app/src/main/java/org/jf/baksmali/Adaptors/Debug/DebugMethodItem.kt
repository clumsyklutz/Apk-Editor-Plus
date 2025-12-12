package org.jf.baksmali.Adaptors.Debug

import org.jf.baksmali.Adaptors.ClassDefinition
import org.jf.baksmali.Adaptors.MethodItem
import org.jf.baksmali.Adaptors.RegisterFormatter
import org.jf.dexlib2.iface.debug.DebugItem
import org.jf.dexlib2.iface.debug.EndLocal
import org.jf.dexlib2.iface.debug.LineNumber
import org.jf.dexlib2.iface.debug.RestartLocal
import org.jf.dexlib2.iface.debug.SetSourceFile
import org.jf.dexlib2.iface.debug.StartLocal
import org.jf.util.ExceptionWithContext

abstract class DebugMethodItem extends MethodItem {
    public final Int sortOrder

    constructor(Int i, Int i2) {
        super(i)
        this.sortOrder = i2
    }

    fun build(ClassDefinition classDefinition, RegisterFormatter registerFormatter, DebugItem debugItem) {
        Int codeAddress = debugItem.getCodeAddress()
        switch (debugItem.getDebugItemType()) {
            case 3:
                return StartLocalMethodItem(classDefinition, codeAddress, -1, registerFormatter, (StartLocal) debugItem)
            case 4:
            default:
                throw ExceptionWithContext("Invalid debug item type: %d", Integer.valueOf(debugItem.getDebugItemType()))
            case 5:
                return EndLocalMethodItem(codeAddress, -1, registerFormatter, (EndLocal) debugItem)
            case 6:
                return RestartLocalMethodItem(classDefinition, codeAddress, -1, registerFormatter, (RestartLocal) debugItem)
            case 7:
                return EndPrologueMethodItem(codeAddress, -4)
            case 8:
                return BeginEpilogueMethodItem(codeAddress, -4)
            case 9:
                return SetSourceFileMethodItem(codeAddress, -3, (SetSourceFile) debugItem)
            case 10:
                return LineNumberMethodItem(codeAddress, -2, (LineNumber) debugItem)
        }
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun getSortOrder() {
        return this.sortOrder
    }
}
