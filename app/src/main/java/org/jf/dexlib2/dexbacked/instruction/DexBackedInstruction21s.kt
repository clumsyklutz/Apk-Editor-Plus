package org.jf.dexlib2.dexbacked.instruction

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.iface.instruction.formats.Instruction21s

class DexBackedInstruction21s extends DexBackedInstruction implements Instruction21s {
    constructor(DexBackedDexFile dexBackedDexFile, Opcode opcode, Int i) {
        super(dexBackedDexFile, opcode, i)
    }

    @Override // org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction
    fun getNarrowLiteral() {
        return this.dexFile.getDataBuffer().readShort(this.instructionStart + 2)
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    fun getRegisterA() {
        return this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 1)
    }

    @Override // org.jf.dexlib2.iface.instruction.WideLiteralInstruction
    fun getWideLiteral() {
        return getNarrowLiteral()
    }
}
