package org.jf.dexlib2.builder.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.ReferenceType
import org.jf.dexlib2.builder.BuilderInstruction
import org.jf.dexlib2.iface.instruction.formats.Instruction20bc
import org.jf.dexlib2.iface.reference.Reference
import org.jf.dexlib2.util.Preconditions

class BuilderInstruction20bc extends BuilderInstruction implements Instruction20bc {
    public static val FORMAT = Format.Format20bc
    public final Reference reference
    public final Int verificationError

    constructor(Opcode opcode, Int i, Reference reference) {
        super(opcode)
        this.verificationError = Preconditions.checkVerificationError(i)
        this.reference = reference
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
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
