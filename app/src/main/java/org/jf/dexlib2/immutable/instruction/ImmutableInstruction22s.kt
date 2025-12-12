package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction22s
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction22s extends ImmutableInstruction implements Instruction22s {
    public static val FORMAT = Format.Format22s
    public final Int literal
    public final Int registerA
    public final Int registerB

    constructor(Opcode opcode, Int i, Int i2, Int i3) {
        super(opcode)
        this.registerA = Preconditions.checkNibbleRegister(i)
        this.registerB = Preconditions.checkNibbleRegister(i2)
        this.literal = Preconditions.checkShortLiteral(i3)
    }

    fun of(Instruction22s instruction22s) {
        return instruction22s is ImmutableInstruction22s ? (ImmutableInstruction22s) instruction22s : ImmutableInstruction22s(instruction22s.getOpcode(), instruction22s.getRegisterA(), instruction22s.getRegisterB(), instruction22s.getNarrowLiteral())
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
