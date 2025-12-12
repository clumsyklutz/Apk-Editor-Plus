package org.jf.dexlib2.dexbacked.instruction

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.ReferenceType
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.reference.DexBackedReference
import org.jf.dexlib2.iface.instruction.formats.Instruction20bc
import org.jf.dexlib2.iface.reference.Reference

class DexBackedInstruction20bc extends DexBackedInstruction implements Instruction20bc {
    constructor(DexBackedDexFile dexBackedDexFile, Opcode opcode, Int i) {
        super(dexBackedDexFile, opcode, i)
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    fun getReference() {
        val ushort = this.dexFile.getDataBuffer().readUshort(this.instructionStart + 2)
        try {
            return DexBackedReference.makeReference(this.dexFile, getReferenceType(), ushort)
        } catch (ReferenceType.InvalidReferenceTypeException e) {
            return Reference(this) { // from class: org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction20bc.1
                @Override // org.jf.dexlib2.iface.reference.Reference
                fun validateReference() throws Reference.InvalidReferenceException {
                    throw new Reference.InvalidReferenceException(String.format("%d@%d", Integer.valueOf(e.getReferenceType()), Integer.valueOf(ushort)), e)
                }
            }
        }
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    fun getReferenceType() {
        Int ubyte = (this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 1) >>> 6) + 1
        ReferenceType.validateReferenceType(ubyte)
        return ubyte
    }

    @Override // org.jf.dexlib2.iface.instruction.VerificationErrorInstruction
    fun getVerificationError() {
        return this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 1) & 63
    }
}
