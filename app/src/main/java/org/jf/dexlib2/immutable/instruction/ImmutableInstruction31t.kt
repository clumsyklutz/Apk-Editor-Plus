package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction31t
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction31t extends ImmutableInstruction implements Instruction31t {
    public static val FORMAT = Format.Format31t
    public final Int codeOffset
    public final Int registerA

    constructor(Opcode opcode, Int i, Int i2) {
        super(opcode)
        this.registerA = Preconditions.checkByteRegister(i)
        this.codeOffset = i2
    }

    fun of(Instruction31t instruction31t) {
        return instruction31t is ImmutableInstruction31t ? (ImmutableInstruction31t) instruction31t : ImmutableInstruction31t(instruction31t.getOpcode(), instruction31t.getRegisterA(), instruction31t.getCodeOffset())
    }

    @Override // org.jf.dexlib2.iface.instruction.OffsetInstruction
    fun getCodeOffset() {
        return this.codeOffset
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    fun getRegisterA() {
        return this.registerA
    }
}
