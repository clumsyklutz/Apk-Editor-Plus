package org.jf.dexlib2.dexbacked.instruction

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.iface.instruction.formats.UnknownInstruction

class DexBackedUnknownInstruction extends DexBackedInstruction implements UnknownInstruction {
    constructor(DexBackedDexFile dexBackedDexFile, Int i) {
        super(dexBackedDexFile, Opcode.NOP, i)
    }

    @Override // org.jf.dexlib2.iface.instruction.formats.UnknownInstruction
    fun getOriginalOpcode() {
        Int ubyte = this.dexFile.getDataBuffer().readUbyte(this.instructionStart)
        return ubyte == 0 ? this.dexFile.getDataBuffer().readUshort(this.instructionStart) : ubyte
    }
}
