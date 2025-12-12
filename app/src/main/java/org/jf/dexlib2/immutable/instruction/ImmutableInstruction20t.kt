package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction20t
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction20t extends ImmutableInstruction implements Instruction20t {
    public static val FORMAT = Format.Format20t
    public final Int codeOffset

    constructor(Opcode opcode, Int i) {
        super(opcode)
        this.codeOffset = Preconditions.checkShortCodeOffset(i)
    }

    fun of(Instruction20t instruction20t) {
        return instruction20t is ImmutableInstruction20t ? (ImmutableInstruction20t) instruction20t : ImmutableInstruction20t(instruction20t.getOpcode(), instruction20t.getCodeOffset())
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
