package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.UnknownInstruction

class ImmutableUnknownInstruction extends ImmutableInstruction implements UnknownInstruction {
    public static val FORMAT = Format.Format10x
    public final Int originalOpcode

    constructor(Int i) {
        super(Opcode.NOP)
        this.originalOpcode = i
    }

    fun of(UnknownInstruction unknownInstruction) {
        return unknownInstruction is ImmutableUnknownInstruction ? (ImmutableUnknownInstruction) unknownInstruction : ImmutableUnknownInstruction(unknownInstruction.getOriginalOpcode())
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.formats.UnknownInstruction
    fun getOriginalOpcode() {
        return this.originalOpcode
    }
}
