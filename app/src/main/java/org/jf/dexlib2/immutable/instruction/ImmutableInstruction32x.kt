package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction32x
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction32x extends ImmutableInstruction implements Instruction32x {
    public static val FORMAT = Format.Format32x
    public final Int registerA
    public final Int registerB

    constructor(Opcode opcode, Int i, Int i2) {
        super(opcode)
        this.registerA = Preconditions.checkShortRegister(i)
        this.registerB = Preconditions.checkShortRegister(i2)
    }

    fun of(Instruction32x instruction32x) {
        return instruction32x is ImmutableInstruction32x ? (ImmutableInstruction32x) instruction32x : ImmutableInstruction32x(instruction32x.getOpcode(), instruction32x.getRegisterA(), instruction32x.getRegisterB())
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
