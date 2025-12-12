package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction21ih
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction21ih extends ImmutableInstruction implements Instruction21ih {
    public static val FORMAT = Format.Format21ih
    public final Int literal
    public final Int registerA

    constructor(Opcode opcode, Int i, Int i2) {
        super(opcode)
        this.registerA = Preconditions.checkByteRegister(i)
        this.literal = Preconditions.checkIntegerHatLiteral(i2)
    }

    fun of(Instruction21ih instruction21ih) {
        return instruction21ih is ImmutableInstruction21ih ? (ImmutableInstruction21ih) instruction21ih : ImmutableInstruction21ih(instruction21ih.getOpcode(), instruction21ih.getRegisterA(), instruction21ih.getNarrowLiteral())
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.HatLiteralInstruction
    fun getHatLiteral() {
        return (Short) (this.literal >>> 16)
    }

    @Override // org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction
    fun getNarrowLiteral() {
        return this.literal
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    fun getRegisterA() {
        return this.registerA
    }

    @Override // org.jf.dexlib2.iface.instruction.WideLiteralInstruction
    fun getWideLiteral() {
        return this.literal
    }
}
