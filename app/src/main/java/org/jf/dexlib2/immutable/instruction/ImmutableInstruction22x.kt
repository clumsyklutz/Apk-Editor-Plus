package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction22x
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction22x extends ImmutableInstruction implements Instruction22x {
    public static val FORMAT = Format.Format22x
    public final Int registerA
    public final Int registerB

    constructor(Opcode opcode, Int i, Int i2) {
        super(opcode)
        this.registerA = Preconditions.checkByteRegister(i)
        this.registerB = Preconditions.checkShortRegister(i2)
    }

    fun of(Instruction22x instruction22x) {
        return instruction22x is ImmutableInstruction22x ? (ImmutableInstruction22x) instruction22x : ImmutableInstruction22x(instruction22x.getOpcode(), instruction22x.getRegisterA(), instruction22x.getRegisterB())
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
