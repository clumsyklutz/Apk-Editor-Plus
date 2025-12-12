package org.jf.dexlib2.dexbacked.instruction

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.reference.DexBackedReference
import org.jf.dexlib2.iface.instruction.formats.Instruction22c
import org.jf.dexlib2.iface.reference.Reference
import org.jf.util.NibbleUtils

class DexBackedInstruction22c extends DexBackedInstruction implements Instruction22c {
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
        return NibbleUtils.extractLowUnsignedNibble(this.dexFile.getDataBuffer().readByte(this.instructionStart + 1))
    }

    @Override // org.jf.dexlib2.iface.instruction.TwoRegisterInstruction
    fun getRegisterB() {
        return NibbleUtils.extractHighUnsignedNibble(this.dexFile.getDataBuffer().readByte(this.instructionStart + 1))
    }
}
