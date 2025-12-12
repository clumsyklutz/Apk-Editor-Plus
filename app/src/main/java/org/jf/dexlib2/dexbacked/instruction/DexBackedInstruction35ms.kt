package org.jf.dexlib2.dexbacked.instruction

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.iface.instruction.formats.Instruction35ms
import org.jf.util.NibbleUtils

class DexBackedInstruction35ms extends DexBackedInstruction implements Instruction35ms {
    constructor(DexBackedDexFile dexBackedDexFile, Opcode opcode, Int i) {
        super(dexBackedDexFile, opcode, i)
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

    @Override // org.jf.dexlib2.iface.instruction.VtableIndexInstruction
    fun getVtableIndex() {
        return this.dexFile.getDataBuffer().readUshort(this.instructionStart + 2)
    }
}
