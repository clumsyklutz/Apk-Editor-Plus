package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction10x

class ImmutableInstruction10x extends ImmutableInstruction implements Instruction10x {
    public static val FORMAT = Format.Format10x

    constructor(Opcode opcode) {
        super(opcode)
    }

    fun of(Instruction10x instruction10x) {
        return instruction10x is ImmutableInstruction10x ? (ImmutableInstruction10x) instruction10x : ImmutableInstruction10x(instruction10x.getOpcode())
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    fun getFormat() {
        return FORMAT
    }
}
