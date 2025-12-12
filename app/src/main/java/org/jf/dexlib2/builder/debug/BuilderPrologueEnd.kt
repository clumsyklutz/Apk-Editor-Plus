package org.jf.dexlib2.builder.debug

import org.jf.dexlib2.builder.BuilderDebugItem
import org.jf.dexlib2.iface.debug.PrologueEnd

class BuilderPrologueEnd extends BuilderDebugItem implements PrologueEnd {
    @Override // org.jf.dexlib2.iface.debug.DebugItem
    fun getDebugItemType() {
        return 7
    }
}
