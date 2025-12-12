package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction51l
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction51l extends ImmutableInstruction implements Instruction51l {
    public static val FORMAT = Format.Format51l
    public final Long literal
    public final Int registerA

    constructor(Opcode opcode, Int i, Long j) {
        super(opcode)
        this.registerA = Preconditions.checkByteRegister(i)
        this.literal = j
    }

    fun of(Instruction51l instruction51l) {
        return instruction51l is ImmutableInstruction51l ? (ImmutableInstruction51l) instruction51l : ImmutableInstruction51l(instruction51l.getOpcode(), instruction51l.getRegisterA(), instruction51l.getWideLiteral())
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    fun getFormat() {
        return FORMAT
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
