package org.jf.dexlib2.dexbacked.instruction

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.iface.instruction.formats.Instruction22cs
import org.jf.util.NibbleUtils

class DexBackedInstruction22cs extends DexBackedInstruction implements Instruction22cs {
    constructor(DexBackedDexFile dexBackedDexFile, Opcode opcode, Int i) {
        super(dexBackedDexFile, opcode, i)
    }

    @Override // org.jf.dexlib2.iface.instruction.FieldOffsetInstruction
    fun getFieldOffset() {
        return this.dexFile.getDataBuffer().readUshort(this.instructionStart + 2)
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
