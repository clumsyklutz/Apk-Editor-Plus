package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction23x
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction23x extends ImmutableInstruction implements Instruction23x {
    public static val FORMAT = Format.Format23x
    public final Int registerA
    public final Int registerB
    public final Int registerC

    constructor(Opcode opcode, Int i, Int i2, Int i3) {
        super(opcode)
        this.registerA = Preconditions.checkByteRegister(i)
        this.registerB = Preconditions.checkByteRegister(i2)
        this.registerC = Preconditions.checkByteRegister(i3)
    }

    fun of(Instruction23x instruction23x) {
        return instruction23x is ImmutableInstruction23x ? (ImmutableInstruction23x) instruction23x : ImmutableInstruction23x(instruction23x.getOpcode(), instruction23x.getRegisterA(), instruction23x.getRegisterB(), instruction23x.getRegisterC())
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

    @Override // org.jf.dexlib2.iface.instruction.ThreeRegisterInstruction
    fun getRegisterC() {
        return this.registerC
    }
}
