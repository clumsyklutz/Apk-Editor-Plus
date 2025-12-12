package org.jf.dexlib2.builder

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.OffsetInstruction
import org.jf.util.ExceptionWithContext

abstract class BuilderOffsetInstruction extends BuilderInstruction implements OffsetInstruction {
    public final Label target

    constructor(Opcode opcode, Label label) {
        super(opcode)
        this.target = label
    }

    @Override // org.jf.dexlib2.iface.instruction.OffsetInstruction
    fun getCodeOffset() {
        Int iInternalGetCodeOffset = internalGetCodeOffset()
        if (getCodeUnits() == 1) {
            if (iInternalGetCodeOffset < -128 || iInternalGetCodeOffset > 127) {
                throw ExceptionWithContext("Invalid instruction offset: %d. Offset must be in [-128, 127]", Integer.valueOf(iInternalGetCodeOffset))
            }
        } else if (getCodeUnits() == 2 && (iInternalGetCodeOffset < -32768 || iInternalGetCodeOffset > 32767)) {
            throw ExceptionWithContext("Invalid instruction offset: %d. Offset must be in [-32768, 32767]", Integer.valueOf(iInternalGetCodeOffset))
        }
        return iInternalGetCodeOffset
    }

    fun getTarget() {
        return this.target
    }

    fun internalGetCodeOffset() {
        return this.target.getCodeAddress() - getLocation().getCodeAddress()
    }
}
