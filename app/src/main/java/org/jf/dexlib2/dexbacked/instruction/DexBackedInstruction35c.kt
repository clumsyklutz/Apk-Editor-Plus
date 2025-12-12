package org.jf.dexlib2.dexbacked.instruction

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.reference.DexBackedReference
import org.jf.dexlib2.iface.instruction.formats.Instruction35c
import org.jf.dexlib2.iface.reference.Reference
import org.jf.util.NibbleUtils

class DexBackedInstruction35c extends DexBackedInstruction implements Instruction35c {
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

    @Override // org.jf.dexlib2.iface.instruction.FiveRegisterInstruction
    fun getRegisterC() {
        return NibbleUtils.extractLowUnsignedNibble(this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 4))
    }

    @Override // org.jf.dexlib2.iface.instruction.VariableRegisterInstruction
    fun getRegisterCount() {
        return NibbleUtils.extractHighUnsignedNibble(this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 1))
    }

    @Override // org.jf.dexlib2.iface.instruction.FiveRegisterInstruction
    fun getRegisterD() {
        return NibbleUtils.extractHighUnsignedNibble(this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 4))
    }

    @Override // org.jf.dexlib2.iface.instruction.FiveRegisterInstruction
    fun getRegisterE() {
        return NibbleUtils.extractLowUnsignedNibble(this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 5))
    }

    @Override // org.jf.dexlib2.iface.instruction.FiveRegisterInstruction
    fun getRegisterF() {
        return NibbleUtils.extractHighUnsignedNibble(this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 5))
    }

    @Override // org.jf.dexlib2.iface.instruction.FiveRegisterInstruction
    fun getRegisterG() {
        return NibbleUtils.extractLowUnsignedNibble(this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 1))
    }
}
