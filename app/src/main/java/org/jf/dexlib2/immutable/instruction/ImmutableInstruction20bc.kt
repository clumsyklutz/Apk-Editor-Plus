package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.ReferenceType
import org.jf.dexlib2.iface.instruction.formats.Instruction20bc
import org.jf.dexlib2.iface.reference.Reference
import org.jf.dexlib2.immutable.reference.ImmutableReference
import org.jf.dexlib2.immutable.reference.ImmutableReferenceFactory
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction20bc extends ImmutableInstruction implements Instruction20bc {
    public static val FORMAT = Format.Format20bc
    public final ImmutableReference reference
    public final Int verificationError

    constructor(Opcode opcode, Int i, Reference reference) {
        super(opcode)
        this.verificationError = Preconditions.checkVerificationError(i)
        this.reference = ImmutableReferenceFactory.of(opcode.referenceType, reference)
    }

    fun of(Instruction20bc instruction20bc) {
        return instruction20bc is ImmutableInstruction20bc ? (ImmutableInstruction20bc) instruction20bc : ImmutableInstruction20bc(instruction20bc.getOpcode(), instruction20bc.getVerificationError(), instruction20bc.getReference())
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
        return ReferenceType.getReferenceType(this.reference)
    }

    @Override // org.jf.dexlib2.iface.instruction.VerificationErrorInstruction
    fun getVerificationError() {
        return this.verificationError
    }
}
