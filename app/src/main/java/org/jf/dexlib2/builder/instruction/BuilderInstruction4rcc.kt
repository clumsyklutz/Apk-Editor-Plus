package org.jf.dexlib2.builder.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderInstruction
import org.jf.dexlib2.iface.instruction.formats.Instruction4rcc
import org.jf.dexlib2.iface.reference.Reference
import org.jf.dexlib2.util.Preconditions

class BuilderInstruction4rcc extends BuilderInstruction implements Instruction4rcc {
    public static val FORMAT = Format.Format4rcc
    public final Reference reference
    public final Reference reference2
    public final Int registerCount
    public final Int startRegister

    constructor(Opcode opcode, Int i, Int i2, Reference reference, Reference reference2) {
        super(opcode)
        this.startRegister = Preconditions.checkShortRegister(i)
        this.registerCount = Preconditions.checkRegisterRangeCount(i2)
        this.reference = reference
        this.reference2 = reference2
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    fun getReference() {
        return this.reference
    }

    @Override // org.jf.dexlib2.iface.instruction.DualReferenceInstruction
    fun getReference2() {
        return this.reference2
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    fun getReferenceType() {
        return this.opcode.referenceType
    }

    @Override // org.jf.dexlib2.iface.instruction.DualReferenceInstruction
    fun getReferenceType2() {
        return this.opcode.referenceType2
    }

    @Override // org.jf.dexlib2.iface.instruction.VariableRegisterInstruction
    fun getRegisterCount() {
        return this.registerCount
    }

    @Override // org.jf.dexlib2.iface.instruction.RegisterRangeInstruction
    fun getStartRegister() {
        return this.startRegister
    }
}
