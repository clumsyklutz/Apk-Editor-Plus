package org.jf.dexlib2.dexbacked.instruction

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.reference.DexBackedReference
import org.jf.dexlib2.iface.instruction.formats.Instruction21c
import org.jf.dexlib2.iface.reference.Reference

class DexBackedInstruction21c extends DexBackedInstruction implements Instruction21c {
    constructor(DexBackedDexFile dexBackedDexFile, Opcode opcode, Int i) {
        super(dexBackedDexFile, opcode, i)
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    fun getReference() {
        DexBackedDexFile dexBackedDexFile = this.dexFile
        return DexBackedReference.makeReference(dexBackedDexFile, this.opcode.referenceType, dexBackedDexFile.getDataBuffer().readUshort(this.instructionStart + 2))
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    fun getReferenceType() {
        return this.opcode.referenceType
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    fun getRegisterA() {
        return this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 1)
    }
}
