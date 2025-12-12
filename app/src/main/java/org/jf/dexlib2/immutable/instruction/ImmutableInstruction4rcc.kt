package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction4rcc
import org.jf.dexlib2.iface.reference.Reference
import org.jf.dexlib2.immutable.reference.ImmutableReference
import org.jf.dexlib2.immutable.reference.ImmutableReferenceFactory
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction4rcc extends ImmutableInstruction implements Instruction4rcc {
    public static val FORMAT = Format.Format4rcc
    public final ImmutableReference reference
    public final ImmutableReference reference2
    public final Int registerCount
    public final Int startRegister

    constructor(Opcode opcode, Int i, Int i2, Reference reference, Reference reference2) {
        super(opcode)
        this.startRegister = Preconditions.checkShortRegister(i)
        this.registerCount = Preconditions.checkRegisterRangeCount(i2)
        this.reference = ImmutableReferenceFactory.of(reference)
        this.reference2 = ImmutableReferenceFactory.of(reference2)
    }

    fun of(Instruction4rcc instruction4rcc) {
        return instruction4rcc is ImmutableInstruction4rcc ? (ImmutableInstruction4rcc) instruction4rcc : ImmutableInstruction4rcc(instruction4rcc.getOpcode(), instruction4rcc.getStartRegister(), instruction4rcc.getRegisterCount(), instruction4rcc.getReference(), instruction4rcc.getReference2())
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
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
