package org.jf.dexlib2.builder.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderInstruction
import org.jf.dexlib2.iface.instruction.formats.Instruction51l
import org.jf.dexlib2.util.Preconditions

class BuilderInstruction51l extends BuilderInstruction implements Instruction51l {
    public static val FORMAT = Format.Format51l
    public final Long literal
    public final Int registerA

    constructor(Opcode opcode, Int i, Long j) {
        super(opcode)
        this.registerA = Preconditions.checkByteRegister(i)
        this.literal = j
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
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
