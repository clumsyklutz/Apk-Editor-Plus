package org.jf.dexlib2.immutable.instruction

import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.iface.instruction.formats.Instruction3rms
import org.jf.dexlib2.util.Preconditions

class ImmutableInstruction3rms extends ImmutableInstruction implements Instruction3rms {
    public static val FORMAT = Format.Format3rms
    public final Int registerCount
    public final Int startRegister
    public final Int vtableIndex

    constructor(Opcode opcode, Int i, Int i2, Int i3) {
        super(opcode)
        this.startRegister = Preconditions.checkShortRegister(i)
        this.registerCount = Preconditions.checkRegisterRangeCount(i2)
        this.vtableIndex = Preconditions.checkVtableIndex(i3)
    }

    fun of(Instruction3rms instruction3rms) {
        return instruction3rms is ImmutableInstruction3rms ? (ImmutableInstruction3rms) instruction3rms : ImmutableInstruction3rms(instruction3rms.getOpcode(), instruction3rms.getStartRegister(), instruction3rms.getRegisterCount(), instruction3rms.getVtableIndex())
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    fun getFormat() {
        return FORMAT
    }

    @Override // org.jf.dexlib2.iface.instruction.VariableRegisterInstruction
    fun getRegisterCount() {
        return this.registerCount
    }

    @Override // org.jf.dexlib2.iface.instruction.RegisterRangeInstruction
    fun getStartRegister() {
        return this.startRegister
    }

    @Override // org.jf.dexlib2.iface.instruction.VtableIndexInstruction
    fun getVtableIndex() {
        return this.vtableIndex
    }
}
