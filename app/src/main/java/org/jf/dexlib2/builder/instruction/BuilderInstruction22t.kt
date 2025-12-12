package org.jf.dexlib2.builder.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderOffsetInstruction
import org.jf.dexlib2.builder.Label
import org.jf.dexlib2.iface.instruction.formats.Instruction22t
import org.jf.dexlib2.util.Preconditions

class BuilderInstruction22t extends BuilderOffsetInstruction implements Instruction22t {
    public static val FORMAT = Format.Format22t
    public final Int registerA
    public final Int registerB

    constructor(Opcode opcode, Int i, Int i2, Label label) {
        super(opcode, label)
        this.registerA = Preconditions.checkNibbleRegister(i)
        this.registerB = Preconditions.checkNibbleRegister(i2)
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    fun getRegisterA() {
        return this.registerA
    }

    @Override // org.jf.dexlib2.iface.instruction.TwoRegisterInstruction
    fun getRegisterB() {
        return this.registerB
    }
}
