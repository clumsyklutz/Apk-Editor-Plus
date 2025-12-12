package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction3rmi
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction3rmi extends ImmutableInstruction implements Instruction3rmi {
    public static val FORMAT = Format.Format3rmi
    public final Int inlineIndex
    public final Int registerCount
    public final Int startRegister

    constructor(Opcode opcode, Int i, Int i2, Int i3) {
        super(opcode)
        this.startRegister = Preconditions.checkShortRegister(i)
        this.registerCount = Preconditions.checkRegisterRangeCount(i2)
        this.inlineIndex = Preconditions.checkInlineIndex(i3)
    }

    fun of(Instruction3rmi instruction3rmi) {
        return instruction3rmi is ImmutableInstruction3rmi ? (ImmutableInstruction3rmi) instruction3rmi : ImmutableInstruction3rmi(instruction3rmi.getOpcode(), instruction3rmi.getStartRegister(), instruction3rmi.getRegisterCount(), instruction3rmi.getInlineIndex())
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.InlineIndexInstruction
    fun getInlineIndex() {
        return this.inlineIndex
    }

    @Override // org.jf.dexlib2.iface.instruction.VariableRegisterInstruction
    fun getRegisterCount() {
        return this.registerCount
    }

    @Override // org.jf.dexlib2.iface.instruction.RegisterRangeInstruction
    fun getStartRegister() {
        return this.startRegister
    }
}
