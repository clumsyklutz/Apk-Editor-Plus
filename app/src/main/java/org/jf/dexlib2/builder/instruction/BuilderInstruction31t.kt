package org.jf.dexlib2.builder.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderOffsetInstruction
import org.jf.dexlib2.builder.Label
import org.jf.dexlib2.iface.instruction.formats.Instruction31t
import org.jf.dexlib2.util.Preconditions

class BuilderInstruction31t extends BuilderOffsetInstruction implements Instruction31t {
    public static val FORMAT = Format.Format31t
    public final Int registerA

    constructor(Opcode opcode, Int i, Label label) {
        super(opcode, label)
        this.registerA = Preconditions.checkByteRegister(i)
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    fun getRegisterA() {
        return this.registerA
    }
}
