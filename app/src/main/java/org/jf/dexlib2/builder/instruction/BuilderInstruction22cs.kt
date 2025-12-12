package org.jf.dexlib2.builder.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderInstruction
import org.jf.dexlib2.iface.instruction.formats.Instruction22cs
import org.jf.dexlib2.util.Preconditions

class BuilderInstruction22cs extends BuilderInstruction implements Instruction22cs {
    public static val FORMAT = Format.Format22cs
    public final Int fieldOffset
    public final Int registerA
    public final Int registerB

    constructor(Opcode opcode, Int i, Int i2, Int i3) {
        super(opcode)
        this.registerA = Preconditions.checkNibbleRegister(i)
        this.registerB = Preconditions.checkNibbleRegister(i2)
        this.fieldOffset = i3
    }

    @Override // org.jf.dexlib2.iface.instruction.FieldOffsetInstruction
    fun getFieldOffset() {
        return this.fieldOffset
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
