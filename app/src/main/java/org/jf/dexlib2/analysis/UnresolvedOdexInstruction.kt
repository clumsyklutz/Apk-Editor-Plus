package org.jf.dexlib2.analysis

import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.Instruction

class UnresolvedOdexInstruction implements Instruction {
    public final Int objectRegisterNum
    public final Instruction originalInstruction

    constructor(Instruction instruction, Int i) {
        this.originalInstruction = instruction
        this.objectRegisterNum = i
    }

    @Override // org.jf.dexlib2.iface.instruction.Instruction
    fun getCodeUnits() {
        return this.originalInstruction.getCodeUnits()
    }

    @Override // org.jf.dexlib2.iface.instruction.Instruction
    fun getOpcode() {
        return this.originalInstruction.getOpcode()
    }
}
