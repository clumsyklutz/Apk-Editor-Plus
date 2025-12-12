package org.jf.dexlib2.builder.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderInstruction
import org.jf.dexlib2.iface.instruction.formats.Instruction11x
import org.jf.dexlib2.util.Preconditions

class BuilderInstruction11x extends BuilderInstruction implements Instruction11x {
    public static val FORMAT = Format.Format11x
    public final Int registerA

    constructor(Opcode opcode, Int i) {
        super(opcode)
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
