package org.jf.dexlib2.dexbacked.instruction

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.iface.instruction.formats.Instruction22s
import org.jf.util.NibbleUtils

class DexBackedInstruction22s extends DexBackedInstruction implements Instruction22s {
    constructor(DexBackedDexFile dexBackedDexFile, Opcode opcode, Int i) {
        super(dexBackedDexFile, opcode, i)
    }

    @Override // org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction
    fun getNarrowLiteral() {
        return this.dexFile.getDataBuffer().readShort(this.instructionStart + 2)
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    fun getRegisterA() {
        return NibbleUtils.extractLowUnsignedNibble(this.dexFile.getDataBuffer().readByte(this.instructionStart + 1))
    }

    @Override // org.jf.dexlib2.iface.instruction.TwoRegisterInstruction
    fun getRegisterB() {
        return NibbleUtils.extractHighUnsignedNibble(this.dexFile.getDataBuffer().readByte(this.instructionStart + 1))
    }

    @Override // org.jf.dexlib2.iface.instruction.WideLiteralInstruction
    fun getWideLiteral() {
        return getNarrowLiteral()
    }
}
