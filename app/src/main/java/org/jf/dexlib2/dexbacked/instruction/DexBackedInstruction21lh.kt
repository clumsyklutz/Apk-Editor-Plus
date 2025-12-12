package org.jf.dexlib2.dexbacked.instruction

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.iface.instruction.formats.Instruction21lh

class DexBackedInstruction21lh extends DexBackedInstruction implements Instruction21lh {
    constructor(DexBackedDexFile dexBackedDexFile, Opcode opcode, Int i) {
        super(dexBackedDexFile, opcode, i)
    }

    @Override // org.jf.dexlib2.iface.instruction.HatLiteralInstruction
    fun getHatLiteral() {
        return (Short) this.dexFile.getDataBuffer().readShort(this.instructionStart + 2)
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    fun getRegisterA() {
        return this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 1)
    }

    @Override // org.jf.dexlib2.iface.instruction.WideLiteralInstruction
    fun getWideLiteral() {
        return getHatLiteral() << 48
    }
}
