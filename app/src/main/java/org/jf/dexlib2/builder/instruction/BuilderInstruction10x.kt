package org.jf.dexlib2.builder.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderInstruction
import org.jf.dexlib2.iface.instruction.formats.Instruction10x

class BuilderInstruction10x extends BuilderInstruction implements Instruction10x {
    public static val FORMAT = Format.Format10x

    constructor(Opcode opcode) {
        super(opcode)
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
    fun getFormat() {
        return FORMAT
    }
}
