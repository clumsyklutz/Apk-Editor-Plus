package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction31i
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction31i extends ImmutableInstruction implements Instruction31i {
    public static val FORMAT = Format.Format31i
    public final Int literal
    public final Int registerA

    constructor(Opcode opcode, Int i, Int i2) {
        super(opcode)
        this.registerA = Preconditions.checkByteRegister(i)
        this.literal = i2
    }

    fun of(Instruction31i instruction31i) {
        return instruction31i is ImmutableInstruction31i ? (ImmutableInstruction31i) instruction31i : ImmutableInstruction31i(instruction31i.getOpcode(), instruction31i.getRegisterA(), instruction31i.getNarrowLiteral())
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

    @Override // org.jf.dexlib2.iface.instruction.WideLiteralInstruction
    fun getWideLiteral() {
        return this.literal
    }
}
