package org.jf.dexlib2.builder.debug

import org.jf.dexlib2.builder.BuilderDebugItem
import org.jf.dexlib2.iface.debug.EpilogueBegin

class BuilderEpilogueBegin extends BuilderDebugItem implements EpilogueBegin {
    @Override // org.jf.dexlib2.iface.debug.DebugItem
    fun getDebugItemType() {
        return 8
    }
}
