package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction22t
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction22t extends ImmutableInstruction implements Instruction22t {
    public static val FORMAT = Format.Format22t
    public final Int codeOffset
    public final Int registerA
    public final Int registerB

    constructor(Opcode opcode, Int i, Int i2, Int i3) {
        super(opcode)
        this.registerA = Preconditions.checkNibbleRegister(i)
        this.registerB = Preconditions.checkNibbleRegister(i2)
        this.codeOffset = Preconditions.checkShortCodeOffset(i3)
    }

    fun of(Instruction22t instruction22t) {
        return instruction22t is ImmutableInstruction22t ? (ImmutableInstruction22t) instruction22t : ImmutableInstruction22t(instruction22t.getOpcode(), instruction22t.getRegisterA(), instruction22t.getRegisterB(), instruction22t.getCodeOffset())
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

    @Override // org.jf.dexlib2.iface.instruction.TwoRegisterInstruction
    fun getRegisterB() {
        return this.registerB
    }
}
