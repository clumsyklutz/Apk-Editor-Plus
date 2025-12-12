package org.jf.dexlib2.dexbacked.instruction

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.iface.instruction.formats.Instruction11n
import org.jf.util.NibbleUtils

class DexBackedInstruction11n extends DexBackedInstruction implements Instruction11n {
    constructor(DexBackedDexFile dexBackedDexFile, Opcode opcode, Int i) {
        super(dexBackedDexFile, opcode, i)
    }

    @Override // org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction
    fun getNarrowLiteral() {
        return NibbleUtils.extractHighSignedNibble(this.dexFile.getDataBuffer().readByte(this.instructionStart + 1))
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    fun getRegisterA() {
        return NibbleUtils.extractLowUnsignedNibble(this.dexFile.getDataBuffer().readByte(this.instructionStart + 1))
    }

    @Override // org.jf.dexlib2.iface.instruction.WideLiteralInstruction
    fun getWideLiteral() {
        return getNarrowLiteral()
    }
}
