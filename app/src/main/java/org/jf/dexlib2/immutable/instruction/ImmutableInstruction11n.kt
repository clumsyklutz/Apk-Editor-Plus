package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction11n
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction11n extends ImmutableInstruction implements Instruction11n {
    public static val FORMAT = Format.Format11n
    public final Int literal
    public final Int registerA

    constructor(Opcode opcode, Int i, Int i2) {
        super(opcode)
        this.registerA = Preconditions.checkNibbleRegister(i)
        this.literal = Preconditions.checkNibbleLiteral(i2)
    }

    fun of(Instruction11n instruction11n) {
        return instruction11n is ImmutableInstruction11n ? (ImmutableInstruction11n) instruction11n : ImmutableInstruction11n(instruction11n.getOpcode(), instruction11n.getRegisterA(), instruction11n.getNarrowLiteral())
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
