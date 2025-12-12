package org.jf.dexlib2.builder.instruction

import org.jf.dexlib2.builder.BuilderSwitchPayload
import org.jf.dexlib2.builder.Label
import org.jf.dexlib2.iface.instruction.SwitchElement

class BuilderSwitchElement implements SwitchElement {
    public final Int key
    public BuilderSwitchPayload parent
    public final Label target

    constructor(BuilderSwitchPayload builderSwitchPayload, Int i, Label label) {
        this.parent = builderSwitchPayload
        this.key = i
        this.target = label
    }

    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
    fun getKey() {
        return this.key
    }

    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
    fun getOffset() {
        return this.target.getCodeAddress() - this.parent.getReferrer().getCodeAddress()
    }
}
