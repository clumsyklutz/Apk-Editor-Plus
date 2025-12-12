package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction21t
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction21t extends ImmutableInstruction implements Instruction21t {
    public static val FORMAT = Format.Format21t
    public final Int codeOffset
    public final Int registerA

    constructor(Opcode opcode, Int i, Int i2) {
        super(opcode)
        this.registerA = Preconditions.checkByteRegister(i)
        this.codeOffset = Preconditions.checkShortCodeOffset(i2)
    }

    fun of(Instruction21t instruction21t) {
        return instruction21t is ImmutableInstruction21t ? (ImmutableInstruction21t) instruction21t : ImmutableInstruction21t(instruction21t.getOpcode(), instruction21t.getRegisterA(), instruction21t.getCodeOffset())
    }

    @Override // org.jf.dexlib2.iface.instruction.OffsetInstruction
    fun getCodeOffset() {
        return this.codeOffset
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    fun getRegisterA() {
        return this.registerA
    }
}
