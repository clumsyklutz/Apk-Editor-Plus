package org.jf.dexlib2.builder.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderInstruction
import org.jf.dexlib2.iface.instruction.formats.Instruction21lh
import org.jf.dexlib2.util.Preconditions

class BuilderInstruction21lh extends BuilderInstruction implements Instruction21lh {
    public static val FORMAT = Format.Format21lh
    public final Long literal
    public final Int registerA

    constructor(Opcode opcode, Int i, Long j) {
        super(opcode)
        this.registerA = Preconditions.checkByteRegister(i)
        this.literal = Preconditions.checkLongHatLiteral(j)
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.HatLiteralInstruction
    fun getHatLiteral() {
        return (Short) (this.literal >>> 48)
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
