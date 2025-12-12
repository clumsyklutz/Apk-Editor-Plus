package org.jf.dexlib2.dexbacked.instruction

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.iface.instruction.formats.Instruction23x

class DexBackedInstruction23x extends DexBackedInstruction implements Instruction23x {
    constructor(DexBackedDexFile dexBackedDexFile, Opcode opcode, Int i) {
        super(dexBackedDexFile, opcode, i)
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    fun getRegisterA() {
        return this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 1)
    }

    @Override // org.jf.dexlib2.iface.instruction.TwoRegisterInstruction
    fun getRegisterB() {
        return this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 2)
    }

    @Override // org.jf.dexlib2.iface.instruction.ThreeRegisterInstruction
    fun getRegisterC() {
        return this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 3)
    }
}
