package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction11x
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction11x extends ImmutableInstruction implements Instruction11x {
    public static val FORMAT = Format.Format11x
    public final Int registerA

    constructor(Opcode opcode, Int i) {
        super(opcode)
        this.registerA = Preconditions.checkByteRegister(i)
    }

    fun of(Instruction11x instruction11x) {
        return instruction11x is ImmutableInstruction11x ? (ImmutableInstruction11x) instruction11x : ImmutableInstruction11x(instruction11x.getOpcode(), instruction11x.getRegisterA())
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
