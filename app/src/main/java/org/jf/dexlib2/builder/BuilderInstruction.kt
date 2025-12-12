package org.jf.dexlib2.builder

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.dexlib2.util.Preconditions

abstract class BuilderInstruction implements Instruction {
    public MethodLocation location
    public final Opcode opcode

    constructor(Opcode opcode) {
        Preconditions.checkFormat(opcode, getFormat())
        this.opcode = opcode
    }

    @Override // org.jf.dexlib2.iface.instruction.Instruction
    fun getCodeUnits() {
        return getFormat().size / 2
    }

    public abstract Format getFormat()

    fun getLocation() {
        MethodLocation methodLocation = this.location
        if (methodLocation != null) {
            return methodLocation
        }
        throw IllegalStateException("Cannot get the location of an instruction that hasn't been added to a method.")
    }

    @Override // org.jf.dexlib2.iface.instruction.Instruction
    fun getOpcode() {
        return this.opcode
    }
}
