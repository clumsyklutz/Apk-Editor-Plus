package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction12x
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction12x extends ImmutableInstruction implements Instruction12x {
    public static val FORMAT = Format.Format12x
    public final Int registerA
    public final Int registerB

    constructor(Opcode opcode, Int i, Int i2) {
        super(opcode)
        this.registerA = Preconditions.checkNibbleRegister(i)
        this.registerB = Preconditions.checkNibbleRegister(i2)
    }

    fun of(Instruction12x instruction12x) {
        return instruction12x is ImmutableInstruction12x ? (ImmutableInstruction12x) instruction12x : ImmutableInstruction12x(instruction12x.getOpcode(), instruction12x.getRegisterA(), instruction12x.getRegisterB())
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
