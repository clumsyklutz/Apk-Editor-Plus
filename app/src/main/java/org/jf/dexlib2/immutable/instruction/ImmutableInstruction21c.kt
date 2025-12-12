package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction21c
import org.jf.dexlib2.iface.reference.Reference
import org.jf.dexlib2.immutable.reference.ImmutableReference
import org.jf.dexlib2.immutable.reference.ImmutableReferenceFactory
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction21c extends ImmutableInstruction implements Instruction21c {
    public static val FORMAT = Format.Format21c
    public final ImmutableReference reference
    public final Int registerA

    constructor(Opcode opcode, Int i, Reference reference) {
        super(opcode)
        this.registerA = Preconditions.checkByteRegister(i)
        this.reference = ImmutableReferenceFactory.of(opcode.referenceType, reference)
    }

    fun of(Instruction21c instruction21c) {
        return instruction21c is ImmutableInstruction21c ? (ImmutableInstruction21c) instruction21c : ImmutableInstruction21c(instruction21c.getOpcode(), instruction21c.getRegisterA(), instruction21c.getReference())
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    fun getReference() {
        return this.reference
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    fun getReferenceType() {
        return this.opcode.referenceType
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    fun getRegisterA() {
        return this.registerA
    }
}
