package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction30t

class ImmutableInstruction30t extends ImmutableInstruction implements Instruction30t {
    public static val FORMAT = Format.Format30t
    public final Int codeOffset

    constructor(Opcode opcode, Int i) {
        super(opcode)
        this.codeOffset = i
    }

    fun of(Instruction30t instruction30t) {
        return instruction30t is ImmutableInstruction30t ? (ImmutableInstruction30t) instruction30t : ImmutableInstruction30t(instruction30t.getOpcode(), instruction30t.getCodeOffset())
    }

    @Override // org.jf.dexlib2.iface.instruction.OffsetInstruction
    fun getCodeOffset() {
        return this.codeOffset
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    fun getFormat() {
        return FORMAT
    }
}
