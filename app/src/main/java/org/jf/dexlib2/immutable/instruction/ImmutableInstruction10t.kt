package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction10t
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction10t extends ImmutableInstruction implements Instruction10t {
    public static val FORMAT = Format.Format10t
    public final Int codeOffset

    constructor(Opcode opcode, Int i) {
        super(opcode)
        this.codeOffset = Preconditions.checkByteCodeOffset(i)
    }

    fun of(Instruction10t instruction10t) {
        return instruction10t is ImmutableInstruction10t ? (ImmutableInstruction10t) instruction10t : ImmutableInstruction10t(instruction10t.getOpcode(), instruction10t.getCodeOffset())
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
