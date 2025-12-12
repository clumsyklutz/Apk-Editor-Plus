package org.jf.dexlib2.builder.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.BuilderInstruction
import org.jf.dexlib2.iface.instruction.formats.Instruction21c
import org.jf.dexlib2.iface.reference.Reference
import org.jf.dexlib2.util.Preconditions

class BuilderInstruction21c extends BuilderInstruction implements Instruction21c {
    public static val FORMAT = Format.Format21c
    public final Reference reference
    public final Int registerA

    constructor(Opcode opcode, Int i, Reference reference) {
        super(opcode)
        this.registerA = Preconditions.checkByteRegister(i)
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
        return this.opcode.referenceType
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    fun getRegisterA() {
        return this.registerA
    }
}
