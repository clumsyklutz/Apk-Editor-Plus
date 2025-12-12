package org.jf.dexlib2.builder.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderOffsetInstruction
import org.jf.dexlib2.builder.Label
import org.jf.dexlib2.iface.instruction.formats.Instruction10t

class BuilderInstruction10t extends BuilderOffsetInstruction implements Instruction10t {
    public static val FORMAT = Format.Format10t

    constructor(Opcode opcode, Label label) {
        super(opcode, label)
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
    fun getFormat() {
        return FORMAT
    }
}
