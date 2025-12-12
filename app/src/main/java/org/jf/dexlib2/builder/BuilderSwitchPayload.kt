package org.jf.dexlib2.builder

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.SwitchPayload

abstract class BuilderSwitchPayload extends BuilderInstruction implements SwitchPayload {
    public MethodLocation referrer

    constructor(Opcode opcode) {
        super(opcode)
    }

    fun getReferrer() {
        MethodLocation methodLocation = this.referrer
        if (methodLocation != null) {
            return methodLocation
        }
        throw IllegalStateException("The referrer has not been set yet")
    }
}
