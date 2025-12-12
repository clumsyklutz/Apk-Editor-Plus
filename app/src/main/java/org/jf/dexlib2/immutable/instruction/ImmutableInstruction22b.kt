package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction22b
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction22b extends ImmutableInstruction implements Instruction22b {
    public static val FORMAT = Format.Format22b
    public final Int literal
    public final Int registerA
    public final Int registerB

    constructor(Opcode opcode, Int i, Int i2, Int i3) {
        super(opcode)
        this.registerA = Preconditions.checkByteRegister(i)
        this.registerB = Preconditions.checkByteRegister(i2)
        this.literal = Preconditions.checkByteLiteral(i3)
    }

    fun of(Instruction22b instruction22b) {
        return instruction22b is ImmutableInstruction22b ? (ImmutableInstruction22b) instruction22b : ImmutableInstruction22b(instruction22b.getOpcode(), instruction22b.getRegisterA(), instruction22b.getRegisterB(), instruction22b.getNarrowLiteral())
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction
    fun getNarrowLiteral() {
        return this.literal
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    fun getRegisterA() {
        return this.registerA
    }

    @Override // org.jf.dexlib2.iface.instruction.TwoRegisterInstruction
    fun getRegisterB() {
        return this.registerB
    }

    @Override // org.jf.dexlib2.iface.instruction.WideLiteralInstruction
    fun getWideLiteral() {
        return this.literal
    }
}
