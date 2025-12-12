package org.jf.dexlib2.dexbacked.instruction

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.iface.instruction.formats.Instruction3rms

class DexBackedInstruction3rms extends DexBackedInstruction implements Instruction3rms {
    constructor(DexBackedDexFile dexBackedDexFile, Opcode opcode, Int i) {
        super(dexBackedDexFile, opcode, i)
    }

    @Override // org.jf.dexlib2.iface.instruction.VariableRegisterInstruction
    fun getRegisterCount() {
        return this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 1)
    }

    @Override // org.jf.dexlib2.iface.instruction.RegisterRangeInstruction
    fun getStartRegister() {
        return this.dexFile.getDataBuffer().readUshort(this.instructionStart + 4)
    }

    @Override // org.jf.dexlib2.iface.instruction.VtableIndexInstruction
    fun getVtableIndex() {
        return this.dexFile.getDataBuffer().readUshort(this.instructionStart + 2)
    }
}
